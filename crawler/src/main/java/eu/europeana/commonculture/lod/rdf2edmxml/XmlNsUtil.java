package eu.europeana.commonculture.lod.rdf2edmxml;

import java.util.HashMap;
import java.util.Map;

public class XmlNsUtil {
	private static enum Namespace {
		ORE("ore", "http://www.openarchives.org/ore/terms/"),
		OWL("owl", "http://www.w3.org/2002/07/owl#"),
		RDF("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
		RDFS("rdfs", "http://www.w3.org/2000/01/rdf-schema#"),
		SKOS("skos", "http://www.w3.org/2004/02/skos/core#"),
		DCTERMS("dcterms", "http://purl.org/dc/terms/"),
		WGS84_POS("wgs84_pos", "http://www.w3.org/2003/01/geo/wgs84_pos#"),
		RDAGRP2("rdagrp2", "http://rdvocab.info/ElementsGr2/"),
		FOAF("foaf", "http://xmlns.com/foaf/0.1/"),
		EDM("edm", "http://www.europeana.eu/schemas/edm/"),
		DC("dc",  "http://purl.org/dc/elements/1.1/"),
		OAI_DC("oai_dc",  "http://www.openarchives.org/OAI/2.0/oai_dc/"),
		SVCS("svcs",  "http://rdfs.org/sioc/services#"),
		DOAP("doap",  "http://usefulinc.com/ns/doap#");

		public String preffix;
		public String url;
		
		Namespace(String preffix, String url) {
			this.preffix = preffix;
			this.url = url;
		}
	}
	
	public static final String ORE=Namespace.ORE.url;
	public static final String OWL=Namespace.OWL.url;
	public static final String RDF=Namespace.RDF.url;
	public static final String RDFS=Namespace.RDFS.url;
	public static final String SKOS=Namespace.SKOS.url;
	public static final String DCTERMS=Namespace.DCTERMS.url;
	public static final String WGS84_POS=Namespace.WGS84_POS.url;
	public static final String RDAGRP2=Namespace.RDAGRP2.url;
	public static final String FOAF=Namespace.FOAF.url;
	public static final String EDM=Namespace.EDM.url;
	public static final String DC=Namespace.DC.url;
	public static final String OAI_DC=Namespace.OAI_DC.url;
	public static final String SVCS=Namespace.SVCS.url;
	public static final String DOAP=Namespace.DOAP.url;

	
	public static final Map<String, String> xpathEdmPrefixMap=new HashMap<String, String>() {{
		for(Namespace ns: Namespace.values())
			put(ns.preffix, ns.url);
	}};
	
	private static final Map<String, String> nsToPrefixMap=new HashMap<String, String>() {{
		for(Namespace ns: Namespace.values())
			put(ns.url, ns.preffix);
	}};
	
	public static String getPreffix(String namespaceUri) {
		return nsToPrefixMap.get(namespaceUri);
	}

}
