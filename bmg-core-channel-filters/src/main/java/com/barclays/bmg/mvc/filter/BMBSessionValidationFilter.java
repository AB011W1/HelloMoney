package com.barclays.bmg.mvc.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.UrlBusinessMapService;
import com.barclays.bmg.service.request.UrlBusinessMapServiceRequest;
import com.barclays.bmg.service.response.UrlBusinessMapServiceResponse;

/**
 * @author E20041929 Entry point for hellomoney project
 * 
 */
public class BMBSessionValidationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(BMBSessionValidationFilter.class);

    /**
     * Channel Id
     */
    private static final String CH_ID = "chId";
    /**
     * Business Id
     */
    private static final String BIZ_ID = "bizId";
    final static String BUSINESS_URL_SERVICE = "urlBusinessMapService";

    /**
     * Default constructor.
     */
    public BMBSessionValidationFilter() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
	// TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpSession httpSession = httpRequest.getSession(false);

	LOGGER.debug("BMBSessionValidationFilter:Request is recieved in and being served wait for next message in this class");

	if (getUserMapFromSession(httpRequest) == null) {
	    String applicationVersion = request.getParameter(SessionConstant.APPLICATION_VERSION);
	    String deviceId = request.getParameter(SessionConstant.DEVICE_ID);
	    String deviceOsName = request.getParameter(SessionConstant.DEVICE_OS_NAME);
	    String deviceOsVesrion = request.getParameter(SessionConstant.DEVICE_OS_VESRION);
	    String deviceModelName = request.getParameter(SessionConstant.DEVICE_MODEL_NAME);
	    // USSD BMG Integration
	    String bizId = (String) request.getAttribute(BIZ_ID);
	    String chId = request.getParameter(CH_ID);
	    WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(httpSession.getServletContext());

	    UrlBusinessMapService urlBusinessMapService = (UrlBusinessMapService) webContext.getBean(BUSINESS_URL_SERVICE);

	    UrlBusinessMapServiceRequest urlBusinessMapServiceRequest = new UrlBusinessMapServiceRequest();

	    String url = getUrlPatternWithPortWithContext(httpRequest);

	    String url2 = url.replace("/" + ResponseIdConstants.RESPONSE_SERVICE_VERSION, "");
	    urlBusinessMapServiceRequest.setUrl(url2);
	    urlBusinessMapServiceRequest.setBusinessId(bizId);
	    UrlBusinessMapServiceResponse urlBusinessMapServiceResponse = null;

	    if (StringUtils.isNotEmpty(bizId) && StringUtils.isNotEmpty(chId)) {
		urlBusinessMapServiceResponse = urlBusinessMapService.getUrlBizMapFrmBizId(bizId.trim().toUpperCase(), chId.trim().toUpperCase());
	    } else {
		urlBusinessMapServiceResponse = urlBusinessMapService.getUrlBusinessMap(urlBusinessMapServiceRequest);
	    }

	    if (urlBusinessMapServiceResponse == null || urlBusinessMapServiceResponse.getUrlBusinessMapDTO() == null) {
		url = getUrlPatternWithContext(httpRequest);
		url2 = url.replace("/" + ResponseIdConstants.RESPONSE_SERVICE_VERSION, "");
		urlBusinessMapServiceRequest.setUrl(url2);
		urlBusinessMapServiceResponse = urlBusinessMapService.getUrlBusinessMap(urlBusinessMapServiceRequest);

	    }

	    if (urlBusinessMapServiceResponse != null) {
		LOGGER.debug("BMBSessionValidationFilter:urlBusinessMapServiceResponse Recieved and is not null:");
		LOGGER.debug("so url is found in database and being forwarded to next filter for service");

	    }
	    // please do not extract urlBusinessMapServiceResponse.getUrlBusinessMapDTO()...
	    // this is dynamically retireing some other object from DB.
	    if (urlBusinessMapServiceResponse != null && urlBusinessMapServiceResponse.getUrlBusinessMapDTO() != null) {

		// String systemId = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getSystemId();
		// String businessId = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getBusinessId();
		// String languageId = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getLanguageId();

		/* Below three request params systemId, businessId and languageId are set in the USSDRequestFilter */
		String systemId = (String) request.getAttribute(BMGConstants.BMG_CHANNEL_ID_PARAM_NAME);
		String businessId = (String) request.getAttribute(BMGConstants.BMG_BUSINESS_ID_PARAM_NAME);
		String languageId = (String) request.getAttribute(BMGConstants.BMG_LANGUAGE_ID_PARAM_NAME);

		String countryCode = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getCountryCode();
		String localCurrencyCode = urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getLocalCurrencyCode();
		String timeZoneOffSet = Integer.valueOf(urlBusinessMapServiceResponse.getUrlBusinessMapDTO().getTzOffSet()).toString();

		setUserMapIntoSession(httpRequest, SessionConstant.SESSION_SYSTEM_ID, systemId);
		setUserMapIntoSession(httpRequest, SessionConstant.SESSION_BUSINESS_ID, businessId);
		setUserMapIntoSession(httpRequest, SessionConstant.SESSION_LANGUAGE_ID, languageId);
		setUserMapIntoSession(httpRequest, SessionConstant.SESSION_COUNTRY_CODE, countryCode);
		setUserMapIntoSession(httpRequest, SessionConstant.SESSION_COUNTRY_CODE, countryCode);
		setUserMapIntoSession(httpRequest, SessionConstant.SESSION_LOCAL_CURRENCY, localCurrencyCode);
		setUserMapIntoSession(httpRequest, SessionConstant.SESSION_TIMEZONE_OFFSET, timeZoneOffSet);
		setUserMapIntoSession(httpRequest, SessionConstant.SESSION_ID, httpSession.getId());
		setUserMapIntoSession(httpRequest, SessionConstant.APPLICATION_VERSION, applicationVersion);
		setUserMapIntoSession(httpRequest, SessionConstant.DEVICE_ID, deviceId);
		setUserMapIntoSession(httpRequest, SessionConstant.DEVICE_OS_NAME, deviceOsName);
		setUserMapIntoSession(httpRequest, SessionConstant.DEVICE_OS_VESRION, deviceOsVesrion);
		setUserMapIntoSession(httpRequest, SessionConstant.DEVICE_MODEL_NAME, deviceModelName);

	    } else {
		LOGGER.debug("BMBSessionValidationFilter:No Business Url Found in Business URL Table");
		throw new BMBDataAccessException("No Business Url Found in Business URL Table");
	    }

	}

	Map<String, String> userMap = getUserMapFromSession(httpRequest);

	Context context = new Context();
	String opCde = request.getParameter("opCde");
	context.setBusinessId(userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	context.setCountryCode(userMap.get(SessionConstant.SESSION_COUNTRY_CODE));
	context.setLanguageId(userMap.get(SessionConstant.SESSION_LANGUAGE_ID));
	context.setSystemId(userMap.get(SessionConstant.SESSION_SYSTEM_ID));
	context.setSessionId(userMap.get(SessionConstant.SESSION_ID));
	context.setApplicationVersion(userMap.get(SessionConstant.APPLICATION_VERSION));
	context.setDeviceId(userMap.get(SessionConstant.DEVICE_ID));
	context.setDeviceOsName(userMap.get(SessionConstant.DEVICE_OS_NAME));
	context.setDeviceOsVersion(userMap.get(SessionConstant.DEVICE_OS_VESRION));
	context.setDeviceModelName(userMap.get(SessionConstant.DEVICE_MODEL_NAME));
	context.setLocalCurrency(userMap.get(SessionConstant.SESSION_LOCAL_CURRENCY));
	context.setServiceVersion((String) httpSession.getAttribute(SessionConstant.SESSION_BMG_SERVICE_VERSION));
	context.setTimezoneOffset(userMap.get(SessionConstant.SESSION_TIMEZONE_OFFSET));
	context.setOpCde(opCde);

	context.setCustomerId(userMap.get(SessionConstant.SESSION_CUSTOMER_ID));
	String mobNO = (String) httpRequest.getAttribute("mobNo");

	context.setMobileUserId(mobNO); // 11072014
	context.setOpCde(opCde);

	BMBContextHolder.setContext(context);
	LOGGER.debug("BMBSessionValidationFilter :succeeded without error and forwarding to questionair service/authentication ");

	// pass the request along the filter chain
	chain.doFilter(request, response);
	// TODO check this
	// BMBContextHolder.remove();
	// BMGContextHolder.remove();
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
	// TODO Auto-generated method stub
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
     * get user from session
     * 
     * @param request
     * @return
     */
    protected Map<String, String> getUserMapFromSession(HttpServletRequest request) {

	Map<String, String> userMap = (Map<String, String>) getSessionAttribute(request, SessionConstant.SESSION_USER);

	return userMap;

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
	    if (!StringUtils.isEmpty(key)) {
		if (value != null) {
		    session.setAttribute(key, value);
		} else {
		    session.setAttribute(key, null);
		}
	    }
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

}
