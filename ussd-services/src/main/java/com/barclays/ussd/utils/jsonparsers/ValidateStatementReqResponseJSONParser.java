package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.PayHeader;
import com.barclays.ussd.utils.jsonparsers.bean.request.statementReq.ValidateStatementRequestJSON;

public class ValidateStatementReqResponseJSONParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(ValidateStatementReqResponseJSONParser.class);

    /**
     * This class is not going to have any screen to render. Just set the txn ref number in the list and call the next request. Even sending back a
     * null MenuItemDTO is not a problem.
     * 
     * @throws USSDNonBlockingException
     * */
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	ObjectMapper mapper = new ObjectMapper();
	MenuItemDTO menuItemDTO = new MenuItemDTO();

	try {
	    ValidateStatementRequestJSON validateStmntReq = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
		    ValidateStatementRequestJSON.class);
	    if (validateStmntReq != null) {
		PayHeader payHdr = validateStmntReq.getPayHdr();
		if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {

		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(validateStmntReq.getPayData().getTxnRefNo());
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.STMT_REQ_CONFIRM.getTranId(), txnRefNo);
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
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }
}