package com.barclays.ussd.bmg.mobilewallettopup.request;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.FromAcntLst;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.SrcAccount;

public class MobileWalletTopupValidateRequest implements BmgBaseRequestBuilder {

    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @Autowired
    UssdMenuBuilder ussdMenuBuilder;

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	String userInput = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getParamName());

	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	/*List<SrcAccount> srcLst = (List<SrcAccount>) txSessions.get(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getTranId());
	SrcAccount selectedFrmAcct = srcLst.get(Integer.parseInt(userInput) - 1);*/
	List<MobileWalletProvider> mobileWalletProvider = (List<MobileWalletProvider>) txSessions.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST
		.getTranId());
	String selectedMobileWalletProvider = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName());
	MobileWalletProvider mobileWalletProviderSelected = mobileWalletProvider.get(Integer.parseInt(selectedMobileWalletProvider) - 1);
	MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(ussdSessionMgmt.getCountryCode(), ussdSessionMgmt.getBusinessId());

	/*String mWalletMobileNo = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName());
	Integer ccvalue = msisdnDTO.getCcvalue();
	String mWalletAccountNumber = USSDUtils.appendCountryCode(ussdSessionMgmt.getBusinessId(), mWalletMobileNo, msisdnDTO.getSnlen(), ccvalue);
*/
	String txtAmt = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_AMOUNT.getParamName());

	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_M_WALLET, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	//CR82
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Payee payee=(Payee)txSessions.get(USSDInputParamsEnum.MOBILE_WALLET_BENF_DTlS.getTranId());
	String mAtWtSavedBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
	if(mAtWtSavedBenf.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
		String mWalletMobileNo = payee.getRefNo().getPhNo();
		Integer ccvalue = msisdnDTO.getCcvalue();
		String mWalletAccountNumber = USSDUtils.appendCountryCode(ussdSessionMgmt.getBusinessId(), mWalletMobileNo, msisdnDTO.getSnlen(), ccvalue);
		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(), mWalletAccountNumber);
		FromAcntLst fromAcntLst = (FromAcntLst) txSessions.get("AccountListSaved");
		List<AccountData> lstFromAcct = (List<AccountData>)fromAcntLst.getFrActLst();

		if(USSDConstants.CREDIT_MOBILE_WALLET.equals(txSessions.get(USSDConstants.CREDIT_MOBILE_WALLET))){

			List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>) txSessions
						.get(USSDInputParamsEnum.MOBILE_WALLET_CREDIT_CARD_LIST.getTranId());
			String userCreditSelection = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_CREDIT_CARD_LIST.getParamName());
			CustomerMobileRegAcct creditCard = creditCardList.get(Integer.parseInt(userCreditSelection) - 1);
			requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getParamName(), creditCard.getActNo());
			requestParamMap.put("CrditCardFlag", USSDConstants.CREDIT_MOBILE_WALLET);
			BillerCreditDTO billerCreditDTO = (BillerCreditDTO) txSessions.get("BillerCreditDTO");
			requestParamMap.put("actionCode", billerCreditDTO.getActionCode());
			requestParamMap.put("storeNumber",billerCreditDTO.getStoreNumber());
		} else {
			requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getParamName(), lstFromAcct.get(
				Integer.parseInt(userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getParamName())) - 1).getActNo());
		}

		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName(), payee.getBilrId());
		userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(), mWalletMobileNo);

	} else {
		String mWalletMobileNo = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName());
		Integer ccvalue = msisdnDTO.getCcvalue();
		if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID)){
			if(mWalletMobileNo==null){
				mWalletMobileNo = ussdSessionMgmt.getMsisdnNumber();
				userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(), mWalletMobileNo);
			}
		}
		String mWalletAccountNumber = USSDUtils.appendCountryCode(ussdSessionMgmt.getBusinessId(), mWalletMobileNo, msisdnDTO.getSnlen(), ccvalue);
		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(), mWalletAccountNumber);

		if(USSDConstants.CREDIT_MOBILE_WALLET.equals(txSessions.get(USSDConstants.CREDIT_MOBILE_WALLET))){

		List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>) txSessions
					.get(USSDInputParamsEnum.MOBILE_WALLET_CREDIT_CARD_LIST.getTranId());
		String userCreditSelection = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_CREDIT_CARD_LIST.getParamName());
		CustomerMobileRegAcct creditCard = creditCardList.get(Integer.parseInt(userCreditSelection) - 1);
		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getParamName(), creditCard.getActNo());
		requestParamMap.put("CrditCardFlag", USSDConstants.CREDIT_MOBILE_WALLET);
		BillerCreditDTO billerCreditDTO = (BillerCreditDTO) txSessions.get("BillerCreditDTO");
		requestParamMap.put("actionCode", billerCreditDTO.getActionCode());
		requestParamMap.put("storeNumber",billerCreditDTO.getStoreNumber());

		} else {
		List<SrcAccount> srcLst = (List<SrcAccount>) txSessions.get(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getTranId());
		SrcAccount selectedFrmAcct = srcLst.get(Integer.parseInt(userInput) - 1);
		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getParamName(), selectedFrmAcct.getActNo());
		}
		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName(), mobileWalletProviderSelected.getBillerId());
	}

	/*Map<String, String> requestParamMap = new HashMap<String, String>();*/
	/*requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName(), mobileWalletProviderSelected.getBillerId());*/
	requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST_REF_NUM.getParamName(), mobileWalletProviderSelected.getBillerRefNo());
	/*requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(), mWalletAccountNumber);*/
	requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_AMOUNT.getParamName(), txtAmt);
	/*requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getParamName(), selectedFrmAcct.getActNo());*/

	requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_TRANSACTION_REMARKS.getParamName(), transactionRemarks);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	USSDBaseRequest request = new USSDBaseRequest();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID)){
		//requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(), ussdSessionMgmt.getMsisdnNumber());
		requestParamMap.put("isGHMWFreeDialUssdFlow", "TRUE");
	}

	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	request.setRequestParamMap(requestParamMap);

	return request;
    }

}
