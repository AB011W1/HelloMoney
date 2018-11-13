package com.barclays.ussd.bmg.freedialussd;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;



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
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class FreeDialConfirmValidateRequestBuilder implements BmgBaseRequestBuilder {
	@Autowired
	UssdResourceBundle ussdResourceBundle;

	@Autowired
	UssdMenuBuilder ussdMenuBuilder;

	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
		USSDSessionManagement session = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, Object> userInputMapAirtel = session.getTxSessions();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		if(userInputMapAirtel!=null && userInputMapAirtel.get("extra")!=null && userInputMapAirtel.get("extra").toString().equals("FREEDIALAIRTEL")){
			requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_MOB_NUM.getParamName(), userInputMapAirtel.get("mblNo").toString());
			requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_AMOUNT.getParamName(), userInputMapAirtel.get("txnAmt").toString());
			requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_MNO_LIST.getParamName(), "AIRTELCR-5");
			AuthUserData aData=(AuthUserData)session.getUserAuthObj();
			List<CustomerMobileRegAcct> cmra= aData.getPayData().getCustActs();
			String acctNo=null;
			for(CustomerMobileRegAcct racct:cmra)
				if(racct.getPriInd().equals("Y")){
					acctNo=racct.getActNo();
					break;
				}

			requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_ACCT_LIST.getParamName(), acctNo);
			requestParamMap.put("extra", "FREEDIALAIRTEL");
			requestParamMap.put("actionCode", "FREEDIALAIRTEL");
		}else{
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		Map<String, Object> txSessions = session.getTxSessions();
		List<Biller> mnoList = (List<Biller>) txSessions.get(USSDInputParamsEnum.FREE_DIAL_MNO_LIST.getTranId());
		List<Account> accounts = (List<Account>) txSessions.get(USSDInputParamsEnum.FREE_DIAL_ACCT_LIST.getTranId());
		MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(session.getCountryCode(), session.getBusinessId());

		String billerUserIdx = userInputMap.get(USSDInputParamsEnum.FREE_DIAL_MNO_LIST.getParamName());
		Biller biller = mnoList.get(Integer.parseInt(billerUserIdx) - 1);
		String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_AIRTIME_TOPUP, new Locale(session.getUserProfile()
				.getLanguage(), session.getUserProfile().getCountryCode()));

		String airTimeMobileNO = userInputMap.get(USSDInputParamsEnum.FREE_DIAL_MOB_NUM.getParamName());
		Integer ccvalue = msisdnDTO.getCcvalue();
		String airTimeAccountNumber = USSDUtils.appendCountryCode(session.getBusinessId(), airTimeMobileNO, msisdnDTO.getSnlen(), ccvalue);
		String acctListUserIdx = userInputMap.get(USSDInputParamsEnum.FREE_DIAL_ACCT_LIST.getParamName());
		Account account = accounts.get(Integer.parseInt(acctListUserIdx) - 1);

		requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_ACCT_LIST.getParamName(), account.getActNo());
		requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_MNO_LIST.getParamName(), biller.getBillerId());
		requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_MOB_NUM.getParamName(), airTimeAccountNumber);
		requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_TRANSACTION_REMARKS.getParamName(), transactionRemarks);
		requestParamMap.put(USSDInputParamsEnum.FREE_DIAL_AMOUNT.getParamName(), userInputMap.get(USSDInputParamsEnum.FREE_DIAL_AMOUNT.getParamName()));
		}

		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		USSDBaseRequest request = new USSDBaseRequest();
		request.setRequestParamMap(requestParamMap);

		return request;
	}
}
