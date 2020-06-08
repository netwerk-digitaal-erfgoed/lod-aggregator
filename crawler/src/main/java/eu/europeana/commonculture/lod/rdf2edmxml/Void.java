package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class Void {
	public static String PREFIX="void";
	public static String NS="http://rdfs.org/ns/void#";

	public static final Property exampleResource = ResourceFactory.createProperty("http://rdfs.org/ns/void#exampleResource");
	public static final Property triples = ResourceFactory.createProperty("http://rdfs.org/ns/void#triples");
	public static final Property distinctObjects = ResourceFactory.createProperty("http://rdfs.org/ns/void#distinctObjects");
	public static final Property target = ResourceFactory.createProperty("http://rdfs.org/ns/void#target");
	public static final Property classPartition = ResourceFactory.createProperty("http://rdfs.org/ns/void#classPartition");
	public static final Property objectsTarget = ResourceFactory.createProperty("http://rdfs.org/ns/void#objectsTarget");
	public static final Property vocabulary = ResourceFactory.createProperty("http://rdfs.org/ns/void#vocabulary");
	public static final Property documents = ResourceFactory.createProperty("http://rdfs.org/ns/void#documents");
	public static final Resource Linkset = ResourceFactory.createResource("http://rdfs.org/ns/void#Linkset");
	public static final Property propertyPartition = ResourceFactory.createProperty("http://rdfs.org/ns/void#propertyPartition");
	public static final Property uriLookupEndpoint = ResourceFactory.createProperty("http://rdfs.org/ns/void#uriLookupEndpoint");
	public static final Property uriSpace = ResourceFactory.createProperty("http://rdfs.org/ns/void#uriSpace");
	public static final Property entities = ResourceFactory.createProperty("http://rdfs.org/ns/void#entities");
	public static final Property property = ResourceFactory.createProperty("http://rdfs.org/ns/void#property");
	public static final Property class_ = ResourceFactory.createProperty("http://rdfs.org/ns/void#class");
	public static final Resource TechnicalFeature = ResourceFactory.createResource("http://rdfs.org/ns/void#TechnicalFeature");
	public static final Property properties = ResourceFactory.createProperty("http://rdfs.org/ns/void#properties");
	public static final Property rootResource = ResourceFactory.createProperty("http://rdfs.org/ns/void#rootResource");
	public static final Property classes = ResourceFactory.createProperty("http://rdfs.org/ns/void#classes");
	public static final Property inDataset = ResourceFactory.createProperty("http://rdfs.org/ns/void#inDataset");
	public static final Property openSearchDescription = ResourceFactory.createProperty("http://rdfs.org/ns/void#openSearchDescription");
	public static final Resource DatasetDescription = ResourceFactory.createResource("http://rdfs.org/ns/void#DatasetDescription");
	public static final Property subjectsTarget = ResourceFactory.createProperty("http://rdfs.org/ns/void#subjectsTarget");
	public static final Property dataDump = ResourceFactory.createProperty("http://rdfs.org/ns/void#dataDump");
	public static final Property sparqlEndpoint = ResourceFactory.createProperty("http://rdfs.org/ns/void#sparqlEndpoint");
	public static final Resource Dataset = ResourceFactory.createResource("http://rdfs.org/ns/void#Dataset");
	public static final Property subset = ResourceFactory.createProperty("http://rdfs.org/ns/void#subset");
	public static final Property feature = ResourceFactory.createProperty("http://rdfs.org/ns/void#feature");
	public static final Property distinctSubjects = ResourceFactory.createProperty("http://rdfs.org/ns/void#distinctSubjects");
	public static final Property uriRegexPattern = ResourceFactory.createProperty("http://rdfs.org/ns/void#uriRegexPattern");
	public static final Property linkPredicate = ResourceFactory.createProperty("http://rdfs.org/ns/void#linkPredicate");
}
