package eu.europeana.commonculture.lod.datasetmetadata;

public class Distribution {
	String url;
	String contentType;
	
	public Distribution(String url, String contentType) {
		super();
		this.url = url;
		this.contentType = contentType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
