package com.barclays.bmg.dao.operation.accountservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.AuthenticationDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.PostalAddressDTO;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.AuthenticationVerifyResponse;
import com.barclays.grcb.mcfe.ssc.authentication.entity.CustomerInfo;
import com.barclays.grcb.mcfe.ssc.authentication.entity.PostalAddress;
import com.barclays.grcb.mcfe.ssc.authentication.entity.ProductProcessorDetail;
import com.barclays.grcb.mcfe.ssc.authentication.entity.ResultCode;

public class SPAuthenticationResAdptOperation extends AbstractAuthResAdaptOperation {

    public AuthenticationServiceResponse adaptResponseForSPAuthentication(WorkContext workContext, Object obj) {

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
	    }

	    /**
	     * retrieve product processor details from response
	     */

	    ProductProcessorDetail[] details = response.getResult().getCustomerInfo().getProductProcessorDetails();
	    if (details != null && details.length > 0) {

		authenticationDTO.setPPMap(new HashMap<String, String>(6));
		for (ProductProcessorDetail ppDetail : details) {
		    authenticationDTO.getPPMap().put(ppDetail.getProductProcessorTypeCode(), ppDetail.getProductProcessorId());
		}
	    }

	    /**
	     * retrieve postal address from response
	     */

	    PostalAddress[] postalAddresses = response.getResult().getCustomerInfo().getPostalAddress();
	    if (postalAddresses != null && postalAddresses.length > 0) {

		List<PostalAddressDTO> paList = retrievePostalAddress(postalAddresses);
		if (authenticationDTO.getCustomerDTO() == null) {
		    authenticationDTO.setCustomerDTO(new CustomerDTO());
		}
		authenticationDTO.getCustomerDTO().setPostalAddresses(paList);
	    }

	    if (respCustomerInfo != null) {
		authenticationServiceResponse.getContext().setUserId(respCustomerInfo.getUserId());
		authenticationServiceResponse.getContext().setCustomerId(respCustomerInfo.getCustomerID());
		if (respCustomerInfo.getFullName() != null) {
		    authenticationServiceResponse.getContext().setFullName(respCustomerInfo.getFullName());
		} else {
		    authenticationServiceResponse.getContext().setFullName(respCustomerInfo.getFirstName() + " " + respCustomerInfo.getLastName());
		}
		authenticationServiceResponse.getContext().setMobilePhone(respCustomerInfo.getMobilePhone());
		authenticationServiceResponse.setSuccess(true);
	    }
	} else {
	    authenticationServiceResponse.setSuccess(false);

	}

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

	// set password migrated flag
	if (response.getResult().getPwdMigrated() == null || response.getResult().getPwdMigrated().equalsIgnoreCase("Y")) {
	    authenticationDTO.setPwdMigrated(true);
	} else if (response.getResult().getPwdMigrated().equalsIgnoreCase("N")) {
	    authenticationDTO.setPwdMigrated(false);
	}

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
	/*
	 * if(respCustomerInfo.getPostalAddress() != null && respCustomerInfo.getPostalAddress().length > 0){
	 * customerDTO.setEmailAddresses(respCustomerInfo.getPostalAddress(0).getAddressLine6()); }
	 */

	// Added for 5.X migration
	customerDTO.setEmail(respCustomerInfo.getEmail());
	if (respCustomerInfo.getDateofBirth() != null) {
	    customerDTO.setDOB(respCustomerInfo.getDateofBirth().getTime());
	}

	return customerDTO;

    }

    /**
     * retrieve postal address from response
     * 
     * @param postalAddresses
     * @return
     */
    private List<PostalAddressDTO> retrievePostalAddress(PostalAddress[] postalAddresses) {
	List<PostalAddressDTO> paList = new ArrayList<PostalAddressDTO>();
	for (PostalAddress pa : postalAddresses) {
	    if (pa != null) {
		PostalAddressDTO paDTO = new PostalAddressDTO();
		paDTO.setAddressLine1(pa.getAddressLine1());
		paDTO.setAddressLine2(pa.getAddressLine2());
		paDTO.setAddressLine3(pa.getAddressLine3());
		paDTO.setCityCode(pa.getCityCode());
		paDTO.setCityName(pa.getCityName());
		paDTO.setCountry(pa.getCountry());
		paDTO.setCountryCode(pa.getCountryCode());
		paDTO.setStateCode(pa.getStateCode());
		paDTO.setStateName(pa.getStateName());
		paDTO.setTypeCode(pa.getAddressTypeCode());
		paDTO.setZipCode(pa.getZipCode());
		paList.add(paDTO);
	    }
	}

	return paList;
    }

}
