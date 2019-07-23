package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
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
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitAccount;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitResponse;

public class ProbaseAccountJsonParser implements BmgBaseJsonParser{
	private static final Logger LOGGER = Logger.getLogger(ProbaseAccountJsonParser.class);
	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		ObjectMapper mapper = new ObjectMapper();
		try {
		    OTBPInitResponse otbpInitResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), OTBPInitResponse.class);
		    if (otbpInitResponse != null) {
			if (otbpInitResponse.getPayHdr() != null
				&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(otbpInitResponse.getPayHdr().getResCde())) {
			    Collections.sort(otbpInitResponse.getPayData().getFromAcctList(), new OneTimeBillPayAcctNumComparator());

			    // set the from accnt list to the session
			    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
				    .put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getTranId(), otbpInitResponse.getPayData().getFromAcctList());
			} else if (otbpInitResponse.getPayHdr() != null) {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(otbpInitResponse.getPayHdr().getResCde());
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());

	}
	class OneTimeBillPayAcctNumComparator implements Comparator<OTBPInitAccount>, Serializable {
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
}
