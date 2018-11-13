package com.barclays.ussd.bmg.installment.offer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdOfferLookUpDTO;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class InstallmentOfferPartnerDetailsRequestBuilder implements BmgBaseRequestBuilder {

    private static final String EXTRA_INSTALLMENT_OFFER_ACTIVITY_ID = "ExtraInstallmentOffer";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	String selectedPartnerOption = userInputMap.get(USSDInputParamsEnum.INSTALLMENT_OFFER_PARTNER_LIST.getParamName());

	HashMap<String, Object> txSessionMap = (HashMap<String, Object>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	List<UssdOfferLookUpDTO> partnerList = (List<UssdOfferLookUpDTO>) txSessionMap.get(USSDInputParamsEnum.INSTALLMENT_OFFER_PARTNER_LIST
		.getTranId());
	String selectePartnerName = partnerList.get(Integer.parseInt(selectedPartnerOption) - 1).getPartnerName();

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), EXTRA_INSTALLMENT_OFFER_ACTIVITY_ID);
	requestParamMap.put(USSDInputParamsEnum.BUSINESS_ID.getParamName(), requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());
	// requestParamMap.put(USSDInputParamsEnum.AUDIT_REQUIRED.getParamName(), "Y");
	requestParamMap.put(USSDInputParamsEnum.SELECTED_RESTAURANT_NAME.getParamName(), selectePartnerName);

	request.setRequestParamMap(requestParamMap);
	return request;
    }
}