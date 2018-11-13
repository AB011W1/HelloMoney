package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class SelfRegisterDebitExpiryDateParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

    private static final Logger LOGGER = Logger.getLogger(SelfRegisterDebitExpiryDateParser.class);
        private static final String DEBIT_CARD_EXP_DATE_LABEL = "label.selfreg.debitcard.expirydate";
        private static final String DEBIT_CARD_INVA_RAN_LABEL = "label.selfreg.debitcard.invalid.random.number";
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	// TODO Auto-generated method stub
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException,USSDBlockingException{
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	String debitCardLabel="";
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	debitCardLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(DEBIT_CARD_EXP_DATE_LABEL,
				new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	pageBody.append(debitCardLabel);
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	menuItemDTO.setPageBody(pageBody.toString());
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo());

    }
    @Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo();
		String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
			seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_ELEVEN.getSequenceNo();
		}
		return seqNo;
	}
}
