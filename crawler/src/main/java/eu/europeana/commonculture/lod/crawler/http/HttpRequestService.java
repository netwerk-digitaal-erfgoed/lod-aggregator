package eu.europeana.commonculture.lod.crawler.http;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;

import eu.europeana.commonculture.lod.crawler.http.UrlRequest.HttpMethod;

public class HttpRequestService {
	public static final HttpRequestService INSTANCE = new HttpRequestService();
	static {
		INSTANCE.init();
	}

	private static TaskSyncManager taskSyncManager = new TaskSyncManager();

	CloseableHttpClient httpClient;
	CookieStore httpCookieStore;
	boolean followRedirects=true;

//	ArrayList<FetchRequest> requestsQueue=new ArrayList<>(50);

	Vector<Long> requestTimeStats = null;
//	Vector<Long> requestTimeStats=new Vector<>();

	public void init() {
		this.init(null, null);
	}

	public void init(String username, File credentials) {
		if (DevelopementSingleton.DEVEL_TEST && DevelopementSingleton.HTTP_REQUEST_TIME_STATS)
			requestTimeStats = new Vector<>();

		SSLContextBuilder builder = new SSLContextBuilder();
		try {
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(),
					NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", new PlainConnectionSocketFactory()).register("https", sslConnectionSocketFactory)
					.build();

			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(200);
			cm.setDefaultMaxPerRoute(41);

			httpCookieStore = new BasicCookieStore();

			HttpClientBuilder httpClientBuilder = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory)
					.setConnectionManager(cm).setDefaultCookieStore(httpCookieStore)
//                .setRedirectStrategy(new LaxRedirectStrategy())
			;
			if (username != null) {
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(new AuthScope(AuthScope.ANY),
						new UsernamePasswordCredentials(username, FileUtils.readFileToString(credentials, "UTF-8")));
				httpClientBuilder.setDefaultCredentialsProvider(credsProvider);

			}
			httpClient = httpClientBuilder.build();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

//		 PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
//		 cm.setMaxTotal(100);
//		 CloseableHttpClient httpclient = HttpClients.custom()
//		            .setSSLSocketFactory(sslConnectionSocketFactory)
//		            .setConnectionManager(cm)
//		            .build();

	}

	
	
	public void close() throws Exception {
		httpClient.close();
	}

	public void start() throws Exception {

	}

	public void stop() throws Exception {

	}

	public void fetch(HttpRequest url) throws InterruptedException, IOException {
		taskSyncManager.acquireHttpFetch();
		try {
			Long startTime = requestTimeStats != null ? System.nanoTime() : null;
			HttpRequestBase request;
			if (url.getHttpMethod() == null || url.getHttpMethod() == HttpMethod.GET)
				request = new HttpGet(url.getUrl());
			else if (url.getHttpMethod() == HttpMethod.HEAD)
				request = new HttpHead(url.getUrl());
			else if (url.getHttpMethod() == HttpMethod.POST) {
				request = new HttpPost(url.getUrl());
				if(url.getRequestContent()!=null) 
					((HttpPost)request).setEntity(url.getRequestContent());
//			    ArrayList<NameValuePair> postParameters;
//			    postParameters = new ArrayList<NameValuePair>();
//			    postParameters.add(new BasicNameValuePair("param1", "param1_value"));
//			    postParameters.add(new BasicNameValuePair("param2", "param2_value"));
			} else
				throw new RuntimeException("Not implemented: " + url.getHttpMethod());

			url.addHeaders(request);
			CloseableHttpResponse response = httpClient.execute(request);

//			if(httpCookieStore.getCookies().size()>50) {
//				httpCookieStore.clearExpired(new Date());
//			}
			if (httpCookieStore.getCookies().size() > 100)
				httpCookieStore.clear();
//			System.out.println("cookies: "+httpCookieStore.getCookies().size());
//			System.out.println("cookies: "+httpCookieStore.getCookies());

			if (startTime != null) {
				long duration = System.nanoTime() - startTime;
				synchronized (requestTimeStats) {
					requestTimeStats.add(duration);
					if (requestTimeStats.size() % 10 == 0) {
						long sum = 0;
						for (int i = requestTimeStats.size() - 1; i >= requestTimeStats.size() - 10; i--)
							sum += requestTimeStats.get(i);
						long recTime = sum / 10;
						float recRate = 60000000000f / recTime;
						float recRateHour = recRate * 60 * 60;
						System.out.println("10 Requests done at " + recTime + "ns/rec - " + recRate + " recs/sec "
								+ recRateHour + " recs/hour");
					}
				}
			}
			url.setResponse(response);
		} finally {
			taskSyncManager.releaseHttpFetch();
		}
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

//	public String printStatus() {
//		return String.format("Http Fetcher Status: %d avail., %d queued [Priority: %d avail., %d queued]",fetchSemaphore.availablePermits(), fetchSemaphore.getQueueLength(),fetchWithPrioritySemaphore.availablePermits(), fetchWithPrioritySemaphore.getQueueLength()); 
//	}

}
