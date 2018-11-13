package com.barclays.ussd.auth.filters;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.response.ResponseMessage;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.utils.CrypterUtil;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSessionConstants;
import com.barclays.ussd.xmlrequest.USSDXMLRequest;
import com.barclays.ussd.xmlrequest.USSDXMLRequestBody;
import com.barclays.ussd.xmlrequest.USSDXMLRequestHeader;
import com.barclays.ussd.xmlrequest.XMLRequestResponseParser;
import com.barclays.ussd.xmlresponse.USSDXMLResponseBody;
import com.barclays.ussd.xmlresponse.USSDXMLResponseHeader;

/**
 * @author E20041929
 *
 */
public class USSDRequestFilter implements Filter {

    private static final String TECHNICAL_ISSUE = "TECHNICAL_ISSUE";
    private static final String MSISDN_WITH_COUNTRY = "msisdnWithCountry";

    private static final String UNKNOWN = "UNKNOWN";

    ResponseMessage responseMessage = new ResponseMessage();

    private static final String LOGIN = "LOGIN";
    private static final Logger LOGGER = Logger.getLogger(USSDRequestFilter.class);
    private static final Logger TIMELOGGER = Logger.getLogger("com.barclays.ussd.audit-time");
    private static final String NV = "NV";

    private static final String BEM_RESPONSE_TIME = "BEM_RESPONSE_TIME";
    private static final String BEM_RESPONSE_TIME_BREAKUP = "BEM_RESPONSE_TIME_BREAKUP";
    private static final Long ZERO = new Long(0);

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
	    ServletException {
	MDC.put("ROUTINGKEY", "COMMON");
	LOGGER.debug("Entered into USSD Request Filter...");

	StopWatch sw = null;
	if (TIMELOGGER.isInfoEnabled()) {
	    sw = new StopWatch();
	    sw.start();
	    MDC.put(BEM_RESPONSE_TIME, ZERO);
	    MDC.put(BEM_RESPONSE_TIME_BREAKUP, "");
	}

	HttpServletRequest request = (HttpServletRequest) servletRequest;
	HttpServletResponse response = (HttpServletResponse) servletResponse;
	 Enumeration<String> headerNames = request.getHeaderNames();


	try {
	    HttpSession httpSession = null;
	    String nextNonce = null;

	    USSDXMLResponseBody responseBody = new USSDXMLResponseBody();
	    USSDXMLResponseHeader responseHeader = createResponseHeader();

	    if (!validateRequestMethod(request, responseBody, responseHeader)) {
		return;
	    }

	    // printSessionAttributes("Real ", httpSession);
	    httpSession = request.getSession(true);

	    USSDXMLRequest reqObject = getRequestObject(request, responseBody, responseHeader);
	    USSDXMLRequestBody body = reqObject.getRequestBody();
	    USSDXMLRequestHeader header = reqObject.getRequestHeader();
	    String action = body.getAction();

	    header.setSessionId("MB" + Math.abs(httpSession.getId().hashCode()));

	    request.setAttribute(USSDSessionConstants.INPUT_ACTION, action); // ???

	    request.setAttribute(USSDSessionConstants.REQ_OBJECT, reqObject);
	    request.setAttribute(USSDConstants.BMG_BUSINESS_ID_PARAM_NAME, header.getBusinessId());
	    request.setAttribute(USSDConstants.BMG_CHANNEL_ID_PARAM_NAME, USSDConstants.BMG_CHANNEL_ID_DEFAULT_VALUE);
	    request.setAttribute(USSDConstants.BMG_LANGUAGE_ID_PARAM_NAME, USSDConstants.BMG_LANGUAGE_ID_DEFAULT_VALUE);
	    String msisdn = header.getMsisdn();
	    request.setAttribute("mobNo", msisdn);

	    MDC.put("ROUTINGKEY", header.getBusinessId());
	    NDC.push(header.getSessionId());
	    NDC.push(header.getBusinessId());
	    NDC.push(msisdn);

	    LOGGER.debug("---Header Printed for Etransact Debugging Start---");
	     if (headerNames != null) {
	             while (headerNames.hasMoreElements()) {
	            	 String headerName = (String)headerNames.nextElement();
	            	 LOGGER.debug(headerName + " => " + request.getHeader(headerName));
	             }
	     }
	     LOGGER.debug("---Header Printed for Etransact Debugging End---");

	    USSDSessionManagement ussdSessionMgmt = (USSDSessionManagement) httpSession.getAttribute(msisdn);
	    if (ussdSessionMgmt == null || !ussdSessionMgmt.isFirstRequest()) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("reqObject: " + reqObject);
		}
	    }
	    printCookies(request);

	    if (!validateNonce(request, response, httpSession, reqObject, responseBody, responseHeader)) {
		return;
	    }

