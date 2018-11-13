/*
 * ExceptionUtils.java
 */
package com.barclays.ussd.utils;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.auth.response.ResponseMessage;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;

/**
 * The Class ExceptionUtils.
 * 
 * @author BTCI This is exception utils class
 */
public final class ExceptionUtils {

    private ExceptionUtils() {
	// TODO Auto-generated constructor stub
    }

    /**
     * Handle non blocking exception.
     * 
     * @param nbe
     *            the nbe
     * @param menuItemDTO
     *            the menu item dto
     */
    public static void handleNonBlockingException(USSDNonBlockingException nbe, MenuItemDTO menuItemDTO) {
	String errorCode = nbe.getErrorCode();
	menuItemDTO.setPageError(errorCode);
    }

    /**
     * Handle non blocking exception.
     * 
     * @param nbe
     *            the nbe
     * @param ussdResourceBundle
     *            the ussd resource bundle
     * @param countryCode
     *            the country code
     * @param lang
     *            the lang
     * @return the string
     */
    public static String handleNonBlockingException(USSDNonBlockingException nbe, UssdResourceBundle ussdResourceBundle, String countryCode,
	    String lang) {
	String strLabel = ussdResourceBundle.getErrorLabel(nbe.getErrorCode(), new Locale(lang, countryCode));
	if (strLabel == null || StringUtils.isEmpty(strLabel)) {
	    strLabel = ussdResourceBundle.getLabel(USSDConstants.GENERIC_ERROR_CODE, new Locale(lang, countryCode));
	}
	// return new ResponseMessage().responseMessageFromMenuList(strLabel, USSDConstants.STATUS_CONTINUE);
	return strLabel;
    }

    // public static String handle
    /**
     * Handle blocking exception.
     * 
     * @param ube
     *            the ube
     * @param ussdResourceBundle
     *            the ussd resource bundle
     * @param countryCode
     *            the country code
     * @param lang
     *            the lang
     * @return String
     */
    public static String handleBlockingException(USSDBlockingException ube, UssdResourceBundle ussdResourceBundle, String countryCode, String lang) {
	Locale locale = null;
	if (StringUtils.isNotEmpty(countryCode) && StringUtils.isNotEmpty(lang)) {
	    locale = new Locale(lang, countryCode);
	}
	String errCode = ube.getErrCode();
	String errorCodefrmConstant = USSDExceptions.getUssdErrorCodeforBMG(errCode);

	if (errorCodefrmConstant == null || StringUtils.isEmpty(errorCodefrmConstant)) {
	    errorCodefrmConstant = USSDConstants.GENERIC_ERROR_CODE;
	}
	// USSDXMLResponseBody responseBody = new USSDXMLResponseBody();
	// responseBody.setStatus("end");
	// responseBody.setText(ussdResourceBundle.getLabel(errorCodefrmConstant, locale));
	// USSDXMLResponseHeader responseHeader = new USSDXMLResponseHeader();
	// responseHeader.setMsisdn("UNKNOWN");
	// responseHeader.setNonceValue("UNKNOWN");
	// responseHeader.setSession("UNKNOWN");
	return ussdResourceBundle.getErrorLabel(errorCodefrmConstant, locale);
	// return responseMessage.getXmlResponseString(request, responseBody, responseHeader);
	// return responseMessage.responseMessageFromMenuList(ussdResourceBundle.getLabel(errorCodefrmConstant, locale),
	// USSDConstants.STATUS_END);
    }

    /**
     * Handle blocking exception.
     * 
     * @param ube
     *            the ube
     * @param ussdResourceBundle
     *            the ussd resource bundle
     * @param countryCode
     *            the country code
     * @param lang
     *            the lang
     * @return String
     */
    public static String handleBlockingException(USSDBlockingException ube) {
	String errorCodefrmConstant = USSDExceptions.getUssdErrorCodeforBMG(ube.getErrCode());

	if (errorCodefrmConstant == null || StringUtils.isEmpty(errorCodefrmConstant)) {
	    errorCodefrmConstant = USSDConstants.GENERIC_ERROR_CODE;
	}
	// return responseMessage.responseMessageFromMenuList(ConfigurationManager.getString(errorCodefrmConstant), USSDConstants.STATUS_END);
	return ConfigurationManager.getString(errorCodefrmConstant);
    }

    /**
     * Handle the Exception like null point Exception and other Exception
     * 
     * @param e
     * @param ussdResourceBundle
     * @param countryCode
     * @param lang
     * @return
     */
    public static String handlerException(Exception e, MenuItemDTO menuItemDTO, UssdResourceBundle ussdResourceBundle, String countryCode, String lang) {
	Locale locale = null;
	if (StringUtils.isNotEmpty(countryCode) && StringUtils.isNotEmpty(lang)) {
	    locale = new Locale(lang, countryCode);
	}

	return new ResponseMessage().responseMessageFromMenuList(ussdResourceBundle.getLabel("", locale), USSDConstants.STATUS_END);
    }
}
