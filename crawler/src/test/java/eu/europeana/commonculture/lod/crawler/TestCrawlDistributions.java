package eu.europeana.commonculture.lod.crawler;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class TestCrawlDistributions {

	@Test
	void zipTest() {
		String outputFilePath="target/rise-centsprenten.test.n3"; 
		File outputFile=new File(outputFilePath);
		
		String datasetUri = "https://rnd-2.eanadev.org/share/lod-pilots/commonculturetest/dataset.rdf";
		
		String[] args=new String[] {
				"-dataset_uri", datasetUri
				, "-output_file", outputFilePath
		};
		CommandLineInterface.main(args);
		if(!outputFile.exists())
			fail("No output file");
		
		String dump=null;
		try {
			dump = FileUtils.readFileToString(outputFile, StandardCharsets.UTF_8);
		} catch (IOException e) {
			fail(e);
		}
		
		if(!dump.contains("\n<http://data.bibliotheken.nl/id/dataset/rise-centsprenten> "))
			fail("dump statements not in result");
		
		if(!dump.contains("\n<"+datasetUri))
			fail("dataset description statements not in result");
	}

	
	@Test
	void gzipTest() {
		String outputFilePath="target/rise-centsprenten.test.n3"; 
		File outputFile=new File(outputFilePath);
		
		String datasetUri = "https://rnd-2.eanadev.org/share/lod-pilots/commonculturetest/dataset_dcat.rdf";
		
		String[] args=new String[] {
				"-dataset_uri", datasetUri
				, "-output_file", outputFilePath
		};
		CommandLineInterface.main(args);
		if(!outputFile.exists())
			fail("No output file");
		
		String dump=null;
		try {
			dump = FileUtils.readFileToString(outputFile, StandardCharsets.UTF_8);
		} catch (IOException e) {
			fail(e);
		}
		
		if(!dump.contains("\n<http://data.bibliotheken.nl/id/dataset/rise-centsprenten> "))
			fail("dump statements not in result");
		
		if(!dump.contains("\n<"+datasetUri))
			fail("dataset description statements not in result");
	}

	@Test
	void plainRdfTest() {
		String outputFilePath="target/rise-centsprenten.test.n3"; 
		File outputFile=new File(outputFilePath);
		
		String datasetUri = "https://rnd-2.eanadev.org/share/lod-pilots/commonculturetest/dataset_void.rdf";
		
		String[] args=new String[] {
				"-dataset_uri", datasetUri
				, "-output_file", outputFilePath
		};
		CommandLineInterface.main(args);
		if(!outputFile.exists())
			fail("No output file");
		
		String dump=null;
		try {
			dump = FileUtils.readFileToString(outputFile, StandardCharsets.UTF_8);
		} catch (IOException e) {
			fail(e);
		}
		
		if(!dump.contains("\n<https://rnd-2.eanadev.org/share/lod-pilots/commonculturetest/dataset.rdf> "))
			fail("dump statements not in result");
		
		if(!dump.contains("\n<"+datasetUri))
			fail("dataset description statements not in result");
	}

}
