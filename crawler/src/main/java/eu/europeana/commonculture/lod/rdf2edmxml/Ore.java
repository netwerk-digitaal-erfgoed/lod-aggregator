package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class Ore {
	public static String PREFIX="ore";
	public static String NS="http://www.openarchives.org/ore/terms/";

	public static final Resource Proxy = ResourceFactory.createResource("http://www.openarchives.org/ore/terms/Proxy");
	public static final Property proxyFor = ResourceFactory.createProperty("http://www.openarchives.org/ore/terms/proxyFor");
	public static final Property similarTo = ResourceFactory.createProperty("http://www.openarchives.org/ore/terms/similarTo");
	public static final Property isDescribedBy = ResourceFactory.createProperty("http://www.openarchives.org/ore/terms/isDescribedBy");
	public static final Property describes = ResourceFactory.createProperty("http://www.openarchives.org/ore/terms/describes");
	public static final Resource Aggregation = ResourceFactory.createResource("http://www.openarchives.org/ore/terms/Aggregation");
	public static final Resource AggregatedResource = ResourceFactory.createResource("http://www.openarchives.org/ore/terms/AggregatedResource");
	public static final Property proxyIn = ResourceFactory.createProperty("http://www.openarchives.org/ore/terms/proxyIn");
	public static final Property lineage = ResourceFactory.createProperty("http://www.openarchives.org/ore/terms/lineage");
	public static final Property isAggregatedBy = ResourceFactory.createProperty("http://www.openarchives.org/ore/terms/isAggregatedBy");
	public static final Property aggregates = ResourceFactory.createProperty("http://www.openarchives.org/ore/terms/aggregates");
	public static final Resource ResourceMap = ResourceFactory.createResource("http://www.openarchives.org/ore/terms/ResourceMap");
}
