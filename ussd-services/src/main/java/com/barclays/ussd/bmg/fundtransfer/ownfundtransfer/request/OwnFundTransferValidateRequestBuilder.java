package com.barclays.ussd.bmg.fundtransfer.ownfundtransfer.request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class OwnFundTransferValidateRequestBuilder implements BmgBaseRequestBuilder {
	private static final String CREDIT_CARD_ACCOUNT_TYPE = "CC";

    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	/*List<AccountDetails> payeeList = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId());
	List<AccountDetails> srcList = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId());
	// ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().put(USSDInputParamsEnum.OLAFT_AMOUNT.getParamName(),
	// requestBuilderParamsDTO.getUserInput());
	String payeeAcNoInput = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap()
		.get(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName());
	String srcAcNoInput = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName());
	String srcAcNo = srcList.get(Integer.parseInt(srcAcNoInput) - 1).getActNo();
	String payeeAcNo = payeeList.get(Integer.parseInt(payeeAcNoInput) - 1).getActNo();*/

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

	Calendar now = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_FT_BARCLAYS, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	requestParamMap.put(USSDConstants.TRANSACTION_DATE_PARAM_NAME, sdf.format(now.get(Calendar.DATE)));
	requestParamMap.put(USSDConstants.TRANSACTION_NOTE_PARAM_NAME, transactionRemarks);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);


	if("OwnLinkedAcctFundTxCredit".equals(ussdSessionMgmt.getUserTransactionDetails()
			.getCurrentRunningTransaction().getServiceName())){

		List<AccountDetails> payeeList = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId());
		String payeeAcNoInput = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName());
		String payeeAcNo = payeeList.get(Integer.parseInt(payeeAcNoInput) - 1).getActNo();

		List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId());
			String userSelection = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName());
			CustomerMobileRegAcct creditCard = creditCardList.get(Integer.parseInt(userSelection) - 1);
			requestParamMap.put(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName(),creditCard.getCrdNo());
			requestParamMap.put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName(), payeeAcNo);
			requestParamMap.put("identifier","CreditCard");
			requestParamMap.put(USSDInputParamsEnum.RETRIVE_ACCOUNT_TYPE.getParamName(), CREDIT_CARD_ACCOUNT_TYPE);
			requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "ACT_CREDIT_CARD_DETAIL");
	} else {

		List<AccountDetails> payeeList = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId());
		List<AccountDetails> srcList = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId());

		// ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().put(USSDInputParamsEnum.OLAFT_AMOUNT.getParamName(),
		// requestBuilderParamsDTO.getUserInput());
		String payeeAcNoInput = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap()
			.get(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName());
		String srcAcNoInput = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName());
		String srcAcNo = srcList.get(Integer.parseInt(srcAcNoInput) - 1).getActNo();
		String payeeAcNo = payeeList.get(Integer.parseInt(payeeAcNoInput) - 1).getActNo();
		requestParamMap.put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName(), payeeAcNo);
		requestParamMap.put(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName(), srcAcNo);

	}

	if (StringUtils.isNotBlank(requestBuilderParamsDTO.getUserInput())) {
	    requestParamMap.put(USSDInputParamsEnum.OLAFT_AMOUNT.getParamName(), requestBuilderParamsDTO.getUserInput());
	}

	request.setRequestParamMap(requestParamMap);
	return request;
    }

}
