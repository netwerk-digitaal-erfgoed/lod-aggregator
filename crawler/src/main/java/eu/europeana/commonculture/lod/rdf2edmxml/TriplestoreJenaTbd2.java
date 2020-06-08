package eu.europeana.commonculture.lod.rdf2edmxml;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.tdb2.sys.StoreConnection;

import eu.europeana.commonculture.lod.crawler.rdf.SparqlClient;

public class TriplestoreJenaTbd2 extends SparqlClient implements Closeable {
	public TriplestoreJenaTbd2(File dbFolder, ReadWrite openFor) {
		this(TDB2Factory.connectDataset(dbFolder.getAbsolutePath()), openFor);
	}
	
	private TriplestoreJenaTbd2(Dataset dataset, ReadWrite openFor) {
		super(dataset, "");
		dataset.begin(openFor);
	}
	
	public TriplestoreJenaTbd2(File dbFolder, String preffixes, ReadWrite openFor) {
		this(dbFolder, openFor);
		this.queryPrefix=preffixes;
	}
 	
	public void close() {
		dataset.commit();
		dataset.end();
		dataset.close();
		File lockFile=new File(TDB2Factory.location(dataset).getDirectoryPath(), "tdb.lock");
		if(lockFile.exists())
			lockFile.delete();
	}
	
	public void importTriples(File rdfFile, Lang lang) {
			try {
				InputStream r = Files.newInputStream(rdfFile.toPath());
				RDFDataMgr.read(dataset, r, lang);
				r.close();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
	}
	
	public static void main(String[] args) throws Exception {
//		String directory = "C:\\Users\\nfrei\\Desktop\\data\\wikidata-to-edm\\triplestore-wikidata" ;
//		File importFile=new File("C:\\Users\\nfrei\\Desktop\\data\\wikidata-to-edm\\wikidata2.rt");

		String directory = "C:\\Users\\nfrei\\Desktop\\data\\entity-collection-enrich\\triplestore-viaf";
		File importFile=new File("C:\\Users\\nfrei\\Desktop\\data\\coreference-semanticweb\\viaf.rt");
		
		Lang importFileLang=Lang.RDFTHRIFT;
		
		TriplestoreJenaTbd2 ts=new TriplestoreJenaTbd2(new File(directory), ReadWrite.WRITE);
		ts.importTriples(importFile, importFileLang);
		ts.close();
	}

	public StmtIterator listStatements(Resource s, Property p, RDFNode o) {
		return dataset.getDefaultModel().listStatements(s, p, o);
	}
	
	public ResIterator listSubjectsWithProperty(Property p, RDFNode o) {
		return dataset.getDefaultModel().listSubjectsWithProperty(p, o);
	}
	
	public Model getModel() {
		return dataset.getDefaultModel();
	}
	
}
