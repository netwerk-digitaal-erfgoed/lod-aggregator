package eu.europeana.commonculture.lod.rdf2edmxml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class EdmRdfToXmlSerializer {
	boolean discardDataTypes=true;
	
	Resource cho;
	Resource aggregation;

	Document edmDom;
	Element rootEl;

	HashSet<String> addedResources=new HashSet<>();
	List<Resource> toAddResources=new ArrayList<>();

	public EdmRdfToXmlSerializer(Resource cho, Resource aggregation) {
		super();
		this.cho = cho;
		this.aggregation = aggregation;
	}

	public Document getXmlDom() {
		edmDom = XmlUtil.newDocument();
		rootEl = edmDom.createElementNS(Rdf.NS, "rdf:RDF");		
		edmDom.appendChild(rootEl);
		rootEl.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		
		for(Entry<String, String> ns : Edm.NS_EXTERNAL_PREFERRED_BY_NS.entrySet()) {
//			rootEl.setAttribute("xmlns:"+ns.getValue(), ns.getKey());
			rootEl.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:"+ns.getValue(), ns.getKey());
		}
		addedResources.add(getUriOrAnonId(cho));
		Element choEl = edmDom.createElementNS(Edm.NS, "edm:ProvidedCHO");
		choEl.setAttributeNS(Rdf.NS, "rdf:about", cho.getURI());
		rootEl.appendChild(choEl);

		StmtIterator choStms = cho.listProperties();
		while (choStms.hasNext()) {
				Statement st = choStms.next();
//			System.out.println(st);
			if(st.getPredicate().equals(Rdf.type)) 
				continue;//it is a CHO 
			
			Element predicateEl=createXmlElement(st);
			
 			RDFNode object = st.getObject();
	        if (object.isURIResource()) {
	        	predicateEl.setAttributeNS(Rdf.NS, "rdf:resource", ((Resource) object).getURI());
	        	if(! addedResources.contains(getUriOrAnonId(object.asResource())))
	        		toAddResources.add(object.asResource());
	        }else if (object.isAnon()) {
	        	predicateEl.setAttributeNS(Rdf.NS, "rdf:resource", ((Resource) object).getId().getLabelString());
	        	if(! addedResources.contains(getUriOrAnonId((Resource) object)))
	        		toAddResources.add(((Resource) object));	 
	        } else if (object.isLiteral()) {
	            String litStr = ((Literal)object).getString();
	            if(StringUtils.isEmpty(litStr))
	            	continue;
				predicateEl.setTextContent(litStr);
	            if(!StringUtils.isEmpty(((Literal)object).getLanguage()))
	                predicateEl.setAttributeNS(RdfReg.NsXml, "xml:lang", ((Literal)object).getLanguage());
	            if(!discardDataTypes)
	            	if(((Literal)object).getDatatype()!=null && !((Literal)object).getDatatype().getURI().equals("http://www.w3.org/2001/XMLSchema#string"))
	            		predicateEl.setAttributeNS(Rdf.NS, "rdf:datatype", ((Literal)object).getDatatype().getURI());
	        } else
	            throw new RuntimeException("? what to do ?"+ object.getClass().getName());
	        choEl.appendChild(predicateEl);
		}
		choStms.close();
				
		Element aggResourceToXml = resourceToXml(aggregation);
		if(aggResourceToXml!=null)
			rootEl.appendChild(aggResourceToXml);
			
		for(int i=0; i<toAddResources.size(); i++) {
			Resource r=toAddResources.get(i);
			Element resourceToXml = resourceToXml(r);
			if(resourceToXml!=null)
				rootEl.appendChild(resourceToXml);
		}
		
		return edmDom;
	}

	private String getUriOrAnonId(Resource cho2) {
		if(cho2.isAnon())
			return cho2.getId().getLabelString();
		return cho2.getURI();
	}

	private Element resourceToXml(Resource r) {
		if(addedResources.contains(getUriOrAnonId(r)))
			return null;
		Resource edmClass=findEdmClassOfResource(r);
		if(edmClass == null) 
			return null;
		
		Element resourceEl = createXmlClassElement(edmClass, getUriOrAnonId(r));
		addedResources.add(getUriOrAnonId(r));
		
		StmtIterator props = r.listProperties();
		while (props.hasNext()) {
			Statement st = props.next();
			
			if(st.getPredicate().equals(Rdf.type) && st.getObject().equals(edmClass)) 
				continue;//it's the element type 
			
			Element predicateEl=createXmlElement(st);
			
			RDFNode object = st.getObject();
	        if (object.isURIResource()) {
//	        	System.out.println(st);
	        	predicateEl.setAttributeNS(Rdf.NS, "rdf:resource", ((Resource) object).getURI());
	        	if(! addedResources.contains(getUriOrAnonId((Resource) object)))
	        		toAddResources.add(((Resource) object));
	        }else if (object.isAnon()) {
	        	predicateEl.setAttributeNS(Rdf.NS, "rdf:resource", ((Resource) object).getId().getLabelString());
	        	if(! addedResources.contains(getUriOrAnonId((Resource) object)))
	        		toAddResources.add(((Resource) object));	        	
	        } else if (object.isLiteral()) {
	            String litStr = ((Literal)object).getString();
	            if(StringUtils.isEmpty(litStr))
	            	continue;
				predicateEl.setTextContent(litStr);
//	            predicateEl.setTextContent(((Literal)object).getString());
                if(!StringUtils.isEmpty(((Literal)object).getLanguage()))
	                predicateEl.setAttributeNS(RdfReg.NsXml, "xml:lang", ((Literal)object).getLanguage());
                if(!discardDataTypes)
	            	if(((Literal)object).getDatatype()!=null && !((Literal)object).getDatatype().getURI().equals("http://www.w3.org/2001/XMLSchema#string"))
		                predicateEl.setAttributeNS(Rdf.NS, "rdf:datatype", ((Literal)object).getDatatype().toString());
	        } else
	            throw new RuntimeException("? what to do ?"+ object.getClass().getName());
	        resourceEl.appendChild(predicateEl);
		}
		
		return resourceEl;
	}


	private Resource findEdmClassOfResource(Resource r) {
		StmtIterator types = r.getModel().listStatements(r, Rdf.type, (RDFNode)null);
		while (types.hasNext()) {
			RDFNode object = types.next().getObject();
			if(object.isURIResource() && EdmReg.isEdmClass((Resource)object))
				return object.asResource();
		}
		return null;
	}

	private Element createXmlElement(Statement st) {
		String propUri = st.getPredicate().getURI();
		int endOfPrefixIdx=Math.max(propUri.lastIndexOf('#'), propUri.lastIndexOf('/'))+1; 
		String ns = propUri.substring(0, endOfPrefixIdx);
		String elName = propUri.substring(endOfPrefixIdx);
		Element stEl=edmDom.createElementNS(ns, Edm.NS_EXTERNAL_PREFERRED_BY_NS.get(ns)+":"+elName);
		return stEl;
	}

	private Element createXmlClassElement(Resource edmClassResourceType, String aboutUri) {
		String propUri = edmClassResourceType.getURI();
		int endOfPrefixIdx=Math.max(propUri.lastIndexOf('#'), propUri.lastIndexOf('/'))+1; 
		String ns = propUri.substring(0, endOfPrefixIdx);
		String elName = propUri.substring(endOfPrefixIdx);
		Element stEl=edmDom.createElementNS(ns, Edm.NS_EXTERNAL_PREFERRED_BY_NS.get(ns)+":"+elName);
		stEl.setAttributeNS(Rdf.NS, "rdf:about", aboutUri);
		return stEl;
	}
	
}
