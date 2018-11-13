package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitAccount;

public class OneTimeBillPayAcctNumJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(OneTimeBillPayAcctNumJsonParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	//for Kadikope
	Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	if(txSessions.containsKey(USSDConstants.CREDIT_CARD_FT_ONETIME_CASA)){
		txSessions.remove(USSDConstants.CREDIT_CARD_FT_ONETIME_CASA);
	}
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	try {
	    List<OTBPInitAccount> accList = (List<OTBPInitAccount>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getTranId());
	    menuDTO = renderMenuOnScreen(accList, responseBuilderParamsDTO);
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
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(List<OTBPInitAccount> payData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	if (payData != null) {
	    for (OTBPInitAccount acctDet : payData) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index++);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(acctDet.getMkdActNo());
		menuItemDTO.setPageBody(pageBody.toString());
	    }
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo());

    }

}

/* This class compares the customer account w.r.t primary indicator */
class OneTimeBillPayAcctNumJsonParserComparator implements Comparator<OTBPInitAccount>, Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public int compare(final OTBPInitAccount accountDetail1, final OTBPInitAccount accountDetail2) {
	int retVal = 0;
	if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail1.getPriInd())) {
	    retVal = -1;
	} else if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail2.getPriInd())) {
	    retVal = 1;
	} else {
	    retVal = 0;
	}
	return retVal;
    }
}