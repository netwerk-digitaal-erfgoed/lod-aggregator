package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class RdfReg {
	public static String NsXml="http://www.w3.org/XML/1998/namespace";
//	public static String NsRdfs="http://www.w3.org/2000/01/rdf-schema#";
	public static String NsDcmiType="http://purl.org/dc/dcmitype/";
	public static String NsIiif="http://iiif.io/api/presentation/2#";
	public static String NsCc="http://creativecommons.org/ns#";
	public static String NsDoap="http://usefulinc.com/ns/doap#";
	public static String NsRdaGr2="http://rdvocab.info/ElementsGr2/"; 
	public static String NsVcard="http://www.w3.org/2006/vcard/ns#";
	public static String NsDqv="http://www.w3.org/ns/dqv#";
	public static String NsOa="http://www.w3.org/ns/oa#";
	public static String NsProv="http://www.w3.org/ns/prov#";
	
	public static final Property DQV_HAS_QUALITY_ANNOTATION=ResourceFactory.createProperty(NsDqv+"hasQualityAnnotation");

	public static final Property OA_HAS_BODY=ResourceFactory.createProperty(NsOa+"hasBody");

	public static final Resource IIIF_MANIFEST = ResourceFactory.createResource("http://iiif.io/api/presentation/2#Manifest");
	public static final Resource IIIF_COLLECTION = ResourceFactory.createResource("http://iiif.io/api/presentation/2#Collection");
	public static final Property IIIF_METADATA_LABELS = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#metadataLabels");
	public static final Property IIIF_PROFiLE_DOAP_IMPLEMENTS = ResourceFactory.createProperty("http://usefulinc.com/ns/doap#implements");
	public static final Property IIIF_NAV_DATE = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#presentationDate");
	public static final Property IIIF_HAS_SEQUENCES = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#hasSequences");
	public static final Property IIIF_HAS_CANVASES = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#hasCanvases");
	public static final Property IIIF_HAS_IMAGE_ANNOTATIONS = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#hasImageAnnotations");
	public static final Property SVCS_HAS_SERVICE = ResourceFactory.createProperty("http://rdfs.org/sioc/services#has_service");
	public static final Resource SKOS_CONCEPT = ResourceFactory.createResource("http://www.w3.org/2004/02/skos/core#Concept");
	public static final Resource SKOS_CONCEPT_SCHEME = ResourceFactory.createResource("http://www.w3.org/2004/02/skos/core#ConceptScheme");
	public static final Resource CC_LICENSE  = ResourceFactory.createResource("http://creativecommons.org/ns#License");

	public static final Resource DCMITYPE_COLLECTION  = ResourceFactory.createResource(NsDcmiType+"Collection");
	
	public static final Resource SVCS_SERVICE  = ResourceFactory.createResource("http://rdfs.org/sioc/services#Service");
	public static final Resource VCARD_ADDRESS = ResourceFactory.createResource(NsVcard+"Address");
	public static final Property VCARD_REGION = ResourceFactory.createProperty(NsVcard+"region");
	public static final Property VCARD_LOCALITY = ResourceFactory.createProperty(NsVcard+"locality");
	public static final Property VCARD_POSTAL_CODE = ResourceFactory.createProperty(NsVcard+"postal-code");
	
	public static final Property PROV_WAS_GENERATED_BY = ResourceFactory.createProperty(NsProv+"wasGeneratedBy");
	public static final Resource Schemaorg_NEWSPAPER = ResourceFactory.createResource(Schemaorg.NS+"Newspaper");
	public static final Resource Schemaorg_DRAWING = ResourceFactory.createResource(Schemaorg.NS+"Drawing");
	public static final Resource Schemaorg_POSTER = ResourceFactory.createResource(Schemaorg.NS+"Poster");
	public static final Resource Schemaorg_MANUSCRIPT = ResourceFactory.createResource(Schemaorg.NS+"Manuscript");
	public static final Property Schemaorg_JOB_TITLE = ResourceFactory.createProperty(Schemaorg.NS+"jobTitle");;
}