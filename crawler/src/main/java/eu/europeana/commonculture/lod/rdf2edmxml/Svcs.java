package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class Svcs {
	public static String PREFIX="svcs";
	public static String NS="http://rdfs.org/sioc/services#";

	public static final Property results_format = ResourceFactory.createProperty("http://rdfs.org/sioc/services#results_format");
	public static final Resource Service = ResourceFactory.createResource("http://rdfs.org/sioc/services#Service");
	public static final Property service_protocol = ResourceFactory.createProperty("http://rdfs.org/sioc/services#service_protocol");
	public static final Property service_endpoint = ResourceFactory.createProperty("http://rdfs.org/sioc/services#service_endpoint");
	public static final Property service_definition = ResourceFactory.createProperty("http://rdfs.org/sioc/services#service_definition");
	public static final Property has_service = ResourceFactory.createProperty("http://rdfs.org/sioc/services#has_service");
	public static final Property service_of = ResourceFactory.createProperty("http://rdfs.org/sioc/services#service_of");
	public static final Property max_results = ResourceFactory.createProperty("http://rdfs.org/sioc/services#max_results");
}