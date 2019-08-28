package com.barclays.bmg.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.PostalAddressDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.dto.ValidateUserDTO;
import com.barclays.bmg.operation.request.ValidateUserOperationRequest;
import com.barclays.bmg.operation.response.ValidateUserOperationResponse;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class VerifyUserOperation extends BMBCommonOperation {
    static Map<String, ValidateUserDTO> usersMap;

    static {
	usersMap = new HashMap<String, ValidateUserDTO>();
	fillUpStaticData("900000000053", "65-94889106", "EN", "MUBRB001", "MU");
	fillUpStaticData("900000000050", "65-94889106", "EN", "9000000005", "MU");
	fillUpStaticData("900000000053", "65-94889106", "EN", "65-94889106", "TZ");

    }

    private static void fillUpStaticData(String customerID, String mobilePhone, String preferredLanguage, String userId, String countryCode) {
	ValidateUserDTO validateUserDTO = new ValidateUserDTO();

	validateUserDTO.setUserId(userId);
	CustomerDTO cust1Mubrb = new CustomerDTO();
	cust1Mubrb.setUserName(userId);
	cust1Mubrb.setUserId(userId);
	cust1Mubrb.setCustomerID(customerID);
	cust1Mubrb.setMobilePhone(mobilePhone);
	List<PostalAddressDTO> postalAddresses = new ArrayList<PostalAddressDTO>();
	PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
	postalAddressDTO.setCountryCode(countryCode);
	postalAddresses.add(postalAddressDTO);
	cust1Mubrb.setPostalAddresses(postalAddresses);
	/*
	 * <CountryCode>MU</CountryCode> <SystemID>MB</SystemID> <BusinessID>MUBRB</BusinessID> <BranchCode></BranchCode> <TerminalID>T01</TerminalID>
	 */
	validateUserDTO.setCustomerDTO(cust1Mubrb);
	validateUserDTO.setPreferredLanguage(preferredLanguage);
	/*
	 * },"payData":{"mobilePhone":"65-94889106","preferredLanguage":"EN","userId" :"65-94889106","businessID":"BBT", "countryCode":"TZ",
	 * "status":"Active"}}
	 */
	usersMap.put(validateUserDTO.getUserId(), validateUserDTO);
    }

    /**
     * SP Verify operation to verify username/password Retrieve second authentication method system parameter
     * 
     * @param request
     * @return
     */
    public ValidateUserOperationResponse verifyUserExistsOrNot(ValidateUserOperationRequest request) {
	ValidateUserServiceRequest validateUserServiceRequest;
	ValidateUserOperationResponse validateUserOperationResponse = new ValidateUserOperationResponse();
	Context context = request.getContext();
	loadParameters(context, context.getActivityId(), "COMMON");
	String username = request.getUsername();
	validateUserServiceRequest = new ValidateUserServiceRequest();
	validateUserServiceRequest.setUserName(username);
	validateUserServiceRequest.setContext(request.getContext());
	validateUserOperationResponse.setContext(request.getContext());
	if (usersMap.containsKey(username)) {
	    validateUserOperationResponse.setSuccess(true);
	    validateUserOperationResponse.setValidateUserDto(usersMap.get(username));
	    validateUserOperationResponse.setSuccess(true);
	    validateUserOperationResponse.setResCde("Active");

	} else {
	    validateUserOperationResponse.setSuccess(false);
	    validateUserOperationResponse.setValidateUserDto(usersMap.get(username));
	    validateUserOperationResponse.setResCde("NotActive");
	}
	return validateUserOperationResponse;
    }

    public SystemParameterServiceResponse loadParams(Context context) {
	//
	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

	systemParameterDTO.setActivityId(context.getActivityId());
	systemParameterDTO.setBusinessId(context.getBusinessId());
	systemParameterDTO.setSystemId(context.getSystemId());
	systemParameterDTO.setParameterId(SystemParameterConstant.SECOND_AUTH_TYPE_SYSPARAM_KEY);
	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();

	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

	SystemParameterServiceResponse systemParameterServiceResponse = super.getSystemParameterService().getSystemParameter(
		systemParameterServiceRequest);

	systemParameterServiceRequest.getSystemParameterDTO().setActivityId("COMMON");
	SystemParameterListServiceResponse systemParameterListServiceResponse = super.getSystemParameterService().getSysParamByActivityId(
		systemParameterServiceRequest);

	Map<String, Object> systemParameterMap = new HashMap<String, Object>();
	for (SystemParameterDTO eachSPDto : systemParameterListServiceResponse.getSystemParameterDTOList()) {
	    systemParameterMap.put(eachSPDto.getParameterId(), eachSPDto.getParameterValue());
	}

	BMGGlobalContext bmggloContext = new BMGGlobalContext();
	bmggloContext.setActivityId(context.getActivityId());
	bmggloContext.setBusinessId(context.getBusinessId());
	bmggloContext.setSystemId(context.getSystemId());
	bmggloContext.setContextMap(systemParameterMap);

	BMGContextHolder.setLogContext(bmggloContext);

	return systemParameterServiceResponse;
    }
}