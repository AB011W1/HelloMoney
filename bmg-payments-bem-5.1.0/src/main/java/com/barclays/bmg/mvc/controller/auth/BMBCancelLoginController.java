package com.barclays.bmg.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;

public class BMBCancelLoginController extends BMBAbstractController {

    private BMBJSONBuilder bmbJsonBuilder;

    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest request, HttpServletResponse response) throws Exception {

	ResponseContext responseContext = new ResponseContext();

	responseContext.setContext(makeContext(request));

	finishOff(request);
	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(responseContext);
    }

    public Context makeContext(HttpServletRequest request) {
	Context context = new Context();

	Map<String, Object> userMap = getUserMapFromSession(request);

	context.setBusinessId((String) userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	context.setCountryCode((String) userMap.get(SessionConstant.SESSION_COUNTRY_CODE));
	context.setLanguageId((String) userMap.get(SessionConstant.SESSION_LANGUAGE_ID));
	context.setSystemId((String) userMap.get(SessionConstant.SESSION_SYSTEM_ID));
	context.setSessionId((String) userMap.get(SessionConstant.SESSION_ID));

	return context;
	// setSessionAttribute(httpRequest, "context", context);

    }

    @Override
    protected void finishOff(HttpServletRequest httpRequest) {
	HttpSession httpSession = httpRequest.getSession(false);
	if (httpSession != null) {
	    httpSession.invalidate();
	}
	/*
	 * SecurityContext securityContext = SecurityContextHolder.getContext(); if (securityContext != null) { SecurityContextHolder.clearContext();
	 * }
	 */
    }

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    @Override
    protected String getActivityId() {
	// TODO Auto-generated method stub
	return null;
    }

}
