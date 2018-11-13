package com.barclays.ussd.bmg.creditcard.statement;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class CcStatCreditCardListRequestBuilder implements BmgBaseRequestBuilder {

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject(com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "ACT_CCD_STATEMENT_TRANS");
	request.setRequestParamMap(requestParamMap);
	return request;

    }

}
