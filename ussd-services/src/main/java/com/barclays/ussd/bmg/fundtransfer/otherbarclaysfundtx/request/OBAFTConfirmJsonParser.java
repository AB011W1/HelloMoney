package com.barclays.ussd.bmg.fundtransfer.otherbarclaysfundtx.request;

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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OwnFundTxConfirm;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OwnFundTxConfirmPayData;

public class OBAFTConfirmJsonParser implements BmgBaseJsonParser {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(OBAFTConfirmJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    OwnFundTxConfirm ownFundTxConfirmObj = mapper.readValue(jsonString, OwnFundTxConfirm.class);
	    if (ownFundTxConfirmObj != null) {
		if (ownFundTxConfirmObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(ownFundTxConfirmObj.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(ownFundTxConfirmObj.getPayData(), responseBuilderParamsDTO);

		} else if (ownFundTxConfirmObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(ownFundTxConfirmObj.getPayHdr().getResCde());
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
     * @param ownFundTxConfirmPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(OwnFundTxConfirmPayData ownFundTxConfirmPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	if (ownFundTxConfirmPayData != null) {
	    String txIdLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(USSDConstants.TRAN_ID_LABEL_ID,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(txIdLabel);
	    pageBody.append(ownFundTxConfirmPayData.getRefNo()).append(USSDConstants.NEW_LINE);
	    menuItemDTO.setPageBody(pageBody.toString());
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	}

	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_END);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	ussdSessionMgmt.clean();
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	// Refer to sequencer.properties to set the below value
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
    }

}
