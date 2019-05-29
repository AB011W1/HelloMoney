package com.barclays.bmg.dao.operation.accountservices;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.PostalAddressDTO;
import com.barclays.bmg.dto.ValidateUserDTO;
import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.bmg.service.response.ValidateUserServiceResponse;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.AuthResHeader;
import com.barclays.grcb.mcfe.ssc.authentication.ValidateUserByLoginNameIgnoreCase.ValidateUserByLoginNameIgnoreCaseResponse;
import com.barclays.grcb.mcfe.ssc.authentication.entity.CustomerInfo;
import com.barclays.grcb.mcfe.ssc.authentication.entity.PostalAddress;
import com.barclays.grcb.mcfe.ssc.authentication.entity.ResultCode;
import com.barclays.grcb.mcfe.ssc.authentication.entity.VerificationResult;

public class ValidateUserResAdptOperation extends AbstractAuthResAdaptOperation {

    public ValidateUserServiceResponse adaptResponseForValidateUser(WorkContext workContext, Object obj) {

	ValidateUserByLoginNameIgnoreCaseResponse response = (ValidateUserByLoginNameIgnoreCaseResponse) obj;
	ValidateUserDTO validateUserDTO = null;
	/**
	 * prepare authentication service response
	 */

	ValidateUserServiceResponse validateUserServiceResponse = new ValidateUserServiceResponse();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	ValidateUserServiceRequest validateUserServiceRequest = (ValidateUserServiceRequest) args[0];

	validateUserServiceResponse.setContext(validateUserServiceRequest.getContext());

	/**
	 * Check response code
	 */

	String resCde = checkAuthResponse(response.getResponseHeader());

	if (AuthResponseCodeConstants.AUTH_SUCCESS.equals(resCde)) {
	    validateUserDTO = retrieveUserInfo(response);
	    /**
	     * retrieve customer details from response
	     */
	    VerificationResult result = response.getResult();
	    if (result != null) {
		CustomerInfo respCustomerInfo = result.getCustomerInfo();
		if (respCustomerInfo != null) {
		    CustomerDTO customerDTO = retrieveCustomerInfo(respCustomerInfo);
		    validateUserDTO.setCustomerDTO(customerDTO);
		    validateUserDTO.setUserId(respCustomerInfo.getUserId());
		}

		if (respCustomerInfo != null) {
		    validateUserServiceResponse.getContext().setUserId(respCustomerInfo.getUserId());
		    validateUserServiceResponse.getContext().setCustomerId(respCustomerInfo.getCustomerID());
		    if (respCustomerInfo.getFullName() != null) {
			validateUserServiceResponse.getContext().setFullName(respCustomerInfo.getFullName());
		    } else {
			validateUserServiceResponse.getContext().setFullName(respCustomerInfo.getFirstName() + " " + respCustomerInfo.getLastName());
		    }
		    validateUserServiceResponse.getContext().setMobilePhone(respCustomerInfo.getMobilePhone());
		    validateUserServiceResponse.setSuccess(true);
		}
	    }
	} else {
	    validateUserServiceResponse.setSuccess(false);

	}
	validateUserServiceResponse.setResCde(resCde);

	validateUserServiceResponse.setValidateUserDTO(validateUserDTO);

	return validateUserServiceResponse;
    }

    /**
     * retrieve user info from response
     *
     * @param response
     * @return
     */
    private ValidateUserDTO retrieveUserInfo(ValidateUserByLoginNameIgnoreCaseResponse response) {

	ValidateUserDTO validateUserDto = new ValidateUserDTO();

	VerificationResult resResult = response.getResult();
	String value = null;
	if (resResult != null) {
	    value = resResult.getResultCode().getValue();
	    validateUserDto.setResultCode(value);
	    validateUserDto.setPreferredLanguage("EN");
	    validateUserDto.setUserStatus("Active");
	    // checkResponseCode(response.getResponseHeader());

	    validateUserDto.setFailures(resResult.getFailures());
	    if (value.equals(ResultCode._success)) {
		validateUserDto.setAuthenticated(true);
	    }
	} else {
	    validateUserDto.setAuthenticated(false);
	}

	return validateUserDto;
    }

    /**
     * Check authentication response code
     *
     * @param authResHeader
     * @returnSEC00005
     */
    @Override
    public String checkAuthResponse(AuthResHeader authResHeader) {

	String resCde = authResHeader.getServiceResStatus().getServiceResCode();
	// String resDesc = authResHeader.getServiceResStatus()
	// .getServiceResDesc();

	//if (AuthServiceErrorCodeConstant.SEC_REGISTRATION_LOGINNAME_EXIST.equals(resCde)) {
	    resCde = AuthResponseCodeConstants.AUTH_SUCCESS;
	/*} else {
	    // TODO change later AuthResponseCodeConstants.AUTH_INVALID_USERNAME;
	    resCde = AuthResponseCodeConstants.AUTH_SUCCESS;
	}*/

	return resCde;

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
	 * if(respCustomerInfo.getPostalAddress() != null && respCustomerInfo.getPostalAddress().length > 0){ customerDTO.setEmailAddresses
	 * (respCustomerInfo.getPostalAddress(0).getAddressLine6()); }
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
