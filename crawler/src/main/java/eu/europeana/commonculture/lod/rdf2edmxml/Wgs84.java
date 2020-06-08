package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class Wgs84 {
	public static String PREFIX="wgs84";
	public static String NS="http://www.w3.org/2003/01/geo/wgs84_pos#";

	public static final Property location = ResourceFactory.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#location");
	public static final Property lat = ResourceFactory.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
	public static final Property long_ = ResourceFactory.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
	public static final Property lat_long = ResourceFactory.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat_long");
	public static final Property alt = ResourceFactory.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#alt");
	public static final Resource SpatialThing = ResourceFactory.createResource("http://www.w3.org/2003/01/geo/wgs84_pos#SpatialThing");
	public static final Resource Point = ResourceFactory.createResource("http://www.w3.org/2003/01/geo/wgs84_pos#Point");
}
