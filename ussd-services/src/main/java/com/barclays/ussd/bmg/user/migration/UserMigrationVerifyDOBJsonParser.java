package com.barclays.ussd.bmg.user.migration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.SelfRegistrationChallengeQnResponse;

public class UserMigrationVerifyDOBJsonParser implements BmgBaseJsonParser {
    private static final int NO_OF_ATTEMPTS = 0;
    private static final Logger LOGGER = Logger.getLogger(UserMigrationVerifyDOBJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    SelfRegistrationChallengeQnResponse selfRegInitResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
		    SelfRegistrationChallengeQnResponse.class);
	    if (selfRegInitResponse != null) {
		if (selfRegInitResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(selfRegInitResponse.getPayHdr().getResCde())) {
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTwoFAattemptCount(NO_OF_ATTEMPTS);
		} else if (selfRegInitResponse.getPayHdr() != null
			&& USSDExceptions.USSD_TOKEN_BLOCKED.getBmgCode().equalsIgnoreCase(selfRegInitResponse.getPayHdr().getResCde())) {
		    throw new USSDBlockingException(USSDExceptions.USSD_INVALID_2FA_SELF_REG.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& USSDExceptions.BEM08729.getBmgCode().equalsIgnoreCase(selfRegInitResponse.getPayHdr().getResCde())) {
		    int twoFAattemptCount = responseBuilderParamsDTO.getUssdSessionMgmt().getTwoFAattemptCount();
		    if (twoFAattemptCount > USSDConstants.MAX_TWO_FACT_ATTEMPTS - 1) {
			throw new USSDBlockingException(USSDExceptions.USSD_FAILED_2FA_LOGOFF.getBmgCode());
		    }
		    throw new USSDNonBlockingException(USSDExceptions.USSD_INVALID_2FA_SELF_REG.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& USSDExceptions.BEM08780.getBmgCode().equalsIgnoreCase(selfRegInitResponse.getPayHdr().getResCde())) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_INVALID_2FA_SELF_REG.getBmgCode());
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
		    throw new USSDNonBlockingException(selfRegInitResponse.getPayHdr().getResCde());
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
	    } else if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
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
}