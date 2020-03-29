package eu.europeana.commonculture.lod.crawler;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class TestCrawlNatLibPortugalDataset {

	@Test
	void crawlTest() {
		String outputFilePath="target/nlp.test.n3"; 
		File outputFile=new File(outputFilePath);
		
		String[] args=new String[] {
				"-dataset_uri", "http://oai.bn.pt/conjunto_de_dados/rnod-europeana.rdf"
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
