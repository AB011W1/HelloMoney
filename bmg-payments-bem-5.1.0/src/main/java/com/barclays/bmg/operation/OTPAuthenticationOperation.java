package com.barclays.bmg.operation;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ScriptResourceConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dto.AuthenticationDTO;
import com.barclays.bmg.operation.request.OTPAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.OTPAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.service.AuthenticationChallengeService;
import com.barclays.bmg.service.AuthenticationService;
import com.barclays.bmg.service.ScriptResourceService;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.request.ScriptResourceServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;
import com.barclays.bmg.service.response.ScriptResourceServiceResponse;

/**
 * OTP Authentication Operation
 * 
 * @author e20026338
 * 
 */
public class OTPAuthenticationOperation extends BMBCommonOperation {

    AuthenticationChallengeService authenticationChallengeService;
    AuthenticationService authenticationService;
    ScriptResourceService scriptResourceService;

    /**
     * Verify otp operation
     * 
     * @param request
     * @return
     */
    public OTPAuthenticationOperationResponse verify(OTPAuthenticationOperationRequest request) {
	AuthenticationServiceRequest authenticationServiceRequest = new AuthenticationServiceRequest();

	OTPAuthenticationOperationResponse otpAuthenticationOperationResponse = null;

	/**
	 * Prepare request
	 */
	authenticationServiceRequest.setContext(request.getContext());

	String customerId = request.getContext().getCustomerId();

	authenticationServiceRequest.setChallengeId(request.getChallengeId());
	authenticationServiceRequest.setCustomerId(customerId);
	authenticationServiceRequest.setMobilePhone(request.getMobilePhone());
	authenticationServiceRequest.setOtp(request.getOtp());

	/**
	 * Call service
	 */
	AuthenticationServiceResponse authenticationServiceResponse = authenticationService.verify(authenticationServiceRequest);

	/**
	 * Prepare response
	 */
	otpAuthenticationOperationResponse = new OTPAuthenticationOperationResponse();

	otpAuthenticationOperationResponse.setContext(authenticationServiceResponse.getContext());

	boolean authenticated = authenticationServiceResponse.isSuccess();
	AuthenticationDTO authDTO = authenticationServiceResponse.getAuthenticationDTO();
	String resCde = authenticationServiceResponse.getResCde();
	String resMsg = authenticationServiceResponse.getResMsg();

	otpAuthenticationOperationResponse.setSuccess(authenticated);
	otpAuthenticationOperationResponse.setResCde(resCde);
	otpAuthenticationOperationResponse.setResMsg(resMsg);

	/**
	 * Get message for corresponding code
	 */
	if (!authenticated) {

	    Integer leftRetryTimes = authDTO.getRetryBeforeLock();
	    getMessage(otpAuthenticationOperationResponse, new Object[] { leftRetryTimes });
	}

	return otpAuthenticationOperationResponse;

    }

