package eu.europeana.commonculture.lod.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFBase;
import org.apache.jena.riot.system.StreamRDFWriter;

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
import eu.europeana.commonculture.lod.crawler.rdf.SparqlClient;
import eu.europeana.commonculture.lod.crawler.rdf.SparqlClient.Handler;
import eu.europeana.commonculture.lod.datasetmetadata.DatasetDescription;
import eu.europeana.commonculture.lod.datasetmetadata.Distribution;
import eu.europeana.commonculture.lod.datasetmetadata.HarvestMethod;
import eu.europeana.commonculture.lod.datasetmetadata.SparqlEndpoint;

public class LinkedDataHarvester {
	String datasetUri;
	int maxDepth = 0;
	int maxSeedsPerSet = -1;
	boolean datasetDescriptionOnly=false;
	
	public LinkedDataHarvester(String uri) {
		super();
		this.datasetUri = uri;
	}
	
	public LinkedDataHarvester(String uri, boolean datasetDescriptionOnly) {
		this(uri);
		this.datasetDescriptionOnly = datasetDescriptionOnly;
	}

	public LinkedDataHarvester(String uri, int maxDepth, int maxSeedsPerSet) {
		super();
		this.datasetUri = uri;
		this.maxDepth = maxDepth;
		this.maxSeedsPerSet = maxSeedsPerSet;
	}

	public int harvest(File outFile) throws IOException, AccessException, InterruptedException {
		if(!outFile.getParentFile().exists())
			outFile.getParentFile().mkdirs();
		else if(outFile.getParentFile().exists() && outFile.isDirectory())
			throw new IOException("Invalid parameter: Crawling 'output_file' parameter cannot be a directory: "+outFile.getPath());
		
		if(datasetDescriptionOnly) 
			return harvestDatasetDescription(outFile);

		DatasetDescription dataset = new DatasetDescription(datasetUri);
		
		HarvestMethod harvestMethod = dataset.chooseHarvestMethod();
		if(harvestMethod==null)
			throw new AccessException(datasetUri, "Dataset description: No suitable harvesting method found");
		switch (harvestMethod) {
		case DISTRIBUTION:
			download(dataset, outFile);
			return 1; //for distributions the number of resources is not known
		case ROOT_RESOURCES:
			return crawl(dataset, outFile);
		case SPARQL:
			return crawlSparql(dataset, outFile);			
		default:
			throw new AccessException(datasetUri, "Dataset description: No suitable harvesting method found");
		}
	}

	private void download(DatasetDescription dataset, File outFile) throws IOException, AccessException, InterruptedException {
		FileOutputStream fos = new FileOutputStream(outFile);
		StreamRDF writer = StreamRDFWriter.getWriterStream(fos, Lang.NTRIPLES);
		for(Triple t :dataset.getRdf().getGraph().find().toList()) 
			writer.triple(t);
		
		String error=null;
		for(Distribution dist: dataset.getDistributions()) {
			error=DistributionDownload.streamRdf(dist.getUrl(), dist.getContentType(), writer);
			if (error==null) break; //download was successful, do not attempt other distributions
		}
		
		writer.finish();
		fos.close();
		
		if(error!=null)
			throw new AccessException("Error downloading distribution: "+error);
	}

	private int crawlSparql(DatasetDescription dataset, File outFile) throws IOException, AccessException, InterruptedException {
		final SparqlEndpoint endpoint = dataset.getSparqlEndpoint();	
		LinkedDataCrawler crawler=new LinkedDataCrawler(maxDepth, maxSeedsPerSet);
    	if (StringUtils.isEmpty(endpoint.getQuery()))
    		endpoint.setQuery("SELECT DISTINCT ?s  WHERE { ?s ?p ?o }");
    	SparqlClient sparql=new SparqlClient(endpoint.getUrl(), (String)null);
    	int[] seeds=new int[] {0};
    	sparql.query(endpoint.getQuery(), new Handler() {
			public boolean handleSolution(QuerySolution solution) throws Exception {
				Iterator<String> varNames = solution.varNames();
				if(varNames.hasNext()) {
					seeds[0]++;
					crawler.addSeed(solution.getResource(varNames.next()).getURI());
				} else 
					throw new Exception("Invalid query: "+endpoint.getQuery());
				return true;
			}
		});

		return crawler.crawl(outFile);
	}

	private int crawl(DatasetDescription dataset, File outFile) throws AccessException, InterruptedException, IOException {
		ArrayList<String> seeds=new ArrayList<String>(dataset.listRootResources());
		seeds.add(dataset.getUri());
		LinkedDataCrawler crawler=new LinkedDataCrawler(seeds, maxDepth, maxSeedsPerSet);
		return crawler.crawl(outFile);
	}

	private int harvestDatasetDescription(File outFile) throws IOException, AccessException, InterruptedException {
		LinkedDataCrawler crawler=new LinkedDataCrawler(Arrays.asList(datasetUri), maxDepth, maxSeedsPerSet);
		return crawler.crawl(outFile);
	}
}
