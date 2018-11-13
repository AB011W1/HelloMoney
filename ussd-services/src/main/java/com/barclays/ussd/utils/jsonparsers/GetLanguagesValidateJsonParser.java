package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.LanguageDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;

public class GetLanguagesValidateJsonParser implements BmgBaseJsonParser {

    @Autowired
    private UssdMenuBuilder ussdMenuBuilder;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
	if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>(5));
	}
	List<String> txnRefNo = new ArrayList<String>(1);
	txnRefNo.add("1");
	responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.LANG_PREF_CONFIRM.getTranId(), txnRefNo);
	return menuDTO;
    }

    /**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {

	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInpMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	String selectedLocale = userInpMap.get(USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getParamName());
	LanguageDTO langDTO = ussdMenuBuilder.getLanguages(ussdSessionMgmt.getUserProfile().getCountryCode(), ussdSessionMgmt.getUserProfile()
		.getBusinessId());

	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.confirm",
		new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	if (selectedLocale != null && !selectedLocale.equalsIgnoreCase("")) {
	    String[] localeArray = selectedLocale.split(USSDConstants.UNDERSCORE_SEPERATOR);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(ussdResourceBundle.getLabel(langDTO.getDisplayLabelId(), new Locale(localeArray[0], localeArray[1])));

	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }
	}

	menuItemDTO.setPageBody(pageBody.toString());
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

}
