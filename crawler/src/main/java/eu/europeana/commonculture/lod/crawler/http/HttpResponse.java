package eu.europeana.commonculture.lod.crawler.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.apache.http.Header;

public class HttpResponse {
	public HttpResponse(byte[] body, List<Entry<String, String>> headers, int status) {
		super();
		this.body = body;
		this.headers = headers;
		this.status = status;
	}
	public HttpResponse(HttpRequest req) throws IOException {
		headers=new ArrayList<>(5);
		for(String headerName: new String[] { "Content-Type", "Content-Encoding", "Content-Disposition"/*, "Content-MD5"*/}) {
			for(Header h : req.getResponseHeaders(headerName))
				headers.add(new SimpleEntry<String, String>(h.getName(), h.getValue()));
		}
		body=req.getContent().asBytes(); 
		status=req.getResponseStatusCode();
	}
	public byte[] body; 
	public List<Entry<String, String>> headers;
	public int status=-1;
	public String getHeader(String headerToGet) {
		for (Entry<String, String> h : headers) {
			if(h.getKey().equalsIgnoreCase(headerToGet))
				return h.getValue();
		}
		return null;
	}
	public boolean isSuccess() {
		return status==200 && hasBody();
	}
	public boolean hasBody() {
		return body!=null && body.length>0;
	}
	public String getContentType() {
		return getHeader("Content-Type");
	}
	public AccessException throwException(String resourceUrl) {
		String msg=null;
		if (hasBody()) {
			try {
				msg=new String(body, "UTF8");
				if(msg.length()>300)
					msg=msg.substring(0, 300);
			} catch (UnsupportedEncodingException e) { msg=null; }
		}
		return new AccessException(resourceUrl, status, msg);
	}
}