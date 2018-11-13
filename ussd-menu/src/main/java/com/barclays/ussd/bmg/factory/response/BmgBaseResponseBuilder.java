package com.barclays.ussd.bmg.factory.response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.bean.CurrentRunningTransaction;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseResponse;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.IUSSDJsonParserFactory;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.jsonparsers.bean.login.LoggerData;

public class BmgBaseResponseBuilder {
    private static final String TRUE = "true";

    private static final Logger LOGGER = Logger.getLogger(BmgBaseResponseBuilder.class);

    @Autowired
    IUSSDJsonParserFactory ussdJsonParserFactory;

    /**
     * @param ussdJsonParserFactory
     *            the ussdJsonParserFactory to set
     */
    public void setUssdJsonParserFactory(IUSSDJsonParserFactory ussdJsonParserFactory) {
	this.ussdJsonParserFactory = ussdJsonParserFactory;
    }

    public USSDBaseResponse getResponseObject(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException,
	    USSDNonBlockingException {
	USSDBaseResponse response = new USSDBaseResponse();

	printLog(responseBuilderParamsDTO);

	// get the respective json parser as per the bmgopcode
	BmgBaseJsonParser bmgBaseJsonParser = ussdJsonParserFactory.getParser(responseBuilderParamsDTO);

	CurrentRunningTransaction currentRunningTransaction = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails()
		.getCurrentRunningTransaction();
	boolean skipNode = isSkippedNode(currentRunningTransaction.getSkipNode());
	MenuItemDTO menuItemDTO = null;

	if (!skipNode) {
	    // parse the json and populate MenuItemDTO for menu render on the phone
	    menuItemDTO = bmgBaseJsonParser.parseJsonIntoJava(responseBuilderParamsDTO);
	}
	if (menuItemDTO == null && !StringUtils.equals(responseBuilderParamsDTO.getTranDataOpCode(), USSDConstants.NEGATE_UI_RENDERING)) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}
	currentRunningTransaction.setNextScreenSequenceNumber(menuItemDTO.getNextScreenSequenceNumber());
	if (StringUtils.equals(responseBuilderParamsDTO.getTranDataOpCode(), USSDConstants.NEGATE_UI_RENDERING)) {
	    response.setMenuItemDTO(null);
	} else {
	    response.setMenuItemDTO(menuItemDTO);
	}
	return response;
    }

    private boolean isSkippedNode(String skipNode) {
	if (skipNode != null && StringUtils.equalsIgnoreCase(skipNode, TRUE)) {
	    return true;
	} else {
	    return false;
	}
    }

    private void printLog(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	StringBuilder bmgOpCode = new StringBuilder("OPCODE=").append(responseBuilderParamsDTO.getBmgOpCode());
	String jsonString = responseBuilderParamsDTO.getJsonString();

	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info(bmgOpCode.toString());
	}

	if (jsonString != null && StringUtils.isNotEmpty(jsonString)) {
	    ObjectMapper mapper = new ObjectMapper();
	    try {
		LoggerData loggerObj = mapper.readValue(jsonString, LoggerData.class);
		StringBuilder resCode = new StringBuilder("RESCODE=").append(loggerObj.getPayHdr().getResCde());

		if (LOGGER.isInfoEnabled()) {
		    LOGGER.info(resCode.toString());
		}
	    } catch (Exception e) {
		LOGGER.error(e.getMessage(), e);
	    }
	}

    }

}
