package com.barclays.ussd.bmg.langpref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.LanguageDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;

public class GetLanguagesRetrieveLangListJsonParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{

    @Autowired
    private UssdMenuBuilder ussdMenuBuilder;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	LanguageDTO langDTO = ussdMenuBuilder.getLanguages(ussdSessionMgmt.getUserProfile().getCountryCode(), ussdSessionMgmt.getUserProfile()
		.getBusinessId());

	if (langDTO != null && langDTO.getLanguages() != null) {
	    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
		responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>(5));
	    }
	    // set the lang list to the session
	    // List<String> languages = langDTO.getLanguages();
	    // for (String lang : languages) {
	    // if (StringUtils.endsWithIgnoreCase(ussdSessionMgmt.getUserProfile().getLanguage(), lang.split(USSDConstants.UNDERSCORE_SEPERATOR)[0]))
	    // {
	    // languages.remove(lang);
	    // break;
	    // }
	    // }
	    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.LANG_PREF_RETRIEVE_LANGUAGE.getTranId(), langDTO);
	    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .put(USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getTranId(), langDTO.getLanguages());
	}
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<String> langList = (List<String>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getTranId());
	if (langList != null && langList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }
}