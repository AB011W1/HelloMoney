package com.barclays.ussd.bmg.groupwallet.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.service.impl.BillerServiceImpl;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
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
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CASAccountTransactionList;

public class TranStatusSubmitRequestBuilder implements BmgBaseRequestBuilder {
	@Autowired
	UssdResourceBundle ussdResourceBundle;

    @Autowired
    private BillerServiceImpl billerService;
    private static final String PAYMENT_SUB_CATEGORY_MW = "LOCALWALLETTUP";

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		// TODO Auto-generated method stub
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO
				.getUssdSessionMgmt();

		BillerServiceRequest brequest = new BillerServiceRequest();
		brequest.setBillerId("AMCASHIN-2");
		brequest.setBusinessId(requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());
		brequest.setBillerCategoryId("MobileWallet");

		Map<String, String> userInputMap = ussdSessionMgmt
				.getUserTransactionDetails().getUserInputMap();
		String userInput = userInputMap.get("accno");

		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
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
		requestParamMap.put("accono", selectedFrmAcct
				.getAccountAdditionalInfo().getAccountId());

		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST
				.getParamName(), mobileWalletProviderSelected.getBillerId());

		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST_REF_NUM
				.getParamName(), mobileWalletProviderSelected.getBillerRefNo());


		requestParamMap.put("actionCode", "UPDATE");

		requestParamMap.put("remarks", transactionRemarks);
		String status=userInputMap.get("status");
		int count=Integer.parseInt(userInputMap.get("count"))+1;
		int aLevel=Integer.parseInt(userInputMap.get("authLevel"));

		if(status.equals("1")){
			if((count+1)==aLevel || aLevel==1){
				requestParamMap.put("transactionStatus", "Authorized");
				requestParamMap.put("sendSMS","Y");
			}
			else
				requestParamMap.put("transactionStatus", "PendingAuthorization");

			requestParamMap.put("transactionStatusMessage", "Approved");
		}
		else{
		requestParamMap.put("transactionStatus", "Rejected");
		requestParamMap.put("transactionStatusMessage", "Rejected");
		requestParamMap.put("sendSMS","Y");
		}

		String bankCif=null;
		List<CustomerMobileRegAcct> alistforBcif= ((AuthUserData)ussdSessionMgmt.getUserAuthObj()).getPayData().getCustActs();
		for(CustomerMobileRegAcct acct:alistforBcif){
			if(acct.getMkdActNo().equals(selectedFrmAcct.getAccountAdditionalInfo().getMaskedAccountId()))
				bankCif=acct.getBankCif();
		}

		requestParamMap.put("bankCIF", bankCif);
		int tranSelected=Integer.parseInt(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("tranNo"));
		Object tranList[]=ussdSessionMgmt.getFinalTransactionList().toArray();
		String tranNo=tranList[tranSelected-1].toString();
		requestParamMap.put("transactionNumber", tranNo);
		CASAccountTransactionList tran =(CASAccountTransactionList)ussdSessionMgmt.getTransactionList().get(0);

		if(tran!=null && tran.getTransactionActivity()!=null){
			requestParamMap.put("debitAmount", tran.getTransactionActivity().getDebitAmount());
		}

		/*requestParamMap.put("billerId",mobileWalletProviderSelected.getBillerId());
		requestParamMap.put("billerCategoryId",billerDTO.getBillerCategoryId());
		requestParamMap.put("billerCategoryName",billerDTO.getBillerCategoryName());
		requestParamMap.put("billerName",billerDTO.getBillerName());
		requestParamMap.put("currency",billerDTO.getCurrency());
		requestParamMap.put("externalBillerId",billerDTO.getExternalBillerId());
		requestParamMap.put("maxPaymentAmount",String.valueOf(billerDTO.getMaxPaymentAmount()));
		requestParamMap.put("minPaymentAmount",String.valueOf(billerDTO.getMinPaymentAmount()));*/
		requestParamMap.put("fundTransferType", PAYMENT_SUB_CATEGORY_MW);
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