package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.bmg.fundtransfer.internal.editbenf.EditBenfInternalGetAccNoJsonParser;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.validation.USSDAccountNoLengthValidator;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDLengthValidator;

public class IntNonRegFTEnterActJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer,SystemPreferenceValidator {
	@Resource(name = "branchCodeCountryList")
	private List<String> branchCodeCountryList;
	private static final String ACCT_NO_LEN = "ACCTN_NO_LEN";
	@Autowired
	private UssdResourceBundle ussdResourceBundle;
	private static final Logger LOGGER = Logger.getLogger(EditBenfInternalGetAccNoJsonParser.class);
	private String transNodeId;
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		transNodeId=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		MenuItemDTO menuDTO = new MenuItemDTO();
		menuDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
		menuDTO.setPaginationType(PaginationEnum.NOT_REQD);
		setNextScreenSequenceNumber(menuDTO);
		return menuDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

	}
	//CR#48 implementation
	@Override
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
		
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
		if(businessId.equalsIgnoreCase("TZBRB") && transNodeId.equals("ussd0.3.3.2"))
		{
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
		}
		else if(businessId.equalsIgnoreCase("TZBRB"))
		{
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
		}
		return seqNo;
	}

	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
		String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
		if((businessId.equalsIgnoreCase("BWBRB") && transNodeId.equals("ussd0.3.3.2")) || 
		   (businessId.equalsIgnoreCase("ZMBRB") && transNodeId.equals("ussd4.3.3.2")) ||
		   (businessId.equalsIgnoreCase("TZBRB") && transNodeId.equals("ussd0.3.3.2"))) {
			USSDBackFlowValidator backFlowAccnoValidator = new USSDBackFlowValidator();
			try {
				backFlowAccnoValidator.validateAccountNumber(userInput);//CR-86

			} catch (USSDNonBlockingException e) {
				e.setBackFlow(true);
				e.addErrorParam(userInput);
			    e.setErrorCode(USSDExceptions.USSD_USER_INPUT_INVALID_ACCNO_REGBENF.getUssdErrorCode());
			    throw e;
			}
		}
		else
		{
			USSDCompositeValidator validator = null;
			String accountNoLen = ussdResourceBundle.getLabel(ACCT_NO_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
					.getUserProfile().getCountryCode()));
			USSDBackFlowValidator backFlowAccnoValidator = new USSDBackFlowValidator();//CR-86
			validator = new USSDCompositeValidator(new USSDAccountNoLengthValidator(accountNoLen));
			try {
				backFlowAccnoValidator.validateAccountNumber(userInput);//CR-86
				validator.validate("" + userInput.length());
			} catch (USSDNonBlockingException e) {
				//CR-86 changes
				e.setBackFlow(true);
				e.addErrorParam(userInput);
			    e.setErrorCode(USSDExceptions.USSD_USER_INPUT_INVALID_ACCNO_REGBENF.getUssdErrorCode());
				throw e;
			}
		}
			
	}
}