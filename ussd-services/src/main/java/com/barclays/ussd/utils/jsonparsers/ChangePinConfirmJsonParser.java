package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.changepin.ChangePinBean;
import com.barclays.ussd.utils.jsonparsers.bean.changepin.ChangePinPayData;

public class ChangePinConfirmJsonParser implements BmgBaseJsonParser {
    private static final String CONFIRM_LABEL = "label.pin.updated.success";
    private static final Logger LOGGER = Logger.getLogger(ChangePinConfirmJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    ChangePinBean changePinObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), ChangePinBean.class);
	    if (changePinObj != null) {
		if (changePinObj.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(changePinObj.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(changePinObj.getPayData(), responseBuilderParamsDTO);
		} else if (changePinObj.getPayHdr() != null) {
			//Change Pin showing ForgotPin option fixed
			if(changePinObj.getPayHdr().getResCde().equalsIgnoreCase("BEM08729") ||
					changePinObj.getPayHdr().getResCde().equalsIgnoreCase("BEM09027") ||
					changePinObj.getPayHdr().getResCde().equalsIgnoreCase("BEM9431") ) {
				 throw new USSDNonBlockingException(USSDExceptions.USSD_INVALID_PIN_ENTERED.getBmgCode());
			}
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
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}

	return menuDTO;
    }

    /**
     * @param changePinPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ChangePinPayData changePinPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	if (changePinPayData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    String confirmLabel = ussdResourceBundle.getLabel(CONFIRM_LABEL, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(confirmLabel);

	}

	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_END);
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	responseBuilderParamsDTO.getUssdSessionMgmt().clean();
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }

}
