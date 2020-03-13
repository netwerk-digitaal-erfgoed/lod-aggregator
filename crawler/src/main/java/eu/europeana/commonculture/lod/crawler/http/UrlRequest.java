package eu.europeana.commonculture.lod.crawler.http;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;

public class UrlRequest {
	public enum HttpMethod {GET, POST, PUT, HEAD};
	
	String url;
	HttpMethod method=HttpMethod.GET;
	List<AbstractMap.SimpleImmutableEntry<String, String>> headers;
	HttpEntity requestContent;
	boolean refresh=false;
	
	public UrlRequest(String url) {
		super();
		this.url = url;
	}
	public UrlRequest(String url, HttpMethod method) {
		this(url);
		this.method = method;
	}

	public UrlRequest(String url, Date ifModifiedSince) {
		this(url);
		if(ifModifiedSince!=null)
			addHttpHeader(HttpHeaders.IF_MODIFIED_SINCE, getIfModifiedSinceString(ifModifiedSince));
	}
	public UrlRequest(String url, Date ifModifiedSince, String contentTypesForAcceptHeader) {
		this(url);
		if(ifModifiedSince!=null)
			addHttpHeader(HttpHeaders.IF_MODIFIED_SINCE, getIfModifiedSinceString(ifModifiedSince));
		if(contentTypesForAcceptHeader!=null)
			addHttpHeader(HttpHeaders.ACCEPT, contentTypesForAcceptHeader);
	}
	
	public UrlRequest(String url, String httpHeaderName, String httpHeaderValue) {
			this(url);
		headers=new ArrayList<>(1);
		headers.add(new AbstractMap.SimpleImmutableEntry<String,String>(httpHeaderName, httpHeaderValue));
	}
	
	public UrlRequest(String url, HttpMethod method, String httpHeaderName, String httpHeaderValue) {
		this(url, httpHeaderName, httpHeaderValue);
		setMethod(method);
	}
	
	public void addHttpHeader(String httpHeaderName, String httpHeaderValue) {
		if(headers==null)
			headers=new ArrayList<>(5);
		headers.add(new AbstractMap.SimpleImmutableEntry<String,String>(httpHeaderName, httpHeaderValue));		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public HttpMethod getMethod() {
		return method;
	}
	
	public List<AbstractMap.SimpleImmutableEntry<String, String>> getHeaders() {
		return headers==null ? Collections.emptyList() : headers;
	}
	
	public static String getIfModifiedSinceString(Date ifModifiedSince) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat(
	        "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
	    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    return dateFormat.format(ifModifiedSince);
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	@Override
	public String toString() {
		return url ;
	}
	public HttpEntity getRequestContent() {
		return requestContent;
	}
	public void setRequestContent(HttpEntity requestContent) {
		this.requestContent=requestContent;
	}

	
}
