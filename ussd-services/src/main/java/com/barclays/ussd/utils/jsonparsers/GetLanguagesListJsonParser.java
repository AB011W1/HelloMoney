package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.LanguageDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;

public class GetLanguagesListJsonParser implements BmgBaseJsonParser {

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	LanguageDTO langDTO = (LanguageDTO) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.LANG_PREF_RETRIEVE_LANGUAGE.getTranId());

	menuDTO = renderMenuOnScreen(langDTO, responseBuilderParamsDTO);
	return menuDTO;
    }

    /**
     * @param langDTO
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(LanguageDTO langDTO, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	List<String> langList = (List<String>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		.get(USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getTranId());
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	if (langList != null && !langList.isEmpty()) {
	    for (String locale : langList) {
		String[] localeArray = locale.split(USSDConstants.UNDERSCORE_SEPERATOR);
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(ussdResourceBundle.getLabel(langDTO.getDisplayLabelId(), new Locale(localeArray[0], localeArray[1])));
		index++;
	    }
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_LANGUAGE_FOUND.getBmgCode());
	}
	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
    }

}
