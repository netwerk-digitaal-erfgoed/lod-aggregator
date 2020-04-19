package eu.europeana.commonculture.lod.datasetmetadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import eu.europeana.commonculture.lod.crawler.http.AccessException;
import eu.europeana.commonculture.lod.crawler.rdf.RdfReg;
import eu.europeana.commonculture.lod.crawler.rdf.RdfRegRdf;
import eu.europeana.commonculture.lod.crawler.rdf.RdfUtil;
import eu.europeana.commonculture.lod.crawler.rdf.RegSchemaorg;

public class DatasetDescription {
	String datasetUri;
	Model model;
	
	public DatasetDescription(String datasetUri) throws AccessException, InterruptedException, IOException {
		this.datasetUri = datasetUri;
		model = RdfUtil.readRdfFromUri(datasetUri);
		if (model!=null && RdfUtil.contains(datasetUri, model))
			return;
		//Search for an RDF resource for a Dataset
		Set<Resource> dss=new HashSet<Resource>();
		for(Resource r : model.listResourcesWithProperty(RdfRegRdf.type, RegSchemaorg.Dataset).toList()) 
			dss.add(r);
		for(Resource r : model.listResourcesWithProperty(RdfRegRdf.type, RdfReg.DCAT_DATASET).toList()) 
			dss.add(r);
		for(Resource r : model.listResourcesWithProperty(RdfRegRdf.type, RdfReg.VOID_DATASET).toList()) 
			dss.add(r);
		if(dss.size()==1)
			datasetUri=dss.iterator().next().getURI();
		else if(dss.size()>1)
			throw new AccessException(datasetUri, "More than one Dataset found at URL");
		throw new AccessException(datasetUri, "No RDF resource available for the dataset URI");
	}

	public HarvestMethod chooseHarvestMethod() {
		Resource  dsResource=model.createResource(datasetUri);
		if (dsResource==null ) return null;
		Statement voidRootResource = dsResource.getProperty(RdfReg.VOID_ROOT_RESOURCE);
		if(voidRootResource!=null)
			return HarvestMethod.ROOT_RESOURCES;
		if(getSparqlEndpointUrl()!=null)
			return HarvestMethod.SPARQL;
		if (!getDistributions().isEmpty())
			return HarvestMethod.DISTRIBUTION;
		return null;
	}
	
	public List<String> listRootResources() throws AccessException, InterruptedException, IOException{
		ArrayList<String> uris= new ArrayList<String>();
		Resource  dsResource=model.createResource(datasetUri);
		if (dsResource==null ) return uris;
		StmtIterator voidRootResources = dsResource.listProperties(RdfReg.VOID_ROOT_RESOURCE);
		voidRootResources.forEachRemaining(st -> {
				if(st.getObject().isURIResource())
					uris.add(st.getObject().asResource().getURI());
			}
		);
		return uris;
	}


	private String getSparqlEndpointUrl() {
		Resource  dsResource=model.createResource(datasetUri);
		if (dsResource==null ) return null;
		
		Statement endPointUrl = dsResource.getProperty(RdfReg.VOID_SPARQL_ENDPOINT);
		if (endPointUrl!=null ) return RdfUtil.getUriOrLiteralValue(endPointUrl.getObject());
		
		Resource distribution=RdfUtil.findResource(dsResource, RdfReg.DCAT_DISTRIBUTION);
		if (distribution==null ) 
			distribution=RdfUtil.findResource(dsResource, RegSchemaorg.distribution);
		if (distribution==null ) 
			return null;
		Resource dataService=RdfUtil.findResource(distribution, RdfReg.DCAT_ACCESS_SERVICE);
		if (dataService==null ) {
			endPointUrl = distribution.getProperty(RdfReg.DCAT_ACCESS_URL);
			if(endPointUrl!=null) 
				if (isSparqlCompliant(distribution.listProperties(RdfReg.DCTERMS_CONFORMS_TO)))
					return RdfUtil.getUriOrLiteralValue(endPointUrl.getObject());
			Resource generatedBy=RdfUtil.findResource(distribution, model.createProperty("http://www.w3.org/ns/prov#wasGeneratedBy"));
			if(generatedBy!=null) 
				dataService=RdfUtil.findResource(generatedBy, model.createProperty("http://www.w3.org/ns/prov#used"));
		}
		if (dataService==null ) 			
			return null;
		
		if (!isSparqlCompliant(dataService.listProperties(RdfReg.DCTERMS_CONFORMS_TO))) return null;
		endPointUrl = dataService.getProperty(RdfReg.DCAT_ENDPOINT_URL);
		if (endPointUrl==null ) return null;
		
		return RdfUtil.getUriOrLiteralValue(endPointUrl.getObject());
	}
	
