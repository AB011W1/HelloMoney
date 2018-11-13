package com.barclays.ussd.bmg.atmlocator;

import java.util.Locale;

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
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BranchAtmAddress;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BranchAtmAddressLst;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BranchAtmAddressLstData;

public class AtmLocatorResponseJsonParser implements BmgBaseJsonParser {

    private static final String LABEL_ATM_ADDRESS = "label.atm.address";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AtmLocatorResponseJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    BranchAtmAddress branchAtmAddress = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BranchAtmAddress.class);
	    if (branchAtmAddress != null) {
		if (branchAtmAddress.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(branchAtmAddress.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(branchAtmAddress.getPayData(), responseBuilderParamsDTO, "");

		} else if (branchAtmAddress.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(branchAtmAddress.getPayHdr().getResCde());
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
     * @param branchAtmAddressLstData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(BranchAtmAddressLstData branchAtmAddressLstData, ResponseBuilderParamsDTO responseBuilderParamsDTO,
	    String warningMsg) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	if (branchAtmAddressLstData == null || branchAtmAddressLstData.getAtmLst() == null || branchAtmAddressLstData.getAtmLst().isEmpty()) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_BRANCH_AVAILABLE.getBmgCode());
	}
	if (branchAtmAddressLstData.getAtmLst() != null) {
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    String atmLabel = ussdResourceBundle.getLabel(LABEL_ATM_ADDRESS, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));
	    pageBody.append(atmLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    int index = 1;
	    for (BranchAtmAddressLst atmAddress : branchAtmAddressLstData.getAtmLst()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index++);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(atmAddress.getAddress());
	    }
	    menuItemDTO.setPageBody(pageBody.toString());
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageFooter(menuItemDTO.getPageFooter() + warningMsg);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    setNextScreenSequenceNumber(menuItemDTO);
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }
}