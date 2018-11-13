package com.barclays.ussd.bmg.creditcard.statement;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

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

/**
 * @author BTCI
 * 
 */
public class CcStatDateListJsonParser implements BmgBaseJsonParser {

    @Autowired
    private static final Logger LOGGER = Logger.getLogger(CcStatDateListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
	    CreditCardStmtData creditCardStmtObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), CreditCardStmtData.class);
	    if (creditCardStmtObj != null) {
		if (creditCardStmtObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(creditCardStmtObj.getPayHdr().getResCde())) {
		    List<CreditCardStatement> creditCardStmtList = creditCardStmtObj.getPayData().getCreditCardStmtBalanceInfoJSONModelList();
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, creditCardStmtList);
		} else if (creditCardStmtObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(creditCardStmtObj.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    } else {
		LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
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
     * @param creditCardStmtList
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<CreditCardStatement> creditCardStmtList)
	    throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = null;
	menuItemDTO = new MenuItemDTO();

	if (creditCardStmtList != null && !creditCardStmtList.isEmpty()) {
	    int index = 1;
	    StringBuilder pageBody = new StringBuilder();
	    Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    txSessions.put(USSDInputParamsEnum.CR_CARD_STAT_TRAN_DATE_LIST.getTranId(), creditCardStmtList);
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);

	    for (CreditCardStatement cardDetail : creditCardStmtList) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(cardDetail.getStatementDate());
		index++;
	    }

	    menuItemDTO.setPageBody(pageBody.toString());
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    setNextScreenSequenceNumber(menuItemDTO);
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_STATEMENT_FOUND.getBmgCode());
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }

}
