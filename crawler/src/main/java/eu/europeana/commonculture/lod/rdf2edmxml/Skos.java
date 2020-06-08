package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class Skos {
	public static String PREFIX="skos";
	public static String NS="http://www.w3.org/2004/02/skos/core#";

	public static final Property changeNote = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#changeNote");
	public static final Property hiddenLabel = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#hiddenLabel");
	public static final Property memberList = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#memberList");
	public static final Property broader = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#broader");
	public static final Property note = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#note");
	public static final Property notation = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#notation");
	public static final Property historyNote = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#historyNote");
	public static final Property mappingRelation = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#mappingRelation");
	public static final Property example = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#example");
	public static final Property hasTopConcept = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#hasTopConcept");
	public static final Resource Concept = ResourceFactory.createResource("http://www.w3.org/2004/02/skos/core#Concept");
	public static final Property definition = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#definition");
	public static final Resource Collection = ResourceFactory.createResource("http://www.w3.org/2004/02/skos/core#Collection");
	public static final Property broadMatch = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#broadMatch");
	public static final Property exactMatch = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#exactMatch");
	public static final Property topConceptOf = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#topConceptOf");
	public static final Property inScheme = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#inScheme");
	public static final Property member = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#member");
	public static final Property broaderTransitive = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#broaderTransitive");
	public static final Property altLabel = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#altLabel");
	public static final Property narrowMatch = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#narrowMatch");
	public static final Property closeMatch = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#closeMatch");
	public static final Property relatedMatch = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#relatedMatch");
	public static final Resource OrderedCollection = ResourceFactory.createResource("http://www.w3.org/2004/02/skos/core#OrderedCollection");
	public static final Property narrower = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#narrower");
	public static final Property prefLabel = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#prefLabel");
	public static final Property scopeNote = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#scopeNote");
	public static final Property editorialNote = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#editorialNote");
	public static final Property semanticRelation = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#semanticRelation");
	public static final Property narrowerTransitive = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#narrowerTransitive");
	public static final Resource ConceptScheme = ResourceFactory.createResource("http://www.w3.org/2004/02/skos/core#ConceptScheme");
	public static final Property related = ResourceFactory.createProperty("http://www.w3.org/2004/02/skos/core#related");
}