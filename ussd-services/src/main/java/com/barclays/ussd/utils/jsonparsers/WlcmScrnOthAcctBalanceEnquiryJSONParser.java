/**
 * CasaDetailJSONParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.BalanceEnquiryDetailPayData;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.BalanceEnquiryDetails;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.BalanceEnquriy;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI This class is used for Casa Detail Parsing
 * 
 */
public class WlcmScrnOthAcctBalanceEnquiryJSONParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WlcmScrnOthAcctBalanceEnquiryJSONParser.class);

    private static final String BUSINESS_ID = "UGBRB";

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.utils.BmgBaseJsonParser#parseJsonIntoJava(com.barclays .ussd.bmg.dto.ResponseBuilderParamsDTO)
     */
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	ObjectMapper mapper = new ObjectMapper();
	MenuItemDTO menuDTO = null;
	try {
	    BalanceEnquriy balanceEnquriy = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BalanceEnquriy.class);
	    if (balanceEnquriy != null) {
		PayHdr payHdr = balanceEnquriy.getPayHdr();
		if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, balanceEnquriy.getPayData());
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
     * @param responseBuilderParamsDTO
     * @param balanceEnqDetailPayData
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, BalanceEnquiryDetailPayData balanceEnqDetailPayData)
	    throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	Locale locale = getLocale(responseBuilderParamsDTO);
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	BalanceEnquiryDetails actDetls = balanceEnqDetailPayData.getActDetls();

	if (actDetls != null) {
	    if (actDetls.getMkdActNo() != null && actDetls.getMkdActNo().trim().length() > 0 && actDetls.getCurr() != null) {
		StringBuilder pageBody = new StringBuilder();
		menuItemDTO = new MenuItemDTO();

		pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_ACCNT_NO, locale) + actDetls.getMkdActNo());
		pageBody.append(USSDConstants.NEW_LINE);
		if (ussdSessionMgmt.getBusinessId().equalsIgnoreCase(BUSINESS_ID)) {
		    pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_AVAIL_AC_BAL, locale)
			    + actDetls.getCurrentBookBalanceAmount().getAmt() + USSDConstants.SINGLE_WHITE_SPACE
			    + actDetls.getCurrentBookBalanceAmount().getCurr());
		    pageBody.append(USSDConstants.NEW_LINE);

		    pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_ACTUL_AC_BAL, locale) + actDetls.getNetBalanceAmount().getAmt()
			    + USSDConstants.SINGLE_WHITE_SPACE + actDetls.getNetBalanceAmount().getCurr());
		    pageBody.append(USSDConstants.NEW_LINE);
		} else {
		    pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_AVAIL_AC_BAL, locale) + actDetls.getAvblBal().getAmt()
			    + USSDConstants.SINGLE_WHITE_SPACE + actDetls.getAvblBal().getCurr());
		    pageBody.append(USSDConstants.NEW_LINE);

		    pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_CURR_AC_BAL, locale) + actDetls.getCurBal().getAmt()
			    + USSDConstants.SINGLE_WHITE_SPACE + actDetls.getCurBal().getCurr());
		    pageBody.append(USSDConstants.NEW_LINE);
		}

		menuItemDTO.setPageBody(pageBody.toString());
		// insert the back and home options
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPaginationType(PaginationEnum.SPACED);
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		setNextScreenSequenceNumber(menuItemDTO);
	    }

	}
	return menuItemDTO;
    }

    private Locale getLocale(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	Locale locale = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	String language = ussdSessionMgmt.getUserProfile().getLanguage();
	String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	locale = new Locale(language, countryCode);
	return locale;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }
}