	private boolean isSparqlCompliant(StmtIterator conforms) {
		for(Statement s: conforms.toList()) {
			String standard = RdfUtil.getUriOrLiteralValue(s.getObject());
			if(standard!=null && (standard.equals("http://www.w3.org/TR/sparql11-query/"))) 
				return true;
		}
		return false;
	}
	
	public SparqlEndpoint getSparqlEndpoint() {
			Resource  dsResource=model.createResource(datasetUri);
			if (dsResource==null ) return null;
			String endPointUrl=getSparqlEndpointUrl();
			if (endPointUrl==null ) return null;
			
			SparqlEndpoint endpoint=new SparqlEndpoint(endPointUrl);
		
		Resource searchAction=RdfUtil.findResource(dsResource, RdfReg.DCAT_DISTRIBUTION, RdfReg.PROV_WAS_GENERATED_BY);
		if (searchAction!=null ) {
			Statement query = searchAction.getProperty(RdfReg.SCHEMAORG_QUERY);
			if (query!=null ) 
				endpoint.setQuery(query.getObject().asLiteral().getString().trim());
		}
		return endpoint;
	}

	public List<Distribution> getDistributions(){
		ArrayList<Distribution> uris= new ArrayList<>();
		Resource  dsResource=model.createResource(datasetUri);
		if (dsResource==null ) return uris;
		StmtIterator voidDumps = dsResource.listProperties(RdfReg.VOID_DATA_DUMP);
		voidDumps.forEachRemaining(st -> uris.add(new Distribution(st.getObject().asResource().getURI(), null)));

		StmtIterator distributions = dsResource.listProperties(RdfReg.DCAT_DISTRIBUTION);
		distributions.forEachRemaining(st -> {
			if (st.getObject().isResource()) {
				Statement downloadUrl = st.getResource().getProperty(RdfReg.DCAT_DOWNLOAD_URL);
				if(downloadUrl!=null) {
					Statement mediaType = st.getResource().getProperty(RdfReg.DCAT_MEDIA_TYPE);
					Distribution dist=new Distribution(RdfUtil.getUriOrLiteralValue(downloadUrl.getObject()), 
							mediaType==null ? null : RdfUtil.getUriOrLiteralValue(mediaType.getObject()));
					uris.add(dist);
				}
			}
			});

		distributions = dsResource.listProperties(RegSchemaorg.distribution);
		distributions.forEachRemaining(st -> {
			if (st.getObject().isResource()) {
				Statement downloadUrl = st.getResource().getProperty(RegSchemaorg.contentUrl);
				if(downloadUrl!=null) {
					Statement mediaType = st.getResource().getProperty(RegSchemaorg.encodingFormat);
					Distribution dist=new Distribution(RdfUtil.getUriOrLiteralValue(downloadUrl.getObject()), 
							mediaType==null ? null : RdfUtil.getUriOrLiteralValue(mediaType.getObject()));
					uris.add(dist);
				}
			}
		});
		return uris;
	}

	public Model getRdf() {
		return model;
	}

	public String getUri() {
		return datasetUri;
	}
}
