package com.barclays.bmg.operation;

import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.dto.ValidateUserDTO;
import com.barclays.bmg.operation.request.SPAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.ValidateUserOperationRequest;
import com.barclays.bmg.operation.response.SPAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.ValidateUserOperationResponse;
import com.barclays.bmg.service.ValidateUserService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.bmg.service.response.ValidateUserServiceResponse;
import com.barclays.bmg.type.AuthType;

/**
 * SP Authentication Operation
 * 
 * @author e20026338
 * 
 */

public class ValidateUserOperation extends BMBCommonOperation {

    ValidateUserService validateUserService;

    /**
     * SP Verify operation to verify username/password Retrieve second authentication method system parameter
     * 
     * @param request
     * @return
     */
    public ValidateUserOperationResponse validateUserIgnoreCase(ValidateUserOperationRequest request) {

	ValidateUserServiceRequest validateUserServiceRequest;
	ValidateUserOperationResponse validateUserOperationResponse;
	Context context = request.getContext();
	loadParameters(context, context.getActivityId(), "COMMON");
	/**
	 * Prepare authentication service request and
	 */
	ValidateUserDTO validateUserDTO = new ValidateUserDTO();
	String username = request.getUsername();
	validateUserDTO.setAuthType(AuthType.SP);
	validateUserDTO.setLoginName(username);
	validateUserServiceRequest = new ValidateUserServiceRequest();
	validateUserServiceRequest.setUserName(username);
	validateUserServiceRequest.setContext(request.getContext());
	ValidateUserServiceResponse validateUserServiceResponse = validateUserService.validateUserIgnoreCase(validateUserServiceRequest);
	/**
	 * Prepare authentication operation response
	 */
	validateUserOperationResponse = new ValidateUserOperationResponse();
	validateUserOperationResponse.setContext(validateUserServiceResponse.getContext());
	validateUserOperationResponse.setValidateUserDto(validateUserServiceResponse.getValidateUserDTO());
	boolean isValidUser = validateUserServiceResponse.isSuccess();
	String resCde = validateUserServiceResponse.getResCde();
	String resMsg = validateUserServiceResponse.getResMsg();
	validateUserOperationResponse.setSuccess(isValidUser);
	validateUserOperationResponse.setResCde(resCde);

	validateUserOperationResponse.setResMsg(resMsg);

	/**
	 * Get message for corresponding code
	 */
	if (!isValidUser) {
	    getMessage(validateUserOperationResponse);
	}

	/*
		*//**
	 * Call system parameter service
	 */
	/*
	 * if (isValidUser) { loadParams(request.getContext()); validateUserOperationResponse .setSecondAuthMethod((String) context .getContextMap()
	 * .get( SystemParameterConstant.SECOND_AUTH_TYPE_SYSPARAM_KEY));}
	 */
	return validateUserOperationResponse;
    }

    public SPAuthenticationOperationResponse verify(SPAuthenticationOperationRequest request) {
	return null;
    }

    public SystemParameterServiceResponse loadParams(Context context) {

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

    public ValidateUserService getValidateUserService() {
	return validateUserService;
    }

    public void setValidateUserService(ValidateUserService validateUserService) {
	this.validateUserService = validateUserService;
    }

    /**
     * Loads system parameters into Context
     * 
     * @param context
     * @param activities
     */
    /*
     * private void loadParameters(Context context, String... activities) {
     * 
     * SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
     * 
     * systemParameterDTO.setBusinessId(context.getBusinessId()); systemParameterDTO.setSystemId(context.getSystemId());
     * 
     * SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
     * systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);
     * 
     * SystemParameterListServiceResponse systemParameterListServiceResponse; List<SystemParameterDTO> systemParameterDTOList; Map<String, Object>
     * systemParameterMap = new HashMap<String, Object>();
     * 
     * for (int i = 0; i < activities.length; i++) {
     * 
     * systemParameterServiceRequest.getSystemParameterDTO() .setActivityId(activities[i]); systemParameterListServiceResponse =
     * super.getSystemParameterService() .getSysParamByActivityId(systemParameterServiceRequest); systemParameterDTOList =
     * systemParameterListServiceResponse .getSystemParameterDTOList();
     * 
     * for (SystemParameterDTO eachSPDto : systemParameterDTOList) { systemParameterMap.put(eachSPDto.getParameterId(), eachSPDto
     * .getParameterValue()); } }
     * 
     * context.setContextMap(systemParameterMap);
     * 
     * BMGGlobalContext bmggloContext = new BMGGlobalContext(); bmggloContext.setActivityId(context.getActivityId());
     * bmggloContext.setBusinessId(context.getBusinessId()); bmggloContext.setSystemId(context.getSystemId());
     * bmggloContext.setContextMap(systemParameterMap);
     * 
     * BMGContextHolder.setLogContext(bmggloContext);
     * 
     * }
     */

}
