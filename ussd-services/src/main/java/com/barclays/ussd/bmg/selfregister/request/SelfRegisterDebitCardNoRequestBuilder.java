package com.barclays.ussd.bmg.selfregister.request;

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

public class SelfRegisterDebitCardNoRequestBuilder implements BmgBaseRequestBuilder {

	 private static final String DEBIT_CARD_AUTH_ID = "DebitCard";
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	// TODO Auto-generated method stub
    	/*USSDBaseRequest request = new USSDBaseRequest();
    	Map<String, String> requestParamMap = new HashMap<String, String>();
    	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();



    	HashMap<String, Object> txSessionMap = (HashMap<String, Object>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();


    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

    	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), DEBIT_CARD_AUTH_ID);
    	requestParamMap.put(USSDInputParamsEnum.BUSINESS_ID.getParamName(), requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());



    	request.setRequestParamMap(requestParamMap);*/
    	return null;
    }

}
