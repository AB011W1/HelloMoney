package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTBeneficiary;

public class GhipsDelBenfConfirmJsonParser implements BmgBaseJsonParser {

    private static final String CONFIRM_LABEL = "label.confirm";
    private static final String NICKNAME_LABEL = "label.nickname";
    private static final Logger LOGGER = Logger.getLogger(GhipsDelBenfConfirmJsonParser.class);

    @SuppressWarnings("unchecked")
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	List<OBAFTBeneficiary> bnfLst = (List<OBAFTBeneficiary>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		.get(USSDInputParamsEnum.GHIPS_DELETE_BENEF_PAYEE.getTranId());
	if (bnfLst == null || bnfLst.isEmpty()) {
	    LOGGER.error(" Beneficiary List is empty ");
	    throw new USSDNonBlockingException(USSDExceptions.TEMP_FAIL.getUssdErrorCode());
	} else {
	    menuDTO = renderMenuOnScreen(bnfLst, responseBuilderParamsDTO);
	}
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    /**
     * @param bnfLst
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(List<OBAFTBeneficiary> bnfLst, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	if (bnfLst != null && !bnfLst.isEmpty()) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
	    String bnfListUsrInp = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap()
		    .get(USSDInputParamsEnum.GHIPS_DELETE_BENEF_PAYEE.getParamName());
	    OBAFTBeneficiary benef = bnfLst.get(Integer.parseInt(bnfListUsrInp) - 1);
	    String nickNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(NICKNAME_LABEL, locale);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(nickNameLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(benef.getDisLbl());
	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CONFIRM_LABEL, locale));
	    }
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }
}
