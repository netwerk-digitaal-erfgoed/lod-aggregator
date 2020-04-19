package eu.europeana.commonculture.lod.crawler;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class TestCrawlFennicaDataset {

	@Test
	void crawlTest() {
		String outputFilePath="target/fennica.test.n3"; 
		File outputFile=new File(outputFilePath);
		
		String[] args=new String[] {
				"-dataset_uri", "http://cclod.netwerkdigitaalerfgoed.nl/fennica.ttl"
//				"-dataset_uri", "https://www.semantics.gr/authorities/vocabularies/ecc-books-dataset"
				, "-output_file", outputFilePath
		};
		CommandLineInterface.main(args);
		if(!outputFile.exists())
			fail("No output file");
		if(outputFile.length()<300000)
			fail("Output file too small");
		System.out.println("Crawled ok: size of output file:");
		System.out.println(outputFile.length());
	}

}
