package com.barclays.ussd.bmg.creditcard.link;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class CreditCardLinkSubmitRequestBuilder implements BmgBaseRequestBuilder {


    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject(com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	AuthUserData userAuthObj = (AuthUserData) ussdSessionMgmt.getUserAuthObj();

	List<CustomerMobileRegAcct> custActs = userAuthObj.getPayData().getCustActs();

	Map<String, String> requestParamMap = new HashMap<String, String>();
	for (CustomerMobileRegAcct acct : custActs) {
	    if (USSDConstants.PRIMARY_INDICATOR_YES.equalsIgnoreCase(acct.getPriInd())) {
	    	requestParamMap.put(USSDConstants.PRIMARY_ACC_NUMBER, acct.getMkdActNo());
	    }
	    }
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDConstants.DATA_TYPE_CREDIT_CARD_NUMBER, userInputMap.get(USSDConstants.DATA_TYPE_CREDIT_CARD_NUMBER));
	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "CREDIT_CARD_LINK");
	requestParamMap.put(USSDConstants.DATA_TYPE_MOBILE_NUMBER,requestBuilderParamsDTO.getMsisdnNo());
	request.setRequestParamMap(requestParamMap);
	return request;

    }
}