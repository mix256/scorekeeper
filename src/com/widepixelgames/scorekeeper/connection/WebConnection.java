package com.widepixelgames.scorekeeper.connection;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.widepixelgames.scorekeeper.logging.RegisterLogger;
import com.widepixelgames.scorekeeper.properties.GlobalProperties;

import android.os.AsyncTask;

public class WebConnection extends AsyncTask<String, Integer, HttpResponse>{

	private final static int CONNECTION_TIMEOUT = 15000;
	private final static int SOCKET_TIMEOUT     = 15000;	

	private WebConnectionResponseListener listener;

	public void login(String user, String password, WebConnectionResponseListener listener){
		
		this.listener = listener;
		String rawtextContainingProperties = GlobalProperties.getInstance().getString("${login_format}", "not_found:login_format");
		GlobalProperties.getInstance().put("${user}", user);
		GlobalProperties.getInstance().put("${password}", password);
		
		String params = GlobalProperties.getInstance().resolve(rawtextContainingProperties);
		String host = GlobalProperties.getInstance().getString("${host}", "not_found:host");
		System.out.println(host + " " + params);
		execute(host, params, "");
	}

	public void registerWithEntry(String userName, String score, boolean voidEntry, boolean completeEntry, WebConnectionResponseListener listener){
		
		this.listener = listener;
		
		String rawtextContainingProperties = "";
		String rawtextForLogContainingProperties = "";
		if(completeEntry){
			rawtextContainingProperties = GlobalProperties.getInstance().getString("${registerscore_format_with_entry}", "not_found:registerscore_format_with_entry");
			rawtextForLogContainingProperties = GlobalProperties.getInstance().getString("${registerscore_format_with_entry_for_logger}", "not_found:registerscore_format_for_logger");
		} else {
			rawtextContainingProperties = GlobalProperties.getInstance().getString("${registerscore_format}", "not_found:registerscore_format_with_entry");
			rawtextForLogContainingProperties = GlobalProperties.getInstance().getString("${registerscore_format_for_logger}", "not_found:registerscore_format_for_logger");
		}

		GlobalProperties.getInstance().put("${score}", score);
		GlobalProperties.getInstance().put("${void}", voidEntry ? "true" : "false");
		
		String params = GlobalProperties.getInstance().resolve(rawtextContainingProperties);
		String logParams = GlobalProperties.getInstance().resolve(rawtextForLogContainingProperties);
		
		String host = GlobalProperties.getInstance().getString("${host}", "not_found:host");
		RegisterLogger.log(userName, logParams);
		execute(host, params, logParams);
	}
	
	@Override
	protected HttpResponse doInBackground(String... url) {
		
		String originalMessage = url[2];
		String[] p = url[1].split("\\?");
		String params = p[1];
		String phpFile = p[0];
		String host = url[0];

	    HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
	    HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);
		
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    String[] paramPairs = params.split("&");
	    for(String paramPair : paramPairs){
	    	String[] keyAndValue = paramPair.split("=");
	    	nameValuePairs.add(new BasicNameValuePair(keyAndValue[0], keyAndValue[1]));
	    }

		HttpResponse response = null;
		HttpContext localContext = new BasicHttpContext();
		HttpClient httpClient = getNewHttpClient();
		
		try {
			HttpPost post = new HttpPost(host + phpFile);
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpClient.execute(post, localContext);
			HttpEntity entity = response.getEntity();
			
			if(entity != null){
				String result = EntityUtils.toString(entity).trim();
				listener.done(result, originalMessage);
			} else {
				listener.done("no_entry", originalMessage);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			listener.done("exception", originalMessage);
		}
		
		return response;
	}

	public HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}	
	
}
