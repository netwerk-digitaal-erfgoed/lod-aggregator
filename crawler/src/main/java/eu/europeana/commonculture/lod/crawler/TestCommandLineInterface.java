package eu.europeana.commonculture.lod.crawler;

public class TestCommandLineInterface {
	
	public static void main(String[] args) throws Exception {
		
//		CommandLineInterface.main(new String[] {
//			"-dataset_uri", "http://data.bibliotheken.nl/id/dataset/rise-centsprenten", 
//			"-dataset_description_only", "-output_file", 
//			"target/centsprenten_dataset.nt", 
//			"-log_file", "target/run.log"
//		});
		CommandLineInterface.main(new String[] {
				"-dataset_uri", "http://semantics.gr/authorities/vocabularies/ecc-books-dataset", 
				"-output_file", 
				"target/ekt.nt", 
				"-log_file", "target/run.log"
		});
		
		
	
		
	}
	
}
