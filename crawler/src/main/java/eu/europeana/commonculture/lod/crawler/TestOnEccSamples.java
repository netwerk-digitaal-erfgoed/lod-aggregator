package eu.europeana.commonculture.lod.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;

import com.ontologycentral.ldspider.Crawler;
import com.ontologycentral.ldspider.frontier.BasicFrontier;
import com.ontologycentral.ldspider.frontier.Frontier;
import com.ontologycentral.ldspider.hooks.content.ContentHandler;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerNx;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerRdfXml;
import com.ontologycentral.ldspider.hooks.content.ContentHandlers;
import com.ontologycentral.ldspider.hooks.links.LinkFilterDomain;
import com.ontologycentral.ldspider.hooks.sink.Sink;
import com.ontologycentral.ldspider.hooks.sink.SinkCallback;
import com.ontologycentral.ldspider.queue.HashTableRedirects;
import com.ontologycentral.ldspider.seen.HashSetSeen;

import eu.europeana.commonculture.lod.crawler.http.AccessException;
import eu.europeana.commonculture.lod.crawler.rdf.CallbackTriplesQuadsBufferedWriter;
import eu.europeana.commonculture.lod.crawler.rdf.RdfReg;
import eu.europeana.commonculture.lod.crawler.rdf.RdfUtil;

public class TestOnEccSamples {
	public static void main(String[] args) {
		try {
			final int maxSeedsPerSet=-1;
			
			File srcDataFolder=new File("src/data");
			
			for(File urisListFile : srcDataFolder.listFiles()) {
				if(!urisListFile.getName().endsWith(".txt")) continue;
				int totalSeeds=0;
				List<String> uris = FileUtils.readLines(urisListFile, "UTF-8");
				Frontier frontier=new BasicFrontier();
				for(String uri: uris) {
					try {
						frontier.add(new URI(uri.trim()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					totalSeeds++;
					if(maxSeedsPerSet>0 && totalSeeds>=maxSeedsPerSet) break;
				}
				Crawler crawler = new Crawler(5);
				ContentHandler contentHandler = new ContentHandlers(new ContentHandlerRdfXml(), new ContentHandlerNx());
				crawler.setContentHandler(contentHandler);
				FileWriter out=new FileWriter(new File("src/data/"+urisListFile.getName().substring(0,urisListFile.getName().lastIndexOf('.'))+".nt"));
				BufferedWriter out2 = new BufferedWriter(out);
				CallbackTriplesQuadsBufferedWriter writer=new CallbackTriplesQuadsBufferedWriter(out2, false, Lang.NTRIPLES);
				Sink sink = new SinkCallback(writer, false);
				crawler.setOutputCallback(sink);		
				LinkFilterDomain linkFilter = new LinkFilterDomain(frontier);  
//				linkFilter.addHost("http://data.bibliotheken.nl/");  
				crawler.setLinkFilter(linkFilter);

				int depth = 0;
				int maxURIs = totalSeeds+depth*totalSeeds*20;
				
				crawler.evaluateBreadthFirst(frontier, new HashSetSeen(), new HashTableRedirects(), depth, maxURIs, -1, -1, false);
				out2.flush();
				out.close();
				crawler.close();				
			}
			
			String uri = "http://data.bibliotheken.nl/id/dataset/rise-centsprenten";
			Model datasetDescModel = RdfUtil.readRdfFromUri(uri);
			Resource  datasetDescResource = datasetDescModel.getResource(uri);
			
			//		configure crawler with the URIs from jena model
//			Crawler crawler = new Crawler(CrawlerConstants.DEFAULT_NB_THREADS);
			Frontier frontier=new BasicFrontier();
			StmtIterator voidRootResources = datasetDescResource.listProperties(RdfReg.VOID_ROOT_RESOURCE);
			if (voidRootResources!=null && voidRootResources.hasNext()) {
				for( ; voidRootResources.hasNext(); ) {
					Statement st=voidRootResources.next();
					frontier.add(new URI(st.getObject().asNode().getURI().toString()));
				}
			} else { //try a Distribution of the dataset
				throw new RuntimeException("TODO");
			}
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
