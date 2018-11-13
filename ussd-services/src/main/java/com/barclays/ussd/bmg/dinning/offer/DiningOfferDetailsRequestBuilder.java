package com.barclays.ussd.bmg.dinning.offer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdOfferLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class DiningOfferDetailsRequestBuilder implements BmgBaseRequestBuilder {

    private static final String DININIG_OFFER_ACTIVITY_ID = "DiningOffer";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	String selectedRestarantOption = userInputMap.get(USSDInputParamsEnum.DINING_OFFER_RESTAURANT_LIST.getParamName());

	HashMap<String, Object> txSessionMap = (HashMap<String, Object>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	List<UssdOfferLookUpDTO> restarantList = (List<UssdOfferLookUpDTO>) txSessionMap.get(USSDInputParamsEnum.DINING_OFFER_RESTAURANT_LIST
		.getTranId());
	String selecteRestarantName = restarantList.get(Integer.parseInt(selectedRestarantOption) - 1).getRestaurentName();
	String selectedCityName = (String) txSessionMap.get(USSDInputParamsEnum.SELECTED_CITY_NAME.getParamName());

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), DININIG_OFFER_ACTIVITY_ID);
	requestParamMap.put(USSDInputParamsEnum.BUSINESS_ID.getParamName(), requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());
	// requestParamMap.put(USSDInputParamsEnum.AUDIT_REQUIRED.getParamName(), "Y");

	requestParamMap.put(USSDInputParamsEnum.SELECTED_CITY_NAME.getParamName(), selectedCityName);
	requestParamMap.put(USSDInputParamsEnum.SELECTED_RESTAURANT_NAME.getParamName(), selecteRestarantName);

	request.setRequestParamMap(requestParamMap);
	return request;
    }
}