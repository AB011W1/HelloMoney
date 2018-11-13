package com.barclays.ussd.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItem;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.session.handler.USSDUserSessionManager;

public class LocaleScreenBuilder {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(LocaleScreenBuilder.class);

    /** The Constant DOT. */
    private static final String NEW_LINE = "\\n";

    @Autowired
    private UssdResourceBundle ussdResourceBundle;

    @Autowired
    USSDUserSessionManager ussdUserSessionManager;

    /**
     * @return UssdResourceBundle
     */
    public UssdResourceBundle getUssdResourceBundle() {
	return ussdResourceBundle;
    }

    // This method sets all the label w.r.t the locale
    public MenuItemDTO getLocaleSpecificResponse(USSDSessionManagement ussdSessionMgmt, String msisdnWithCountry, MenuItemDTO menuItemDTO,
	    String lang, String countryCode) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Customizing the response language w.r.t the locale...");
	}

	String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	String cntryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	boolean sessionCacheEnabled = USSDUtils.isSessionCacheEnabled(businessId, cntryCode);
	if (sessionCacheEnabled && !StringUtils.endsWithIgnoreCase(ussdSessionMgmt.getCustomerType(), USSDConstants.CUSTOMER_TYPE_NON_HM)
		&& !StringUtils.endsWithIgnoreCase(ussdSessionMgmt.getCustomerType(), USSDConstants.CUSTOMER_TYPE_USER_SESSION_RETENTION)) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Set the response to the dynamic cache...");
	    }
	    ussdUserSessionManager.setIntoResponseMap(msisdnWithCountry, menuItemDTO, businessId, cntryCode);
	}
	MenuItemDTO responseDTO = new MenuItemDTO();
	String newFooter = null;
	List<MenuItem> newMenuItemlist = new ArrayList<MenuItem>();
	// setting the label's language depending upon the user preferences.
	if (menuItemDTO != null) {
	    if (menuItemDTO.getMenuItemList() != null) {
		for (MenuItem menuItem : menuItemDTO.getMenuItemList()) {
		    MenuItem item = new MenuItem();
		    // item.setLabelId(ConfigurationManager.getLabel(menuItem.getLabelId(), lang, "", countryCode));
		    item.setLabelId(ussdResourceBundle.getLabel(menuItem.getLabelId(), new Locale(lang, countryCode)));
		    item.setLevel(menuItem.getLevel());
		    item.setOptionId(menuItem.getOptionId());
		    newMenuItemlist.add(item);
		}
	    }
	    if (menuItemDTO.getPageFooter() != null && !menuItemDTO.getPageFooter().trim().equalsIgnoreCase("")) {
		String[] splitedFooter = menuItemDTO.getPageFooter().split(USSDConstants.PIPE_SEPERATOR);
		String params[] = null;
		if (splitedFooter.length > 1) {
		    if (splitedFooter.length == 2) {
			params = new String[1];
			params[0] = splitedFooter[1];
		    } else {
			params = new String[2];
			params[0] = splitedFooter[1];
			params[1] = splitedFooter[2];
		    }
		    newFooter = ussdResourceBundle.getLabel(splitedFooter[0], params, new Locale(lang, countryCode));
		} else {
		    newFooter = ussdResourceBundle.getLabel(menuItemDTO.getPageFooter(), new Locale(lang, countryCode));
		}
		responseDTO.setPageFooter(NEW_LINE + newFooter);
	    }
	    if (StringUtils.isNotEmpty(menuItemDTO.getPageError())) {
		List<String> errorParams = menuItemDTO.getErrorParams();
		if (errorParams != null && errorParams.size() > 0) {
		    String[] errors = errorParams.toArray(new String[errorParams.size()]);
		    String errorMessage = ussdResourceBundle.getErrorLabel(menuItemDTO.getPageError(), errors, new Locale(lang, countryCode));
		    responseDTO.setPageError(errorMessage + NEW_LINE);
		} else {
		    responseDTO.setPageError(ussdResourceBundle.getLabel(menuItemDTO.getPageError(), new Locale(lang, countryCode)) + NEW_LINE);
		    //CR-86 Apply-product list navigation
		    if(ussdSessionMgmt.getUserTransactionDetails() != null &&
		    		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId().equalsIgnoreCase("LG000") ){

		    	responseDTO.setPageError(ussdResourceBundle.getLabel(menuItemDTO.getPageError(), new Locale(lang, countryCode)));
		    }
		}
	    }
	    responseDTO.setPageHeader(ussdResourceBundle.getLabel(menuItemDTO.getPageHeader(), new Locale(lang, countryCode)));
	    responseDTO.setMenuItemList(newMenuItemlist);
	    copyMenuDtoToResponseDto(menuItemDTO, responseDTO);
	} else {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("There was some error occured; response menuItemDTO is null");
	    }
	}
	return responseDTO;
    }

    public void copyMenuDtoToResponseDto(MenuItemDTO menuItemDTO, MenuItemDTO responseDto) {
	responseDto.setNextScreenId(menuItemDTO.getNextScreenId());
	responseDto.setPageBody(menuItemDTO.getPageBody());
	responseDto.setTranId(menuItemDTO.getTranId());
	responseDto.setTranNodeId(menuItemDTO.getTranNodeId());
	responseDto.setStatus(menuItemDTO.getStatus());
	responseDto.setNextScreenNodeId(menuItemDTO.getNextScreenNodeId());
	responseDto.setUserAuthRespMap(menuItemDTO.getUserAuthRespMap());
	responseDto.setPaginationType(menuItemDTO.getPaginationType());
	responseDto.setScrollers(menuItemDTO.getScrollers());
    }

    public void setUssdResourceBundle(UssdResourceBundle ussdResourceBundle) {
	this.ussdResourceBundle = ussdResourceBundle;
    }

    /**
     * @param strLabel
     * @param lang
     * @param countryCode
     * @return String This function returns response in preferred language
     */
    public String getLocaleSpecificResponse(String strLabel, String lang, String countryCode) {
	return ussdResourceBundle.getLabel(strLabel, new Locale(lang, countryCode));
    }

}
