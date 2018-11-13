package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDAccntPayData;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDAccountSummary;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDApplySourceAccount;

public class FDAccountSummaryJSONParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(FDAccountSummaryJSONParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    FDAccountSummary accSummry = mapper.readValue(responseBuilderParamsDTO.getJsonString(), FDAccountSummary.class);
	    if (accSummry != null) {
		if (accSummry.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(accSummry.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, accSummry.getPayData());
		    setOutputInSession(responseBuilderParamsDTO, accSummry);

		} else if (accSummry.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(accSummry.getPayHdr().getResCde());
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
     * @param accSummry
     */
    private void setOutputInSession(ResponseBuilderParamsDTO responseBuilderParamsDTO, FDAccountSummary accSummry) {
	FDAccntPayData acntPayData = accSummry.getPayData();
	if (acntPayData != null) {
	    Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    if (txSessions == null) {
		txSessions = new HashMap<String, Object>(acntPayData.getSrcLst().size());
	    }
	    txSessions.put(USSDInputParamsEnum.FD_APPLY_SEL_AC.getTranId(), acntPayData.getSrcLst());
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
	}
    }

    /**
     * @param responseBuilderParamsDTO
     * @param acntPayData
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, FDAccntPayData acntPayData) {
	MenuItemDTO menuItemDTO = null;
	if (acntPayData != null) {
	    if (acntPayData.getSrcLst() != null && !acntPayData.getSrcLst().isEmpty()) {
		menuItemDTO = new MenuItemDTO();
		int index = 1;
		StringBuilder pageBody = new StringBuilder();

		for (FDApplySourceAccount accountDetail : acntPayData.getSrcLst()) {
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(index++);
		    pageBody.append(USSDConstants.DOT_SEPERATOR);
		    pageBody.append(accountDetail.getMkdActNo());
		}
		menuItemDTO.setPageBody(pageBody.toString());
		// insert the back and home options
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		setNextScreenSequenceNumber(menuItemDTO);
	    }
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }
}
