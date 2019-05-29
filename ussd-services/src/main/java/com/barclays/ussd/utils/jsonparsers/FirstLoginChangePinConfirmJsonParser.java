package com.barclays.ussd.utils.jsonparsers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.changepin.ChangePinBean;

public class FirstLoginChangePinConfirmJsonParser implements BmgBaseJsonParser {
    private static final Logger LOGGER = Logger.getLogger(FirstLoginChangePinConfirmJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    ChangePinBean changePinObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), ChangePinBean.class);
	    if (changePinObj != null) {
		if (changePinObj.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(changePinObj.getPayHdr().getResCde())) {
		    responseBuilderParamsDTO.getUssdSessionMgmt().setFirstLoginOldPin(null);
		    if (StringUtils.equalsIgnoreCase(USSDConstants.USER_STATUS_MIGRATED, responseBuilderParamsDTO.getUssdSessionMgmt()
			    .getUserProfile().getUsrSta())) {
			// set new message for showing the user that (s)he has been migrated.
			throw new USSDBlockingException(USSDExceptions.USSD_MIGRATED_PIN_CHANGED_RELOGIN.getBmgCode());
		    }
		    throw new USSDBlockingException(USSDExceptions.USSD_PIN_CHANGED_RELOGIN.getBmgCode());
		} else if (changePinObj.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), changePinObj.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} else if (changePinObj.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.BMB90004.getBmgCode(), changePinObj.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.BMB90004.getBmgCode());
		} else if (changePinObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(changePinObj.getPayHdr().getResCde());
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
	    }
	    if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	if(null != menuDTO)
		setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }

}
