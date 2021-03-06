/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.bmg.creditcard.statement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

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

/**
 * @author BTCI
 *
 */
public class CcStatDetailsJsonParser implements BmgBaseJsonParser {
    /*private static final String LABEL_BARCLAY_CARD = "label.barclay.card";
    private static final String LABEL_STATMENT_DATE = "label.cc.stmt.date";*/
    private static final String LABEL_PREV_BAL = "label.cc.prev.bal";
   // private static final String LABEL_TOTAL_PURCHASE = "label.cc.total.purchase";
    private static final String LABEL_PAYMENT_RECEIVED = "label.cc.pymt.received";
    //private static final String LABEL_CASH_WITHDRAWN = "label.cc.cash.withdrawn";
    private static final String LABEL_FEE_AND_CHARGES = "label.cc.fee.and.charges";
    //private static final String LABEL_ACCNT_BAL = "label.accnt.bal";
    private static final String LABEL_TOTAL_TXN = "label.cc.total.txn";
    private static final String LABEL_TOTAL_OS = "label.cc.total.os";
    private static final String LABEL_MIN_DUE = "label.cc.min.due";
    private static final String LABEL_DUE_DATE = "label.cc.due.date";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CcStatDetailsJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

			CreditCardStatement creditCardStmt = (CreditCardStatement) ussdSessionMgmt.getTxSessions()
					.get(USSDInputParamsEnum.CR_CARD_STAT_DETAILS.getTranId());
			CreditCardActivityData creditCardStmtObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
					CreditCardActivityData.class);
			CreditCardStatement creditCardActivityInfo = creditCardStmtObj.getPayData();


//			CreditCardStatement creditCardStmt = mapper.readValue(responseBuilderParamsDTO.getJsonString(), CreditCardStatement.class);
	
		    if (creditCardActivityInfo != null) {
		    	menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, creditCardActivityInfo, creditCardStmt);
		    } else {
		    	LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
		    	throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		    }
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
     * @param responseBuilderParamsDTO
     * @param creditCardStmtList
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, CreditCardStatement creditCardActivityInfo, CreditCardStatement creditCardStmt)
	    throws USSDNonBlockingException {
		MenuItemDTO menuItemDTO = null;
		menuItemDTO = new MenuItemDTO();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		String language = ussdSessionMgmt.getUserProfile().getLanguage();
		String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	
		String prevBalLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_PREV_BAL, new Locale(language, countryCode));
		String pymtReceivedLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_PAYMENT_RECEIVED,
			new Locale(language, countryCode));
		String feeChargeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_FEE_AND_CHARGES, new Locale(language, countryCode));
		String totalTxnLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_TOTAL_TXN, new Locale(language, countryCode));
		String totalOsLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_TOTAL_OS, new Locale(language, countryCode));
		String minDueLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_MIN_DUE, new Locale(language, countryCode));
		String dueDateLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_DUE_DATE, new Locale(language, countryCode));
	
	
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		String userSelection = userInputMap.get(USSDInputParamsEnum.CR_CARD_STAT_TRAN_DATE_LIST.getParamName());
	
		StringBuilder pageBody = new StringBuilder();
	
	
		pageBody.append(prevBalLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(creditCardStmt.getPrvBal().getAmt());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(pymtReceivedLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(creditCardActivityInfo.getPmtRecv().getAmt());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(totalTxnLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(creditCardActivityInfo.getTotPur().getAmt());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(feeChargeLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(creditCardActivityInfo.getFeeAndChrg().getAmt());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(totalOsLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(creditCardStmt.getActBal().getAmt());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(minDueLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(creditCardStmt.getMinDueAmt().getAmt());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(dueDateLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		//Due date changes
		//pageBody.append(userSelectedCreditCard.getDueDate());
		 if(null != creditCardStmt.getDueDate()){
		    	pageBody.append(creditCardStmt.getDueDate());
		    } else {
		    	 pageBody.append("NA");
		    }
	
	
		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		// menuItemDTO.setPageFooter(warningMsg);
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPaginationType(PaginationEnum.SPACED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }
}