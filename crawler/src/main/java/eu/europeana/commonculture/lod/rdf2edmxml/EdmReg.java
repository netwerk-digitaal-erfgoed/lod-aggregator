package eu.europeana.commonculture.lod.rdf2edmxml;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Resource;


public class EdmReg {

	public static final Set<Resource> edmClasses = Collections.unmodifiableSet(new HashSet<Resource>() {
		private static final long serialVersionUID = 1L;
		{
			add(Edm.ProvidedCHO);
			add(Edm.WebResource);
			add(Edm.Agent);
			add(Foaf.Organization);
			add(Edm.TimeSpan);
			add(Edm.Place);
			add(Edm.Event);
			add(Edm.PhysicalThing);
			add(Ore.Aggregation);
			add(RdfReg.SKOS_CONCEPT);
			add(RdfReg.SKOS_CONCEPT_SCHEME);
			add(RdfReg.CC_LICENSE);
			add(RdfReg.SVCS_SERVICE);
		}
	});

	public static boolean isEdmClass(Resource object) {
		return edmClasses.contains(object);
	}

	public static final Map<String, String> nsPrefixes = Collections.unmodifiableMap(new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(RdfReg.NsCc, "cc");
			put(Dc.NS, "dc");
			put(DcTerms.NS, "dcterms");
			put(Edm.NS, Edm.PREFIX);
			put(Ore.NS, "ore");
			put(Rdf.NS, Rdf.PREFIX);
			put(Rdfs.NS, Rdfs.PREFIX);
			put(Skos.NS, "skos");
			put(Svcs.NS, "svcs");
			put(RdfReg.NsDoap, "doap");
			put(Wgs84.NS, "wgs84_pos");
			put(Owl.NS, "owl");
			put(RdfReg.NsRdaGr2, "rdaGr2");
			put(Foaf.NS, "foaf");
//		put("cc", RdfReg.NsCc);
//		put("dc", RdfReg.NsDc);
//		put("dcterms", RdfReg.NsDcterms);
//		put("edm", RdfReg.NsEdm);
//		put("ore", RdfReg.NsOre);
//		put("rdf", RdfReg.NsRdf);
//		put("rdfs", RdfReg.NsRdfs);
//		put("skos", RdfReg.NsSkos);
//		put("svcs", RdfReg.NsSvcs);
//		put("doap", RdfReg.NsDoap);
//		put("wgs84_pos", RdfReg.NsWgs84);
//		put("owl", RdfReg.NsOwl);
//		put("rdaGr2", RdfReg.NsRdaGr2);
//		put("foaf", RdfReg.NsFoaf);
		}
	});
}
