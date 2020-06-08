package eu.europeana.commonculture.lod.rdf2edmxml;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;
import java.util.logging.LogManager;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.riot.Lang;


public class Rdf2EdmCl {

//Rdf2EdmCl  --input_file ./data/crawled/  --output_file ./data/crawled/rise-centsprenten.xml.zip
	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption( "input_file", true, "Path to the output file.");
		options.addOption( "output_file", true, "Path to the output zip file.");
		
		CommandLine line=null;
		
		boolean argsOk=true;
		try {
		    line = parser.parse( options, args );
		    if( !line.hasOption("input_file") || !line.hasOption("output_file") ) 
		    	argsOk=false;
		} catch( ParseException exp ) {
			argsOk=false;
		}
	    String result=null;
	    if(argsOk) {
	    	try {
				String inFile = line.getOptionValue("input_file");
				String outFile = line.getOptionValue("output_file");

				File targetZipFile = new File(outFile);
				targetZipFile.getParentFile().mkdirs();
				ZipArchiveExporter ziper = new ZipArchiveExporter(targetZipFile);
				
				File tmpTripleStoreDir=new File("tmp_rdf2edm_ts_"+(new Date().getTime()-new Random().nextInt()));
				
				TriplestoreJenaTbd2 ts=new TriplestoreJenaTbd2(tmpTripleStoreDir, ReadWrite.WRITE);
				ts.importTriples(new File(inFile), Lang.RDFXML);
				ts.close();			
				
				ts=new TriplestoreJenaTbd2(tmpTripleStoreDir, ReadWrite.READ);
				
				ResIterator aggrs = ts.listSubjectsWithProperty(Rdf.type, Ore.Aggregation);
				while( aggrs.hasNext() ) {
					Resource aggRes=aggrs.next();
					Statement choStm = aggRes.getProperty(Edm.aggregatedCHO);
					if (choStm!=null && choStm.getObject().isURIResource()) {
						EdmRdfToXmlSerializer serializer=new EdmRdfToXmlSerializer(choStm.getObject().asResource(), aggRes);
						ziper.addFile(URLEncoder.encode(choStm.getObject().asResource().getURI(),"UTF8") +".edm.xml");
						XmlUtil.writeDomToStream(serializer.getXmlDom(), ziper.outputStream());
					}
				}
				ziper.close();
				aggrs.close();
				ts.close();	
				
				FileUtils.forceDeleteOnExit(tmpTripleStoreDir);
				result="";
			} catch (IOException e) {
				result="ERROR\n"+
				ExceptionUtils.getStackTrace(e);
			}		
	    } else {
	    	StringWriter sw=new StringWriter();
	    	PrintWriter w=new PrintWriter(sw);
	    	HelpFormatter formatter = new HelpFormatter();
	    	formatter.printUsage( w, 120, "rdf2edm.sh", options );
	    	w.close();
	    	result="INVALID PARAMETERS\n"+sw.toString();
	    }
	    System.out.println(result);
	}
}