	    if (!validateMSISDN(request, response, httpSession, reqObject, responseBody, responseHeader)) {
		return;
	    }


	    nextNonce = CrypterUtil.getRandomNumber();
	    setSessionAttribute(httpSession, NV, nextNonce);
	    header.setNonce(nextNonce);

	    filterChain.doFilter(request, response);

	    LOGGER.debug("after filtering");

	    // Get the session since session might have been recreated after
	    // authentication
	    httpSession = request.getSession(true);
	    setSessionAttribute(httpSession, NV, nextNonce);

	    if (TIMELOGGER.isInfoEnabled()) {
		if (sw != null) {
		    sw.stop();
		    long timediff = sw.getTime();

		    Long bemTotalTime = (Long) MDC.get(BEM_RESPONSE_TIME);
		    String breakup = (String) MDC.get(BEM_RESPONSE_TIME_BREAKUP);
		    String bmgOpCode = (String) MDC.get("BMG_OPCODE");
		    if (bmgOpCode == null) {

			TIMELOGGER.info(bmgOpCode + "," + "," + "," + timediff + " , " + +bemTotalTime.longValue() + "||");

		    } else {
			// currentOption, Elapsed Time, BEM_RESPONSE_TIME, BEM_RESPONSE_TIME_BREAKUP
			TIMELOGGER.info(bmgOpCode + "," + timediff + " , " + +bemTotalTime.longValue() + breakup);
		    }
		}
	    }
	} catch (Throwable e) {
	    LOGGER.error(e.getMessage(), e);
	    handleGenericException(servletRequest, servletResponse);
	} finally {
	    // Remove the Log4J diagnostic context for this thread.
	    MDC.clear();
	    NDC.remove();
	    NDC.remove();
	    NDC.remove();
	    NDC.clear();
	    BMBContextHolder.remove();
	    BMGContextHolder.remove();
	}
    }

    /**
     *
     * @param request
     * @param httpSession
     * @param reqObject
     * @param responseBody
     * @param responseHeader
     * @return
     * @throws IOException
     */
    private boolean validateNonce(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, USSDXMLRequest reqObject, USSDXMLResponseBody responseBody,
	    USSDXMLResponseHeader responseHeader) throws IOException {
	USSDXMLRequestHeader requestHeader = reqObject.getRequestHeader();
	String requestNonce = requestHeader.getNonce();
	String expectedNonce = getSessionAttribute(httpSession, NV);

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("isAuth=" + getSessionAttribute(httpSession, "auth"));
	    LOGGER.debug("Nonce Token received from Aggregator=" + requestNonce);
	    LOGGER.debug("expectedNonce=[" + expectedNonce + "] vs requestNonce=[" + requestNonce + "]");
	}

	String action = reqObject.getRequestBody().getAction();

	if (!StringUtils.equalsIgnoreCase(LOGIN, action) && !StringUtils.equals(requestNonce, expectedNonce)) {
	    responseBody.setStatus("end");
	    responseBody.setText("Unable to proceed as no data is passed.");
	    String result = responseMessage.getXmlResponseString(request, responseBody, responseHeader);
	    response.getWriter().write(result);
	    response.getWriter().flush();
	    response.getWriter().close();
	    LOGGER.error(">>>>>> Invalid request : token didn't match, ignoring the request.");
	    return false;
	}

	return true;
    }

    /**
     *
     * @param request
     */
    private void printCookies(HttpServletRequest request) {
	Cookie[] cookies = request.getCookies();
	LOGGER.debug("COOKIE Details: ");
	if (cookies != null && cookies.length > 0) {
	    for (Cookie c : cookies) {
		LOGGER.debug(c.getName() + " : " + c.getValue());
	    }
	} else {
	    LOGGER.debug("No Cookies received.");
	}
    }

    /**
     *
     * @param request
     * @param responseBody
     * @param responseHeader
     * @return
     * @throws IOException
     */
    private USSDXMLRequest getRequestObject(HttpServletRequest request, USSDXMLResponseBody responseBody, USSDXMLResponseHeader responseHeader)
	    throws IOException {

	boolean enableXSDValidation = true;
	if ("FALSE".equalsIgnoreCase(ConfigurationManager.getString("enableXSDValidation"))) {
	    enableXSDValidation = false;
	}

	StringWriter writer = null;
	ServletInputStream sis = null;
	USSDXMLRequest reqObject = null;

	try {
	    writer = new StringWriter();
	    sis = request.getInputStream();
	    IOUtils.copy(sis, writer);
	    reqObject = XMLRequestResponseParser.getXMLRequestObject(writer.toString(), enableXSDValidation);
	} catch (IOException e) {
	    responseBody.setStatus("end");
	    responseBody.setText("Unable to proceed as no data is passed.");
	    responseMessage.getXmlResponseString(request, responseBody, responseHeader);
	    LOGGER.warn(">>>>>> Invalid request : No data passed.");
	} catch (JAXBException e) {
	    responseBody.setStatus("end");
	    responseBody.setText("Unable to proceed as data passed is invalid.");
	    responseMessage.getXmlResponseString(request, responseBody, responseHeader);
	    LOGGER.warn(">>>>>> Invalid request : Data Passed in xml is invalid.");
	} finally {
	    if (writer != null) {
		writer.close();
	    }
	    if (sis != null) {
		sis.close();
	    }

	}
	return reqObject;
    }

    private USSDXMLResponseHeader createResponseHeader() {
	USSDXMLResponseHeader responseHeader = new USSDXMLResponseHeader();
	responseHeader.setMsisdn(UNKNOWN);
	responseHeader.setNonceValue(UNKNOWN);
	responseHeader.setSession(UNKNOWN);
	return responseHeader;
    }

    private boolean validateRequestMethod(HttpServletRequest request, USSDXMLResponseBody responseBody, USSDXMLResponseHeader responseHeader)
	    throws IOException {
	String reqMethod = request.getMethod();
	if (reqMethod.equalsIgnoreCase("GET")) {
	    responseBody.setText("Unable to proceed as data passed is invalid.");
	    responseMessage.getXmlResponseString(request, responseBody, responseHeader);
	    LOGGER.warn(">>>>>> Invalid request : GET request not supported.");
	    return false;
	}
	return true;
    }

    private String getSessionAttribute(HttpSession session, String attribute) {
	String attributeValue = null;
	try {
	    attributeValue = (String) session.getAttribute(attribute);
	} catch (java.lang.IllegalStateException e) {
	    LOGGER.error(e.getMessage(), e);
	}
	return attributeValue;
    }

    private String setSessionAttribute(HttpSession session, String attribute, Object value) {
	String attributeValue = null;
	try {
	    if (session != null) {
		session.setAttribute(attribute, value);
	    }
	} catch (java.lang.IllegalStateException e) {
	    LOGGER.error(e.getMessage(), e);
	}
	return attributeValue;
    }

    private void printSessionAttributes(String id, HttpSession session) {
	if (LOGGER.isDebugEnabled()) {
	    if (session != null) {
		try {
		    LOGGER.debug(id + "HttpSession Info: ");
		    LOGGER.debug(id + " isNew=" + session.isNew());
		    LOGGER.debug(id + " sessionId=" + session.getId());
		    LOGGER.debug(id + " Creation Time=" + session.getCreationTime());
		    LOGGER.debug(id + " LastAccessedTime=" + session.getLastAccessedTime());
		    LOGGER.debug(id + " MaxInactiveInterval=" + session.getMaxInactiveInterval());
		} catch (Throwable e) {
		    LOGGER.warn(e.getMessage(), e);
		}
	    }
	}
    }

    private boolean validateMSISDN(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, USSDXMLRequest reqObject,
	    USSDXMLResponseBody responseBody, USSDXMLResponseHeader responseHeader) throws IOException {
	String msisdnFromRequest = reqObject.getRequestHeader().getMsisdn();
	if (httpSession.isNew()) {
	    httpSession.setAttribute(MSISDN_WITH_COUNTRY, msisdnFromRequest);
	    return true;
	} else {
	    String msisdnFromSession = (String) httpSession.getAttribute(MSISDN_WITH_COUNTRY);
	    if (StringUtils.equalsIgnoreCase(msisdnFromRequest, msisdnFromSession)) {
		return true;
	    }
	}
	LOGGER.error(">>>>>> Invalid request : MSISDN didn't match, ignoring the request.");
	responseBody.setText("Unable to proceed as data passed is invalid.");
	responseMessage.getXmlResponseString(request, responseBody, responseHeader);
	return false;
    }

    private void handleGenericException(ServletRequest servletRequest, ServletResponse servletResponse) {
	HttpServletRequest request = (HttpServletRequest) servletRequest;
	String response = ConfigurationManager.getString(TECHNICAL_ISSUE);
	HttpSession httpSession = request.getSession();
	String msisdnFromSession = (String) httpSession.getAttribute(MSISDN_WITH_COUNTRY);

	ResponseMessage responseMessage = new ResponseMessage();

	try {
	    String xmlResponse = responseMessage.generateXmlRespBlockingException(request, response, msisdnFromSession);
	    servletResponse.getWriter().write(xmlResponse);
	    servletResponse.getWriter().flush();
	    servletResponse.getWriter().close();
	    httpSession.invalidate();
	} catch (Exception e) {
	    LOGGER.warn("Error while handleGenericException", e);
	}
    }

    public void destroy() {
	// add code to release any resource
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}