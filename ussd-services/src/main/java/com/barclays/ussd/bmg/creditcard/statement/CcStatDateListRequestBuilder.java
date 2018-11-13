package com.barclays.ussd.bmg.creditcard.statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class CcStatDateListRequestBuilder implements BmgBaseRequestBuilder {

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject(com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	String userInput = requestBuilderParamsDTO.getUserInput();

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	if (userInput != null && StringUtils.isNotEmpty(userInput)) {
	    List<CustomerMobileRegAcct> ccList = (List<CustomerMobileRegAcct>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		    USSDInputParamsEnum.CR_CARD_STAT_LIST.getTranId());
	    CustomerMobileRegAcct userSeletectedCreditCard = ccList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.CR_CARD_STAT_LIST
		    .getParamName())) - 1);

	    String cardNo = userSeletectedCreditCard.getCrdNo();
	    String AccntNo = userSeletectedCreditCard.getActNo();
	    requestParamMap.put(USSDInputParamsEnum.CR_CARD_STAT_LIST.getParamName(), cardNo);
	    requestParamMap.put(USSDInputParamsEnum.CR_CARD_STAT_ACCT_NO.getParamName(), AccntNo);
	}

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);
	return request;

    }
}
