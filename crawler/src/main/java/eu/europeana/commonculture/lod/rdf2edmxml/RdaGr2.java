package eu.europeana.commonculture.lod.rdf2edmxml;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public final class RdaGr2 {
	public static String PREFIX="rdagr2";
	public static String NS="http://rdvocab.info/ElementsGr2/";

	public static final Property biographicalInformation = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/biographicalInformation");
	public static final Property dateOfBirth = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/dateOfBirth");
	public static final Property dateOfDeath = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/dateOfDeath");
	public static final Property dateOfEstablishment = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/dateOfEstablishment");
	public static final Property dateOfTermination = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/dateOfTermination");
	public static final Property placeOfDeath = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/placeOfDeath");
	public static final Property placeOfBirth = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/placeOfBirth");
	public static final Property professionOrOccupation = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/professionOrOccupation");
	public static final Property gender = ResourceFactory.createProperty("http://rdvocab.info/ElementsGr2/gender");
}