package com.barclays.ussd.utils.jsonparsers;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDBlockingExceptions;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDApplyValidateResponse;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

public class FDApplyValidateParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(FDApplyValidateParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    FDApplyValidateResponse fdApplyValidateResponse = mapper.readValue(jsonString, FDApplyValidateResponse.class);
	    PayHdr payHdr = fdApplyValidateResponse.getPayHdr();
	    if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDConstants.TXN,
			fdApplyValidateResponse.getPayData().getTxnRefNo());
	    } else if (payHdr != null) {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(payHdr.getResCde());
	    } else {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }

	} catch (JsonParseException e) {
	    LOGGER.fatal("JsonParseException : " + e.getMessage());
	    throw new USSDNonBlockingException(USSDBlockingExceptions.PARSER_ERROR.getErrorCode(), e.getMessage());
	} catch (JsonMappingException e) {
	    LOGGER.fatal("JsonMappingException : " + e.getMessage());
	    throw new USSDNonBlockingException(USSDBlockingExceptions.PARSER_ERROR.getErrorCode(), e.getMessage());
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());

    }

}
