package eu.europeana.commonculture.lod.rdf2edmxml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;


public final class Edm {
	public static String NS="http://www.europeana.eu/schemas/edm/";
	public static String PREFIX="edm";
	
	public static Map<String, String> NS_EXTERNAL_PREFERRED_BY_NS=new HashMap<String, String>() {{
		put("http://www.europeana.eu/schemas/edm/", "edm");
		put("http://www.w3.org/2001/XMLSchema#", "xsd");
		put("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf");
		put("http://www.w3.org/2000/01/rdf-schema#", "rdfs");
		put("http://www.w3.org/2002/07/owl#", "owl");
		put("http://www.w3.org/2004/02/skos/core#", "skos");
		put("http://purl.org/vocommons/voaf#", "voaf");
		put("http://purl.org/vocab/vann/", "vann");
		put("http://www.w3.org/ns/adms#", "adms");
		put("http://www.w3.org/ns/radion#", "radion");
		put("http://rdvocab.info/ElementsGr2/", "rdaGr2");
		put("http://purl.org/dc/elements/1.1/", "dc");
		put("http://purl.org/dc/terms/", "dcterms");
		put("http://www.openarchives.org/ore/terms/", "ore");
		put("http://xmlns.com/foaf/0.1/", "foaf");
		put("http://www.w3.org/2003/01/geo/wgs84_pos#", "wgs84_pos");
		put("http://purl.org/dc/dcmitype/", "dcmitype");
		put("http://www.cidoc-crm.org/cidoc-crm/", "cidoc-crm");
		put("http://purl.org/vocab/frbr/core#", "frbr");
		put("http://iflastandards.info/ns/fr/frbr/frbroo/", "frbroo");
		put("http://metadata.net/harmony/abc#", "abc");
		put("http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#", "ebucore");
		put("http://www.loa-cnr.it/ontologies/DOLCE-Lite.owl#", "dolce-lite");
	}};
	public static Map<String, String> NS_EXTERNAL_PREFERRED_BY_PREFIXES=new HashMap<String, String>() {{
		for(Entry<String, String> e : NS_EXTERNAL_PREFERRED_BY_NS.entrySet()) 
			put(e.getValue(), e.getKey());
	}};

	public static final Property completeness = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/completeness");
	public static final Property datasetName = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/datasetName");
	
	public static final Property codecName = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/codecName");
	public static final Property spatialResolution = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/spatialResolution");
	public static final Property hasColorSpace = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/hasColorSpace");
	public static final Property componentColor = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/componentColor");

	public static final Resource EuropeanaAggregation = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/EuropeanaAggregation");
	public static final Property collectionName = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/collectionName");
	public static final Property country = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/country");
	public static final Resource InformationResource = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/InformationResource");
	public static final Resource WebResource = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/WebResource");
	public static final Property unstored = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/unstored");
	public static final Property isRepresentationOf = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isRepresentationOf");
	public static final Property language = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/language");
	public static final Property end = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/end");
	public static final Property isShownAt = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isShownAt");
	public static final Property hasMet = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/hasMet");
	public static final Property preview = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/preview");
	public static final Resource TimeSpan = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/TimeSpan");
	public static final Property hasView = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/hasView");
	public static final Property isSuccessorOf = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isSuccessorOf");
	public static final Property isAnnotationOf = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isAnnotationOf");
	public static final Property dataProvider = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/dataProvider");
	public static final Property happenedAt = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/happenedAt");
	public static final Property isDerivativeOf = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isDerivativeOf");
	public static final Property landingPage = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/landingPage");
	public static final Property aggregatedCHO = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/aggregatedCHO");
	public static final Property currentLocation = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/currentLocation");
	public static final Property hasType = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/hasType");
	public static final Property wasPresentAt = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/wasPresentAt");
	public static final Property provider = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/provider");
	public static final Property intermediateProvider = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/intermediateProvider");
	public static final Property object = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/object");
	public static final Resource Place = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/Place");
	public static final Property isShownBy = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isShownBy");
	public static final Property ugc = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/ugc");
	public static final Resource Event = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/Event");
	public static final Property uri = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/uri");
	public static final Resource NonInformationResource = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/NonInformationResource");
	public static final Resource ProvidedCHO = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/ProvidedCHO");
	public static final Property userTag = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/userTag");
	public static final Property year = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/year");
	public static final Property isNextInSequence = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isNextInSequence");
	public static final Property occurredAt = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/occurredAt");
	public static final Property isSimilarTo = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isSimilarTo");
	public static final Property type = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/type");
	public static final Resource PhysicalThing = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/PhysicalThing");
	public static final Property begin = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/begin");
	public static final Property realizes = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/realizes");
	public static final Resource EuropeanaObject = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/EuropeanaObject");
	public static final Property isRelatedTo = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/isRelatedTo");
	public static final Property incorporates = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/incorporates");
	public static final Property rights = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/rights");
	public static final Property europeanaProxy = ResourceFactory.createProperty("http://www.europeana.eu/schemas/edm/europeanaProxy");
	public static final Resource Agent = ResourceFactory.createResource("http://www.europeana.eu/schemas/edm/Agent");
}