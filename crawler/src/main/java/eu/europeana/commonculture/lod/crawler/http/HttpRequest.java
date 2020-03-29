package eu.europeana.commonculture.lod.crawler.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;

import eu.europeana.commonculture.lod.crawler.http.UrlRequest.HttpMethod;


public class HttpRequest {
	UrlRequest url;
//	String contentTypeToRequest;
//	CrawlingSession session;
//	Semaphore fetchingSemaphore=new Semaphore(1);
	CloseableHttpResponse response;
	Content content;
	Throwable error;
	private boolean streaming;

	public HttpRequest(String url) {
		super();
		this.url = new UrlRequest(url);
	}
	
	public HttpRequest(UrlRequest url) {
		super();
		this.url = url;
	}

//	public HttpRequest(UrlRequest url, CrawlingSession session) {
//		super();
//		this.url = url;
//		this.session = session;
//	}



//	public HttpRequest(UrlRequest url, CrawlingSession crawlingSession, Throwable error) {
//		this(url, crawlingSession);
//		this.error=error;
//	}

	public String getUrl() {
		return url.getUrl();
	}
	public UrlRequest getUrlRequest() {
		return url;
	}

	public HttpMethod getHttpMethod() {
		return url.getMethod();
	}
	
//	public void setUrl(UrlRequest url) {
//		this.url = url;
//	}
//
//	public CrawlingSession getSession() {
//		return session;
//	}
//
//	public void setSession(CrawlingSession session) {
//		this.session = session;
//	}
	
	
	/**
	 * May be used by another thread to wait for the request to be processed
	 * @throws IOException 
	 * @throws UnsupportedOperationException 
	 * 
	 * @throws InterruptedException
	 */
//	public void waitForFetchReady() throws InterruptedException {
//		fetchingSemaphore.acquire();
//		fetchingSemaphore.release();
//	}
	
//	public void fetchReady() {
//		fetchingSemaphore.release();
//	}

	public CloseableHttpResponse getResponse() {
		return response;
	}

	public void setResponse(CloseableHttpResponse response) throws UnsupportedOperationException, IOException {
		this.response = response;
		if(!streaming) {
			if(response.getEntity()!=null) {
				byte[] byteArray = IOUtils.toByteArray(response.getEntity().getContent());
				ContentType contentType = ContentType.get(response.getEntity());
				this.content=new Content(byteArray, contentType);
			}
			response.close();
		}
	}
		
//		fetchingSemaphore.release();

	public int getResponseStatusCode() {
		return response.getStatusLine().getStatusCode();
	}
	public Content getContent() throws IOException {
//		byte[] byteArray = IOUtils.toByteArray(response.getEntity().getContent());
//		ContentType contentType = ContentType.get(response.getEntity());
//		response.close();
//		return new Content(byteArray, contentType);
		return content;
	}

//	public String getContentTypeToRequest() {
//		return contentTypeToRequest;
//	}
//
//	public void setContentTypeToRequest(String contentTypeToRequest) {
//		this.contentTypeToRequest = contentTypeToRequest;
//	}

	public void addHeaders(HttpMessage request) {
		for(SimpleImmutableEntry<String, String> header : url.getHeaders()) {
			request.addHeader(header.getKey(), header.getValue());
		}
	}

	public List<Header> getResponseHeaders(String... headerNames) {
		List<Header> meta=new ArrayList<>(5);
		if(headerNames==null || headerNames.length==0) {
			for(Header h : getResponse().getAllHeaders())
				meta.add(h);			
		}else {
			for(String headerName: headerNames) {
	//			for(String headerName: new String[] { "Content-Type", "Content-Encoding", "Content-Disposition", "Link"/*, "Content-MD5"*/}) {
				for(Header h : getResponse().getHeaders(headerName))
					meta.add(h);
			}
		}
		return meta;
	}	
	public String getResponseHeader(String headerName) {
		List<Header> meta=new ArrayList<>(5);
		for(Header h : getResponse().getHeaders(headerName))
			return h.getValue();
		return null;
	}

	public void redirectUrl(String location) {
		this.url=new UrlRequest(location);
		response=null;
		content=null;
		error=null;
	}

	public HttpRequest fetch() throws InterruptedException, IOException {
		streaming=false;
		HttpRequestService.INSTANCE.fetch(this);
		return this;
	}

	

	public String getHeader(String headerToGet) {
		for (Header h : response.getHeaders(headerToGet)) {
			if(h.getName().equalsIgnoreCase(headerToGet))
				return h.getValue();
		}
		return null;
	}
	public String getResponseContentType() {
		return getHeader("Content-Type");
	}
	public ContentType getResponseContentTypeParsed() {
		String header = getHeader("Content-Type");
		if(header==null)
			return null;
		return ContentType.parse(header);
	}
	
	public HttpEntity getRequestContent() {
		return url.getRequestContent();
	}

	public HttpRequest fetchStream() throws InterruptedException, IOException {
		streaming=true;
		HttpRequestService.INSTANCE.fetch(this);
		return this;
	}
}