    /**
     * Generate otp operation
     * 
     * @param request
     * @return
     */
    public OTPGenerateAuthenticationOperationResponse generate(OTPGenerateAuthenticationOperationRequest request) {
	OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse = null;
	AuthenticationServiceRequest authenticationServiceRequest = new AuthenticationServiceRequest();

	String mobileNo = request.getMobilePhone();

	/**
	 * If mobile no is not present send error
	 */
	if (mobileNo == null || "".equals(mobileNo.trim())) {
	    otpAuthenticationOperationResponse = new OTPGenerateAuthenticationOperationResponse();
	    otpAuthenticationOperationResponse.setContext(request.getContext());
	    otpAuthenticationOperationResponse.setSuccess(false);
	    otpAuthenticationOperationResponse.setResCde(AuthResponseCodeConstants.NO_MOBILE_ERROR);
	    getMessage(otpAuthenticationOperationResponse);
	    return otpAuthenticationOperationResponse;
	} else {

	    String mobileFormat = "";

	    // Added for 5.x migration
	    String zoneNo = getSysParamValue(request.getContext(), SystemParameterConstant.ZONE_NUMBER, SystemParameterConstant.COMMON_ACTIVITY_ID);

	    if (zoneNo != null) {
		mobileNo = zoneNo.trim() + mobileNo;
	    }

	    mobileFormat = getSysParamValue(request.getContext(), SystemParameterConstant.OTP_MOBILE_FORMAT, "COMMON");

	    if (!StringUtils.isEmpty(mobileFormat) && !StringUtils.isEmpty(mobileNo)) {

		if (!mobileNo.matches(mobileFormat)) {

		    otpAuthenticationOperationResponse = new OTPGenerateAuthenticationOperationResponse();
		    otpAuthenticationOperationResponse.setContext(request.getContext());
		    otpAuthenticationOperationResponse.setSuccess(false);
		    otpAuthenticationOperationResponse.setResCde(AuthResponseCodeConstants.INVALID_MOBILE_NUMBER);
		    getMessage(otpAuthenticationOperationResponse);
		    return otpAuthenticationOperationResponse;
		}

	    }

	}
	/**
	 * Prepare request
	 */
	authenticationServiceRequest.setContext(request.getContext());
	authenticationServiceRequest.setCustomerId(request.getCustomerId());
	authenticationServiceRequest.setMobilePhone(mobileNo);
	/**
	 * get sms template from script response table
	 */
	ScriptResourceServiceRequest scriptResourceServiceRequest = new ScriptResourceServiceRequest();
	ScriptResourceServiceResponse scriptResourceServiceResponse = null;
	scriptResourceServiceRequest.setContext(request.getContext());

	scriptResourceServiceRequest.setScriptKey(request.getContext().getActivityId() + ScriptResourceConstant.SMS_TPL);

	scriptResourceServiceResponse = scriptResourceService.getScriptResourceByKey(scriptResourceServiceRequest);
	String smsTemplate = scriptResourceServiceResponse.getScriptValue();
	authenticationServiceRequest.setSmsTemplate(smsTemplate);

	authenticationServiceRequest.setSmsParams(request.getSmsParams());

	/**
	 * Call service
	 */
	AuthenticationServiceResponse authenticationServiceResponse = authenticationChallengeService.retrieveChallenge(authenticationServiceRequest);

	/**
	 * Prepare response
	 */
	otpAuthenticationOperationResponse = new OTPGenerateAuthenticationOperationResponse();
	otpAuthenticationOperationResponse.setContext(authenticationServiceResponse.getContext());

	boolean authenticated = authenticationServiceResponse.isSuccess();
	String resCde = authenticationServiceResponse.getResCde();
	String resMsg = authenticationServiceResponse.getResMsg();

	otpAuthenticationOperationResponse.setSuccess(authenticated);
	otpAuthenticationOperationResponse.setResCde(resCde);
	otpAuthenticationOperationResponse.setResMsg(resMsg);

	/**
	 * Get message for corresponding code
	 */
	if (!authenticated) {
	    getMessage(otpAuthenticationOperationResponse);
	}
	otpAuthenticationOperationResponse.setSuccess(authenticated);
	AuthenticationDTO authenticationDTO = authenticationServiceResponse.getAuthenticationDTO();

	otpAuthenticationOperationResponse.setChallengeId((String) authenticationDTO.getChallengeId());
	otpAuthenticationOperationResponse.setCustomerId(authenticationDTO.getCustomerDTO().getCustomerID());
	otpAuthenticationOperationResponse.setMobilePhone(authenticationDTO.getCustomerDTO().getMobilePhone());
	otpAuthenticationOperationResponse.setOtpPrefix(authenticationServiceResponse.getAuthenticationDTO().getOtpPrefix());

	/**
	 * get otp header/footer from script response table
	 */

	if (authenticated) {

	    /**
	     * get otp header 1
	     */
	    scriptResourceServiceRequest.setScriptKey(ScriptResourceConstant.OTP_HEADER_LINE_ONE);

	    scriptResourceServiceResponse = scriptResourceService.getScriptResourceByKey(scriptResourceServiceRequest);
	    String otpHeaderLine1 = scriptResourceServiceResponse.getScriptValue();
	    otpAuthenticationOperationResponse.setOtpHeaderLine1(otpHeaderLine1);

	    /**
	     * get otp header 1
	     */
	    scriptResourceServiceRequest.setScriptKey(ScriptResourceConstant.OTP_HEADER_LINE_TWO);

	    scriptResourceServiceResponse = scriptResourceService.getScriptResourceByKey(scriptResourceServiceRequest);
	    String otpHeaderLine2 = scriptResourceServiceResponse.getScriptValue();
	    otpAuthenticationOperationResponse.setOtpHeaderLine2(otpHeaderLine2);

	    /**
	     * get otp footer
	     */

	    /**
	     * get otp header 1
	     */
	    scriptResourceServiceRequest.setScriptKey("otpFooter");

	    scriptResourceServiceResponse = scriptResourceService.getScriptResourceByKey(scriptResourceServiceRequest);
	    String otpFooter = scriptResourceServiceResponse.getScriptValue();
	    otpAuthenticationOperationResponse.setOtpFooter(otpFooter);

	}

	return otpAuthenticationOperationResponse;
    }

    public AuthenticationChallengeService getAuthenticationChallengeService() {
	return authenticationChallengeService;
    }

    public void setAuthenticationChallengeService(AuthenticationChallengeService authenticationChallengeService) {
	this.authenticationChallengeService = authenticationChallengeService;
    }

    public AuthenticationService getAuthenticationService() {
	return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
	this.authenticationService = authenticationService;
    }

    public ScriptResourceService getScriptResourceService() {
	return scriptResourceService;
    }

    public void setScriptResourceService(ScriptResourceService scriptResourceService) {
	this.scriptResourceService = scriptResourceService;
    }

}
