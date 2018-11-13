package com.barclays.ussd.bmg.selfregister.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class SelfRegisterDebitCardVerificationRequestBuilder implements BmgBaseRequestBuilder {

	 private static final String DEBIT_CARD_AUTH_ID = "DebitCard";
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	// TODO Auto-generated method stub
    	USSDBaseRequest request = new USSDBaseRequest();
    	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
    	Map<String, String> requestParamMap = new HashMap<String, String>();

    	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

    	String userEntereddebitCardNumbers=ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("debitCardNo");
    	List<Integer>  positionsList = (List<Integer>) ussdSessionMgmt.getTxSessions().get(
    		    USSDInputParamsEnum.SELFREG_DEBITCARD_EXPIRYDATE.getTranId());
    	String userEnteredDebitCardExpiryDate=ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("debitCardExpiryDate");

    	requestParamMap.put(USSDConstants.RANDOM_DEBIT_CARD_CARD_NO_1, positionsList.get(0).toString());
    	requestParamMap.put(USSDConstants.RANDOM_DEBIT_CARD_CARD_NO_2, positionsList.get(1).toString());
    	requestParamMap.put(USSDConstants.RANDOM_DEBIT_CARD_CARD_NO_3, positionsList.get(2).toString());
    	requestParamMap.put(USSDConstants.RANDOM_DEBIT_CARD_CARD_NO_4, positionsList.get(3).toString());
    	requestParamMap.put(USSDConstants.DATA_TYPE_DEBIT_CARD_NO, userEntereddebitCardNumbers);
    	requestParamMap.put(USSDConstants.DATA_TYPE_DEBIT_CARD_EXPIRY_DATE, userEnteredDebitCardExpiryDate);

    	HashMap<String, Object> txSessionMap = (HashMap<String, Object>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

    	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), DEBIT_CARD_AUTH_ID);
    	requestParamMap.put(USSDInputParamsEnum.BUSINESS_ID.getParamName(), requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());



    	request.setRequestParamMap(requestParamMap);

    	return request;
    }

}
