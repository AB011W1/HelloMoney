package com.barclays.ussd.bmg.fundtransfer.otherbarclaysfundtx.request;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTBeneficiary;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTSrcAcct;

public class OBAFTValidateRequestBuilder implements BmgBaseRequestBuilder {
    @Autowired
    UssdResourceBundle ussdResourceBundle;
    /**Kadikope **/

    private static final String CREDIT_CARD_ACCOUNT_TYPE = "CC";
	private static final String CREDIT_CARD_FLAG = "CreditCard";
	private static final String CREDIT_CARD_FLAG_VALUE = "CreditCardFT";
	private static final String CREDIT_ACTIVITY_ID = "PMT_BP_BILLPAY_ONETIME";
/**Kadikope **/
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}
	List<OBAFTBeneficiary> lstPayee = (List<OBAFTBeneficiary>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.INT_FT_PAYEE_LIST.getTranId());

	List<OBAFTSrcAcct> lstSrcPayee = (List<OBAFTSrcAcct>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.INT_FT_SOURCE_LIST.getTranId());

	// TODO
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	Map<String, Object> txSessions =requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_FT_BARCLAYS, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	requestParamMap.put(USSDConstants.TRANSACTION_DATE_PARAM_NAME, sdf.format(new Date()));
	requestParamMap.put(USSDConstants.TRANSACTION_NOTE_PARAM_NAME, transactionRemarks);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode()); // Cheque
	// boook
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDConstants.INT_BNK_FT_NR_TXN_AMOUNT, userInputMap.get(USSDInputParamsEnum.INT_FT_AMOUNT.getParamName()));

	if(CREDIT_CARD_FLAG_VALUE.equalsIgnoreCase((String) txSessions.get("CreditCard")))
	{
		List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>) txSessions
		.get(USSDInputParamsEnum.INT_FT_CARD_LIST.getTranId());
		CustomerMobileRegAcct creditCard = creditCardList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.INT_FT_CARD_LIST.getParamName())) - 1);
		requestParamMap.put(USSDInputParamsEnum.INT_FT_SOURCE_LIST.getParamName(), creditCard.getActNo());
		requestParamMap.put(CREDIT_CARD_FLAG,CREDIT_CARD_FLAG_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.RETRIVE_ACCOUNT_TYPE.getParamName(), CREDIT_CARD_ACCOUNT_TYPE);
     	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), CREDIT_ACTIVITY_ID);

	}else
	{
		requestParamMap.put(USSDInputParamsEnum.INT_FT_SOURCE_LIST.getParamName(), lstSrcPayee.get(
				Integer.parseInt(userInputMap.get(USSDInputParamsEnum.INT_FT_SOURCE_LIST.getParamName())) - 1).getActNo());

	}


	requestParamMap.put(USSDInputParamsEnum.INT_FT_PAYEE_LIST.getParamName(), lstPayee.get(
		Integer.parseInt(userInputMap.get(USSDInputParamsEnum.INT_FT_PAYEE_LIST.getParamName())) - 1).getPayId());

	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
