package eu.europeana.commonculture.lod.datasetmetadata;

public class SparqlEndpoint {
	String url;
	String query;
	public SparqlEndpoint(String url) {
		this.url=url;
	}
	
	public SparqlEndpoint(String url, String query) {
		super();
		this.url = url;
		this.query = query;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
}
