package com.barclays.bmg.ecrime.impl;

import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.ecrime.EcrimeConfiguration;
import com.barclays.bmg.ecrime.EcrimeContext;
import com.barclays.bmg.ecrime.EcrimeRequestTask;
import com.barclays.bmg.ecrime.EcrimeResponseTask;
import com.barclays.bmg.ecrime.EcrimeService;
import com.barclays.bmg.type.KeyPair;

public class EcrimeServiceImpl implements EcrimeService {
    private static final String TRACKING_COOKIE_NAME = "VISITOR";
    private static final String MSISDN_WITH_COUNTRY = "msisdnWithCountry";
    private static final Logger LOGGER = Logger.getLogger(EcrimeServiceImpl.class);
    private EcrimeConfiguration ecrimeConfig;

    /**
     * @param request
     * @param response
     */
    private void checkCookies(HttpServletRequest request, HttpServletResponse response) {
	// check whether tracking cookie existed
	boolean trackingCookieFlag = false;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
	    for (Cookie cookie : cookies) {
		if (cookie.getName().equals(TRACKING_COOKIE_NAME)) {
		    trackingCookieFlag = true;
		    break;
		}
	    }
	}
	// add the tracking cookie if not existed
	if (!trackingCookieFlag) {
	    Cookie trackCookie = new Cookie(TRACKING_COOKIE_NAME, this.generateTrackingCookie());
	    trackCookie.setPath(request.getContextPath());
	    trackCookie.setMaxAge(Integer.MAX_VALUE);
	    trackCookie.setSecure(true);
	    response.addCookie(trackCookie);
	}

    }

    private String generateTrackingCookie() {
	long unixTimestamp = System.currentTimeMillis() / 1000;
	StringBuilder sb = new StringBuilder();
	sb.append(unixTimestamp);
	sb.append(this.generateRandomNumber(22));
	return sb.toString();
    }

    private String generateRandomNumber(int length) {
	SecureRandom random = new SecureRandom();
	StringBuilder sb = new StringBuilder();
	while (true) {
	    String s = Long.toString(Math.abs(random.nextLong()));
	    sb.append(s);
	    if (sb.length() >= length)
		break;
	}
	sb.setLength(length);
	return sb.toString();
    }

    /**
     * @param httpRequest
     * @return
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeFacadeExecutor#populateErimeCommonAttributes(javax.servlet.http.HttpServletRequest)
     */
    private List<KeyPair> populateEcrimeCommonAttributes(HttpServletRequest httpRequest) {
	List<KeyPair> list = new ArrayList<KeyPair>();
	Context context = BMBContextHolder.getContext();
	LOGGER.debug("populateEcrimeCommonAttributes start");

	if (context != null) {
	    // Add Business ID
	    addKeyPairToList(list, "BusinessId", context.getBusinessId());
	    // Add Customer SCV Id
	    addKeyPairToList(list, "CustomerID", context.getCustomerId());
	    // Add Operation Code
	    addKeyPairToList(list, "OperationCode", context.getOpCde());
	    // Add Mobile number
	    addKeyPairToList(list, "MobileNumber", (String) httpRequest.getSession().getAttribute(MSISDN_WITH_COUNTRY));
	    // Add Business Date
	    addKeyPairToList(list, "Date/Time", "" + new Date());
	}

	// Add Application Version No
	if (context != null && context.getApplicationVersion() != null) {
	    addKeyPairToList(list, "Application version", context.getApplicationVersion());
	}
	// Add Request ID
	addKeyPairToList(list, "RequestId", this.generateRandomNumber(32));
	// Add Session ID
	addKeyPairToList(list, "SessionID", httpRequest.getSession().getId());
	// Add Request URL
	addKeyPairToList(list, "RequestURL", httpRequest.getRequestURL().toString());
	// Add Server Name
	addKeyPairToList(list, "ServerName", httpRequest.getServerName());
	// Add Thread Id
	addKeyPairToList(list, "ThreadId", "" + Thread.currentThread().getId());
	// Add JVM Process Id
	addKeyPairToList(list, "ProcessId", "" + ManagementFactory.getRuntimeMXBean().getName());
	// Add Log File Name
	addKeyPairToList(list, "LogFileName", "hellomoney_ecrime.log");
	// Add Timestamp
	addKeyPairToList(list, "Timestamp", "" + new Date().getTime());
	// Add Remote Host
	addKeyPairToList(list, "remoteHost", httpRequest.getRemoteHost());

	// Add From Head List
	List<String> headList = new ArrayList<String>();
	headList.add("user-agent");
	headList.add("accept-language");
	headList.add("x-forwarded-for");
	headList.add("referer");

	for (int i = 0; i < headList.size(); i++) {
	    addKeyPairToList(list, headList.get(i), httpRequest.getHeader(headList.get(i)));
	}

	// Add From Cookie
	Cookie[] cookies = httpRequest.getCookies();
	if (cookies != null) {
	    for (Cookie cookie : cookies) {
		addKeyPairToList(list, cookie.getName(), cookie.getValue());
	    }
	}
	LOGGER.debug("populateEcrimeCommonAttributes End");
	return list;
    }

    private void addKeyPairToList(List<KeyPair> list, String key, String value) {
	list.add(new KeyPair(key, value));
    }

    @SuppressWarnings("unchecked")
    private List<KeyPair> populateErimeRequestAttributes(HttpServletRequest httpRequest) {
	List<KeyPair> list = new ArrayList<KeyPair>();
	Enumeration<String> en = httpRequest.getParameterNames();
	LOGGER.debug("populateErimeRequestAttributes Start");
	while (en.hasMoreElements()) {
	    String element = en.nextElement();
	    KeyPair keyPair = null;
	    if (element.toLowerCase().contains("actno")) {
		Map<String, String> accountMap = (Map<String, String>) httpRequest.getAttribute("ecrimeAccountMap");
		String accountMapKey = httpRequest.getParameter(element);
		if (accountMap != null) {
		    keyPair = new KeyPair(element, accountMap.get(accountMapKey));
		}
	    } else {
		keyPair = new KeyPair(element, httpRequest.getParameter(element));
	    }

	    list.add(keyPair);
	}
	LOGGER.debug("populateErimeRequestAttributes End");
	return list;
    }

    /**
     * @param event
     * @return
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeFacadeExecutor#populateErimeResponseAttributes(javax.faces.event.PhaseEvent)
     */
    private void populateErimeResponseAttributes(EcrimeContext context, Object bean) {
	List<KeyPair> responseList = retriveResponseFrom(bean);
	context.setResponseAttributes(responseList);
    }

    private List<KeyPair> retriveResponseFrom(Object bean) {
	List<KeyPair> list = new ArrayList<KeyPair>();
	retriveResponseFromValueHolder(bean, list);
	return list;
    }

    private void retriveResponseFromValueHolder(Object bean, List<KeyPair> list) {
	ObjectMapper objMap = new ObjectMapper();
	boolean isMap = false;

	LOGGER.debug("retriveResponseFromValueHolder Start");
	if (bean != null) {
	    Class[] interfaces = bean.getClass().getInterfaces();

	    for (Class name : interfaces) {
		String interfaceName = name.getCanonicalName();
		if ("java.util.Map".equals(interfaceName)) {
		    isMap = true;
		    break;
		} else if ("java.util.List".equals(interfaceName) || "java.util.Set".equals(interfaceName)) {
		    return;
		}

	    }

	    Map<String, Object> fields = null;
	    if (isMap) {
		fields = (Map<String, Object>) bean;
	    } else {
		fields = objMap.convertValue(bean, Map.class);
	    }

	    for ( Map.Entry<String, Object> entry : fields.entrySet()) {
		Object value = fields.get(entry.getKey());

		if (value instanceof String || value instanceof Long || value instanceof Integer || value instanceof Float || value instanceof Double
			|| value instanceof Boolean) {
		    KeyPair keyPair = new KeyPair(entry.getKey(), value.toString());
		    list.add(keyPair);
		} else {
		    if (value != null) {
			retriveResponseFromValueHolder(value, list);
		    }
		}

	    }

	}
	LOGGER.debug("retriveResponseFromValueHolder End");

    }

    /**
     * @param context
     * @param request
     * @param response
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeFacadeExecutor#exeucteRequest(com.barclays.mcfe.ssc.web.ecrime.EcrimeContext,
     *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void executeRequest(EcrimeContext context, HttpServletRequest request, HttpServletResponse response) {
	checkCookies(request, response);
	List<KeyPair> commonAttributes = populateEcrimeCommonAttributes(request);
	context.setCommonAttributes(commonAttributes);
	List<KeyPair> requestAttributes = populateErimeRequestAttributes(request);
	context.setRequestAttributes(requestAttributes);
    }

    /**
     * @param context
     * @param request
     * @param response
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeFacadeExecutor#exeucteResponse(com.barclays.mcfe.ssc.web.ecrime.EcrimeContext,
     *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void executeResponse(EcrimeContext context, Object bean) {
	this.populateErimeResponseAttributes(context, bean);
    }

    /**
     * @param context
     * @return
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeFacadeExecutor#createRequestTask(com.barclays.mcfe.ssc.web.ecrime.EcrimeContext)
     */
    public Runnable createRequestTask(EcrimeContext context) {
	return new EcrimeRequestTask(context, getEcrimeConfig().getMaskRules());
    }

    /**
     * @param context
     * @return
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeFacadeExecutor#createResponseTask(com.barclays.mcfe.ssc.web.ecrime.EcrimeContext)
     */
    public Runnable createResponseTask(EcrimeContext context, Object bean) {
	return new EcrimeResponseTask(context, getEcrimeConfig().getMaskRules());
    }

    /**
     * @param ecrimeConfig
     *            the ecrimeConfig to set
     */
    public void setEcrimeConfig(EcrimeConfiguration ecrimeConfig) {
	this.ecrimeConfig = ecrimeConfig;
    }

    /**
     * @return the ecrimeConfig
     */
    public EcrimeConfiguration getEcrimeConfig() {
	return ecrimeConfig;
    }

    /**
     * @param status
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeFacadeExecutor#setTransactionStatus(boolean)
     */
    public void setTransactionStatus(EcrimeContext context, boolean status, String description) {
	context.setTransactionStatus(status);
	context.setTransactionDescription(description);
	context.setResponseTask(new EcrimeResponseTask(context, getEcrimeConfig().getMaskRules()));
    }

}
