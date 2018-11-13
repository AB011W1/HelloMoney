package com.barclays.ussd.utils.jsonparsers.welcomebalmain;

import java.util.List;
import java.util.Locale;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class WelcomeScreenBalMainEnqJsonParser implements BmgBaseJsonParser {
    private static final String PRIMARY_INDICATOR = "Y";

    private static final String BUSINESS_ID = "UGBRB";

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuDTO = null;

	;

	menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	USSDSessionManagement sessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();
	AuthUserData userAuthObj = (AuthUserData) sessionManagement.getUserAuthObj();
	Locale locale = getLocale(responseBuilderParamsDTO);
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	List<CustomerMobileRegAcct> custActs = userAuthObj.getPayData().getCustActs();
	MenuItemDTO menuItemDTO = null;
	if (custActs != null) {

	    for (CustomerMobileRegAcct acct : custActs) {
		if (PRIMARY_INDICATOR.equalsIgnoreCase(acct.getPriInd())) {
		    StringBuilder pageBody = new StringBuilder();
		    menuItemDTO = new MenuItemDTO();

		    pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_ACCNT_NO, locale) + acct.getMkdActNo());
		    pageBody.append(USSDConstants.NEW_LINE);

		    if (sessionManagement.getBusinessId().equalsIgnoreCase(BUSINESS_ID)) {
			pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_AVAIL_AC_BAL, locale)
				+ acct.getCurrentBookBalanceAmount().getAmt() + USSDConstants.SINGLE_WHITE_SPACE
				+ acct.getCurrentBookBalanceAmount().getCurr());// currency
			pageBody.append(USSDConstants.NEW_LINE);

			pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_ACTUL_AC_BAL, locale) + acct.getNetBalanceAmount().getAmt()
				+ USSDConstants.SINGLE_WHITE_SPACE + acct.getNetBalanceAmount().getCurr());
			pageBody.append(USSDConstants.NEW_LINE);
		    } else {
			pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_AVAIL_AC_BAL, locale) + acct.getAvblBal().getAmt()
				+ USSDConstants.SINGLE_WHITE_SPACE + acct.getAvblBal().getCurr());
			pageBody.append(USSDConstants.NEW_LINE);

			pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_CURR_AC_BAL, locale) + acct.getCurBal().getAmt()
				+ USSDConstants.SINGLE_WHITE_SPACE + acct.getCurBal().getCurr());
			pageBody.append(USSDConstants.NEW_LINE);
		    }

		    // pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_AVAIL_AC_BAL, locale) + acct.getAvblBal().getAmt()
		    // + USSDConstants.SINGLE_WHITE_SPACE + acct.getAvblBal().getCurr());
		    // pageBody.append(USSDConstants.NEW_LINE);
		    //
		    // pageBody.append(ussdResourceBundle.getLabel(USSDConstants.LBL_CURR_AC_BAL, locale) + acct.getCurBal().getAmt()
		    // + USSDConstants.SINGLE_WHITE_SPACE + acct.getCurBal().getCurr());
		    // pageBody.append(USSDConstants.NEW_LINE);

		    menuItemDTO.setPageBody(pageBody.toString());
		    break;
		}
	    }

	    // insert the back and home options
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    setNextScreenSequenceNumber(menuItemDTO);
	}

	return menuItemDTO;
    }

    private Locale getLocale(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	Locale locale = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	String language = ussdSessionMgmt.getUserProfile().getLanguage();
	String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();

	/*
	 * if (language == null || null == countryCode) { countryLanguage = ussdMenuBuilder.getDefaultLocale(profile.getCountryCode(),
	 * profile.getBusinessId()); locale = new Locale(ConfigurationManager.getString("defaultLanguage"),
	 * ConfigurationManager.getString("defaultCountry")); } else {
	 */
	locale = new Locale(language, countryCode);
	// }

	return locale;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
    }
}
