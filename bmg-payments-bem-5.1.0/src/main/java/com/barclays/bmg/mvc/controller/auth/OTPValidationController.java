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
import com.barclays.bmg.mvc.command.auth.OTPCommand;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.request.OTPAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.OTPAuthenticationOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class OTPValidationController extends BMBAbstractCommandController {

    OTPAuthenticationOperation otpAuthenticationOperation;

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

	OTPCommand otpCommand = (OTPCommand) command;

	OTPAuthenticationOperationRequest otpAuthenticationOperationRequest = makeRequest(request);

	otpAuthenticationOperationRequest.setOtp(otpCommand.getOtp());

	OTPAuthenticationOperationResponse otpAuthenticationOperationResponse = otpAuthenticationOperation.verify(otpAuthenticationOperationRequest);

	if (otpAuthenticationOperationResponse.isSuccess()) {

	    setAuthorisationInSession(request);
	    response.sendRedirect(request.getContextPath() + getSecurityCheckRedirectURL());

	} else {

	}

	return (BMBBaseResponseModel) bmbJSONBldr.createJSONResponse(otpAuthenticationOperationResponse);

    }

    /**
     * make request for otp verification
     * 
     * @param request
     * @return
     */
    private OTPAuthenticationOperationRequest makeRequest(HttpServletRequest request) {

	OTPAuthenticationOperationRequest otpAuthenticationOperationRequest = new OTPAuthenticationOperationRequest();

	Context context = createContext(request);
	context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);

	/*
	 * Map<String, String> userMap = getUserMapFromSession(request); context. setBusinessId(userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	 * context .setCountryCode(userMap.get(SessionConstant.SESSION_COUNTRY_CODE)); context
	 * .setLanguageId(userMap.get(SessionConstant.SESSION_LANGUAGE_ID)); context.setSystemId(userMap.get(SessionConstant.SESSION_SYSTEM_ID));
	 * context .setCustomerId(userMap.get(SessionConstant.SESSION_CUSTOMER_ID)); context.setUserId(userMap.get(SessionConstant.SESSION_USER_ID));
	 */
	context.setActivityRefNo(BMBCommonUtility.generateRefNo());

	otpAuthenticationOperationRequest.setContext(context);

	Map<String, Object> processMap = getProcessMapFromSession(request);

	otpAuthenticationOperationRequest.setChallengeId((String) processMap.get(SessionConstant.SESSION_CHALLENGE_ID));
	otpAuthenticationOperationRequest.setMobilePhone((String) processMap.get(SessionConstant.SESSION_MOBILE_PHONE));

	return otpAuthenticationOperationRequest;

    }

    public OTPAuthenticationOperation getOtpAuthenticationOperation() {
	return otpAuthenticationOperation;
    }

    public void setOtpAuthenticationOperation(OTPAuthenticationOperation otpAuthenticationOperation) {
	this.otpAuthenticationOperation = otpAuthenticationOperation;
    }

    private void setAuthorisationInSession(HttpServletRequest request) {
	// Map<String, Object> userMap = getUserMapFromSession(request);

	/*
	 * GrantedAuthority[] authorities = new GrantedAuthorityImpl[1]; authorities[0] = new GrantedAuthorityImpl(ROLE_USER);
	 * 
	 * Authentication authentication = new PreAuthenticatedAuthenticationToken( userMap.get(SessionConstant.SESSION_USER_ID), "", authorities);
	 * HttpSession session = request.getSession(false); if (session != null) { session.setAttribute(AUTH_RESULT, authentication); }
	 */
    }

    @Override
    protected String getActivityId(Object command) {
	return null;
    }

    public BMBJSONBuilder getBmbJSONBldr() {
	return bmbJSONBldr;
    }

    public void setBmbJSONBldr(BMBJSONBuilder bmbJSONBldr) {
	this.bmbJSONBldr = bmbJSONBldr;
    }

}
