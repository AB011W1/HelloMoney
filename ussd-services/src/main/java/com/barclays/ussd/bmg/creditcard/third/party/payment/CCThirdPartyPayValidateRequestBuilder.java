/**
 * PayBillSubmit.java
 */
package com.barclays.ussd.bmg.creditcard.third.party.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI This class builds request for Confirmation screen, bill pay
 * 
 */
public class CCThirdPartyPayValidateRequestBuilder implements BmgBaseRequestBuilder {
    private static final String CREDIT_CARD_PAYMENT_TYPE = "CCP";
    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	List<CustomerMobileRegAcct> lstFromAcct = (List<CustomerMobileRegAcct>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_FROM_ACNT.getTranId());

	String userSelectedFromAccntInput = userInputMap.get(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_FROM_ACNT.getParamName());
	CustomerMobileRegAcct selectedFrmAcct = lstFromAcct.get(Integer.parseInt(userSelectedFromAccntInput) - 1);

	String cardNo = userInputMap.get(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_CC_NO.getParamName());

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	String amnt = userInputMap.get(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_AMT.getParamName());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDInputParamsEnum.THIRD_PARTY_CC_TRANS_TYPE.getParamName(), CREDIT_CARD_PAYMENT_TYPE);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_CREDIT_CARD_PAYMENT, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	requestParamMap.put(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_RMRKS.getParamName(), transactionRemarks);
	requestParamMap.put(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_FROM_ACNT.getParamName(), selectedFrmAcct.getActNo());
	requestParamMap.put(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_AMT.getParamName(), amnt);
	requestParamMap.put(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_CC_NO.getParamName(), cardNo);
	request.setRequestParamMap(requestParamMap);
	return request;
    }

}
