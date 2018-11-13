package com.barclays.ussd.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.response.ResponseMessage;
import com.barclays.ussd.bean.LocaleDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.sessionMgmt.USSDSessionHandler;
import com.barclays.ussd.utils.ExceptionUtils;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;

public class USSDAbstractController {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(USSDAbstractController.class);

    @Autowired
    protected USSDSessionHandler sessionHandler;

    /** The ussd resource bundle. */
    @Autowired
    protected UssdResourceBundle ussdResourceBundle;

    /** The ussd resource bundle. */
    @Autowired
    protected UssdMenuBuilder ussdMenuBuilder;

    /** The response msg. */
    @Autowired
    protected ResponseMessage responseMsg;

    /**
     * Handle ussd blocking exception.
     *
     * @param ube
     *            the ube
     * @param request
     *            the request
     * @return String
     */

    @ExceptionHandler(USSDBlockingException.class)
    @ResponseBody
    public String handleUSSDBlockingException(final USSDBlockingException ube, final HttpServletRequest request) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Handling the Blocking exception ");
	}

	String response = null;
	USSDRequest ussdRequest = (USSDRequest) request.getAttribute(USSDConstants.REQOBJ);
	String msisdnNumber = ussdRequest.getMsisdn();
	HttpSession httpSession = sessionHandler.getSessionRequest(request);
	LocaleDTO defaultLocaleDTO = ussdMenuBuilder.getDefaultLocaleDTO(USSDUtils.getCountryCode(ussdRequest), ussdRequest.getBusinessId());
	if (httpSession != null) {
	    final USSDSessionManagement ussdSessionMgmt = (USSDSessionManagement) httpSession.getAttribute(msisdnNumber);
	    if (ussdSessionMgmt != null) {
	    	response = ExceptionUtils.handleBlockingException(ube, ussdResourceBundle, ussdSessionMgmt.getCountryCode(), ussdSessionMgmt
	    			.getUserProfile().getLanguage());
	    	//CR-77 start
	    	if (ube.getErrCode().equalsIgnoreCase("USSD_TOKEN_BLOCKED") && !ussdSessionMgmt.getUserProfile().getCustomerFirstName().equalsIgnoreCase(" ")){
	    		String customerFrstName = ussdSessionMgmt.getUserProfile().getCustomerFirstName();
	    		response = customerFrstName+ "," + ExceptionUtils.handleBlockingException(ube, ussdResourceBundle, ussdSessionMgmt.getCountryCode(), ussdSessionMgmt
	    				.getUserProfile().getLanguage());
	    	}
	    	//End
	    } else {
		response = ExceptionUtils.handleBlockingException(ube, ussdResourceBundle, defaultLocaleDTO.getCountry(), defaultLocaleDTO
			.getLanguage());
	    }
	} else {
	    response = ExceptionUtils.handleBlockingException(ube, ussdResourceBundle, defaultLocaleDTO.getCountry(), defaultLocaleDTO.getLanguage());
	}
	sessionHandler.removeSession(request);

	return responseMsg.generateXmlRespBlockingException(request, response, ussdRequest.getMsisdnWithCountry());
	// return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleGenericExceptions(final Exception excp, final HttpServletRequest request) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Handling the generic blocking exception");
	}

	String response = null;
	USSDRequest ussdRequest = (USSDRequest) request.getAttribute(USSDConstants.REQOBJ);
	String msisdnNumber = ussdRequest.getMsisdn();
	HttpSession httpSession = sessionHandler.getSessionRequest(request);
	LocaleDTO defaultLocaleDTO = ussdMenuBuilder.getDefaultLocaleDTO(USSDUtils.getCountryCode(ussdRequest), ussdRequest.getBusinessId());
	if (httpSession != null) {
	    final USSDSessionManagement ussdSessionMgmt = (USSDSessionManagement) httpSession.getAttribute(msisdnNumber);
	    if (ussdSessionMgmt != null) {
		response = handleGenericException(excp, ussdResourceBundle, ussdSessionMgmt.getCountryCode(), ussdSessionMgmt.getUserProfile()
			.getLanguage());
	    } else {
		response = handleGenericException(excp, ussdResourceBundle, defaultLocaleDTO.getCountry(), defaultLocaleDTO.getLanguage());
	    }
	} else {
	    response = handleGenericException(excp, ussdResourceBundle, defaultLocaleDTO.getCountry(), defaultLocaleDTO.getLanguage());
	}
	String generateXmlRespBlockingException = responseMsg.generateXmlRespBlockingException(request, response, ussdRequest.getMsisdnWithCountry());

	sessionHandler.removeSession(request);

	return generateXmlRespBlockingException;
    }

    public static String handleGenericException(Exception ube, UssdResourceBundle ussdResourceBundle, String countryCode, String lang) {
	Locale locale = null;
	if (StringUtils.isNotEmpty(countryCode) && StringUtils.isNotEmpty(lang)) {
	    locale = new Locale(lang, countryCode);
	}

	return ussdResourceBundle.getLabel(USSDConstants.GENERIC_ERROR_CODE, locale);
    }

}
