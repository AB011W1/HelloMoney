/**
 * CasaDetailJSONParser.java
 */
package com.barclays.ussd.bmg.creditcard.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

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
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI This class is used for Casa Detail Parsing
 *
 */
public class CreditCardPaymentDetailsJsonParser implements BmgBaseJsonParser {

    private static final String LABEL_MIN_AMT_DUE = "label.min.amt.due";
    private static final String LABEL_PAYMENT_DUE_DATE = "label.payment.due.date";
    private static final String LABEL_OUTSTANDING_BAL = "label.outstanding.bal";
    private static final String LABEL_BARCLAY_CARD = "label.barclay.card";
    private static final String NAVCONFIRM_LABEL = "label.confirm";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CreditCardPaymentListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	try {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.CR_CARD_PAYMENT_LIST.getTranId());
	    if (creditCardList != null) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, creditCardList);
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
     * @param creditCardList
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<CustomerMobileRegAcct> creditCardList) {
	MenuItemDTO menuItemDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	String userSelection = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(
		USSDInputParamsEnum.CR_CARD_PAYMENT_LIST.getParamName());
	if (creditCardList != null && !creditCardList.isEmpty()) {
	    CustomerMobileRegAcct userSelectedCreditCard = creditCardList.get(Integer.parseInt(userSelection) - 1);
	    menuItemDTO = new MenuItemDTO();

	    Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    if (txSessions == null) {
		responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>(5));
	    }
	    StringBuilder pageBody = new StringBuilder();
	    String language = ussdSessionMgmt.getUserProfile().getLanguage();
	    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	    String barclaysCardLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_BARCLAY_CARD,
		    new Locale(language, countryCode));
	    String outsAmtLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_OUTSTANDING_BAL, new Locale(language, countryCode));
	    String paymentDueDtLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_PAYMENT_DUE_DATE,
		    new Locale(language, countryCode));
	    String minAmtDueLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_MIN_AMT_DUE, new Locale(language, countryCode));
	    String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(NAVCONFIRM_LABEL, new Locale(language, countryCode));

	    pageBody.append(barclaysCardLabel);
	    pageBody.append(userSelectedCreditCard.getMkdCrdNo());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(outsAmtLabel);
	    pageBody.append(userSelectedCreditCard.getOutstandingAmt().getCurr());
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(userSelectedCreditCard.getOutstandingAmt().getAmt());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(paymentDueDtLabel);

	    //Due date changes
	    //pageBody.append(userSelectedCreditCard.getPmtDueDt());

	    if(null != userSelectedCreditCard.getPmtDueDt()){
	    	pageBody.append(userSelectedCreditCard.getPmtDueDt());
	    } else {
	    	 pageBody.append("NA");
	    }

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(minAmtDueLabel);
	    pageBody.append(userSelectedCreditCard.getMinDueAmt().getCurr());
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(userSelectedCreditCard.getMinDueAmt().getAmt());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(confirmLabel);

	    menuItemDTO.setPageBody(pageBody.toString());
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    // menuItemDTO.setPageFooter(warningMsg);
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	    setNextScreenSequenceNumber(menuItemDTO);
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }
}
