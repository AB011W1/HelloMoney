package com.barclays.ussd.utils.jsonparsers;

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
import com.barclays.ussd.utils.jsonparsers.bean.twofactauth.TwoFactorResponse;

public class TwoFactorAuthVerifyParser implements BmgBaseJsonParser {

    private static final int NO_OF_ATTEMPTS = 0;
    private static final Logger LOGGER = Logger.getLogger(TwoFactorAuthVerifyParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    TwoFactorResponse twoFactorResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), TwoFactorResponse.class);
	    if (twoFactorResponse != null) {
		if (twoFactorResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(twoFactorResponse.getPayHdr().getResCde())) {
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTwoFAattemptCount(NO_OF_ATTEMPTS);
		} else if (twoFactorResponse.getPayHdr() != null
			&& USSDExceptions.USSD_TOKEN_BLOCKED.getBmgCode().equalsIgnoreCase(twoFactorResponse.getPayHdr().getResCde())) {
		    throw new USSDBlockingException(USSDExceptions.USSD_INVALID_2FA.getBmgCode());
		} else if (twoFactorResponse.getPayHdr() != null
			&& USSDExceptions.BEM08729.getBmgCode().equalsIgnoreCase(twoFactorResponse.getPayHdr().getResCde())) {
		    int twoFAattemptCount = responseBuilderParamsDTO.getUssdSessionMgmt().getTwoFAattemptCount();
		    if (twoFAattemptCount > USSDConstants.MAX_TWO_FACT_ATTEMPTS - 1) {
			throw new USSDBlockingException(USSDExceptions.USSD_FAILED_2FA_LOGOFF.getBmgCode());
		    }
		    throw new USSDNonBlockingException(USSDExceptions.USSD_INVALID_2FA.getBmgCode());
		} else if (twoFactorResponse.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(twoFactorResponse.getPayHdr().getResCde());
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
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }
}
