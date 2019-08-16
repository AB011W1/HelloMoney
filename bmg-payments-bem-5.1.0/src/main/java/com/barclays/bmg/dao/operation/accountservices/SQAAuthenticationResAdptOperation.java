package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.AuthenticationDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.AuthenticationVerifyResponse;
import com.barclays.grcb.mcfe.ssc.authentication.entity.CustomerInfo;
import com.barclays.grcb.mcfe.ssc.authentication.entity.ResultCode;

public class SQAAuthenticationResAdptOperation extends AbstractAuthResAdaptOperation {

    public AuthenticationServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	AuthenticationVerifyResponse response = (AuthenticationVerifyResponse) obj;

	AuthenticationDTO authenticationDTO = null;

	/**
	 * prepare authentication service response
	 */
	AuthenticationServiceResponse authenticationServiceResponse = new AuthenticationServiceResponse();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AuthenticationServiceRequest authenticationServiceRequest = (AuthenticationServiceRequest) args[0];

	authenticationServiceResponse.setContext(authenticationServiceRequest.getContext());

	/**
	 * Check response code
	 */

	String resCde = checkAuthResponse(response.getResponseHeader());

	if (AuthResponseCodeConstants.AUTH_SUCCESS.equals(resCde)) {

	    /**
	     * retrieve user info from response
	     */
	    authenticationDTO = retrieveUserInfo(response);

	    /**
	     * retrieve customer details from response
	     */

	    CustomerInfo respCustomerInfo = response.getResult().getCustomerInfo();
	    if (respCustomerInfo != null) {

		CustomerDTO customerDTO = retrieveCustomerInfo(respCustomerInfo);
		authenticationDTO.setCustomerDTO(customerDTO);
		authenticationDTO.setUserId(respCustomerInfo.getUserId());
		authenticationServiceResponse.getContext().setUserId(respCustomerInfo.getUserId());
	    }

	    authenticationServiceResponse.setSuccess(true);

	} else {
	    authenticationServiceResponse.setSuccess(false);
	}

	/**
	 * prepare authentication service response
	 */

	authenticationServiceResponse.setResCde(resCde);

	authenticationServiceResponse.setAuthenticationDTO(authenticationDTO);

	return authenticationServiceResponse;
    }

    /**
     * retrieve user info from response
     * 
     * @param response
     * @return
     */
    private AuthenticationDTO retrieveUserInfo(AuthenticationVerifyResponse response) {

	AuthenticationDTO authenticationDTO = new AuthenticationDTO();

	authenticationDTO.setResultCode(response.getResult().getResultCode().getValue());

	// checkResponseCode(response.getResponseHeader());

	authenticationDTO.setRetryBeforeLock(response.getResult().getMaxRetryTimes() - response.getResult().getFailures());
	authenticationDTO.setFailures(response.getResult().getFailures());
	authenticationDTO.setMaxRetryTimes(response.getResult().getMaxRetryTimes());
	authenticationDTO.setNeedChangePWD(response.getResult().isNeedChangePwd());
	authenticationDTO.setNeedChangeTxnPWD(response.getResult().isNeedChangeTxnPwd());
	authenticationDTO.setPasswordLeftWarningDays(response.getResult().getLoginPwdWarningDays());
	authenticationDTO.setTxnPasswordLeftWarningDays(response.getResult().getTxnPwdWarningDays());
	authenticationDTO.setPreferredLanguage(response.getResult().getPreferedLanguage());

	if (response.getResult().getResultCode().getValue().equals(ResultCode._success)) {
	    authenticationDTO.setAuthenticated(true);
	} else {
	    authenticationDTO.setAuthenticated(false);
	}

	return authenticationDTO;
    }

    /**
     * retrieve product processor details from response
     * 
     * @param respCustomerInfo
     * @return
     */
    private CustomerDTO retrieveCustomerInfo(CustomerInfo respCustomerInfo) {

	CustomerDTO customerDTO = new CustomerDTO();
	customerDTO.setSurname1(respCustomerInfo.getLastName());
	customerDTO.setGivenName(respCustomerInfo.getFirstName());
	String customerType = respCustomerInfo.getCustomerSegment() == null ? "MASS" : respCustomerInfo.getCustomerSegment();
	customerDTO.setCustomerSegment(customerType);
	customerDTO.setCustomerID(respCustomerInfo.getCustomerID());
	customerDTO.setMobilePhone(respCustomerInfo.getMobilePhone());
	customerDTO.setSalutation(respCustomerInfo.getSalutation());
	customerDTO.setOrgCode(respCustomerInfo.getOrganizationCode());
	customerDTO.setFullName(respCustomerInfo.getFullName());
	customerDTO.setMiddleName(respCustomerInfo.getMiddleName());
	customerDTO.setUserId(respCustomerInfo.getUserId());
	customerDTO.setUserName(respCustomerInfo.getUserName());
	if (respCustomerInfo.getPostalAddress() != null && respCustomerInfo.getPostalAddress().length > 0) {
	    customerDTO.setEmailAddresses(respCustomerInfo.getPostalAddress(0).getAddressLine6());
	}

	return customerDTO;

    }

}
