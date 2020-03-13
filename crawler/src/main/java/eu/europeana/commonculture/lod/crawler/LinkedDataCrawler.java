package eu.europeana.commonculture.lod.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import eu.europeana.commonculture.lod.crawler.http.HttpRequestService;
import eu.europeana.commonculture.lod.crawler.rdf.CallbackTriplesQuadsBufferedWriter;
import eu.europeana.commonculture.lod.crawler.rdf.RdfReg;
import eu.europeana.commonculture.lod.crawler.rdf.RdfUtil;

public class LinkedDataCrawler {
	String datasetUri;
	int maxDepth = 0;
	int maxSeedsPerSet = -1;
	boolean datasetDescriptionOnly=false;
	
	public LinkedDataCrawler(String uri) {
		super();
		this.datasetUri = uri;
	}
	
	public LinkedDataCrawler(String uri, boolean datasetDescriptionOnly) {
		this(uri);
		this.datasetDescriptionOnly = datasetDescriptionOnly;
	}

	public LinkedDataCrawler(String uri, int maxDepth, int maxSeedsPerSet) {
		super();
		this.datasetUri = uri;
		this.maxDepth = maxDepth;
		this.maxSeedsPerSet = maxSeedsPerSet;
	}

	public int crawl(File outFile) throws IOException, AccessException, InterruptedException {
		if(!outFile.getParentFile().exists())
			outFile.getParentFile().mkdirs();
		else if(outFile.getParentFile().exists() && outFile.isDirectory())
			throw new IOException("Invalid parameter: Crawling 'output_file' parameter cannot be a directory: "+outFile.getPath());
		DatasetDescription dataset = new DatasetDescription(datasetUri);

		// configure crawler with the URIs from jena model
		int totalSeeds = 0;
		Crawler crawler = new Crawler(5);
//			Crawler crawler = new Crawler(CrawlerConstants.DEFAULT_NB_THREADS);
		
		Frontier frontier = new BasicFrontier();
		try {
			//This may be need changing in the future, if we decide to go deeper than depth 1
			frontier.add(new URI(datasetUri));
		} catch (URISyntaxException e) {
			System.out.println("Warning: Resource not crawled due to an invalid URI: " + datasetUri);
		}
		if(!datasetDescriptionOnly) {
			for (String uri : dataset.listRootResources()) {
				try {
					frontier.add(new URI(uri));
					totalSeeds++;
					if (maxSeedsPerSet > 0 && totalSeeds >= maxSeedsPerSet)
						break;
				} catch (URISyntaxException e) {
					System.out.println("Warning: Resource not crawled due to an invalid URI: " + uri);
				}
			}
			if (totalSeeds==0) {
				crawler.close();
				return totalSeeds;
			}
		}
		
		ContentHandler contentHandler = new ContentHandlers(new ContentHandlerRdfXml(), new ContentHandlerNx());
		crawler.setContentHandler(contentHandler);
		FileWriter out = new FileWriter(outFile);
		BufferedWriter out2 = new BufferedWriter(out);
		CallbackTriplesQuadsBufferedWriter writer = new CallbackTriplesQuadsBufferedWriter(out2, false, Lang.NTRIPLES);
		Sink sink = new SinkCallback(writer, false);
		crawler.setOutputCallback(sink);
		LinkFilterDomain linkFilter = new LinkFilterDomain(frontier);
//			linkFilter.addHost("http://data.bibliotheken.nl/");  
		crawler.setLinkFilter(linkFilter);

		// crawl and dump to n-triples
		int maxURIs = totalSeeds * 20;
		crawler.evaluateBreadthFirst(frontier, new HashSetSeen(), new HashTableRedirects(), maxDepth, maxURIs, -1, -1,
				false);
		out2.flush();
		out.close();
		crawler.close();
		return totalSeeds;
	}
}
