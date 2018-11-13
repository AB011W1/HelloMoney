package com.barclays.ussd.bmg.creditcard.unbilled.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class CcUnbilledTranMerchantListRequestBuilder implements BmgBaseRequestBuilder {

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject(com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	userInputMap.put(USSDInputParamsEnum.CR_CARD_UNBILLED_TRAN_CARD_LIST.getParamName(), requestBuilderParamsDTO.getUserInput());
	List<CustomerMobileRegAcct> ccList = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.CR_CARD_UNBILLED_TRAN_CARD_LIST.getTranId());
	CustomerMobileRegAcct userSeletectedCreditCard = ccList.get(Integer.parseInt(userInputMap
		.get(USSDInputParamsEnum.CR_CARD_UNBILLED_TRAN_CARD_LIST.getParamName())) - 1);

	String cardNo = userSeletectedCreditCard.getCrdNo();
	String AccntNo = userSeletectedCreditCard.getActNo();
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_STAT_LIST.getParamName(), cardNo);
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_STAT_ACCT_NO.getParamName(), AccntNo);

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);
	return request;

    }
}
