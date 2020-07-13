package com.barclays.ussd.bmg.mobilewallettopup.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;

public class MobileWalletPayeeValidationRequest implements BmgBaseRequestBuilder{
	
	 @Override
	    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	    	USSDBaseRequest ussdBaseRequest = null;	    	
	    	if(requestBuilderParamsDTO != null && requestBuilderParamsDTO.getUssdSessionMgmt() != null && requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails() != null && requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap() != null && !requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().isEmpty()) {
	    	ussdBaseRequest = new USSDBaseRequest();
	    	ussdBaseRequest.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    	ussdBaseRequest.setOpCde(requestBuilderParamsDTO.getBmgOpCode());  
	    	Map<String, String> requestParamMap = new HashMap<String, String>();
	    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    	requestParamMap.put(USSDConstants.MW_MBL_NO,requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get(USSDConstants.MW_MBL_NO));
	    	ussdBaseRequest.setRequestParamMap(requestParamMap);	    	
	    	}
	    	return ussdBaseRequest;
	    }

}
