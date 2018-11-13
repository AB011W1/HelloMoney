package com.barclays.ussd.utils.jsonparsers;

import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.validation.USSDChangePasswordValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;

public class ChangePinReenterNewPassJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator,ScreenSequenceCustomizer {
    private static final Logger LOGGER = Logger.getLogger(ChangePinReenterNewPassJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDChangePasswordValidator(userInputMap
		.get(USSDInputParamsEnum.CHNG_PIN_NEW_PASS.getParamName()), userInput));
	try {
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    // TODO
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug(USSDExceptions.USSD_CHNGE_PIN_PASS_NT_MATCH, e);
	    }
	    e.setErrorCode("USSD_CHNGE_PIN_PASS_NT_MATCH");
	    throw e;
	}

    }
    @Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
		String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
			seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOURTEEN.getSequenceNo();
		}
		return seqNo;
	}


}
