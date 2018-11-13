package com.barclays.ussd.bmg.airtime.request;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.FromAcntLst;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;

public class AirtimeValidateRequest implements BmgBaseRequestBuilder {
    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @Autowired
    UssdMenuBuilder ussdMenuBuilder;

    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	USSDSessionManagement session = requestBuilderParamsDTO.getUssdSessionMgmt();


	Map<String, Object> txSessions = session.getTxSessions();
	// AirtimeInitPayData payData = (AirtimeInitPayData) txSessions.get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName());
	List<Biller> mnoList = (List<Biller>) txSessions.get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
	List<Account> accounts = (List<Account>) txSessions.get(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getTranId());
	MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(session.getCountryCode(), session.getBusinessId());

	String billerUserIdx = userInputMap.get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName());



	Biller biller = mnoList.get(Integer.parseInt(billerUserIdx) - 1);

	/*String airTimeMobileNO = userInputMap.get(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName());
	Integer ccvalue = msisdnDTO.getCcvalue();
	String airTimeAccountNumber = USSDUtils.appendCountryCode(session.getBusinessId(), airTimeMobileNO, msisdnDTO.getSnlen(), ccvalue);*/

	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_AIRTIME_TOPUP, new Locale(session.getUserProfile()
		.getLanguage(), session.getUserProfile().getCountryCode()));

	Map<String, String> requestParamMap = new HashMap<String, String>();
	//CR82
	Payee payee=(Payee)session.getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getTranId());
	String mAtWtSavedBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
	if(mAtWtSavedBenf.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
		String airTimeMobileNO = payee.getRefNo().getPhNo();
		Integer ccvalue = msisdnDTO.getCcvalue();
		String airTimeAccountNumber = USSDUtils.appendCountryCode(session.getBusinessId(), airTimeMobileNO, msisdnDTO.getSnlen(), ccvalue);
		FromAcntLst fromAcntLst = (FromAcntLst) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
				"AccountListSaved");
		List<AccountData> lstFromAcct = (List<AccountData>)fromAcntLst.getFrActLst();
		requestParamMap.put(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getParamName(), lstFromAcct.get(
				Integer.parseInt(userInputMap.get(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getParamName())) - 1).getActNo());
		requestParamMap.put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName(), payee.getBilrId());
		requestParamMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(), airTimeAccountNumber);
		userInputMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(), airTimeMobileNO);

	} else {
		String airTimeMobileNO = userInputMap.get(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName());
		Integer ccvalue = msisdnDTO.getCcvalue();
		String airTimeAccountNumber = USSDUtils.appendCountryCode(session.getBusinessId(), airTimeMobileNO, msisdnDTO.getSnlen(), ccvalue);
		String acctListUserIdx = userInputMap.get(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getParamName());
		Account account = accounts.get(Integer.parseInt(acctListUserIdx) - 1);
		requestParamMap.put(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getParamName(), account.getActNo());
		requestParamMap.put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName(), biller.getBillerId());
		requestParamMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(), airTimeAccountNumber);
	}

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.AIRTIME_TRANSACTION_REMARKS.getParamName(), transactionRemarks);
	requestParamMap.put(USSDInputParamsEnum.AIRTIME_AMOUNT.getParamName(), userInputMap.get(USSDInputParamsEnum.AIRTIME_AMOUNT.getParamName()));
	//requestParamMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(), airTimeAccountNumber);

	USSDBaseRequest request = new USSDBaseRequest();
	request.setRequestParamMap(requestParamMap);

	return request;
    }
}
