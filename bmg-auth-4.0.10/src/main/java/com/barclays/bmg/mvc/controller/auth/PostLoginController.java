package com.barclays.bmg.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.operation.PostAuthenticationOperation;
import com.barclays.bmg.operation.SessionActivityOperation;
import com.barclays.bmg.operation.request.PostAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SessionSummaryOperationRequest;
import com.barclays.bmg.operation.response.PostAuthenticationOperationResponse;

/**
 * This file is stubbed for testing purpose. MockResponses are used for temporary purposes.
 * 
 * @author E20041929
 * 
 */
public class PostLoginController extends BMBAbstractController {

    PostAuthenticationOperation postAuthenticationOperation;

    private SessionActivityOperation sessionActivityOperation;

    private BMBJSONBuilder bmbJsonBuilder;

    private String serVerUri;

    /**
     * entry point into this controller
     */
    @Override
    public BMBBaseResponseModel handleRequestInternal1(HttpServletRequest request, HttpServletResponse response) throws Exception {

	BMBBaseResponseModel returnBMBPayloadResponse = null;

	makeRequest(request);

	/*
	 * PostAuthenticationOperationResponse postAuthenticationOperationResponse = postAuthenticationOperation
	 * .afterLoginSuccess(postAuthenticationOperationRequest);
	 */

	// Mock it
	PostAuthenticationOperationResponse postAuthenticationOperationResponse = new PostAuthenticationOperationResponse();

	postAuthenticationOperationResponse.setSuccess(true);

	if (postAuthenticationOperationResponse.isSuccess()) {

	    /*
	     * setUserMapIntoSession(request, SessionConstant.SESSION_LAST_LOGIN_TIME, postAuthenticationOperationResponse.getLastLoginTime()
	     * .toString());
	     */

	    Map<String, Object> userMap = getProcessMapFromSession(request);

	    String isNeedChgPwd = (String) userMap.get(SessionConstant.IS_NEED_CHANGE_PWD);

	    String secondAuthRespId = (String) userMap.get(SessionConstant.SESSION_SECOND_AUTH_TYPE);

	    boolean isNeedChgPwdBool = false;

	    if (isNeedChgPwd != null) {
		isNeedChgPwdBool = Boolean.parseBoolean(isNeedChgPwd);
	    }

	    if (isNeedChgPwdBool) {
		postAuthenticationOperationResponse.setResCde(AuthResponseCodeConstants.PASSWORD_EXPIRED);
		postAuthenticationOperationResponse.setSecondAuthResId(secondAuthRespId);
		postAuthenticationOperationResponse.setSuccess(false);
		getMessage(postAuthenticationOperationResponse);

		returnBMBPayloadResponse = (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(postAuthenticationOperationResponse);
		finishOff(request);
		return returnBMBPayloadResponse;

	    } else {

		// Added tempUri for UAE Prod issues
		// String tempUri = requestUri;
		String tempUri = "/dataserv?opCde=OP0200";
		if (!StringUtils.isEmpty(serVerUri)) {
		    tempUri += "&" + serVerUri;
		}

		// logger.debug("tempUri:-" + tempUri);

		/*
		 * request.getRequestDispatcher(tempUri) .forward(request, response);
		 */
	    }

	} else {

	}

	SessionSummaryOperationRequest seOperationRequest = new SessionSummaryOperationRequest();
	seOperationRequest.setContext(createContext(request));
	sessionActivityOperation.persistLoginInformation(seOperationRequest);

	postAuthenticationOperationResponse.setResCde("00000");
	postAuthenticationOperationResponse.setSecondAuthResId("RES0100");
	postAuthenticationOperationResponse.setSuccess(true);

	returnBMBPayloadResponse = (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(postAuthenticationOperationResponse);
	return returnBMBPayloadResponse;

    }

    /**
     * make request for post login
     * 
     * @param request
     * @return
     */
    private PostAuthenticationOperationRequest makeRequest(HttpServletRequest request) {

	PostAuthenticationOperationRequest postAuthenticationOperationRequest = new PostAuthenticationOperationRequest();
	Context context = new Context();

	Map<String, Object> userMap = getUserMapFromSession(request);
	context.setBusinessId((String) userMap.get("businessId"));
	context.setCountryCode((String) userMap.get("countryCode"));
	context.setLanguageId((String) userMap.get("languageId"));
	context.setSystemId((String) userMap.get("systemId"));
	context.setCustomerId((String) userMap.get("customerId"));
	context.setUserId((String) userMap.get("userId"));

	postAuthenticationOperationRequest.setContext(context);

	Map<String, Object> processMap = getProcessMapFromSession(request);

	postAuthenticationOperationRequest.setMobilePhone((String) processMap.get("mobilePhone"));

	return postAuthenticationOperationRequest;
    }

    public PostAuthenticationOperation getPostAuthenticationOperation() {
	return postAuthenticationOperation;
    }

    public void setPostAuthenticationOperation(PostAuthenticationOperation postAuthenticationOperation) {
	this.postAuthenticationOperation = postAuthenticationOperation;
    }

    public SessionActivityOperation getSessionActivityOperation() {
	return sessionActivityOperation;
    }

    public void setSessionActivityOperation(SessionActivityOperation sessionActivityOperation) {
	this.sessionActivityOperation = sessionActivityOperation;
    }

    @Override
    public void setRequestUri(String requestUri) {
    }

    @Override
    public void setSerVerUri(String serVerUri) {
	this.serVerUri = serVerUri;
    }

    @Override
    protected String getActivityId() {
	// TODO Auto-generated method stub
	return null;
    }

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

}
