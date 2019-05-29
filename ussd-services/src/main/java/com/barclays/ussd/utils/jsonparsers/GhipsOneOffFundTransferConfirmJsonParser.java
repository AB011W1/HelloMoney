package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.FormValRes;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.FormValResData;

public class GhipsOneOffFundTransferConfirmJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GhipsOneOffFundTransferConfirmJsonParser.class);
    private static final String LBL_AMOUNT = "label.ghips.save.benef.amount";
    private static final String LBL_FRM_AC_NO = "label.ghips.save.benef.fromactno";
    private static final String LBL_TO = "label.ghips.save.benef.to";
    private static final String LBL_CONFIRM = "label.confirm";

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();

	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
	    FormValRes formValRes = mapper.readValue(jsonString, FormValRes.class);
	    if (formValRes != null) {
		if (formValRes.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(formValRes.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, formValRes.getPayData(), "");
		    setOutputInSession(responseBuilderParamsDTO, formValRes);
		} else if (formValRes.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(formValRes.getPayHdr().getResCde());
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
     * @param formValRes
     */
    private void setOutputInSession(ResponseBuilderParamsDTO responseBuilderParamsDTO, FormValRes formValRes) {
	FormValResData formValResData = formValRes.getPayData();
	if (formValResData != null) {
	    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .put(USSDInputParamsEnum.GHIPS_FUND_TRANSFER_SUBMIT.getTranId(), formValResData.getTxnRefNo());
	}
    }

    /**
     * @param responseBuilderParamsDTO
     * @param formValResData
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, FormValResData formValResData, String warningMsg) {
    	MenuItemDTO menuItemDTO = null;
    	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
    	if (formValResData != null) {
    	    menuItemDTO = new MenuItemDTO();
    	    StringBuilder pageBody = new StringBuilder();
    	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
    	    String countryCode = "";
    	    String lang = "";
    	    if (ussdSessionMgmt.getUserProfile() != null) {
    		countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
    		lang = ussdSessionMgmt.getUserProfile().getLanguage();
    	    }
    	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
    	    Locale locale = new Locale(lang, countryCode);
    	    if (StringUtils.isNotEmpty(warningMsg)) {
    		pageBody.append(USSDConstants.NEW_LINE);
    		pageBody.append(warningMsg);
    	    }

    	    addLabelToPageBody(pageBody, ussdResourceBundle.getLabel(LBL_FRM_AC_NO, locale));
    	    if (formValResData.getFrmAct() != null) {
    		pageBody.append(formValResData.getFrmAct().getMkdActNo());
    	    }


    	    addLabelToPageBody(pageBody, ussdResourceBundle.getLabel(LBL_TO, locale));
    	    String benefName= String.valueOf(ussdSessionMgmt.getTxSessions().get(USSDConstants.GHIPPS_CUSTOMER_NAME));
    	    pageBody.append(benefName);

    	    addLabelToPageBody(pageBody, ussdResourceBundle.getLabel(LBL_AMOUNT, locale));
    	    if (formValResData.getFrmAct() != null) {
    		pageBody.append(formValResData.getTxnAmt().getAmt());
    	    }

    	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
    			addLabelToPageBody(pageBody, ussdResourceBundle.getLabel(LBL_CONFIRM, locale));
    		}

    	    menuItemDTO.setPageBody(pageBody.toString());

    	    // insert the back and home options
    	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);

    	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
    	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
    	}
    	if(null != menuItemDTO)
    		setNextScreenSequenceNumber(menuItemDTO);
    	return menuItemDTO;
        }

    private void addLabelToPageBody(StringBuilder pageBody, String label) {
	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(label);
	pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo());

    }
}
