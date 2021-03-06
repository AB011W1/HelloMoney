package com.barclays.ussd.bmg.kits;

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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;

public class KitsRegisterConfirmDetailsRequestBuilder implements BmgBaseRequestBuilder{
	    /*@Autowired
	    UssdResourceBundle ussdResourceBundle;*/

	    @SuppressWarnings("unchecked")
	    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

	    /*
        USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();

		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		List<AccountData> lstFromAcct = (List<AccountData>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
			USSDInputParamsEnum.KITS_STP_ACCOUNT_NUM.getTranId());

		Map<String, String> requestParamMap = new HashMap<String, String>();
        String amount=userInputMap.get(USSDInputParamsEnum.KITS_STP_AMOUNT.getParamName());
        String mobileNumber=userInputMap.get(USSDInputParamsEnum.KITS_STP_MOBILE_NUM.getParamName());
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.putAll(userInputMap);
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_BILL_PAYMENT, new Locale(ussdSessionMgmt
			.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

		//requestParamMap.put(USSDConstants.BILL_PAY_REMARKS, transactionRemarks);
		requestParamMap.put(USSDInputParamsEnum.KITS_STP_ACCOUNT_NUM.getParamName(), lstFromAcct.get(
			Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KITS_STP_ACCOUNT_NUM.getParamName())) - 1).getActNo());

		requestParamMap.put(USSDInputParamsEnum.KITS_STP_AMOUNT.getParamName(), amount);
		requestParamMap.put(USSDInputParamsEnum.KITS_STP_MOBILE_NUM.getParamName(), mobileNumber);


		request.setRequestParamMap(requestParamMap);
		return request;
	    */
	    return null;
	    }
}
