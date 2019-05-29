package com.barclays.ussd.bmg.groupwallet.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;

public class FtGWOneoffSubmitRequestBuilder implements BmgBaseRequestBuilder {
	@Autowired
	UssdResourceBundle ussdResourceBundle;

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		// TODO Auto-generated method stub
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO
				.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt
				.getUserTransactionDetails().getUserInputMap();
		String userInput = userInputMap
				.get(USSDInputParamsEnum.GROUP_WALLET_SELECT_ACC.getParamName());

		String txtAmt = userInputMap
				.get(USSDInputParamsEnum.MOBILE_WALLET_AMOUNT.getParamName());
		String transactionRemarks = ussdResourceBundle.getLabel(
				USSDConstants.TRANSACTION_REMARKS_M_WALLET, new Locale(
						ussdSessionMgmt.getUserProfile().getLanguage(),
						ussdSessionMgmt.getUserProfile().getCountryCode()));

		Map<String, String> requestParamMap = new HashMap<String, String>();

		List<AccountAdditionalInfo> srcLst = (List<AccountAdditionalInfo>) ussdSessionMgmt
				.getCustaccountList();
		AccountAdditionalInfo selectedFrmAcct = srcLst.get(Integer
				.parseInt(userInput) - 1);
		requestParamMap.put("accono", selectedFrmAcct
				.getAccountAdditionalInfo().getAccountId());
		requestParamMap.put("debitAmount", txtAmt);
		requestParamMap.put("actionCode", "SAVE");
		requestParamMap.put("remarks", transactionRemarks);
		requestParamMap.put("transactionStatus", "PendingAuthorization");
		requestParamMap.put("sendSMS","Y");
		String bankCif=null;
		List<CustomerMobileRegAcct> alistforBcif= ((AuthUserData)ussdSessionMgmt.getUserAuthObj()).getPayData().getCustActs();
		for(CustomerMobileRegAcct acct:alistforBcif){
			if(acct.getMkdActNo().equals(selectedFrmAcct.getAccountAdditionalInfo().getMaskedAccountId()))
				bankCif=acct.getBankCif();
		}

		requestParamMap.put("bankCIF", bankCif);
		requestParamMap.put("acconoToCredit", userInputMap.get("mblNo"));
		requestParamMap.put("fundTransferType", "DT");
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME,
				requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME,
				USSDConstants.BMG_SERVICE_VERSION_VALUE);

		USSDBaseRequest request = new USSDBaseRequest();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
		request.setRequestParamMap(requestParamMap);

		return request;
	}

}