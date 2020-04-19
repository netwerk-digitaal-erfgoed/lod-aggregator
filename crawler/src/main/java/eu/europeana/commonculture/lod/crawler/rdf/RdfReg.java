package eu.europeana.commonculture.lod.crawler.rdf;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class RdfReg {
	public static String NsXml="http://www.w3.org/XML/1998/namespace";
//	public static String NsRdfs="http://www.w3.org/2000/01/rdf-schema#";
	public static String NsDc="http://purl.org/dc/elements/1.1/";
	public static String NsDcterms="http://purl.org/dc/terms/";
	public static String NsDcmiType="http://purl.org/dc/dcmitype/";
	public static String NsOre="http://www.openarchives.org/ore/terms/";
	public static String NsIiif="http://iiif.io/api/presentation/2#";
	public static String NsSkos="http://www.w3.org/2004/02/skos/core#";
	public static String NsCc="http://creativecommons.org/ns#";
	public static String NsSvcs="http://rdfs.org/sioc/services#";
	public static String NsDoap="http://usefulinc.com/ns/doap#";
	public static String NsWgs84="http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static String NsOwl="http://www.w3.org/2002/07/owl#";
	public static String NsRdaGr2="http://rdvocab.info/ElementsGr2/"; 
	public static String NsFoaf="http://xmlns.com/foaf/0.1/";
	public static String NsVcard="http://www.w3.org/2006/vcard/ns#";
	public static String NsSchemaOrg="http://schema.org/";
	public static String NsVoid="http://rdfs.org/ns/void#";
	public static String NsDqv="http://www.w3.org/ns/dqv#";
	public static String NsOa="http://www.w3.org/ns/oa#";
	public static String NsDcat="http://www.w3.org/ns/dcat#";
	public static String NsProv="http://www.w3.org/ns/prov#";
	
	public static final Property DQV_HAS_QUALITY_ANNOTATION=ResourceFactory.createProperty(NsDqv+"hasQualityAnnotation");
	public static final Resource DCAT_DATASET = ResourceFactory.createResource(NsDcat+"Dataset");
	public static final Resource VOID_DATASET = ResourceFactory.createResource(NsVoid+"Dataset");

	public static final Property OA_HAS_BODY=ResourceFactory.createProperty(NsOa+"hasBody");

	public static final Resource IIIF_MANIFEST = ResourceFactory.createResource("http://iiif.io/api/presentation/2#Manifest");
	public static final Resource FOAF_ORGANIZATION = ResourceFactory.createResource(NsFoaf+ "Organization");
	public static final Property FOAF_PAGE= ResourceFactory.createProperty(NsFoaf+ "page");
	public static final Resource IIIF_COLLECTION = ResourceFactory.createResource("http://iiif.io/api/presentation/2#Collection");
	public static final Resource ORE_AGGREGATION = ResourceFactory.createResource(NsOre+"Aggregation");
	public static final Resource ORE_PROXY = ResourceFactory.createResource(NsOre+"Proxy");
	public static final Property IIIF_METADATA_LABELS = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#metadataLabels");
	public static final Property IIIF_PROFiLE_DOAP_IMPLEMENTS = ResourceFactory.createProperty("http://usefulinc.com/ns/doap#implements");
	public static final Property DCTERMS_HAS_FORMAT = ResourceFactory.createProperty(NsDcterms+"hasFormat");
	public static final Property DC_FORMAT = ResourceFactory.createProperty(NsDc+"format");
	public static final Property DC_SOURCE = ResourceFactory.createProperty(NsDc+"source");
	public static final Property DC_TITLE = ResourceFactory.createProperty(NsDc+"title");
	public static final Property DCTERMS_TITLE = ResourceFactory.createProperty(NsDcterms+"title");
	public static final Property DC_TYPE = ResourceFactory.createProperty(NsDc+"type");
	public static final Property DCTERMS_MEDIUM = ResourceFactory.createProperty(NsDcterms+"medium");
	public static final Property DCTERMS_PROVENANCE = ResourceFactory.createProperty(NsDcterms+"provenance");
	public static final Property DCTERMS_ALTERNATIVE = ResourceFactory.createProperty(NsDcterms+"alternative");
	public static final Property DC_CREATOR = ResourceFactory.createProperty(NsDc+"creator");
	public static final Property DC_CONTRIBUTOR = ResourceFactory.createProperty(NsDc+"contributor");
	public static final Property DCTERMS_PUBLISHER = ResourceFactory.createProperty(NsDcterms+"publisher");
	public static final Property DC_SUBJECT = ResourceFactory.createProperty(NsDc+"subject");
	public static final Property DC_LANGUAGE = ResourceFactory.createProperty(NsDc+"language");
	public static final Property DC_DESCRIPTION = ResourceFactory.createProperty(NsDc+"description");
	public static final Property IIIF_NAV_DATE = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#presentationDate");
	public static final Property IIIF_HAS_SEQUENCES = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#hasSequences");
	public static final Property IIIF_HAS_CANVASES = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#hasCanvases");
	public static final Property IIIF_HAS_IMAGE_ANNOTATIONS = ResourceFactory.createProperty("http://iiif.io/api/presentation/2#hasImageAnnotations");
	public static final Property SVCS_HAS_SERVICE = ResourceFactory.createProperty("http://rdfs.org/sioc/services#has_service");
	public static final Property DCTERMS_RIGHTS = ResourceFactory.createProperty(NsDcterms+"rights");
	public static final Property DC_RIGHTS = ResourceFactory.createProperty(NsDc+"rights");
	public static final Property DC_DATE = ResourceFactory.createProperty(NsDc+"date");
	public static final Property DCTERMS_CREATED = ResourceFactory.createProperty(NsDcterms+"created");
	public static final Property DCTERMS_ISSUED = ResourceFactory.createProperty(NsDcterms+"issued");
	public static final Property DCTERMS_EXTENT = ResourceFactory.createProperty(NsDcterms+"extent");
	public static final Property DCTERMS_RELATION = ResourceFactory.createProperty(NsDcterms+"relation");
	public static final Property DCTERMS_IS_REFERENCED_BY = ResourceFactory.createProperty(NsDcterms+"isReferencedBy");
	public static final Property DCTERMS_IS_PART_OF = ResourceFactory.createProperty(NsDcterms+"isPartOf");
	public static final Property DCTERMS_TEMPORAL_COVERAGE = ResourceFactory.createProperty(NsDcterms+"temporalCoverage");
	public static final Property DCTERMS_TEMPORAL = ResourceFactory.createProperty(NsDcterms+"temporal");
	public static final Property DC_COVERAGE = ResourceFactory.createProperty(NsDc+"coverage");
	public static final Property DCTERMS_SPATIAL = ResourceFactory.createProperty(NsDcterms+"spatial");
	public static final Resource SKOS_CONCEPT = ResourceFactory.createResource("http://www.w3.org/2004/02/skos/core#Concept");
	public static final Resource SKOS_CONCEPT_SCHEME = ResourceFactory.createResource("http://www.w3.org/2004/02/skos/core#ConceptScheme");
	public static final Resource CC_LICENSE  = ResourceFactory.createResource("http://creativecommons.org/ns#License");

	public static final Resource DCMITYPE_COLLECTION  = ResourceFactory.createResource(NsDcmiType+"Collection");
	
	public static final Resource SVCS_SERVICE  = ResourceFactory.createResource("http://rdfs.org/sioc/services#Service");
	public static final Property DCTERMS_CONFORMS_TO = ResourceFactory.createProperty(NsDcterms+"conformsTo");
	public static final Property DCTERMS_TABLE_OF_CONTENTS = ResourceFactory.createProperty(NsDcterms+"tableOfContents");
	public static final Property DCTERMS_HAS_PART = ResourceFactory.createProperty(NsDcterms+"hasPart");
	public static final Resource VCARD_ADDRESS = ResourceFactory.createResource(NsVcard+"Address");
	public static final Property VCARD_REGION = ResourceFactory.createProperty(NsVcard+"region");
	public static final Property VCARD_LOCALITY = ResourceFactory.createProperty(NsVcard+"locality");
	public static final Property VCARD_POSTAL_CODE = ResourceFactory.createProperty(NsVcard+"postal-code");

	
	
	public static final Property RDAGR2_PROFESSION_OR_OCCUPATION = ResourceFactory.createProperty(NsRdaGr2+"professionOrOccupation");
	public static final Property RDAGR2_BIRTH_DATE = ResourceFactory.createProperty(NsRdaGr2+"birthDate");
	public static final Property RDAGR2_DEATH_DATE = ResourceFactory.createProperty(NsRdaGr2+"deathDate");

	public static final Property WGS84_lONG = ResourceFactory.createProperty(NsWgs84+"long");
	public static final Property WGS84_LAT = ResourceFactory.createProperty(NsWgs84+"lat");

	public static final Property OWL_SAME_AS = ResourceFactory.createProperty(RdfReg.NsOwl+"sameAs");
	public static final Property OWL_EQUIVALENT_CLASS = ResourceFactory.createProperty(RdfReg.NsOwl+"equivalentClass");
	public static final Property OWL_EQUIVALENT_PROPERTY = ResourceFactory.createProperty(RdfReg.NsOwl+"equivalentProperty");
	public static final Resource OWL_CLASS  = ResourceFactory.createResource(NsOwl+"Class");
	public static final Resource OWL_Thing  = ResourceFactory.createResource(NsOwl+"Thing");
	public static final Resource OWL_Nothing  = ResourceFactory.createResource(NsOwl+"Nothing");
	public static final Resource OWL_DATA_RANGE  = ResourceFactory.createResource(NsOwl+"DataRange");
	public static final Resource OWL_DATA_TYPE_PROPERTY  = ResourceFactory.createResource(NsOwl+"DatatypeProperty");
	public static final Resource OWL_FUNCTIONAL_PROPERTY  = ResourceFactory.createResource(NsOwl+"FunctionalProperty");
	public static final Resource OWL_OBJECT_PROPERTY  = ResourceFactory.createResource(NsOwl+"owl:ObjectProperty");
	public static final Resource OWL_ONTOLOGY_PROPERTY  = ResourceFactory.createResource(NsOwl+"owl:OntologyProperty");

	public static final Property SKOS_PREF_LABEL = ResourceFactory.createProperty(RdfReg.NsSkos+"prefLabel");
	public static final Property SKOS_ALT_LABEL = ResourceFactory.createProperty(RdfReg.NsSkos+"altLabel");
	public static final Property SKOS_NOTE = ResourceFactory.createProperty(RdfReg.NsSkos+"note");
	
	public static final Resource SCHEMAORG_CREATIVE_WORK  = ResourceFactory.createResource(NsSchemaOrg+"CreativeWork");
	public static final Resource SCHEMAORG_CREATIVE_WORK_SERIES  = ResourceFactory.createResource(NsSchemaOrg+"CreativeWorkSeries");
	public static final Resource SCHEMAORG_SCULPTURE  = ResourceFactory.createResource(NsSchemaOrg+"Sculpture");
	public static final Resource SCHEMAORG_PERSON  = ResourceFactory.createResource(NsSchemaOrg+"Person");
	public static final Resource SCHEMAORG_ORGANIZATION  = ResourceFactory.createResource(NsSchemaOrg+"Organization");
	public static final Resource SCHEMAORG_GEO_COORDINATES = ResourceFactory.createResource(NsSchemaOrg+"GeoCoordinates");
	public static final Resource SCHEMAORG_THING  = ResourceFactory.createResource(NsSchemaOrg+"Thing");
	public static final Resource SCHEMAORG_PLACE  = ResourceFactory.createResource(NsSchemaOrg+"Place");
	public static final Resource SCHEMAORG_POSTAL_ADDRESS  = ResourceFactory.createResource(NsSchemaOrg+"PostalAddress");
	public static final Resource SCHEMAORG_AUDIO_OBJECT  = ResourceFactory.createResource(NsSchemaOrg+"AudioObject");
	public static final Resource SCHEMAORG_IMAGE_OBJECT  = ResourceFactory.createResource(NsSchemaOrg+"ImageObject");
	public static final Resource SCHEMAORG_NEWSPAPER  = ResourceFactory.createResource(NsSchemaOrg+"Newspaper");
	public static final Resource SCHEMAORG_PERIODICAL  = ResourceFactory.createResource(NsSchemaOrg+"Periodical");
	public static final Resource SCHEMAORG_PHOTOGRAPH  = ResourceFactory.createResource(NsSchemaOrg+"Photograph");
	public static final Resource SCHEMAORG_DISTANCE  = ResourceFactory.createResource(NsSchemaOrg+"Distance");
	public static final Resource SCHEMAORG_VISUAL_ARTWORK  = ResourceFactory.createResource(NsSchemaOrg+"VisualArtwork");
	public static final Resource SCHEMAORG_PAINTING  = ResourceFactory.createResource(NsSchemaOrg+"Painting");
	public static final Resource SCHEMAORG_WEB_PAGE  = ResourceFactory.createResource(NsSchemaOrg+"WebPage");
	public static final Resource SCHEMAORG_BOOK  = ResourceFactory.createResource(NsSchemaOrg+"Book");
	public static final Resource SCHEMAORG_MEDIA_OBJECT  = ResourceFactory.createResource(NsSchemaOrg+"MediaObject");
//	public static final Resource SCHEMAORG_  = ResourceFactory.createResource(NsSchemaOrg+"");
	
	public static final Property SCHEMAORG_NAME  = ResourceFactory.createProperty(NsSchemaOrg+"name");
	public static final Property SCHEMAORG_GIVEN_NAME  = ResourceFactory.createProperty(NsSchemaOrg+"givenName");
	public static final Property SCHEMAORG_FAMILY_NAME  = ResourceFactory.createProperty(NsSchemaOrg+"familyName");
	public static final Property SCHEMAORG_DESCRIPTION = ResourceFactory.createProperty(NsSchemaOrg+"description");
	public static final Property SCHEMAORG_GENDER = ResourceFactory.createProperty(NsSchemaOrg+"gender");
	public static final Property SCHEMAORG_BIRTH_DATE = ResourceFactory.createProperty(NsSchemaOrg+"birthDate");
	public static final Property SCHEMAORG_DEATH_DATE = ResourceFactory.createProperty(NsSchemaOrg+"deathDate");
	public static final Property SCHEMAORG_LATITUDE = ResourceFactory.createProperty(NsSchemaOrg+"latitude");
	public static final Property SCHEMAORG_LONGITUDE = ResourceFactory.createProperty(NsSchemaOrg+"longitude");
	public static final Property SCHEMAORG_GEO = ResourceFactory.createProperty(NsSchemaOrg+"geo");
	public static final Property SCHEMAORG_ADDRESS = ResourceFactory.createProperty(NsSchemaOrg+"address");
	public static final Property SCHEMAORG_ALTERNATE_NAME = ResourceFactory.createProperty(NsSchemaOrg+"alternateName");
	public static final Property SCHEMAORG_ADDRESS_REGION = ResourceFactory.createProperty(NsSchemaOrg+"addressRegion");
	public static final Property SCHEMAORG_ABOUT = ResourceFactory.createProperty(NsSchemaOrg+"about");
	public static final Property SCHEMAORG_KEYWORDS = ResourceFactory.createProperty(NsSchemaOrg+"keywords");
	public static final Property SCHEMAORG_IS_PART_OF = ResourceFactory.createProperty(NsSchemaOrg+"isPartOf");
	public static final Property SCHEMAORG_HAS_PART = ResourceFactory.createProperty(NsSchemaOrg+"hasPart");
	public static final Property SCHEMAORG_URL = ResourceFactory.createProperty(NsSchemaOrg+"url");
	public static final Property SCHEMAORG_THUMBNAIL_URL = ResourceFactory.createProperty(NsSchemaOrg+"thumbnailUrl");
	public static final Property SCHEMAORG_IMAGE = ResourceFactory.createProperty(NsSchemaOrg+"image");
	public static final Property SCHEMAORG_IN_LANGUAGE = ResourceFactory.createProperty(NsSchemaOrg+"inLanguage");
	public static final Property SCHEMAORG_DATE_CREATED = ResourceFactory.createProperty(NsSchemaOrg+"dateCreated");
	public static final Property SCHEMAORG_CONTENT_LOCATION = ResourceFactory.createProperty(NsSchemaOrg+"contentLocation");
	public static final Property SCHEMAORG_CREATOR = ResourceFactory.createProperty(NsSchemaOrg+"creator");
	public static final Property SCHEMAORG_AUDIO = ResourceFactory.createProperty(NsSchemaOrg+"audio");
	public static final Property SCHEMAORG_CONTRIBUTOR = ResourceFactory.createProperty(NsSchemaOrg+"contributor");
	public static final Property SCHEMAORG_PUBLISHER = ResourceFactory.createProperty(NsSchemaOrg+"publisher");
	public static final Property SCHEMAORG_MATERIAL = ResourceFactory.createProperty(NsSchemaOrg+"material");
	public static final Property SCHEMAORG_ENCODING_FORMAT = ResourceFactory.createProperty(NsSchemaOrg+"encodingFormat");
	public static final Property SCHEMAORG_UPLOAD_DATE = ResourceFactory.createProperty(NsSchemaOrg+"uploadDate");
	public static final Property SCHEMAORG_CONTENT_URL = ResourceFactory.createProperty(NsSchemaOrg+"contentUrl");
	public static final Property SCHEMAORG_SAME_AS = ResourceFactory.createProperty(NsSchemaOrg+"sameAs");
	public static final Property SCHEMAORG_JOB_TITLE = ResourceFactory.createProperty(NsSchemaOrg+"jobTitle");
	public static final Property SCHEMAORG_TITLE = ResourceFactory.createProperty(NsSchemaOrg+"title");
	public static final Property SCHEMAORG_FILE_FORMAT = ResourceFactory.createProperty(NsSchemaOrg+"fileFormat");
	public static final Property SCHEMAORG_WIDTH = ResourceFactory.createProperty(NsSchemaOrg+"width");
	public static final Property SCHEMAORG_HEIGHT = ResourceFactory.createProperty(NsSchemaOrg+"height");
	public static final Property SCHEMAORG_ASSOCIATED_MEDIA = ResourceFactory.createProperty(NsSchemaOrg+"associatedMedia");
	public static final Property SCHEMAORG_ART_MEDIUM = ResourceFactory.createProperty(NsSchemaOrg+"artMedium");
	public static final Property SCHEMAORG_GENRE = ResourceFactory.createProperty(NsSchemaOrg+"genre");
	public static final Property SCHEMAORG_COPYRIGHT_HOLDER = ResourceFactory.createProperty(NsSchemaOrg+"copyrightHolder");
	public static final Property SCHEMAORG_PROVIDER = ResourceFactory.createProperty(NsSchemaOrg+"provider");
	public static final Property SCHEMAORG_ARTFORM = ResourceFactory.createProperty(NsSchemaOrg+"artform");
	public static final Property SCHEMAORG_ARTWORK_SURFACE = ResourceFactory.createProperty(NsSchemaOrg+"artworkSurface");
	public static final Property SCHEMAORG_TEXT = ResourceFactory.createProperty(NsSchemaOrg+"text");
	public static final Property SCHEMAORG_ADDITIONAL_TYPE = ResourceFactory.createProperty(NsSchemaOrg+"additionalType");
	public static final Property SCHEMAORG_LOCATION_CREATED = ResourceFactory.createProperty(NsSchemaOrg+"locationCreated");
	public static final Property SCHEMAORG_EXAMPLE_OF_WORK = ResourceFactory.createProperty(NsSchemaOrg+"exampleOfWork");
	public static final Property SCHEMAORG_MAIN_ENTITY_OF_PAGE = ResourceFactory.createProperty(NsSchemaOrg+"mainEntityOfPage");
	public static final Property SCHEMAORG_AUTHOR = ResourceFactory.createProperty(NsSchemaOrg+"author");
	public static final Property SCHEMAORG_SPATIAL_COVERAGE = ResourceFactory.createProperty(NsSchemaOrg+"spatialCoverage");
	public static final Property SCHEMAORG_LOCATION = ResourceFactory.createProperty(NsSchemaOrg+"location");
	public static final Property SCHEMAORG_TEMPORAL_COVERAGE = ResourceFactory.createProperty(NsSchemaOrg+"temporalCoverage");
	public static final Property SCHEMAORG_LICENSE = ResourceFactory.createProperty(NsSchemaOrg+"license");
//	public static final Property SCHEMAORG_ = ResourceFactory.createProperty(NsSchemaOrg+"");
	public static final Property VOID_ROOT_RESOURCE = ResourceFactory.createProperty(NsVoid+"rootResource");
	public static final Property VOID_DATA_DUMP = ResourceFactory.createProperty(NsVoid+"dataDump");
	public static final Property SCHEMAORG_IDENTIFIER = ResourceFactory.createProperty(NsSchemaOrg+"identifier");
	public static final Property SCHEMAORG_NUMBER_OF_PAGES = ResourceFactory.createProperty(NsSchemaOrg+"numberOfPages");
	public static final Property SCHEMAORG_PAGINATION = ResourceFactory.createProperty(NsSchemaOrg+"pagination");
	public static final Property DC_IDENTIFIER = ResourceFactory.createProperty(NsDc+"identifier");

	public static final Property SCHEMAORG_QUERY = ResourceFactory.createProperty(NsSchemaOrg+"query");
	public static final Property DCAT_ACCESS_SERVICE = ResourceFactory.createProperty(NsDcat+"accessService");
	public static final Property DCAT_ACCESS_URL = ResourceFactory.createProperty(NsDcat+"accessUrl");
	public static final Property DCAT_DISTRIBUTION = ResourceFactory.createProperty(NsDcat+"distribution");
	public static final Property DCAT_DOWNLOAD_URL = ResourceFactory.createProperty(NsDcat+"downloadURL");
	public static final Property DCAT_MEDIA_TYPE = ResourceFactory.createProperty(NsDcat+"mediaType");
	public static final Property DCAT_ENDPOINT_URL = ResourceFactory.createProperty(NsDcat+"endpointURL");
	public static final Resource DCAT_DATA_SERVICE  = ResourceFactory.createResource(NsDcat+"DataService");
	public static final Resource DCAT_DISTRIBUTION_CLASS  = ResourceFactory.createResource(NsDcat+"Distribution");
	public static final Property PROV_WAS_GENERATED_BY = ResourceFactory.createProperty(NsProv+"wasGeneratedBy");
	public static final Property VOID_SPARQL_ENDPOINT = ResourceFactory.createProperty(NsVoid+"sparqlEndpoint");
	
	
	
	
}