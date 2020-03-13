package eu.europeana.commonculture.lod.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import eu.europeana.commonculture.lod.crawler.http.AccessException;
import eu.europeana.commonculture.lod.crawler.rdf.RdfReg;
import eu.europeana.commonculture.lod.crawler.rdf.RdfUtil;
import eu.europeana.commonculture.lod.crawler.rdf.RegSchemaorg;


public class DatasetDescription {
	String datasetUri;
	Model model;
	
	public DatasetDescription(String datasetUri) throws AccessException, InterruptedException, IOException {
		this.datasetUri = datasetUri;
		model = RdfUtil.readRdfFromUri(datasetUri);
		if (model==null || !RdfUtil.contains(datasetUri, model))
			throw new AccessException(datasetUri, "No RDF resource available for the dataset URI");
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



	public String getSparqlEndpoint() {
		Resource  dsResource=model.createResource(datasetUri);
		if (dsResource==null ) return null;
		Resource dataService=RdfUtil.findResource(dsResource, RdfReg.DCAT_DISTRIBUTION, RdfReg.DCAT_ACCESS_SERVICE);
		if (dataService==null ) return null;
		
		boolean isSparqlConformant=false;
		StmtIterator conforms = dataService.listProperties(RdfReg.DCTERMS_CONFORMS_TO);
		for(Statement s: conforms.toList()) {
			String standard = RdfUtil.getUriOrLiteralValue(s.getObject());
			if(standard!=null && (standard.equals("http://www.w3.org/2005/09/sparql-protocol-types/#") 
					|| standard.equals("http://www.w3.org/2005/sparql-results#") 
					|| standard.equals("http://www.w3.org/2005/08/sparql-protocol-query/#"))) {
				isSparqlConformant=true;
				break;
			}
		}
		if (!isSparqlConformant) return null;
		
		Statement endPoint = dataService.getProperty(RdfReg.DCAT_ENDPOINT_URL);
		if (endPoint==null ) return null;
		return RdfUtil.getUriOrLiteralValue(endPoint.getObject());		
	}

	public String getSparqlEndpointQuery() {
		Resource  dsResource=model.createResource(datasetUri);
		if (dsResource==null ) return null;
		Resource searchAction=RdfUtil.findResource(dsResource, RdfReg.DCAT_DISTRIBUTION, RdfReg.PROV_WAS_GENERATED_BY);
		if (searchAction==null ) return null;
		Statement query = searchAction.getProperty(RdfReg.SCHEMAORG_QUERY);
		if (query==null ) return null;
		return query.getObject().asLiteral().getString() ;		
	}
	
	public List<Distribution> getDistributions(){
		ArrayList<Distribution> uris= new ArrayList<>();
		Resource  dsResource=model.createResource(datasetUri);
		if (dsResource==null ) return uris;
		StmtIterator voidRootResources = dsResource.listProperties(RdfReg.VOID_DATA_DUMP);
		voidRootResources.forEachRemaining(st -> uris.add(new Distribution(st.getObject().asResource().getURI(), null)));

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
}
