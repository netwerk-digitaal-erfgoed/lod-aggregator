package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class Dc {
	public static String PREFIX="dc";
	public static String NS="http://purl.org/dc/elements/1.1/";
	
	public static final Property coverage = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/coverage");
	public static final Property description = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/description");
	public static final Property date = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/date");
	public static final Property publisher = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/publisher");
	public static final Property source = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/source");
	public static final Property rights = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/rights");
	public static final Property format = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/format");
	public static final Property subject = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/subject");
	public static final Resource Policy = ResourceFactory.createResource("http://purl.org/dc/elements/1.1/Policy");
	public static final Property title = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/title");
	public static final Property type = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/type");
	public static final Property creator = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/creator");
	public static final Property contributor = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/contributor");
	public static final Property identifier = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/identifier");
	public static final Property language = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/language");
	public static final Property relation = ResourceFactory.createProperty("http://purl.org/dc/elements/1.1/relation");
}