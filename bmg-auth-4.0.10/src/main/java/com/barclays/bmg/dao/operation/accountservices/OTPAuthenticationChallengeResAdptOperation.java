package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.AuthServiceResponseCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.AuthenticationDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.AuthenticationChallengeResponse;

public class OTPAuthenticationChallengeResAdptOperation extends AbstractAuthResAdaptOperation {

    public AuthenticationServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	AuthenticationChallengeResponse response = (AuthenticationChallengeResponse) obj;

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AuthenticationServiceRequest authenticationServiceRequest = (AuthenticationServiceRequest) args[0];

	AuthenticationServiceResponse authenticationServiceResponse = new AuthenticationServiceResponse();

	authenticationServiceResponse.setContext(authenticationServiceRequest.getContext());

	AuthenticationDTO authenticationDTO = null;

	/**
	 * Check response code
	 */

	String resCde = checkAuthResponse(response.getResponseHeader());

	if (AuthResponseCodeConstants.AUTH_SUCCESS.equals(resCde)) {

	    String customerId = authenticationServiceRequest.getCustomerId();
	    String mobilePhone = authenticationServiceRequest.getMobilePhone();
	    authenticationDTO = new AuthenticationDTO();
	    String challengeId = response.getOTPInfo().getChallengeID();
	    String prefix = response.getOTPInfo().getOTPPrefix();
	    authenticationDTO.setChallengeId(challengeId);
	    authenticationDTO.setOtpPrefix(prefix);

	    CustomerDTO customerDTO = new CustomerDTO();
	    customerDTO.setCustomerID(customerId);
	    customerDTO.setMobilePhone(mobilePhone);
	    authenticationDTO.setCustomerDTO(customerDTO);
	    authenticationServiceResponse.setSuccess(true);
	    if (AuthServiceResponseCodeConstant.SUCCESS_CODE.equals(response.getResponseHeader().getServiceResStatus().getServiceResCode())) {
		authenticationDTO.setAuthenticated(true);
	    } else {
		authenticationDTO.setAuthenticated(false);
	    }

	} else {
	    authenticationServiceResponse.setSuccess(false);
	}

	authenticationServiceResponse.setResCde(resCde);

	authenticationServiceResponse.setAuthenticationDTO(authenticationDTO);

	return authenticationServiceResponse;
    }

}
