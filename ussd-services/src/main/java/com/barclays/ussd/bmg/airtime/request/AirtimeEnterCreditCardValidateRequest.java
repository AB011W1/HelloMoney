/**
 *
 */
package com.barclays.ussd.bmg.airtime.request;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.dto.BillerCreditDTO;
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
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
public class AirtimeEnterCreditCardValidateRequest implements BmgBaseRequestBuilder {



    @Autowired
    UssdResourceBundle ussdResourceBundle;
    @Autowired
    UssdMenuBuilder ussdMenuBuilder;

    private static final String CREDIT_CARD_ACCOUNT_TYPE = "CC";
	private static final String CREDIT_CARD_FLAG = "creditCardFlag";
	private static final String CREDIT_CARD_FLAG_VALUE = "CreditCardAT";
	private static final String CREDIT_ACTIVITY_ID = "ACT_CREDIT_CARD_DETAIL";

	@SuppressWarnings("unchecked")
	@Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {


		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		USSDSessionManagement session = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, Object> txSessions = session.getTxSessions();

		List<Biller> mnoList = (List<Biller>) txSessions.get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
		List<CustomerMobileRegAcct> lstAccntDet = (List<CustomerMobileRegAcct>) session.getTxSessions().get(
				USSDInputParamsEnum.AIRTIME_CARD_LIST.getTranId());
		String userSelection = userInputMap.get(USSDInputParamsEnum.AIRTIME_CARD_LIST.getParamName());
		CustomerMobileRegAcct acntDet = lstAccntDet.get(Integer.parseInt(userSelection) - 1);
		MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(session.getCountryCode(), session.getBusinessId());
		String billerUserIdx = userInputMap.get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName());
		Biller biller = mnoList.get(Integer.parseInt(billerUserIdx) - 1);
		String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_AIRTIME_TOPUP, new Locale(session.getUserProfile()
				.getLanguage(), session.getUserProfile().getCountryCode()));
		Map<String, String> requestParamMap = new HashMap<String, String>();

		String airTimeMobileNO="";
		String airTimeAccountNumber = "";

		Payee payee=(Payee)session.getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getTranId());
		String mAtWtSavedBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
		if(mAtWtSavedBenf.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
			airTimeMobileNO = payee.getRefNo().getPhNo();
			Integer ccvalue = msisdnDTO.getCcvalue();
			airTimeAccountNumber = USSDUtils.appendCountryCode(session.getBusinessId(), airTimeMobileNO, msisdnDTO.getSnlen(), ccvalue);
			userInputMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(), airTimeMobileNO);
		} else {
			airTimeMobileNO = userInputMap.get(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName());
			Integer ccvalue = msisdnDTO.getCcvalue();
			airTimeAccountNumber = USSDUtils.appendCountryCode(session.getBusinessId(), airTimeMobileNO, msisdnDTO.getSnlen(), ccvalue);
		}


			BillerCreditDTO billerCreditDTO = (BillerCreditDTO) txSessions.get("BillerCreditDTO");
			//Account account = accounts.get(Integer.parseInt(acctListUserIdx) - 1);
			//lstAccntDet.get(Integer.parseInt(acctListUserIdx) - 1);
			requestParamMap.put(USSDInputParamsEnum.AIRTIME_CARD_LIST.getParamName(), acntDet.getActNo());
			requestParamMap.put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName(), billerCreditDTO.getBillerId());
			requestParamMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(), airTimeAccountNumber);

			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
			requestParamMap.put(USSDInputParamsEnum.AIRTIME_TRANSACTION_REMARKS.getParamName(), transactionRemarks);
			requestParamMap.put(USSDInputParamsEnum.AIRTIME_AMOUNT.getParamName(), userInputMap.get(USSDInputParamsEnum.AIRTIME_AMOUNT.getParamName()));
            requestParamMap.put(CREDIT_CARD_FLAG, CREDIT_CARD_FLAG_VALUE);
            requestParamMap.put(USSDInputParamsEnum.RETRIVE_ACCOUNT_TYPE.getParamName(), CREDIT_CARD_ACCOUNT_TYPE);
        	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), CREDIT_ACTIVITY_ID);

    		requestParamMap.put("actionCode", billerCreditDTO.getActionCode());
    		requestParamMap.put("storeNumber",billerCreditDTO.getStoreNumber());


		 USSDBaseRequest request = new USSDBaseRequest();
		 request.setRequestParamMap(requestParamMap);


		return request;

    }

}
