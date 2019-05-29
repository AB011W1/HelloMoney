/**
 *
 */
package com.barclays.hellomoney.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HelloMoneyClientServlet extends HttpServlet {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(HelloMoneyClientServlet.class);

    private CommonHttpClientExecutor commonHttpClientExecutor = new CommonHttpClientExecutor();

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

	String httpPostMethod = "POST"; // default
	String responseString = "";

	Map<String, String> requestParameterMap = new HashMap<String, String>();
	Enumeration<String> paramNamesEnum = request.getParameterNames();
	while (paramNamesEnum.hasMoreElements()) {
	    String param = (String) paramNamesEnum.nextElement();
	    requestParameterMap.put(param.trim(), request.getParameter(param));
//	    LOGGER.debug("=>" + param.trim() + "=" + request.getParameter(param));
	}

	LOGGER.debug("\n\nrequest Parameter Map=" + requestParameterMap);
	String targetURL = requestParameterMap.get("targetURL");
	httpPostMethod = requestParameterMap.get("formMethod");

//	LOGGER.debug("nonceValue=" + nonceValue);
//	LOGGER.debug("targetURL=" + targetURL);
//	LOGGER.debug("httpPostMethod=" + httpPostMethod);

	// Set the response message's MIME type
	response.setContentType("text/html;charset=UTF-8");
	// Allocate a output writer to write the response message into the network socket
	PrintWriter out = response.getWriter();
	HTTPClientResponse clientResponse = null;
	try {
	    // targetURL = "http://localhost:8080/ussd-war/selcom";
	    clientResponse = commonHttpClientExecutor.processHttpRequest(targetURL, httpPostMethod, requestParameterMap);
	    responseString = clientResponse.getResponseBody();

	} catch (Exception e) {
	    LOGGER.error(e);
	    String error = "Servr Error - " + e.getMessage();
	    responseString = "null|" + error + "|null|null|end|null||";
	}

	// Write the response message, in an HTML page
	try {
	    out.println(responseString);
	    out.flush();
	} catch (Exception e) {
	} finally {
	    out.close(); // Always close the output writer
	    out = null;
	}

    }

    public final static void main(String[] args) throws Exception {
	DefaultHttpClient httpclient = new DefaultHttpClient();
	try {
	    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	    FileInputStream instream = new FileInputStream(new File("my.keystore"));
	    try {
		trustStore.load(instream, "nopassword".toCharArray());
	    } finally {
		try {
		    instream.close();
		} catch (Exception ignore) {
		}
	    }

	    SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
	    Scheme sch = new Scheme("https", 443, socketFactory);
	    httpclient.getConnectionManager().getSchemeRegistry().register(sch);

	    HttpGet httpget = new HttpGet("https://localhost/");

	    //System.out.println("executing request" + httpget.getRequestLine());

	    HttpResponse response = httpclient.execute(httpget);
	    HttpEntity entity = response.getEntity();

	    //System.out.println("----------------------------------------");
	    //System.out.println(response.getStatusLine());
	    if (entity != null) {
		//System.out.println("Response content length: " + entity.getContentLength());
	    }
	    EntityUtils.consume(entity);

	} finally {
	    // When HttpClient instance is no longer needed,
	    // shut down the connection manager to ensure
	    // immediate deallocation of all system resources
	    httpclient.getConnectionManager().shutdown();
	}
    }

    public static void main2(String[] args) throws Exception {
	Request.Get("https://widd.wload.global:52288/hellomoney/selcom").bodyForm(
		Form.form().add("BUSINESS", "TZBRB").add("MSISDN", "0000184486").add("INPUT", "null").build()).execute().returnContent();

    }
}
