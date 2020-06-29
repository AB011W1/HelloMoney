/**
 * PayBillSubmit.java
 */
package com.barclays.ussd.bmg.creditcard.payment;

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
public class CreditCardPaymentValidateRequestBuilder implements BmgBaseRequestBuilder {

    private static final String txnType = "CCP";

    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	List<CustomerMobileRegAcct> lstAccntDet = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.CR_CARD_PAYMENT_LIST.getTranId());
	String userSelection = userInputMap.get(USSDInputParamsEnum.CR_CARD_PAYMENT_LIST.getParamName());
	CustomerMobileRegAcct acntDet = lstAccntDet.get(Integer.parseInt(userSelection) - 1);

	List<CustomerMobileRegAcct> lstFromAcct = (List<CustomerMobileRegAcct>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.CR_CARD_PAYMENT_SRC_ACC_NO.getTranId());

	String userSelectedFromAccntInput = userInputMap.get(USSDInputParamsEnum.CR_CARD_PAYMENT_SRC_ACC_NO.getParamName());
	CustomerMobileRegAcct selectedFrmAcct = lstFromAcct.get(Integer.parseInt(userSelectedFromAccntInput) - 1);

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	String amnt = userInputMap.get(USSDInputParamsEnum.CR_CARD_PAYMENT_AMOUNT.getParamName());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_TRANS_TYPE.getParamName(), txnType);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_CREDIT_CARD_PAYMENT, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_REMRKS.getParamName(), transactionRemarks);
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_SRC_ACC_NO.getParamName(), selectedFrmAcct.getActNo());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_AMOUNT.getParamName(), amnt);
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_LIST.getParamName(), acntDet.getActNo());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_CARD_NO.getParamName(), acntDet.getCrdNo());
	request.setRequestParamMap(requestParamMap);
	return request;
    }

}
