package com.barclays.ussd.bmg.branchlocator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class BranchLocatorResponseRequestBuilder implements BmgBaseRequestBuilder {
    /** The Constant LOGGER. */

    private static final String BRANCH_LOCATOR_ACTIVITY_ID = "BranchLocator";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();

	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	String selectedAreaOption = userInputMap.get(USSDInputParamsEnum.BRANCH_LOCATOR_AREA_LIST.getParamName());

	HashMap<String, Object> txSessionMap = (HashMap<String, Object>) ussdSessionMgmt.getTxSessions();
	List<UssdBranchLocatorLookUpDTO> areaList = (List<UssdBranchLocatorLookUpDTO>) txSessionMap.get(USSDInputParamsEnum.BRANCH_LOCATOR_AREA_LIST
		.getTranId());
	String selecteAreaName = areaList.get(Integer.parseInt(selectedAreaOption) - 1).getBranchName();
	String SelectedCityName = (String) txSessionMap.get(USSDInputParamsEnum.SELECTED_CITY_NAME.getParamName());

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), BRANCH_LOCATOR_ACTIVITY_ID);
	requestParamMap.put(USSDInputParamsEnum.BUSINESS_ID.getParamName(), ussdSessionMgmt.getBusinessId());
	requestParamMap.put(USSDInputParamsEnum.AUDIT_REQUIRED.getParamName(), "Y");

	requestParamMap.put(USSDInputParamsEnum.SELECTED_CITY_NAME.getParamName(), SelectedCityName);
	requestParamMap.put(USSDInputParamsEnum.SELECTED_AREA_NAME.getParamName(), selecteAreaName);

	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
