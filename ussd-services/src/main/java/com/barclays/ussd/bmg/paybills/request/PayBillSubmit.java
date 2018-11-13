/**
 * PayBillSubmit.java
 */
package com.barclays.ussd.bmg.paybills.request;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI This class builds request for Confirmation screen, bill pay
 * 
 */
public class PayBillSubmit implements BmgBaseRequestBuilder {
    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	List<AccountData> lstFromAcct = (List<AccountData>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.PAY_BILLS_FROM_ACNT.getTranId());

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.putAll(userInputMap);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_BILL_PAYMENT, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	requestParamMap.put(USSDConstants.BILL_PAY_REMARKS, transactionRemarks);

	if(USSDConstants.CREDIT_BILL_PAY.equals(ussdSessionMgmt.getTxSessions().get(USSDConstants.CREDIT_BILL_PAY))){
		List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.PAY_BILLS_CARD_LIST.getTranId());
		String userCreditSelection = userInputMap.get(USSDInputParamsEnum.PAY_BILLS_CARD_LIST.getParamName());
		CustomerMobileRegAcct creditCard = creditCardList.get(Integer.parseInt(userCreditSelection) - 1);
		requestParamMap.put(USSDInputParamsEnum.PAY_BILLS_FROM_ACNT.getParamName(), creditCard.getActNo());
		requestParamMap.put("crditCardFlag",USSDConstants.CREDIT_BILL_PAY);
		BillerCreditDTO billerCreditDTO = (BillerCreditDTO) ussdSessionMgmt.getTxSessions().get("BillerCreditDTO");
		requestParamMap.put("actionCode", billerCreditDTO.getActionCode());
		requestParamMap.put("storeNumber",billerCreditDTO.getStoreNumber());

	} else {
	requestParamMap.put(USSDInputParamsEnum.PAY_BILLS_FROM_ACNT.getParamName(), lstFromAcct.get(
		Integer.parseInt(userInputMap.get(USSDInputParamsEnum.PAY_BILLS_FROM_ACNT.getParamName())) - 1).getActNo());
	}

	List<Beneficiery> lstBenef = (List<Beneficiery>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getTranId());
	if (!lstBenef.isEmpty()) {
	    Beneficiery bene = lstBenef.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getParamName())) - 1);
	    requestParamMap.put(USSDInputParamsEnum.PAY_BILLS_AREA_CODE.getParamName(), bene.getAreaCode());
	}

	String payeeId = requestParamMap.get(USSDInputParamsEnum.PAY_BILLS_PAYEE_ID.getParamName());
	requestParamMap.remove(payeeId);
	request.setRequestParamMap(requestParamMap);
	return request;
    }

}
