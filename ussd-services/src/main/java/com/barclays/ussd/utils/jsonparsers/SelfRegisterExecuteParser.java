/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.SelfRegistrationChallengeQnResponse;

/**
 * @author BTCI
 *
 */
public class SelfRegisterExecuteParser implements BmgBaseJsonParser {
    private static final Logger LOGGER = Logger.getLogger(SelfRegisterExecuteParser.class);
    private static final String USER_ACCOUNT_INVALID = "BEM06754";

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	ObjectMapper mapper = new ObjectMapper();

	try {
	    SelfRegistrationChallengeQnResponse selfRegInitResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
		    SelfRegistrationChallengeQnResponse.class);
	    if (selfRegInitResponse != null) {
		if (selfRegInitResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(selfRegInitResponse.getPayHdr().getResCde())) {
		    LOGGER.debug("User created successfully");
		    throw new USSDBlockingException(USSDExceptions.USSD_SELF_REG_SUCCESS.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& USSDExceptions.BEM08784.getBmgCode().equalsIgnoreCase(selfRegInitResponse.getPayHdr().getResCde())) {
		    throw new USSDBlockingException(USSDExceptions.BEM08784.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& USSDExceptions.BEM08792.getBmgCode().equalsIgnoreCase(selfRegInitResponse.getPayHdr().getResCde())) {
		    throw new USSDBlockingException(USSDExceptions.BEM08792.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.BMB90004.getBmgCode(), selfRegInitResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.BMB90004.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), selfRegInitResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.BEM06001.getBmgCode(), selfRegInitResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.BEM06001.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(selfRegInitResponse.getPayHdr().getResCde());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USER_ACCOUNT_INVALID, selfRegInitResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.SELF_REG_INVALID_ACCOUNT.getBmgCode());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    } else {
		LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }

	/*@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo();
		String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
			seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo();
		}
		return seqNo;
	}*/

}
