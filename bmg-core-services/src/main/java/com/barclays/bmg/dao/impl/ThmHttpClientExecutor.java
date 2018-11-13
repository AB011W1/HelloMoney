package com.barclays.bmg.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

/**
 * The Class CommonHttpClientExecuter.
 */
public class ThmHttpClientExecutor {
    private static final String CHARSET = "UTF-8";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ThmHttpClientExecutor.class);

    private static final HashMap<String, HttpContext> sessionMap = new HashMap<String, HttpContext>();

    private String url;

    /** The conn manager. */
    private PoolingClientConnectionManager connManager;

    /** The http client. */
    private DefaultHttpClient httpClient;

    /** The host scheme. */
    private String hostScheme;

    /** The host scheme. */
    private String hostName;

    /** The host scheme. */
    private String hostPath;

    /** The max total connections. */
    private int maxTotalConnections = 10;

    /** The host config port. */
    private int hostConfigPort;

    /** The default max per route. */
    private int defaultMaxPerRoute = 3000;

    /**
     * Gets the host scheme.
     * 
     * @return the host scheme
     */
    public String getHostScheme() {
	return hostScheme;
    }

    /**
     * Sets the host scheme.
     * 
     * @param hostScheme
     *            the new host scheme
     */
    public void setHostScheme(final String hostScheme) {
	this.hostScheme = hostScheme;
    }

    /**
     * Gets the max total connections.
     * 
     * @return the max total connections
     */
    public int getMaxTotalConnections() {
	return maxTotalConnections;
    }

    /**
     * Sets the max total connections.
     * 
     * @param maxTotalConnections
     *            the new max total connections
     */
    public void setMaxTotalConnections(final int maxTotalConnections) {
	this.maxTotalConnections = maxTotalConnections;
    }

    /**
     * Gets the host config port.
     * 
     * @return the host config port
     */
    public int getHostConfigPort() {
	return hostConfigPort;
    }

    /**
     * Sets the host config port.
     * 
     * @param hostConfigPort
     *            the new host config port
     */
    public void setHostConfigPort(final int hostConfigPort) {
	this.hostConfigPort = hostConfigPort;
    }

    /**
     * postHttpRequest.
     * 
     * @param url
     *            the url
     * @param localContext
     *            the local context
     * @param queryParamMap
     *            the query param map
     * @return the string
     * @throws Exception
     *             the uSSD blocking exception
     */
    public String processHttpRequest(String custMSISDN, String custPIN) throws Exception {

	String response = null;

	try {
	    int startIndex = -1, endIndex = -1;

	    hostScheme = url.startsWith("https") ? "https" : "http";
	    startIndex = url.indexOf(":");
	    startIndex = url.indexOf(":", startIndex + 1);
	    if (startIndex > 0) {
		hostName = url.substring(hostScheme.length() + 3, startIndex);
		endIndex = url.indexOf("/", startIndex);
		hostConfigPort = Integer.parseInt(url.substring(startIndex + 1, endIndex));
	    } else {
		endIndex = url.indexOf("/", hostScheme.length() + 3);
		hostName = url.substring(hostScheme.length() + 3, endIndex);
		hostConfigPort = 80;
	    }
	    hostPath = url.substring(endIndex);
	    this.afterPropertiesSet();

	    HttpContext localContext = null;

	    localContext = sessionMap.get("custMSISDN");

	    if (localContext == null) {
		localContext = new BasicHttpContext();
		sessionMap.put("custMSISDN", localContext);
	    }

	    response = httpCall(httpClient, localContext, custMSISDN, custPIN);

	} catch (final Exception e) {
	    LOGGER.error(e.getMessage(), e);
	    throw e;
	}
	return response;
    }

    private void afterPropertiesSet() throws Exception {
	connManager = new PoolingClientConnectionManager();

	connManager.setMaxTotal(maxTotalConnections);
	connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
	try {
	    setSchemeRegistry();
	} catch (final KeyManagementException e1) {
	    LOGGER.error(e1.getMessage(), e1);
	} catch (final NoSuchAlgorithmException e1) {
	    LOGGER.error(e1.getMessage(), e1);
	}
	httpClient = new DefaultHttpClient();
	httpClient = (DefaultHttpClient) sslTrust(httpClient, connManager);
	httpClient.setRedirectStrategy(new DefaultRedirectStrategy() {
	    @Override
	    public boolean isRedirected(final HttpRequest request, final HttpResponse response, final HttpContext context) {
		boolean isRedirect = false;
		try {
		    isRedirect = super.isRedirected(request, response, context);
		} catch (final ProtocolException e) {
		    LOGGER.error(e.getMessage(), e);
		}
		if (!isRedirect) {
		    final int responseCode = response.getStatusLine().getStatusCode();
		    if (responseCode == 301 || responseCode == 302) {
			return true;
		    }
		}
		return isRedirect;
	    }
	});
	httpClient.setReuseStrategy(new DefaultConnectionReuseStrategy());
	httpClient.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
	setProtocolParam();
    }

    /**
     * Http call.
     * 
     * @param url
     *            the url
     * @param httpClient
     *            the http client
     * @param httpContext
     *            the http context
     * @param queryParamMap
     *            the query param map
     * @return the string
     * @throws InterruptedException
     *             the interrupted exception
     * @throws Exception
     *             the uSSD blocking exception
     */
    private String httpCall(DefaultHttpClient httpClient, HttpContext httpContext, String custMSISDN, String custPIN) throws Exception {

	final StringBuilder responseString = new StringBuilder();
	HttpPost postRequest = null;

	try {
	    final HttpParams httpParameters = new BasicHttpParams();
	    final int connectionTimeout = 300000;

	    HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeout);
	    final int socketTimeout = 300000;
	    HttpConnectionParams.setSoTimeout(httpParameters, socketTimeout);
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("target URL =[" + url + "]");
	    }

	    HttpResponse response = null;
	    postRequest = new HttpPost(url);
	    postRequest = preparePostBody(postRequest, custMSISDN, custPIN);
	    response = httpClient.execute(postRequest);
	    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	    String readLine = "";
	    while ((readLine = rd.readLine()) != null) {
		responseString.append(readLine);
	    }

	    /*
	     * else { // getRequest = new HttpGet(url); getRequest = prepareGetRequest(queryParamMap); LOGGER.info("Requesting the Hello Money URL.");
	     * response = httpClient.execute(getRequest, httpContext); BufferedReader br = new BufferedReader(new
	     * InputStreamReader(response.getEntity().getContent(), CHARSET)); responseString.append(br.readLine()); }
	     */

	    LOGGER.info("Received response from THM application : " + responseString.toString());

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("response status- " + response.getStatusLine().getReasonPhrase());
	    }
	    if (response.getStatusLine().getStatusCode() != 200) {
		String error = "[" + response.getStatusLine().getStatusCode() + "] - " + response.getStatusLine().getReasonPhrase();

		throw new Exception(error);
	    }

	} catch (final ConnectException e) {
	    LOGGER.fatal("ConnectException while connecting to Server", e);
	    throw e;
	} catch (final ClientProtocolException e) {
	    postRequest.abort();
	    LOGGER.fatal("ClientProtocolException while connecting to Server", e);
	    throw e;
	} catch (final Exception e) {
	    LOGGER.fatal("Exception while connecting to Server", e);
	    throw e;
	} finally {
	    if (postRequest != null) {
		postRequest.releaseConnection();
	    }
	}

	return responseString.toString();
    }

    /**
     * Ssl trust.
     * 
     * @param httpClient2
     *            the http client2
     * @param connManager2
     *            the conn manager2
     * @return the http client
     */
    @SuppressWarnings("deprecation")
    private HttpClient sslTrust(final HttpClient httpClient2, final PoolingClientConnectionManager connManager2) {
	SSLContext sc = null;

	try {
	    sc = SSLContext.getInstance("TLS");
	    final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		    return new X509Certificate[0];
		}

		public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
		}

		public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
		}
	    } };

	    sc.init(null, trustAllCerts, null);
	    final SSLSocketFactory ssf = new SSLSocketFactory(sc);
	    ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	    final SchemeRegistry sr = connManager2.getSchemeRegistry();
	    sr.register(new Scheme("https", 443, ssf));

	} catch (final NoSuchAlgorithmException e) {
	    LOGGER.fatal("NoSuchAlgorithmException while connecting to Server", e);
	} catch (final KeyManagementException e) {
	    LOGGER.fatal("KeyManagementException while connecting to Server", e);
	}
	// Create all-trusting host name verifier

	final HostnameVerifier allHostsValid = new HostnameVerifier() {
	    public boolean verify(final String hostname, final SSLSession session) {
		return true;
	    }
	};

	// Install the all-trusting host verifier
	HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	return new DefaultHttpClient(connManager2, httpClient2.getParams());
    }

    /**
     * Prepare post body.
     * 
     * @param postRequest
     *            the post request
     * @param queryParamMap
     *            the query param map
     * @return the http post
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     */
    private HttpPost preparePostBody(final HttpPost postRequest, final String custMSISDN, final String custPIN) throws UnsupportedEncodingException {
	final List<NameValuePair> nvps = new ArrayList<NameValuePair>();

	nvps.add(new BasicNameValuePair("custMSISDN", custMSISDN));
	nvps.add(new BasicNameValuePair("custPIN", custPIN));
	postRequest.setEntity(new UrlEncodedFormEntity(nvps));

	return postRequest;
    }

    /**
     * Sets the scheme registry.
     * 
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws KeyManagementException
     *             the key management exception
     */
    public void setSchemeRegistry() throws NoSuchAlgorithmException, KeyManagementException {
	final SchemeRegistry sr = new SchemeRegistry();

	final SSLContext sslcontext = SSLContext.getInstance("TLS");
	sslcontext.init(null, null, null);
	final SSLSocketFactory sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
	final Scheme https = new Scheme("https", hostConfigPort, sf);
	sr.register(https);

    }

    /**
     * Sets the protocol param.
     */
    public void setProtocolParam() {
	final HttpParams params = new BasicHttpParams();
	final HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
	paramsBean.setVersion(HttpVersion.HTTP_1_1);
	paramsBean.setUserAgent("HttpComponents/1.1");
	paramsBean.setContentCharset(CHARSET);
	paramsBean.setUseExpectContinue(true);
    }

    /**
     * Gets the http client.
     * 
     * @return the http client
     */
    public DefaultHttpClient getHttpClient() {
	return httpClient;
    }

    /**
     * Sets the http client.
     * 
     * @param httpClient
     *            the new http client
     */
    public void setHttpClient(final DefaultHttpClient httpClient) {
	this.httpClient = httpClient;
    }

    /**
     * Sets the default max per route.
     * 
     * @param defaultMaxPerRoute
     *            the new default max per route
     */
    public void setDefaultMaxPerRoute(final int defaultMaxPerRoute) {
	this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    /**
     * Gets the default max per route.
     * 
     * @return the default max per route
     */
    public int getDefaultMaxPerRoute() {
	return defaultMaxPerRoute;
    }

    /**
     * Destroy.
     */
    public void destroy() {
	connManager.shutdown();
    }

    /**
     * @return the hostName
     */
    public final String getHostName() {
	return hostName;
    }

    /**
     * @param hostName
     *            the hostName to set
     */
    public final void setHostName(String hostName) {
	this.hostName = hostName;
    }

    /**
     * @return the hostPath
     */
    public final String getHostPath() {
	return hostPath;
    }

    /**
     * @param hostPath
     *            the hostPath to set
     */
    public final void setHostPath(String hostPath) {
	this.hostPath = hostPath;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }
}
