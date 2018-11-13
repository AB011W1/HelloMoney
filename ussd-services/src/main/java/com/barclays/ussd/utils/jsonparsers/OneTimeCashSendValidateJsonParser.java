package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.dao.product.impl.ListValueResDAOImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OneTimeCashSendValidate;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OneTimeCashSendValidatePayData;

public class OneTimeCashSendValidateJsonParser implements BmgBaseJsonParser {
    private static final String CONFRIM_LABEL = "label.confirm";
    private static final String AMOUNT_LABEL = "label.amount";
    private static final String MOBILE_NO_LABEL = "label.mobnum.cashsend";
    private static final String FROMACC_LABEL = "label.fromaccount.cashsend";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final String CASHSEND_TRANSACTION_AMT_LIMIT_ERROR = "BMB90003";
    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
    private String pilotValue = null;
    // Xcelerate is offline message
    private static final String XCELERATE_OFFLINE_LABEL = "label.xcelerate.offline.acceptance";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(OwnFundTransferValidateJsonParser.class);

    @Autowired
    ListValueResDAOImpl listValueResDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    OneTimeCashSendValidate oneTimeCashSendValidateObj = mapper.readValue(jsonString, OneTimeCashSendValidate.class);
	    if (oneTimeCashSendValidateObj != null) {
		if (oneTimeCashSendValidateObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(oneTimeCashSendValidateObj.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(oneTimeCashSendValidateObj.getPayData(), responseBuilderParamsDTO);
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(oneTimeCashSendValidateObj.getPayData().getTxnRefNo());
		    // set the payee accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_VALIDATE.getTranId(),
			    txnRefNo);
		} else if (oneTimeCashSendValidateObj.getPayHdr() != null
			&& TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(oneTimeCashSendValidateObj.getPayHdr().getResCde())) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
		} else if (oneTimeCashSendValidateObj.getPayHdr() != null
			&& CASHSEND_TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(oneTimeCashSendValidateObj.getPayHdr().getResCde())) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_NUMBER_OF_TRAN_LIMIT_EXCEEDED.getBmgCode());
		} else if (oneTimeCashSendValidateObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(oneTimeCashSendValidateObj.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    }
	} catch (JsonParseException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (JsonMappingException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}

	return menuDTO;
    }

    /**
     * @param oneTimeCashSendValidatePayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(OneTimeCashSendValidatePayData oneTimeCashSendValidatePayData,
	    ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	if (oneTimeCashSendValidatePayData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	    String fromAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(FROMACC_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String mobilNoLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MOBILE_NO_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String amountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AMOUNT_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    // CPB demo change
	    String transactionFeeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FEE_LABEL,
			    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	    String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CONFRIM_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(fromAccLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(oneTimeCashSendValidatePayData.getFrmAct().getMkdActNo());
	    String mobileNo = userInputMap.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_MOBILE_NUM.getParamName());
	    if (mobileNo != null && StringUtils.isNotEmpty(mobileNo)) {
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(mobilNoLabel);
			pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
			pageBody.append(mobileNo);
		    }
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(amountLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(oneTimeCashSendValidatePayData.getTxnAmt().getAmt());

	    // String mobileNo = oneTimeCashSendValidatePayData.getMobileNo();

	    // Pilot Migration check CBP  22/08
		/*ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(BMBContextHolder.getContext());
		listValueResServiceRequest.setListValueKey("CashSend");
		ListValueResByGroupServiceResponse listResp = listValueResDAOImpl.findListValueResByKey(listValueResServiceRequest);
		pilotValue = listResp.getListValueCahceDTO().get(0).getLabel();*/

	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		//CPB change
		// Removing PilotValue check(pilotValue !=null && pilotValue.equalsIgnoreCase("Y")) its not going with Regulatory 6.0.0 changes - 02/11/2017
		if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB")){
			if(oneTimeCashSendValidatePayData.getTransactionFeeAmount()!= null && oneTimeCashSendValidatePayData.getTransactionFeeAmount().getAmt()!=null){

				Double accumulatedCharge = 0.0;
				Double roundedAccumulatedVal = 0.0;
	    		Double tranFee = Double.valueOf(oneTimeCashSendValidatePayData.getTransactionFeeAmount().getAmt());
				Double taxAmt = oneTimeCashSendValidatePayData.getTaxAmount();
				if(tranFee!=null && taxAmt!=null){
					accumulatedCharge = tranFee + taxAmt;
					roundedAccumulatedVal =(double) Math.round(accumulatedCharge * 100) / 100;
				}

				pageBody.append(transactionFeeLabel);
				pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(oneTimeCashSendValidatePayData.getTransactionFeeAmount().getCurr());
				pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(roundedAccumulatedVal);
				pageBody.append(USSDConstants.NEW_LINE);
			}else {
				String xelerateOfflineLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(XCELERATE_OFFLINE_LABEL,
					    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
				pageBody.append(xelerateOfflineLabel);
				pageBody.append(USSDConstants.NEW_LINE);
		    }
		}
		pageBody.append(confirmLabel);
	    }

	}

	menuItemDTO.setPageBody(pageBody.toString());

	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());

    }

}
