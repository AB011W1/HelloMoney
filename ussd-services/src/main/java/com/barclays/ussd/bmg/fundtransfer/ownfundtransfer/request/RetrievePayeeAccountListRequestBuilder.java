package com.barclays.ussd.bmg.fundtransfer.ownfundtransfer.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;

public class RetrievePayeeAccountListRequestBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
    	USSDBaseRequest request = new USSDBaseRequest();

    	if("OwnLinkedAcctFundTxCredit".equals(requestBuilderParamsDTO.getUssdSessionMgmt()
    			.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName())){
    	Map<String, String> requestParamMap = new HashMap<String, String>();
    	requestParamMap.put(USSDConstants.CREDIT_CARD_FT_CASA, USSDConstants.CREDIT_CARD_FT_CASA);

    	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
    	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
    	request.setRequestParamMap(requestParamMap);
    	return request;

    	} else {
    		return null;
    	}
    }
}
