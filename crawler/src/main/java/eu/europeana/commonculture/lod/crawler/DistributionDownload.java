package eu.europeana.commonculture.lod.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.RDFParserBuilder;
import org.apache.jena.riot.RiotException;
import org.apache.jena.riot.system.StreamRDF;
import eu.europeana.commonculture.lod.crawler.http.HttpRequest;

public class DistributionDownload {
	/*
	 * returns an error message or null if download is successfull
	 */
	public static String streamRdf(String url, String defaultContentType, StreamRDF handler) throws IOException, InterruptedException {
		HttpRequest request = new HttpRequest(url).fetchStream();
		
		try {
			InputStream contentStream =  request.getResponse().getEntity().getContent();
			String contentType = request.getResponseContentType();
			if (contentType == null && url.toLowerCase().endsWith(".gz"))
				contentType = "application/x-gzip";
			if (contentType == null && url.toLowerCase().endsWith(".zip"))
				contentType = "application/zip";
			if (contentType == null && url.toLowerCase().endsWith(".bz2"))
				contentType = "application/x-bzip2";
			if (StringUtils.equals(contentType, "application/x-gzip")) {
				contentStream = new GZIPInputStream(contentStream);
				String uriWithoutFileExtension = null;
				if (url.toLowerCase().endsWith(".gz"))
					uriWithoutFileExtension = url.substring(0, url.length()-3);
				if (uriWithoutFileExtension == null)
					return "undeterminable mime type: " + url;
				Lang filenameToLang = RDFLanguages.filenameToLang(uriWithoutFileExtension);
	
				RDFParserBuilder builder = RDFParser.create().checking(false).forceLang(filenameToLang);
				
				builder.source(contentStream).parse(handler);
			} else if (StringUtils.equals(contentType, "application/x-bzip2")) {
			    BufferedReader br;
				BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(contentStream);
				br = new BufferedReader(new InputStreamReader(bzIn,  "UTF-8"));
	
					String uriWithoutFileExtension = null;
				if (url.toLowerCase().endsWith(".bz2"))
					uriWithoutFileExtension = url.substring(0, url.length()-4);
				if (uriWithoutFileExtension == null)
					return "undeterminable mime type: " + url;
				Lang filenameToLang = RDFLanguages.filenameToLang(uriWithoutFileExtension);
				
				RDFParserBuilder builder = RDFParser.create().checking(false).forceLang(filenameToLang);
				for(String line=br.readLine() ; line!=null; line=br.readLine()) {
					try {
						builder.source(new StringReader(line)).parse(handler);
					} catch (RiotException e) {
						System.err.println("... Ignoring triple due to RIOT exception");
						e.printStackTrace();
					}
				}
				br.close();
			} else if (StringUtils.equals(contentType, "application/zip") || StringUtils.equals(contentType, "application/x-zip-compressed")) {
				final ZipInputStream zip = new ZipInputStream(contentStream);
				ZipEntry entry = zip.getNextEntry();
				while (entry != null) {
					Lang filenameToLang = RDFLanguages.filenameToLang(entry.getName());
					RDFParserBuilder builder = RDFParser.create().checking(false).forceLang(filenameToLang);
					
					//the zip input stream is wrappped to prevent the parser from closing it.
					try {
						builder.source(new InputStream() { 
							@Override
							public int read() throws IOException {
								return zip.read();
							}
							@Override
							public int read(byte[] b) throws IOException {
								return zip.read(b);
							}
							@Override
							public int read(byte[] b, int off, int len) throws IOException {
								return zip.read(b, off, len);
							}
							@Override
							public int available() throws IOException {
								return zip.available();
							}
							@Override
							public void close() throws IOException {
//							super.close(); 
							}
						}).parse(handler);
					} catch (RiotException e) {
						//Invalid RDF
						//ignore error and proceed to next file
					}
					zip.closeEntry();
					entry = zip.getNextEntry();
				}
			} else {
				if(contentType==null)
					if(defaultContentType!=null)
						contentType=defaultContentType;
				Lang rdfLang = null;
				if(contentType==null)
					rdfLang = RDFLanguages.filenameToLang(url);
				else
					rdfLang = RDFLanguages.contentTypeToLang(contentType);
				if(rdfLang!=null) {
					RDFParserBuilder builder = RDFParser.create().checking(false).forceLang(rdfLang);

					builder.source(contentStream).parse(handler);
				} else {
					return "Could not determine content-type";
				}
			}
			return null;
		} finally {
			request.getResponse().close();
		}
	}
}
