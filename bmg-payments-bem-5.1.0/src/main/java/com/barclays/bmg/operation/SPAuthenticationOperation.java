package com.barclays.bmg.operation;

import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.AuthenticationDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.operation.request.SPAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.SPAuthenticationOperationResponse;
import com.barclays.bmg.service.AuthenticationService;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.bmg.type.AuthType;

/**
 * SP Authentication Operation
 * 
 * @author e20026338
 * 
 */

public class SPAuthenticationOperation extends BMBCommonOperation {

    AuthenticationService authenticationService;

    public AuthenticationService getAuthenticationService() {
	return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
	this.authenticationService = authenticationService;
    }

    /**
     * SP Verify operation to verify username/password Retrieve second authentication method system parameter
     * 
     * @param request
     * @return
     */

    public SPAuthenticationOperationResponse verify(SPAuthenticationOperationRequest request) {

	AuthenticationServiceRequest authenticationServiceRequest;
	SPAuthenticationOperationResponse spAuthenticationOperationResponse;

	Context context = request.getContext();

	SystemParameterServiceRequest systemParameterServiceRequest;
	loadParameters(context, context.getActivityId(), "COMMON");

	/**
	 * Prepare authentication service request and
	 */
	AuthenticationDTO authenticationDTO = new AuthenticationDTO();
	String username = request.getUsername();
	String password = request.getPassword();

	authenticationDTO.setAuthType(AuthType.SP);
	authenticationDTO.setLoginName(username);
	authenticationDTO.setToken(password);

	authenticationServiceRequest = new AuthenticationServiceRequest();

	authenticationServiceRequest.setUsername(username);
	authenticationServiceRequest.setPassword(password);

	authenticationServiceRequest.setContext(request.getContext());

	/**
	 * call authentication service
	 */

	AuthenticationServiceResponse authenticationServiceResponse = authenticationService.verify(authenticationServiceRequest);

	/**
	 * Prepare authentication operation response
	 */
	spAuthenticationOperationResponse = new SPAuthenticationOperationResponse();
	spAuthenticationOperationResponse.setContext(authenticationServiceResponse.getContext());

	/*
	 * boolean authenticated = authenticationServiceResponse .getAuthenticationDTO().isAuthenticated();
	 */

	spAuthenticationOperationResponse.setAuthenticationDTO(authenticationServiceResponse.getAuthenticationDTO());

	boolean authenticated = authenticationServiceResponse.isSuccess();
	String resCde = authenticationServiceResponse.getResCde();
	String resMsg = authenticationServiceResponse.getResMsg();
	spAuthenticationOperationResponse.setSuccess(authenticated);
	spAuthenticationOperationResponse.setResCde(resCde);

	spAuthenticationOperationResponse.setResMsg(resMsg);

	/**
	 * Get message for corresponding code
	 */
	if (!authenticated) {
	    getMessage(spAuthenticationOperationResponse);
	}

	/**
	 * Call system parameter service
	 */
	if (authenticated) {
	    loadParams(request.getContext());
	    spAuthenticationOperationResponse.setSecondAuthMethod((String) context.getContextMap().get(
		    SystemParameterConstant.SECOND_AUTH_TYPE_SYSPARAM_KEY));

	}

	return spAuthenticationOperationResponse;
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
