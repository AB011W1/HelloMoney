/**
 * BillPaySubmitJsonParser.java
 */
package com.barclays.ussd.bmg.creditcard.third.party.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillPaySubmit;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillPaySubmitData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 * 
 */
public class CCThirdPartyPayValidateJsonParser implements BmgBaseJsonParser {

    private static final String LABEL_CARD_NUMBER = "label.enter.third.party.to.card.acc";
    private static final String LABEL_FROM_ACCNT = "label.enter.third.party.frm.card.acc";
    private static final String AMOUNT_LABEL = "label.enter.third.party.amt";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CCThirdPartyPayValidateJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    BillPaySubmit billPaySubmit = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BillPaySubmit.class);
	    if (billPaySubmit != null) {
		PayHdr payHdr = billPaySubmit.getPayHdr();
		if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		    menuDTO = renderMenuOnScreen(billPaySubmit, responseBuilderParamsDTO);
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(billPaySubmit.getPayData().getTxnRefNo());
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_VALIDATE.getTranId(),
			    txnRefNo);
		} else if (payHdr != null && TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(payHdr.getResCde())) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
		} else if (payHdr != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(payHdr.getResCde());
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
     * @param billPaySubmitData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(BillPaySubmit billPaySubmit, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	BillPaySubmitData payData = billPaySubmit.getPayData();

	if (payData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	    Locale locale = new Locale(userProfile.getLanguage(), userProfile.getCountryCode());
	    String toCardLabel = ussdResourceBundle.getLabel(LABEL_CARD_NUMBER, locale);
	    String fromAccntLabel = ussdResourceBundle.getLabel(LABEL_FROM_ACCNT, locale);
	    String amountLabel = ussdResourceBundle.getLabel(AMOUNT_LABEL, locale);
	    String confirmLabel = ussdResourceBundle.getLabel(CONFIRM_LABEL, locale);
	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	    String creditCardNo = userInputMap.get(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_CC_NO.getParamName());

	    List<CustomerMobileRegAcct> lstFromAcct = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_FROM_ACNT.getTranId());

	    String userSelectedFromAccntInput = userInputMap.get(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_FROM_ACNT.getParamName());
	    CustomerMobileRegAcct selectedFrmAcct = lstFromAcct.get(Integer.parseInt(userSelectedFromAccntInput) - 1);

	    String amount = userInputMap.get(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_AMT.getParamName());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(toCardLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(creditCardNo);

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(fromAccntLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(selectedFrmAcct.getMkdActNo());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(amountLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(amount);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(selectedFrmAcct.getCurr());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(confirmLabel);
	}

	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());

    }
}
