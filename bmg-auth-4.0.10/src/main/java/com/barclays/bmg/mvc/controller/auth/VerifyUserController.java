package com.barclays.bmg.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.auth.ValidateUserCommand;
import com.barclays.bmg.operation.VerifyUserOperation;
import com.barclays.bmg.operation.request.ValidateUserOperationRequest;
import com.barclays.bmg.operation.response.ValidateUserOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class VerifyUserController extends BMBAbstractCommandController {

    private VerifyUserOperation veifyUserOperation;

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

	String resId = null;

	// Check session validity
	checkIfSessionActive(request, response);

	ValidateUserCommand sqaCommand = (ValidateUserCommand) command;

	ValidateUserOperationRequest validateUserOperationRequest = makeRequest(request);

	validateUserOperationRequest.setUsername(sqaCommand.getUsrNam());

	ValidateUserOperationResponse validateUserOperationResponse = veifyUserOperation.verifyUserExistsOrNot(validateUserOperationRequest);

	if (validateUserOperationResponse.isSuccess()) {

	    validateUserOperationResponse.setSuccess(true);
	    resId = ResponseIdConstants.VALIDATEUSER_SUCCESS;

	} else {
	    validateUserOperationResponse.setSuccess(false);
	    resId = ResponseIdConstants.VALIDATEUSER_FAILURE_RESPONSE_ID;

	}

	return (BMBBaseResponseModel) bmbJSONBldr.createJSONResponse(validateUserOperationResponse);

    }

    public VerifyUserOperation getVeifyUserOperation() {
	return veifyUserOperation;
    }

    public void setVeifyUserOperation(VerifyUserOperation veifyUserOperation) {
	this.veifyUserOperation = veifyUserOperation;
    }

    @Override
    public void setSecurityCheckRedirectURL(String securityCheckRedirectURL) {
	this.securityCheckRedirectURL = securityCheckRedirectURL;
    }

    /**
     * make request for sqa verification
     * 
     * @param request
     * @return
     */
    private ValidateUserOperationRequest makeRequest(HttpServletRequest request) {

	ValidateUserOperationRequest validateUserOperationRequest = new ValidateUserOperationRequest();

	Context context = createContext(request);
	context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);

	context.setActivityRefNo(BMBCommonUtility.generateRefNo());

	validateUserOperationRequest.setContext(context);

	// Map<String, Object> processMap = getProcessMapFromSession(request);

	/*
	 * validateUserOperationRequest.setQuestionId((String) processMap .get(SessionConstant.SESSION_QUESTION_ID));
	 */

	return validateUserOperationRequest;

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
