package eu.europeana.commonculture.lod.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.semanticweb.yars.util.CallbackNxBufferedWriter;

import com.ontologycentral.ldspider.Crawler;
import com.ontologycentral.ldspider.CrawlerConstants;
import com.ontologycentral.ldspider.frontier.BasicFrontier;
import com.ontologycentral.ldspider.frontier.Frontier;
import com.ontologycentral.ldspider.hooks.content.ContentHandler;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerNx;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerRdfXml;
import com.ontologycentral.ldspider.hooks.content.ContentHandlers;
import com.ontologycentral.ldspider.hooks.links.LinkFilter;
import com.ontologycentral.ldspider.hooks.links.LinkFilterDomain;
import com.ontologycentral.ldspider.hooks.sink.Sink;
import com.ontologycentral.ldspider.hooks.sink.SinkCallback;
import com.ontologycentral.ldspider.queue.HashTableRedirects;
import com.ontologycentral.ldspider.seen.HashSetSeen;
import com.ontologycentral.ldspider.seen.Seen;

import eu.europeana.commonculture.lod.crawler.http.AccessException;
import eu.europeana.commonculture.lod.crawler.http.HttpRequestService;
import eu.europeana.commonculture.lod.crawler.rdf.CallbackNtriplesBufferedWriterJena;
import eu.europeana.commonculture.lod.crawler.rdf.CallbackTriplesQuadsBufferedWriter;
import eu.europeana.commonculture.lod.crawler.rdf.RdfReg;
import eu.europeana.commonculture.lod.crawler.rdf.RdfUtil;

public class Test {
	public static void main(String[] args) {
			String result="";
			String dsUri = "http://data.bibliotheken.nl/id/dataset/rise-centsprenten";
//			String dsUri = "http://data.bibliotheken.nl/id/dataset/rise-childrensbooks";
			String logFilePath = "target/log-crawler.txt";
			String outFile = "target/crawl-test.nt";
//	    	LinkedDataCrawler crawler=new LinkedDataCrawler(dsUri, 0, -1); 
	    	LinkedDataCrawler crawler=new LinkedDataCrawler(dsUri, true); 
			try {
				int seeds=crawler.crawl(new File(outFile));
				if(seeds==0)
					result="FAILURE\nNo URI seeds found";
				else
					result="SUCCESS";
			} catch (IOException | AccessException | InterruptedException e) {
				result="FAILURE\nstackTrace:\n"+ExceptionUtils.getStackTrace(e);
			}
			System.out.println(result);
	}
}
