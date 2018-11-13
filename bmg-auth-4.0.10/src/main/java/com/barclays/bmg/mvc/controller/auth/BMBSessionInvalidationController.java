package com.barclays.bmg.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;

public class BMBSessionInvalidationController extends BMBAbstractController {

    private BMBJSONBuilder bmbJsonBuilder;

    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest request, HttpServletResponse response) throws Exception {

	ResponseContext responseContext = new ResponseContext();

	responseContext.setContext(makeContext(request));
	String resCde = request.getParameter("resCde");

	if (resCde != null) {

	    responseContext.setSuccess(false);
	    responseContext.setResCde(resCde);
	    getMessage(responseContext);

	} else {
	    responseContext.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
	    responseContext.setResMsg("Please Login");
	}

	responseContext.setContext(makeContext(request));
	responseContext.setErrTyp(ErrorCodeConstant.RELOGIN_NEEDED_ERROR);

	// FIXME need to retrieve error message based upon request param and
	// populate
	// responseContext.setResMsg("Please Login");

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

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
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

    @Override
    protected String getActivityId() {
	// TODO Auto-generated method stub
	return null;
    }

}
