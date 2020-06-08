package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class Dcat {
	public static String PREFIX="dcat";
	public static String NS="http://www.w3.org/ns/dcat#";

	public static final Property byteSize = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#byteSize");
	public static final Property dataset = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#dataset");
	public static final Property contactPoint = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#contactPoint");
	public static final Property hadRole = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#hadRole");
	public static final Property servesDataset = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#servesDataset");
	public static final Property packageFormat = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#packageFormat");
	public static final Property themeTaxonomy = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#themeTaxonomy");
	public static final Resource Relationship = ResourceFactory.createResource("http://www.w3.org/ns/dcat#Relationship");
	public static final Property endpointDescription = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#endpointDescription");
	public static final Property service = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#service");
	public static final Property endpointURL = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#endpointURL");
	public static final Resource DataService = ResourceFactory.createResource("http://www.w3.org/ns/dcat#DataService");
	public static final Property record = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#record");
	public static final Property landingPage = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#landingPage");
	public static final Property theme = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#theme");
	public static final Resource Resource = ResourceFactory.createResource("http://www.w3.org/ns/dcat#Resource");
	public static final Property compressFormat = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#compressFormat");
	public static final Resource Dataset = ResourceFactory.createResource("http://www.w3.org/ns/dcat#Dataset");
	public static final Property accessURL = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#accessURL");
	public static final Property bbox = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#bbox");
	public static final Property centroid = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#centroid");
	public static final Property endDate = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#endDate");
	public static final Resource Catalog = ResourceFactory.createResource("http://www.w3.org/ns/dcat#Catalog");
	public static final Property accessService = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#accessService");
	public static final Property catalog = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#catalog");
	public static final Resource CatalogRecord = ResourceFactory.createResource("http://www.w3.org/ns/dcat#CatalogRecord");
	public static final Property spatialResolutionInMeters = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#spatialResolutionInMeters");
	public static final Property mediaType = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#mediaType");
	public static final Property keyword = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#keyword");
	public static final Property downloadURL = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#downloadURL");
	public static final Property startDate = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#startDate");
	public static final Resource Role = ResourceFactory.createResource("http://www.w3.org/ns/dcat#Role");
	public static final Property temporalResolution = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#temporalResolution");
	public static final Property distribution = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#distribution");
	public static final Property qualifiedRelation = ResourceFactory.createProperty("http://www.w3.org/ns/dcat#qualifiedRelation");
	public static final Resource Distribution = ResourceFactory.createResource("http://www.w3.org/ns/dcat#Distribution");
}
