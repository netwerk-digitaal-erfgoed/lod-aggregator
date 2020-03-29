package eu.europeana.commonculture.lod.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

public class LinkedDataCrawler {
	int maxDepth = 0;
	int maxSeedsPerSet = -1;
	Iterable<String> seeds;
	
	public LinkedDataCrawler(Iterable<String> seeds, int maxDepth, int maxSeedsPerSet) {
		super();
		this.seeds = seeds;
		this.maxDepth = maxDepth;
		this.maxSeedsPerSet = maxSeedsPerSet;
	}

	public LinkedDataCrawler(int maxDepth, int maxSeedsPerSet) {
		super();
		this.maxDepth = maxDepth;
		this.maxSeedsPerSet = maxSeedsPerSet;
		this.seeds = new ArrayList<String>();
	}

	
	
	public int crawl(File outFile) throws IOException, AccessException, InterruptedException {
		if(!outFile.getParentFile().exists())
			outFile.getParentFile().mkdirs();
		else if(outFile.getParentFile().exists() && outFile.isDirectory())
			throw new IOException("Invalid parameter: Crawling 'output_file' parameter cannot be a directory: "+outFile.getPath());

		// configure crawler with the URIs from jena model
		int totalSeeds = 0;
		Crawler crawler = new Crawler(5);
//			Crawler crawler = new Crawler(CrawlerConstants.DEFAULT_NB_THREADS);
		Frontier frontier = new BasicFrontier();
		for (String uri : seeds) {
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

	public void addSeed(String uri) {
		try {
			((Collection<String>) seeds).add(uri);
		} catch (ClassCastException e) {
			throw new IllegalStateException("The iterable of seeds is not a Collection. added cannot be used");
		}
	}
}
