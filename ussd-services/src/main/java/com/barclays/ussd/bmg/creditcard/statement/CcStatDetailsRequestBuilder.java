package com.barclays.ussd.bmg.creditcard.statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class CcStatDetailsRequestBuilder implements BmgBaseRequestBuilder {

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject(com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	
	String userInput = requestBuilderParamsDTO.getUserInput();

	if (userInput != null && StringUtils.isNotEmpty(userInput)) {
		List<CreditCardStatement> creditCardStmtList = (List<CreditCardStatement>) requestBuilderParamsDTO
				.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.CR_CARD_STAT_TRAN_DATE_LIST.getTranId());
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails()
				.getUserInputMap();
		String userSelection = userInputMap.get(USSDInputParamsEnum.CR_CARD_STAT_TRAN_DATE_LIST.getParamName());
		CreditCardStatement userSelectedCreditCard = creditCardStmtList.get(Integer.parseInt(userSelection) - 1);
		//Cards Migration
		Map<String, Object> txSessions = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    txSessions.put(USSDInputParamsEnum.CR_CARD_STAT_DETAILS.getTranId(), userSelectedCreditCard);
	  
		requestParamMap.put(USSDInputParamsEnum.CR_CARD_STAT_ACCT_NO.getParamName(),userSelectedCreditCard.getActNo());
		requestParamMap.put(USSDInputParamsEnum.CR_CARD_ACTIVTY_DATE_LIST.getParamName(),userSelectedCreditCard.getStatementDate());
		requestParamMap.put(USSDInputParamsEnum.CR_CARD_CURRENCY.getParamName(),userSelectedCreditCard.getCurrency());
		requestParamMap.put(USSDInputParamsEnum.CR_CARD_SEQUENCE_NUMBER.getParamName(),userSelectedCreditCard.getSequenceNumber());
		request.setRequestParamMap(requestParamMap);
	}else {
    	throw new USSDNonBlockingException(USSDExceptions.USSD_USER_INPUT_INVALID.getBmgCode());
    }


	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "ACT_CCD_ACTIVITY_TRANS");
	request.setRequestParamMap(requestParamMap);
	return request;

    }

}
