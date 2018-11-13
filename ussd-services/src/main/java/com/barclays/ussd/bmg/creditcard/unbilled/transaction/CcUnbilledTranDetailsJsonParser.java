/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.bmg.creditcard.unbilled.transaction;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.creditcard.at.a.glance.RetrieveCreditCardListJsonParser;
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
public class CcUnbilledTranDetailsJsonParser implements BmgBaseJsonParser {

    private static final String LABEL_UNBILLED_TRAN_DATE = "label.cc.unbilled.tran.date";
    private static final String LABEL_UNBILLED_TRAN_POST_DATE = "label.cc.unbilled.tran.post.date";
    private static final String LABEL_UNBILLED_TRAN_DESC = "label.cc.unbilled.tran.desc";
    private static final String LABEL_UNBILLED_TRAN_AMT = "label.cc.unbilled.tran.amt";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RetrieveCreditCardListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	    Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	    List<CreditCardActivity> actActvLst = (List<CreditCardActivity>) txSessions.get(USSDInputParamsEnum.CR_CARD_UNBILLED_TRAN_MERCHANT_LIST
		    .getTranId());
	    CreditCardActivity userSeletectedCreditCard = actActvLst.get(Integer.parseInt(ussdSessionMgmt.getUserTransactionDetails()
		    .getUserInputMap().get(USSDInputParamsEnum.CR_CARD_UNBILLED_TRAN_MERCHANT_LIST.getParamName())) - 1);

	    CreditCardDetails crdDetls = (CreditCardDetails) txSessions.get(USSDInputParamsEnum.CR_CARD_UNBILLED_DETAILS.getTranId());

	    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, userSeletectedCreditCard, crdDetls);
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
     * @param userSeletectedCreditCard
     * @param crdDetls2
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, CreditCardActivity userSeletectedCreditCard,
	    CreditCardDetails crdDetls) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = null;
	if (crdDetls != null && userSeletectedCreditCard != null) {
	    menuItemDTO = new MenuItemDTO();
	    StringBuilder pageBody = new StringBuilder();
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    String language = ussdSessionMgmt.getUserProfile().getLanguage();
	    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();

	    String tranDateLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_UNBILLED_TRAN_DATE,
		    new Locale(language, countryCode));
	    String tranPostDateLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_UNBILLED_TRAN_POST_DATE,
		    new Locale(language, countryCode));
	    String tranDescLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_UNBILLED_TRAN_DESC,
		    new Locale(language, countryCode));
	    String tranAmtLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_UNBILLED_TRAN_AMT,
		    new Locale(language, countryCode));

	    pageBody.append(tranDateLabel);
	    pageBody.append(userSeletectedCreditCard.getDt());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(tranPostDateLabel);
	    pageBody.append(userSeletectedCreditCard.getTxnPostDt());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(tranDescLabel);
	    pageBody.append(userSeletectedCreditCard.getTxnPrt());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(tranAmtLabel);
	    pageBody.append(userSeletectedCreditCard.getAmt().getCurr());
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(userSeletectedCreditCard.getAmt().getAmt());

	    menuItemDTO.setPageBody(pageBody.toString());
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    // menuItemDTO.setPageFooter(warningMsg);
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	    setNextScreenSequenceNumber(menuItemDTO);
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_STATEMENT_FOUND.getBmgCode());
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }
}
