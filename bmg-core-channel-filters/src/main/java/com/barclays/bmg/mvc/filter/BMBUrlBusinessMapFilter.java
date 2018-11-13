package com.barclays.bmg.mvc.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.service.UrlBusinessMapService;
import com.barclays.bmg.service.request.UrlBusinessMapServiceRequest;
import com.barclays.bmg.service.response.UrlBusinessMapServiceResponse;

public class BMBUrlBusinessMapFilter {

    public void getUrlBusinessMap(HttpServletRequest request) {
	HttpSession httpSession = request.getSession();

	WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(httpSession.getServletContext());

	UrlBusinessMapService urlBusinessMapService = (UrlBusinessMapService) webContext.getBean("urlBusinessMapService");

	UrlBusinessMapServiceRequest urlBusinessMapServiceRequest = new UrlBusinessMapServiceRequest();

	String url = getUrlPatternWithPortWithContext(request);

	urlBusinessMapServiceRequest.setUrl(url);

	UrlBusinessMapServiceResponse urlBusinessMapServiceResponse = urlBusinessMapService.getUrlBusinessMap(urlBusinessMapServiceRequest);

	// String systemId = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getSystemId();
	// String businessId = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getBusinessId();
	// String languageId = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getLanguageId();
	/* Below three request params systemId, businessId and languageId are set in the USSDRequestFilter */
	String systemId = (String) request.getAttribute(BMGConstants.BMG_CHANNEL_ID_PARAM_NAME);
	String businessId = (String) request.getAttribute(BMGConstants.BMG_BUSINESS_ID_PARAM_NAME);
	String languageId = (String) request.getAttribute(BMGConstants.BMG_LANGUAGE_ID_PARAM_NAME);
	String countryCode = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getCountryCode();
	String localCurrency = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getLocalCurrencyCode();

	setUserMapIntoSession(request, SessionConstant.SESSION_SYSTEM_ID, systemId);
	setUserMapIntoSession(request, SessionConstant.SESSION_BUSINESS_ID, businessId);
	setUserMapIntoSession(request, SessionConstant.SESSION_LANGUAGE_ID, languageId);
	setUserMapIntoSession(request, SessionConstant.SESSION_COUNTRY_CODE, countryCode);
	setUserMapIntoSession(request, SessionConstant.SESSION_LOCAL_CURRENCY, localCurrency);
	setUserMapIntoSession(request, SessionConstant.SESSION_ID, httpSession.getId());

    }

    /**
     * set user from session
     * 
     * @param request
     * @return
     */
    protected void setUserMapIntoSession(HttpServletRequest request, String key, String value) {

	Map<String, String> userMap = (Map<String, String>) getSessionAttribute(request, SessionConstant.SESSION_USER);
	if (userMap == null) {
	    userMap = new HashMap<String, String>();
	}
	userMap.put(key, value);
	setSessionAttribute(request, SessionConstant.SESSION_USER, userMap);

    }

    /**
     * set session attribute
     * 
     * @param request
     * @param key
     * @return
     */
    protected Object getSessionAttribute(HttpServletRequest request, String key) {
	Object value = null;
	HttpSession session = request.getSession(false);
	if (session != null) {
	    value = session.getAttribute(key);
	}
	return value;
    }

    /**
     * add session attribute
     * 
     * @param request
     * @param key
     * @param value
     */
    protected void setSessionAttribute(HttpServletRequest request, String key, Object value) {
	HttpSession session = request.getSession(false);
	if (session != null) {
	    session.setAttribute(key, value);
	}
    }

    private String getUrlPatternWithContext(HttpServletRequest request) {
	return new StringBuffer().append(request.getScheme()).append("://").append(request.getServerName()).append(request.getContextPath())
		.toString();
    }

    private String getUrlPatternWithPortWithContext(HttpServletRequest request) {
	return new StringBuffer().append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(
		request.getServerPort()).append(request.getContextPath()).toString();
    }

    private String getUrlPattern(HttpServletRequest request) {
	return new StringBuffer().append(request.getScheme()).append("://").append(request.getServerName()).toString();
    }

    private String getUrlPatternWithPort(HttpServletRequest request) {
	return new StringBuffer().append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(
		request.getServerPort()).toString();
    }
}
