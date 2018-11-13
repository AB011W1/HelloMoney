/**
 * PayBillConfirm.java
 */
package com.barclays.ussd.bmg.creditcard.payment;

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

/**
 * @author BTCI This class displays final confirmation screen
 * 
 */
public class CreditCardPaymentSubmitRequestBuilder implements BmgBaseRequestBuilder {

    private static final String payTxnTyp = "CCP";

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	List<String> txnRefNoLst = (List<String>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.CR_CARD_PAYMENT_VALIDATE.getTranId());

	List<CustomerMobileRegAcct> lstAccntDet = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.CR_CARD_PAYMENT_LIST.getTranId());
	String userSelection = userInputMap.get(USSDInputParamsEnum.CR_CARD_PAYMENT_LIST.getParamName());
	CustomerMobileRegAcct userSelectedCreditCardDet = lstAccntDet.get(Integer.parseInt(userSelection) - 1);

	List<CustomerMobileRegAcct> lstFromAcct = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.CR_CARD_PAYMENT_SRC_ACC_NO.getTranId());

	CustomerMobileRegAcct selectedFrmAcct = lstFromAcct.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.CR_CARD_PAYMENT_SRC_ACC_NO
		.getParamName())) - 1);

	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_VALIDATE.getParamName(), txnRefNoLst.get(Integer.parseInt(userInputMap
		.get(USSDInputParamsEnum.CR_CARD_PAYMENT_VALIDATE.getParamName())) - 1));

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_TRANS_TYPE.getParamName(), payTxnTyp);
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_GET_ORG_CODE.getParamName(), userSelectedCreditCardDet.getOrgCode());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_GET_BENF_NAME.getParamName(), userSelectedCreditCardDet.getCardHolder());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_ACCT_NO.getParamName(), userSelectedCreditCardDet.getActNo());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_CARD_NO.getParamName(), userSelectedCreditCardDet.getCrdNo());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_SRC_ACC_NO.getParamName(), selectedFrmAcct.getActNo());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_BRANCH_CODE.getParamName(), userSelectedCreditCardDet.getBrnCde());
	requestParamMap.put(USSDInputParamsEnum.CR_CARD_PAYMENT_GET_CURRENCY.getParamName(), selectedFrmAcct.getCurr());
	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
