package com.barclays.bmg.mvc.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

public class BMBUrlHandlerMapping extends SimpleUrlHandlerMapping {

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {

	String lookupPath = request.getParameter("opCde");

	if (lookupPath != null) {
	    lookupPath = "/" + lookupPath.trim();
	}
	Object handler = lookupHandler(lookupPath, request);
	if (handler == null) {
	    // We need to care for the default handler directly, since we need to
	    // expose the PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE for it as well.
	    Object rawHandler = null;
	    if ("/".equals(lookupPath)) {
		rawHandler = getRootHandler();
	    }
	    if (rawHandler == null) {
		rawHandler = getDefaultHandler();
	    }
	    if (rawHandler != null) {
		validateHandler(rawHandler, request);
		handler = buildPathExposingHandler(rawHandler, lookupPath, null, null);
	    }
	}
	if (handler != null && logger.isDebugEnabled()) {
	    logger.debug("Mapping [" + lookupPath + "] to handler '" + handler + "'");
	} else if (handler == null && logger.isTraceEnabled()) {
	    logger.trace("No handler mapping found for [" + lookupPath + "]");
	}
	return handler;
    }
}
