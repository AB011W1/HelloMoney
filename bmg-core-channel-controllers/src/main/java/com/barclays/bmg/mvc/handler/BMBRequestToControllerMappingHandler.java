package com.barclays.bmg.mvc.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.barclays.bmg.channel.resolver.config.ControllerResolverConfig;
import com.barclays.bmg.channel.resolver.config.RequestControllerMapperConfig;
import com.barclays.bmg.channel.resolver.config.ServiceVersionControllerConfig;

public class BMBRequestToControllerMappingHandler extends SimpleUrlHandlerMapping {

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {

	String opCde = request.getParameter("opCde");
	String serVer = request.getParameter("serVer");
	String businessId = getBusinessIdFromSession(request);

	// HttpSession httpSession = request.getSession(false);

	/*
	 * WebApplicationContext webContext = WebApplicationContextUtils .getRequiredWebApplicationContext(httpSession .getServletContext());
	 */

	ApplicationContext applicationContext = getApplicationContext();

	RequestControllerMapperConfig reqControllerMapperConfig = (RequestControllerMapperConfig) applicationContext.getBean("requestMapper");

	/*
	 * ControllerResolverConfig contResolverConfig = (ControllerResolverConfig) reqControllerMapperConfig .getConMap().get(businessId); String
	 * contName = null; if(contResolverConfig == null){ contResolverConfig = getDefaultResolver(reqControllerMapperConfig); }
	 * 
	 * ServiceVersionControllerConfig serVerContConfig = contResolverConfig .getSnvMapping().get(opCde);
	 * 
	 * if(serVerContConfig==null){ contName = checkInDefaulResolver(reqControllerMapperConfig,opCde, serVer); }else{ contName =
	 * serVerContConfig.getControllerName(serVer); }
	 * 
	 * if(StringUtils.isEmpty(contName)){ contName = checkInDefaulResolver(reqControllerMapperConfig,opCde, serVer); }
	 * 
	 * Object handler = null; handler = applicationContext.getBean(contName);
	 */

	/*
	 * Changes for extends keyword
	 */

	ControllerResolverConfig contResolverConfig = (ControllerResolverConfig) reqControllerMapperConfig.getConMap().get(businessId);
	boolean isExtends = true;
	ServiceVersionControllerConfig serVerContConfig = null;
	String contName = null;
	if (contResolverConfig != null) {
	    while ((serVerContConfig == null || contName == null) && isExtends == true) {
		serVerContConfig = contResolverConfig.getSnvMapping().get(opCde);
		if (serVerContConfig != null) {
		    if (!StringUtils.isEmpty(serVer)) {
			contName = serVerContConfig.getControllerName(serVer);
		    } else {
			contName = serVerContConfig.getDefaultControllerName();
		    }

		}
		isExtends = contResolverConfig.hasParentResolver();
		if (serVerContConfig == null || contName == null) {
		    if (isExtends) {
			String parentResolver = contResolverConfig.getParentResolver();
			contResolverConfig = (ControllerResolverConfig) reqControllerMapperConfig.getConMap().get(parentResolver);
		    }
		}
	    }

	}

	Object handler = null;
	handler = applicationContext.getBean(contName);

	/*
	 * RequestControllerMapperConfig reqControllerMapperConfig = (RequestControllerMapperConfig)webContext.getBean("requestMapper");
	 */

	/*
	 * ControllerResolverConfig contResolverConfig = (ControllerResolverConfig )reqControllerMapperConfig.getConMap().get(businessId);
	 * 
	 * ServiceVersionControllerConfig serVerContConfig = contResolverConfig.getSnvMapping().get(opCde);
	 * 
	 * Object handler = null; String contName = serVerContConfig.getControllerName(serVer); if(contName != null){ handler =
	 * webContext.getBean(contName); }
	 */

	/*
	 * if(opCde!=null){ lookupPath="/"+lookupPath.trim(); } Object handler = lookupHandler(lookupPath, request); if (handler == null) {
	 * 
	 * Object rawHandler = null; if ("/".equals(lookupPath)) { rawHandler = getRootHandler(); } if (rawHandler == null) { rawHandler =
	 * getDefaultHandler(); } if (rawHandler != null) { validateHandler(rawHandler, request); handler = buildPathExposingHandler(rawHandler,
	 * lookupPath); } } if (handler != null && logger.isDebugEnabled()) { logger.debug("Mapping [" + lookupPath + "] to handler '" + handler +
	 * "'"); } else if (handler == null && logger.isTraceEnabled()) { logger.trace("No handler mapping found for [" + lookupPath + "]"); }
	 */
	return handler;
    }

    private ControllerResolverConfig getDefaultResolver(RequestControllerMapperConfig reqControllerMapperConfig) {

	ControllerResolverConfig contResolverConfig = (ControllerResolverConfig) reqControllerMapperConfig.getConMap().get("DefaultResolver");

	return contResolverConfig;

    }

    private String checkInDefaulResolver(RequestControllerMapperConfig reqControllerMapperConfig, String opCde, String serVer) {

	ControllerResolverConfig contResolverConfig = getDefaultResolver(reqControllerMapperConfig);

	ServiceVersionControllerConfig serVerContConfig = contResolverConfig.getSnvMapping().get(opCde);

	String contName = serVerContConfig.getControllerName(serVer);

	return contName;

    }

    private String getBusinessIdFromSession(HttpServletRequest request) {
	if (getUserMapFromSession(request) != null) {
	    return getUserMapFromSession(request).get("businessId");
	} else {
	    return "COMMON";
	}
    }

    /**
     * get user from session
     * 
     * @param request
     * @return
     */
    private Map<String, String> getUserMapFromSession(HttpServletRequest request) {

	Map<String, String> userMap = (Map<String, String>) getSessionAttribute(request, "user");

	return userMap;

    }

    /**
     * set session attribute
     * 
     * @param request
     * @param key
     * @return
     */
    private Object getSessionAttribute(HttpServletRequest request, String key) {
	Object value = null;
	HttpSession session = request.getSession(false);
	if (session != null) {
	    value = session.getAttribute(key);
	}
	return value;
    }
}
