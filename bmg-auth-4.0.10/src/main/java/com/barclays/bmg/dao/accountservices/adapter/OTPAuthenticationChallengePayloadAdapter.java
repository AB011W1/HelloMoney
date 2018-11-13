package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.OTPChallengeRequest;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.Request;
import com.barclays.grcb.mcfe.ssc.authentication.entity.AuthenticationType;
import com.barclays.grcb.mcfe.ssc.authentication.entity.CustomerInfo;

public class OTPAuthenticationChallengePayloadAdapter {

    /**
     * Prepare request body for OTP generation
     * 
     * @param workContext
     * @return
     */
    public Request adaptPayLoad(WorkContext workContext) {
	Request requestBody = new Request();
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	AuthenticationServiceRequest authenticationServiceRequest = (AuthenticationServiceRequest) args[0];

	String[] smsParams = authenticationServiceRequest.getSmsParams();
	String smsTemplate = authenticationServiceRequest.getSmsTemplate();

	requestBody.setAuthenticationType(AuthenticationType.OTP);

	OTPChallengeRequest otpRequest = new OTPChallengeRequest();

	CustomerInfo customerInfo = new CustomerInfo();
	customerInfo.setCustomerID(authenticationServiceRequest.getCustomerId());
	customerInfo.setMobilePhone(authenticationServiceRequest.getMobilePhone());
	otpRequest.setCustomerInfo(customerInfo);
	String[] params = null;
	if (smsParams != null && smsParams.length > 0) {
	    params = new String[smsParams.length + 1];
	    params[0] = smsTemplate;
	    for (int i = 0; i < smsParams.length; i++) {
		params[i + 1] = smsParams[i];
	    }
	} else {
	    params = new String[1];
	    params[0] = smsTemplate;
	}
	otpRequest.setSmsParams(params);
	requestBody.setOTPChallengeRequest(otpRequest);

	return requestBody;
    }
}
