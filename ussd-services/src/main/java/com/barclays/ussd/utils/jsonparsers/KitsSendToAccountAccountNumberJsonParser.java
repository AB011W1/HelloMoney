package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccListPayData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.RetrieveAccList;

public class KitsSendToAccountAccountNumberJsonParser implements BmgBaseJsonParser {

	 /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(KitsSendToAccountAccountNumberJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	String jsonString = responseBuilderParamsDTO.getJsonString();
	ObjectMapper mapper = new ObjectMapper();
	try {
		//AuthUserData accList = (AuthUserData) responseBuilderParamsDTO.getUssdSessionMgmt().getaccList();
		//AuthUserData accList = mapper.readValue(jsonString, AuthUserData.class);
		RetrieveAccList accList = mapper.readValue(jsonString, RetrieveAccList.class);
	    if (accList != null) {
		if (accList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(accList.getPayHdr().getResCde())) {
		    List<AccountDetails> custActs = accList.getPayData().getSrcLst();
		    Collections.sort(custActs, new KITSSTAAccountSummaryCustomerAccountComparator());
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, accList.getPayData());
		} else if (accList.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(accList.getPayHdr().getResCde());
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
     * @param accList
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, AccListPayData acntPayData) {

		MenuItemDTO menuItemDTO = null;
		//AuthenticateUserPayData acntPayData = accList.getPayData();
		if (acntPayData != null) {
			if (acntPayData.getSrcLst() != null && !acntPayData.getSrcLst().isEmpty()) {
				menuItemDTO = new MenuItemDTO();
				int index = 1;
				StringBuilder pageBody = new StringBuilder();
				Map<String, Object> txSessions = new HashMap<String, Object>(acntPayData.getSrcLst().size());

				txSessions.put(USSDInputParamsEnum.KITS_STA_ACCOUNT_NUM.getTranId(), acntPayData.getSrcLst());

				responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
				for (AccountDetails accountDetail : acntPayData.getSrcLst()) {
					pageBody.append(USSDConstants.NEW_LINE);
					pageBody.append(index);
					pageBody.append(USSDConstants.DOT_SEPERATOR);
					pageBody.append(accountDetail.getMkdActNo());
					index++;
				}
				menuItemDTO.setPageBody(pageBody.toString());
				USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
				menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
				menuItemDTO.setPaginationType(PaginationEnum.LISTED);
			}
		}
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }
}

/* This class compares the customer account w.r.t primary indicator*/
	class KITSSTAAccountSummaryCustomerAccountComparator implements Comparator<AccountDetails>, Serializable {

		private static final long serialVersionUID = 1L;

		public int compare(final AccountDetails accountDetail1, final AccountDetails accountDetail2) {
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

