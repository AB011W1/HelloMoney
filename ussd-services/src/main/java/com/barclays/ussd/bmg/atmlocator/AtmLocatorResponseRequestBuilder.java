package com.barclays.ussd.bmg.atmlocator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class AtmLocatorResponseRequestBuilder implements BmgBaseRequestBuilder {

    private static final String ATM_LOCATOR_ACTIVITY_ID = "AtmLocator";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	String selectedAreaOption = userInputMap.get(USSDInputParamsEnum.ATM_LOCATOR_AREA_LIST.getParamName());

	HashMap<String, Object> txSessionMap = (HashMap<String, Object>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	List<UssdBranchLocatorLookUpDTO> areaList = (List<UssdBranchLocatorLookUpDTO>) txSessionMap.get(USSDInputParamsEnum.ATM_LOCATOR_AREA_LIST
		.getTranId());
	String selecteAreaName = areaList.get(Integer.parseInt(selectedAreaOption) - 1).getBranchName();
	String SelectedCityName = (String) txSessionMap.get(USSDInputParamsEnum.SELECTED_CITY_NAME.getParamName());

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), ATM_LOCATOR_ACTIVITY_ID);
	requestParamMap.put(USSDInputParamsEnum.BUSINESS_ID.getParamName(), requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());
	requestParamMap.put(USSDInputParamsEnum.AUDIT_REQUIRED.getParamName(), "Y");

	requestParamMap.put(USSDInputParamsEnum.SELECTED_CITY_NAME.getParamName(), SelectedCityName);
	requestParamMap.put(USSDInputParamsEnum.SELECTED_AREA_NAME.getParamName(), selecteAreaName);

	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
