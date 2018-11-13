package com.barclays.ussd.bmg.cashsend.onetime;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class OneTimeCashSendValidateRequestBuilder implements BmgBaseRequestBuilder {
    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @Autowired
    UssdMenuBuilder ussdMenuBuilder;

    /*
     * @Resource(name = "branchCodeCountryList") private List<String> branchCodeCountryList;
     */

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	String cashSendMobileNum = null;

	List<AccountDetails> srcList = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.ONE_TIME_CASH_SEND_SRC_ACCT_LIST.getTranId());

	String srcAcNoInput = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(
		USSDInputParamsEnum.ONE_TIME_CASH_SEND_SRC_ACCT_LIST.getParamName());

	AccountDetails accountDetails = srcList.get(Integer.parseInt(srcAcNoInput) - 1);
	String sourceAcct = accountDetails.getActNo();
	String branchCode = accountDetails.getBrnCde();
	String curr = accountDetails.getCurr();

	/*
	 * String brnAccNum=null; if (branchCodeCountryList.contains(businessId)) { brnAccNum=branchCode + sourceAcct; }else{ brnAccNum=sourceAcct; }
	 */

	USSDBaseRequest request = new USSDBaseRequest();

	ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(ussdSessionMgmt.getCountryCode(), ussdSessionMgmt.getBusinessId());
	String cSendMobileNum = userInputMap.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_MOBILE_NUM.getParamName());
	Integer ccvalue = msisdnDTO.getCcvalue();

	if (!cSendMobileNum.equals("0")) {
	    cashSendMobileNum = USSDUtils.appendCountryCode(ussdSessionMgmt.getBusinessId(), cSendMobileNum, msisdnDTO.getSnlen(), ccvalue);
	    cashSendMobileNum = "+" + cashSendMobileNum;
	} else {
	    cashSendMobileNum = cSendMobileNum;
	}

	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_CASH_SEND, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_MOBILE_NUM.getParamName(), cashSendMobileNum);
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_AMOUNT.getParamName(), userInputMap
		.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_AMOUNT.getParamName()));
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_CURRENCY.getParamName(), curr);
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_SRC_ACCT_LIST.getParamName(), sourceAcct);
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_BRANCH_CODE.getParamName(), branchCode);
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_ATM_PIN.getParamName(), userInputMap
		.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_ATM_PIN.getParamName()));
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_TRANSACTION_REMARKS.getParamName(), transactionRemarks);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
