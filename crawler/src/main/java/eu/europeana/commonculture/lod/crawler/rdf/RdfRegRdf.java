package eu.europeana.commonculture.lod.crawler.rdf;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class RdfRegRdf {
	public static String PREFIX="rdf";
	public static String NS="http://www.w3.org/1999/02/22-rdf-syntax-ns#";

	public static final Property predicate = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate");
	public static final Property type = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	public static final Resource List = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#List");
	public static final Property value = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#value");
	public static final Resource Property = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#Property");
	public static final Resource Statement = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#Statement");
	public static final Property rest = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#rest");
	public static final Property nil = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#nil");
	public static final Property langString = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#langString");
	public static final Property subject = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#subject");
	public static final Property object = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#object");
	public static final Resource Bag = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag");
	public static final Resource Seq = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq");
	public static final Resource PlainLiteral = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral");
	public static final Resource Alt = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#Alt");
	public static final Property first = ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#first");
	public static final Resource HTML = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#HTML");
	public static final Resource XMLLiteral = ResourceFactory.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral");
}