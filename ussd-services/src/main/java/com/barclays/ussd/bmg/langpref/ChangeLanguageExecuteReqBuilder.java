/**
 * OthBnkEnterAmount.java
 */
package com.barclays.ussd.bmg.langpref;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI
 * 
 */
public class ChangeLanguageExecuteReqBuilder implements BmgBaseRequestBuilder {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ChangeLanguageExecuteReqBuilder.class);

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	List<String> langList = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.CHANGE_LANGUAGE_GET_LANG_LIST.getTranId());
	String locale = langList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.CHANGE_LANGUAGE_GET_LANG_LIST.getParamName())) - 1);

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("SELECTED LOCALE =" + locale);
	}

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	String selectedLanguage = locale.split(USSDConstants.UNDERSCORE_SEPERATOR)[0];
	if (StringUtils.isEmpty(selectedLanguage)) {
	    selectedLanguage = "EN"; // set default language as EN. Added for cases where we will not have preferred language selection
	}
	ussdSessionMgmt.getUserProfile().setLanguage(selectedLanguage);
	ussdSessionMgmt.setUnregCustLangPref(selectedLanguage);

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("User profile language=" + ussdSessionMgmt.getUserProfile().getLanguage());
	}

	return request;
    }
}
