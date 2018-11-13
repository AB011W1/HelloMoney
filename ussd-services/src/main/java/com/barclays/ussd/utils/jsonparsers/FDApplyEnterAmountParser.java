/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDBlockingExceptions;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDProduct;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDRates;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
public class FDApplyEnterAmountParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(FDViewEnterAmountParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	ObjectMapper mapper = new ObjectMapper();
	MenuItemDTO menuItemDTO = null;
	List<FDProduct> fdProductDetails = null;

	try {
	    FDRates rates = mapper.readValue(jsonString, FDRates.class);
	    PayHdr payHdr = rates.getPayHdr();
	    if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		menuItemDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		fdProductDetails = rates.getPayData().getIntRatesList();
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
	if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>());
	}
	responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.FD_APPLY_ENTER_AMT.getTranId(), fdProductDetails);
	return menuItemDTO;
    }

    /**
     * 
     * @param responseBuilderParamsDTO
     * @param tenures
     * @return
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }

}
