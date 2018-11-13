package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.LanguageDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;

public class ChangeLanguageListJsonParser implements BmgBaseJsonParser {

    @Autowired
    private UssdMenuBuilder ussdMenuBuilder;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	LanguageDTO langDTO = ussdMenuBuilder.getLanguages(ussdSessionMgmt.getUserProfile().getCountryCode(), ussdSessionMgmt.getUserProfile()
		.getBusinessId());

	if (langDTO != null && langDTO.getLanguages() != null) {
	    menuDTO = renderMenuOnScreen(langDTO, responseBuilderParamsDTO);
	    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
		responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>(5));
	    }
	    // set the lang list to the session
	    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.CHANGE_LANGUAGE_GET_LANG_LIST.getTranId(),
		    langDTO.getLanguages());
	}
	return menuDTO;
    }

    /**
     * @param langDTO
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(LanguageDTO langDTO, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	if (langDTO != null && langDTO.getLanguages() != null && !langDTO.getLanguages().isEmpty()) {
	    for (String locale : langDTO.getLanguages()) {
		String[] localeArray = locale.split(USSDConstants.UNDERSCORE_SEPERATOR);
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(ussdResourceBundle.getLabel(langDTO.getDisplayLabelId(), new Locale(localeArray[0], localeArray[1])));
		index++;
	    }
	}
	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    /**
     * @param ussdMenuBuilder
     *            the ussdMenuBuilder to set
     */
    public void setUssdMenuBuilder(UssdMenuBuilder ussdMenuBuilder) {
	this.ussdMenuBuilder = ussdMenuBuilder;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }

}
