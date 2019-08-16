package com.barclays.bmg.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.auth.SQACommand;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.request.SQAAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.SQAAuthenticationOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class SQAValidationController extends BMBAbstractCommandController {

    SQAAuthenticationOperation sqaAuthenticationOperation;
    private String securityCheckRedirectURL = "/login2/j_spring_security_check";

    private BMBJSONBuilder bmbJSONBldr;

    @Override
    public String getSecurityCheckRedirectURL() {
	return securityCheckRedirectURL;
    }

    /**
     * Entry point into controller. Handle http request.
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	// Check session validity
	checkIfSessionActive(request, response);

	SQACommand sqaCommand = (SQACommand) command;

	SQAAuthenticationOperationRequest sqaAuthenticationOperationRequest = makeRequest(request);

	sqaAuthenticationOperationRequest.setSqa(sqaCommand.getSqa());

	SQAAuthenticationOperationResponse sqaAuthenticationOperationResponse = sqaAuthenticationOperation.verify(sqaAuthenticationOperationRequest);

	if (sqaAuthenticationOperationResponse.isSuccess()) {

	    setAuthorisationInSession(request);

	    response.sendRedirect(request.getContextPath() + getSecurityCheckRedirectURL());

	}
	return (BMBBaseResponseModel) bmbJSONBldr.createJSONResponse(sqaAuthenticationOperationResponse);

    }

    /**
     * make request for sqa verification
     *
     * @param request
     * @return
     */
    private SQAAuthenticationOperationRequest makeRequest(HttpServletRequest request) {

	SQAAuthenticationOperationRequest sqaAuthenticationOperationRequest = new SQAAuthenticationOperationRequest();

	Context context = createContext(request);
	context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);

	context.setActivityRefNo(BMBCommonUtility.generateRefNo());

	sqaAuthenticationOperationRequest.setContext(context);

	Map<String, Object> processMap = getProcessMapFromSession(request);

	sqaAuthenticationOperationRequest.setQuestionId((String) processMap.get(SessionConstant.SESSION_QUESTION_ID));

	return sqaAuthenticationOperationRequest;

    }

    public SQAAuthenticationOperation getSqaAuthenticationOperation() {
	return sqaAuthenticationOperation;
    }

    public void setSqaAuthenticationOperation(SQAAuthenticationOperation sqaAuthenticationOperation) {
	this.sqaAuthenticationOperation = sqaAuthenticationOperation;
    }

    private void setAuthorisationInSession(HttpServletRequest request) {

	/*
	 * GrantedAuthority[] authorities = new GrantedAuthorityImpl[1]; authorities[0] = new GrantedAuthorityImpl(ROLE_USER);
	 *
	 * Authentication authentication = new PreAuthenticatedAuthenticationToken( userMap.get(SessionConstant.SESSION_USER_ID), "", authorities);
	 * HttpSession session = request.getSession(false); if (session != null) { session.setAttribute(AUTH_RESULT, authentication); }
	 */
    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    public BMBJSONBuilder getBmbJSONBldr() {
	return bmbJSONBldr;
    }

    public void setBmbJSONBldr(BMBJSONBuilder bmbJSONBldr) {
	this.bmbJSONBldr = bmbJSONBldr;
    }

}
