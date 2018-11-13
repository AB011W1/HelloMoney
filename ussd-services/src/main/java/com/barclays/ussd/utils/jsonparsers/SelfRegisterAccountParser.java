/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.validation.USSDAccountNoLengthValidator;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;

/**
 * @author BTCI
 *
 */
public class SelfRegisterAccountParser implements BmgBaseJsonParser, SystemPreferenceValidator,ScreenSequenceCustomizer {
    private static final String ACCT_NO_LEN = "ACCTN_NO_LEN";

    @Autowired
    private UssdResourceBundle ussdResourceBundle;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(SelfRegisterAccountParser.class);
    private static final Logger TIME_AUDIT_LOGGER = Logger.getLogger("com.barclays.ussd.audit-time");

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException {
	// Temporary fix to disable Self registration in case of UGBRB
    //commented as a part to enable self registration on UG with debit card validation
	/*	if (responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase(USSDConstants.BUSINESS_ID_UGBRB)) {
	    TIME_AUDIT_LOGGER.debug("user tried to self register");
	    throw new USSDBlockingException(USSDExceptions.USSD_SELFREGISTRATION_DISABLED.getBmgCode());
	}*/

	/*if (responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase(USSDConstants.BUSINESS_ID_TZNBC)) {
		TIME_AUDIT_LOGGER.debug("user tried to self register");
	    throw new USSDBlockingException(USSDExceptions.USSD_SELFREGISTRATION_DISABLED.getBmgCode());
	}
*/
    	/**
    	 * Self Registration is Not in Scope in Mozambique as there is no SPARROW support
    	 */
    	if (responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase(USSDConstants.BUSINESS_ID_MZBRB)) {
    		TIME_AUDIT_LOGGER.debug("user tried to self register");
    	    throw new USSDBlockingException(USSDExceptions.USSD_SELFREGISTRATION_DISABLED.getBmgCode());
    	}
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	USSDCompositeValidator validator = null;

	String accountNoLen = ussdResourceBundle.getLabel(ACCT_NO_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
		.getUserProfile().getCountryCode()));
	validator = new USSDCompositeValidator(new USSDAccountNoLengthValidator(accountNoLen));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	try {
		backFlowValidator.validateAccountNumber(userInput);//CR-86
	    validator.validate("" + userInput.length());
	} catch (USSDNonBlockingException e) {
		 //CR-86
	    e.setBackFlow(true);
	    //e.addErrorParam(userInput);
	    LOGGER.error(USSDExceptions.USSD_INVALID_ACCNO_FPIN_SREG.getUssdErrorCode(), e);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_ACCNO_FPIN_SREG.getUssdErrorCode());
	    throw e;
	}

    }

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
    	String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
    	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
    		seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
    	}
    	return seqNo;
	}

}
