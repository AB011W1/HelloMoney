package com.barclays.ussd.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.exception.USSDNonBlockingException;

public class UssdServiceEnabler {
    private static final String SYSTEM_ID_UB = "UB";

    private static final String SERVICE_ENABLED_FALSE = "N";

    private static final String SERVICE_ENABLED_TRUE = "Y";

    private static final String SRV = "SRV";

    @Autowired
    // private ListValueResServiceImpl listValueResService;
    private SystemParameterService systemParameterService;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(UssdServiceEnabler.class);

    UssdMenuBuilder ussdMenuBuilder;

    public boolean checkServiceEnabled(String serviceName, String businessId, String langCode, String countryCode) {
	boolean serviceEnabledFlag = true;
	try {
	    String sysParamKey = getSysParamKey(serviceName, businessId, countryCode);
	    // String serviceEnabled = getSystemPreferenceData(langCode, countryCode, sysPrefKey);
	    String serviceEnabled = getSysParamValue(businessId, countryCode, sysParamKey);
	    if (StringUtils.equalsIgnoreCase(serviceEnabled, SERVICE_ENABLED_TRUE)) {
		serviceEnabledFlag = true;
	    } else if (StringUtils.equalsIgnoreCase(serviceEnabled, SERVICE_ENABLED_FALSE)) {
		serviceEnabledFlag = false;
	    } else {
		serviceEnabledFlag = true;
	    }
	} catch (Exception e) {
	    serviceEnabledFlag = true;
	}
	return serviceEnabledFlag;
    }

    private String getSysParamKey(String serviceName, String businessId, String countryCode) {
	StringBuffer key = new StringBuffer(SRV);
	key.append(USSDConstants.UNDERSCORE_SEPERATOR);
	key.append(serviceName);
	return key.toString();
    }

    // private String getSystemPreferenceData(String langCode, String countryCode, String listValueKey) throws USSDNonBlockingException {
    // ListValueResServiceRequest listValReq = new ListValueResServiceRequest(countryCode, SystemPreferenceConstants.SHM_USSD_SERVICE_ENABLER,
    // langCode, listValueKey);
    // ListValueResByGroupServiceResponse listValueByGroup = listValueResService.findListValueResByGroupKey(listValReq);
    // ListValueCacheDTO listValueCacheDTO = listValueByGroup.getListValueCacheDTOOneRow();
    // if (listValueCacheDTO == null) {
    // LOGGER.fatal("System preferences not set for" + listValReq.getListValueKey());
    // throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(),
    // USSDExceptions.USSD_SYS_PREF_MISSING.getUssdErrorCode());
    // }
    // return listValueCacheDTO.getLabel();
    // }

    protected String getSysParamValue(String businessId, String countryCode, String sysParamKey) {
	String paramValue = "";
	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
	systemParameterDTO.setBusinessId(businessId);
	systemParameterDTO.setSystemId(SYSTEM_ID_UB);
	systemParameterDTO.setActivityId(SystemParameterConstant.SHM_USSD_SERVICE_ENABLER);
	systemParameterDTO.setParameterId(sysParamKey);

	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

	SystemParameterServiceResponse response = systemParameterService.getSystemParameter(systemParameterServiceRequest);

	if (response != null && response.getSystemParameterDTO() != null) {
	    paramValue = response.getSystemParameterDTO().getParameterValue();
	} else {
	    paramValue = SERVICE_ENABLED_TRUE;
	}

	return paramValue;
    }

    public void blockServiceIfDisabled(boolean serviceEnabled) throws USSDNonBlockingException {
	if (!serviceEnabled) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_SERVICE_DISABLED_LIMITED_ACCESS.getBmgCode());
	}
    }
    public String getStatusFlag(String businessId, String functionality){
    	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
    	systemParameterDTO.setBusinessId(businessId);
    	systemParameterDTO.setSystemId(SYSTEM_ID_UB);
    	systemParameterDTO.setParameterId(functionality+"FunctionalityStatusFlag");

    	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
    	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

    	SystemParameterServiceResponse response = systemParameterService.getStatusParameter(systemParameterServiceRequest);

    	String status="";
    	if(response!=null && response.getSystemParameterDTO()!=null && response.getSystemParameterDTO().getParameterValue()!=null)
    		status=response.getSystemParameterDTO().getParameterValue();
    	return status;
    }
}