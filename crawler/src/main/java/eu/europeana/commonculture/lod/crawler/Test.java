package eu.europeana.commonculture.lod.crawler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import eu.europeana.commonculture.lod.crawler.http.AccessException;

public class Test {
	public static void main(String[] args) {
			String result="";
			String dsUri = "http://data.bibliotheken.nl/id/dataset/rise-centsprenten";
//			String dsUri = "http://data.bibliotheken.nl/id/dataset/rise-childrensbooks";
			String logFilePath = "target/log-crawler.txt";
			String outFile = "target/crawl-test.nt";
//	    	LinkedDataCrawler crawler=new LinkedDataCrawler(dsUri, 0, -1); 
	    	LinkedDataHarvester crawler=new LinkedDataHarvester(dsUri, true); 
			try {
				int seeds=crawler.harvest(new File(outFile));
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
