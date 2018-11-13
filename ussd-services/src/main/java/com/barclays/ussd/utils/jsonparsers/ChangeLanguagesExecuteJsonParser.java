package com.barclays.ussd.utils.jsonparsers;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;

public class ChangeLanguagesExecuteJsonParser implements BmgBaseJsonParser {

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException {
	if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase(USSDConstants.BUSINESS_ID_TZNBC)){
		 throw new USSDBlockingException(USSDExceptions.USSD_SELFREGISTRATION_DISABLED.getBmgCode());
	}
    	throw new USSDBlockingException(USSDExceptions.USSD_CHANGE_LANGUAGE.getBmgCode());
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }

}
