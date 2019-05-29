/**
 * BillPayFromAcntLstJsonParser.java
 */
package com.barclays.ussd.bmg.creditcard.third.party.payment;

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
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
public class CCThirdPartyPayFrmAccJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CCThirdPartyPayFrmAccJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    AuthUserData creditCardList = mapper.readValue(responseBuilderParamsDTO.getJsonString(), AuthUserData.class);

	    if (creditCardList != null) {
		if ((creditCardList.getPayHdr() != null)
			&& (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(creditCardList.getPayHdr().getResCde()))) {
		    menuDTO = renderMenuOnScreen(creditCardList.getPayData().getCustActs(), responseBuilderParamsDTO);
		    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>(5));
		    }
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_FROM_ACNT.getTranId(),
			    creditCardList.getPayData().getCustActs());
		} else if (creditCardList.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(creditCardList.getPayHdr().getResCde());
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

    private MenuItemDTO renderMenuOnScreen(List<CustomerMobileRegAcct> list, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();

	if ((list != null) && (!list.isEmpty())) {
		 for(int i =0;i<list.size();i++)
		    	if(list.get(i).getGroupWalletIndicator()!=null && list.get(i).getGroupWalletIndicator().equals("Y"))
		    		list.remove(i);
		if (list == null || list.isEmpty() || list.size() == 0) {
			   throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}
	    for (CustomerMobileRegAcct accountDetail : list) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(accountDetail.getMkdActNo());
		index++;
	    }
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }

}
