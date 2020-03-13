package eu.europeana.commonculture.lod.crawler.rdf;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class RdfRegRdfs {
	public static String PREFIX="rdfs";
	public static String NS="http://www.w3.org/2000/01/rdf-schema#";

	public static final Property isDefinedBy = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#isDefinedBy");
	public static final Resource Resource = ResourceFactory.createResource("http://www.w3.org/2000/01/rdf-schema#Resource");
	public static final Resource Container = ResourceFactory.createResource("http://www.w3.org/2000/01/rdf-schema#Container");
	public static final Resource ContainerMembershipProperty = ResourceFactory.createResource("http://www.w3.org/2000/01/rdf-schema#ContainerMembershipProperty");
	public static final Property range = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#range");
	public static final Property seeAlso = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#seeAlso");
	public static final Property comment = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#comment");
	public static final Property domain = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#domain");
	public static final Resource Class = ResourceFactory.createResource("http://www.w3.org/2000/01/rdf-schema#Class");
	public static final Property member = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#member");
	public static final Resource Datatype = ResourceFactory.createResource("http://www.w3.org/2000/01/rdf-schema#Datatype");
	public static final Property label = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#label");
	public static final Resource Literal = ResourceFactory.createResource("http://www.w3.org/2000/01/rdf-schema#Literal");
	public static final Property subPropertyOf = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
	public static final Property subClassOf = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
}