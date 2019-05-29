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
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;

public class GroupWalletOneoffConfirmRequestBuilder implements
		BmgBaseRequestBuilder {
	@Autowired
	UssdResourceBundle ussdResourceBundle;

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO
				.getUssdSessionMgmt();

		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		Map<String, String> userInputMap = ussdSessionMgmt
				.getUserTransactionDetails().getUserInputMap();
		String userInput = userInputMap.get("frActNo");


		List<MobileWalletProvider> mobileWalletProvider = (List<MobileWalletProvider>) txSessions
				.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getTranId());
		String selectedMobileWalletProvider = userInputMap
				.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName());
		MobileWalletProvider mobileWalletProviderSelected = mobileWalletProvider
				.get(Integer.parseInt(selectedMobileWalletProvider) - 1);

		String transactionRemarks = ussdResourceBundle.getLabel(
				USSDConstants.TRANSACTION_REMARKS_M_WALLET, new Locale(
						ussdSessionMgmt.getUserProfile().getLanguage(),
						ussdSessionMgmt.getUserProfile().getCountryCode()));

		Map<String, String> requestParamMap = new HashMap<String, String>();
		List<AccountAdditionalInfo> srcLst = (List<AccountAdditionalInfo>) ussdSessionMgmt
				.getCustaccountList();
		AccountAdditionalInfo selectedFrmAcct = srcLst.get(Integer
				.parseInt(userInput) - 1);
		requestParamMap.put("actNo", selectedFrmAcct.getAccountAdditionalInfo()
				.getAccountId());

		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST
				.getParamName(), mobileWalletProviderSelected.getBillerId());

		requestParamMap.put("actionCode", "SAVE");

		requestParamMap.put("remarks", transactionRemarks);

		requestParamMap.put("txnAmt", userInputMap.get("txnAmt"));
		requestParamMap.put("mblNo", userInputMap.get("mblNo"));
		requestParamMap.put("sendSMS","Y");

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