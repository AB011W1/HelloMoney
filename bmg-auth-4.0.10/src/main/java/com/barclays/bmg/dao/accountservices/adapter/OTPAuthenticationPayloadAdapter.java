package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.Request;
import com.barclays.grcb.mcfe.ssc.authentication.entity.AuthenticationType;
import com.barclays.grcb.mcfe.ssc.authentication.entity.CustomerInfo;
import com.barclays.grcb.mcfe.ssc.authentication.entity.OTPInfo;
import com.barclays.grcb.mcfe.ssc.authentication.entity.OTPVerificationInfo;

public class OTPAuthenticationPayloadAdapter {

    public Request adaptPayLoad(WorkContext workContext) {
	Request requestBody = new Request();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AuthenticationServiceRequest authenticationServiceRequest = (AuthenticationServiceRequest) args[0];

	requestBody.setAuthenticationType(AuthenticationType.OTP);
	OTPVerificationInfo otp = new OTPVerificationInfo();
	CustomerInfo customerInfo = new CustomerInfo();

	customerInfo.setCustomerID(authenticationServiceRequest.getCustomerId());
	customerInfo.setMobilePhone(authenticationServiceRequest.getMobilePhone());
	otp.setCustomerInfo(customerInfo);
	otp.setOTP(authenticationServiceRequest.getOtp());
	OTPInfo otpInfo = new OTPInfo();
	otpInfo.setChallengeID(authenticationServiceRequest.getChallengeId());
	// otpInfo.setOTPPrefix(authentication.getOtpPrefix());
	otp.setOTPInfo(otpInfo);
	requestBody.setOTPVerificationInfo(otp);

	return requestBody;
    }
}
