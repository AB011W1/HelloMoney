package com.barclays.ussd.bmg.groupwallet.requests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CASAccountTransactionList;

public class FtGWStatusRequestBuilder implements BmgBaseRequestBuilder {
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
		String userInput = userInputMap.get("accno");

		String txtAmt = userInputMap
				.get(USSDInputParamsEnum.MOBILE_WALLET_AMOUNT.getParamName());

		Map<String, String> requestParamMap = new HashMap<String, String>();
		List<AccountAdditionalInfo> srcLst = (List<AccountAdditionalInfo>) ussdSessionMgmt
				.getCustaccountList();
		AccountAdditionalInfo selectedFrmAcct = srcLst.get(Integer
				.parseInt(userInput) - 1);
		requestParamMap.put("frActNo", selectedFrmAcct.getAccountAdditionalInfo()
				.getAccountId());
		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_AMOUNT
				.getParamName(), txtAmt);
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME,
				requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME,
				USSDConstants.BMG_SERVICE_VERSION_VALUE);

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

		String transactionRemarks = ussdResourceBundle.getLabel(
				USSDConstants.TRANSACTION_REMARKS_FT_BARCLAYS, new Locale(
						ussdSessionMgmt.getUserProfile().getLanguage(),
						ussdSessionMgmt.getUserProfile().getCountryCode()));
		requestParamMap.put("payDesc", transactionRemarks);
		requestParamMap.put("txnDt", ft.format(dNow));
		requestParamMap.put(USSDConstants.TRANSACTION_NOTE_PARAM_NAME,
				transactionRemarks);
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME,
				requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME,
				USSDConstants.BMG_SERVICE_VERSION_VALUE);

		String srcAcNo = (srcLst.get(Integer.parseInt(userInput) - 1))
				.getAccountAdditionalInfo().getAccountId();
		String accno = userInputMap
				.get(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER
						.getParamName());
		String payeeAcNo = accno;
		requestParamMap.put(
				USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName(), payeeAcNo);
		requestParamMap.put(USSDInputParamsEnum.OLAFT_SOURCE_LIST
				.getParamName(), srcAcNo);

		if (StringUtils.isNotBlank(userInputMap.get("txnAmt"))) {
			requestParamMap.put(
					USSDInputParamsEnum.OLAFT_AMOUNT.getParamName(),
					userInputMap.get("txnAmt"));
		}
		int tranSelected = Integer.parseInt(ussdSessionMgmt
				.getUserTransactionDetails().getUserInputMap().get("tranNo"));
		Object tranList[] = ussdSessionMgmt.getFinalTransactionList().toArray();
		String tranNo = tranList[tranSelected - 1].toString();
		requestParamMap.put("transactionNumber", tranNo);
		List<CASAccountTransactionList> transactionList = (List<CASAccountTransactionList>) ussdSessionMgmt
				.getTransactionList();
		if(transactionList!=null && transactionList.size()>0){
		CASAccountTransactionList tran=(CASAccountTransactionList)transactionList.get(0);
		requestParamMap.put("txnAmt", tran
					.getTransactionActivity().getDebitAmount());
		requestParamMap.put("beneficiaryAccountNumber", tran
					.getTransactionActivity().getBeneficiaryAcctOrMblno());
		}
		requestParamMap.put("curr", "TZS");
		USSDBaseRequest request = new USSDBaseRequest();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
		request.setRequestParamMap(requestParamMap);

		return request;
	}

}