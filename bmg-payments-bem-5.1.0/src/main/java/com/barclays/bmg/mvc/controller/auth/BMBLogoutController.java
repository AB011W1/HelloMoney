package com.barclays.bmg.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.operation.SessionActivityOperation;
import com.barclays.bmg.operation.request.SessionSummaryOperationRequest;
import com.barclays.bmg.operation.response.SessionSummaryOperationResponse;

public class BMBLogoutController extends BMBAbstractController {

    private SessionActivityOperation sessionSummaryRetrievalOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest request, HttpServletResponse response) throws Exception {

	SessionSummaryOperationRequest seOperationRequest = new SessionSummaryOperationRequest();
	seOperationRequest.setContext(createContext(request));

	sessionSummaryRetrievalOperation.persistLogoutInformation(seOperationRequest);

	SessionSummaryOperationResponse resp = sessionSummaryRetrievalOperation.getSessionSummary(seOperationRequest);

	finishOff(request);

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(resp);
    }

    public SessionActivityOperation getSessionSummaryRetrievalOperation() {
	return sessionSummaryRetrievalOperation;
    }

    public void setSessionSummaryRetrievalOperation(SessionActivityOperation sessionSummaryRetrievalOperation) {
	this.sessionSummaryRetrievalOperation = sessionSummaryRetrievalOperation;
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
