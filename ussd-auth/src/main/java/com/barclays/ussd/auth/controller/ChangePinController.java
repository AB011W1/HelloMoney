package com.barclays.ussd.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.auth.response.ResponseMessage;
import com.barclays.ussd.auth.response.USSDResponsePagination;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.controller.USSDAbstractController;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.parser.UssdDecisionParserParamDTO;
import com.barclays.ussd.utils.LocaleScreenBuilder;
import com.barclays.ussd.utils.USSDBlockingExceptions;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.UssdParser;
import com.barclays.ussd.xmlresponse.USSDXMLResponseBody;
import com.barclays.ussd.xmlresponse.USSDXMLResponseHeader;

/**
 * The Class BaseController.
 */
@Controller
public class ChangePinController extends USSDAbstractController {
    private static final String FIRST_LOGIN_CHANGE_PIN_SERVICE = "FirstLoginChangePin";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ChangePinController.class);

    /** The Constant FORWARD_SESSION_INVALID. */
    private static final String FORWARD_SESSION_INVALID = ConfigurationManager.getString("forwardSessionInvld");

    /** The forward auth controller. */
    private final String FORWARD_AUTH_CONTROLLER = ConfigurationManager.getString("forwardAuth");

    @Autowired
    private USSDByPassXMLMenuManager ussdByPassXMLMenuManager;

    @Autowired
    private LocaleScreenBuilder localeScreenBuilder;

    @Autowired
    UssdParser ussdParser;

    /**
     * Handler.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the string
     * @throws Exception
     */

    @RequestMapping(value = "/changePin", method = { RequestMethod.POST })
    @ResponseBody
    public String handler(HttpServletRequest request, HttpServletResponse response) throws Exception {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("ChangePINController Base handler starts...");
	}

	String msisdn = null;
	String inputTxt = null;
	HttpSession httpSession = null;
	boolean isNullTxt = false;
	USSDSessionManagement ussdSessionMgmt = null;
	try {
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setCharacterEncoding("UTF-8");
	    USSDRequest ussdRequest = (USSDRequest) request.getAttribute(USSDConstants.REQOBJ);
	    msisdn = ussdRequest.getMsisdn();
	    inputTxt = ussdRequest.getInput();
	    String msisdnWithCountry = ussdRequest.getMsisdnWithCountry();
	    isNullTxt = (inputTxt == null) || "null".equals(inputTxt);
	    request.setAttribute("optionId", inputTxt);

	    httpSession = sessionHandler.getSessionRequest(request);

	    if (null != httpSession) {
		if (isNullTxt) {
		    sessionHandler.removeSession(request);
		    if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Session has been removed for the user " + msisdn + " in the Change Pin controller");
		    }
		    return FORWARD_AUTH_CONTROLLER;
		}

		ussdSessionMgmt = (USSDSessionManagement) httpSession.getAttribute(msisdn);
	    }

	    if (null == ussdSessionMgmt) {
		throw new USSDBlockingException(USSDBlockingExceptions.AUTHENTICATION_ERROR.getErrorCode());
	    }

	    List<String> errorCodes = new ArrayList<String>();
	    UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	    UssdDecisionParserParamDTO paramDTO = getUssdDecisionParserParam(msisdn, userProfile, inputTxt);
	    MenuItemDTO localeSpecificDTO = null;

	    String firstLoginOldPin = ussdSessionMgmt.getFirstLoginOldPin();
	    if (firstLoginOldPin == null || StringUtils.isEmpty(firstLoginOldPin)) {
		firstLoginOldPin = inputTxt;
		ussdSessionMgmt.setFirstLoginOldPin(firstLoginOldPin);
	    }

	    MenuItemDTO menuItemDTO = ussdByPassXMLMenuManager.handleChangePinRequest(paramDTO, ussdSessionMgmt, errorCodes);

	    // get the locale specific response as per the lang code
	    if (menuItemDTO != null && menuItemDTO.getTranId() == null) {
		localeSpecificDTO = localeScreenBuilder.getLocaleSpecificResponse(ussdSessionMgmt, ussdRequest.getMsisdnWithCountry(), menuItemDTO,
			userProfile.getLanguage(), userProfile.getCountryCode());

		NavigationOptionsDTO navigationOptions = ussdParser.getNavigationOptions(userProfile.getBusinessId(), userProfile.getCountryCode());

		// Apply the pagination if required
		MenuItemDTO pagedDTO = new USSDResponsePagination().handlePagination(localeSpecificDTO, ussdSessionMgmt,
			this.localeScreenBuilder.getUssdResourceBundle(), navigationOptions);

		/*
		 * return new ResponseMessage().responseMessageFromMenuList(pagedDTO, this.localeScreenBuilder.getUssdResourceBundle(), userProfile
		 * .getLanguage(), userProfile.getCountryCode());
		 */
		return xmlResponseStringFromMenuDTO(request, ussdSessionMgmt, userProfile.getLanguage(), userProfile.getCountryCode(), pagedDTO,
			msisdnWithCountry);

	    }
	} catch (USSDBlockingException ube) {
	    LOGGER.error("Blocking Exception occured for the User  : " + msisdn, ube);
	    throw ube;
	} catch (final IllegalStateException ilste) {
	    LOGGER.fatal("Some exception occured while serving the request for the user : " + msisdn, ilste);
	    return FORWARD_SESSION_INVALID;
	} catch (Exception e) {
	    LOGGER.error("Blocking Exception occured for the User  : " + msisdn, e);

	    if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }

	}
	return null;
    }

    private String xmlResponseStringFromMenuDTO(HttpServletRequest request, USSDSessionManagement ussdSessionMgmt, String lang, String countryCode,
	    MenuItemDTO pagedDTO, String msisdnWithCountry) {
	NavigationOptionsDTO backHomeOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getUserProfile().getBusinessId(), ussdSessionMgmt
		.getUserProfile().getCountryCode());
	ResponseMessage responseMessage = new ResponseMessage();
	String responseString = null;
	USSDXMLResponseBody xmlResponseBody = responseMessage.responseMessageFromMenuList(pagedDTO, this.localeScreenBuilder.getUssdResourceBundle(),
		lang, countryCode, ussdSessionMgmt.isDemoMode(), backHomeOptions);
	USSDXMLResponseHeader headerResponse = responseMessage.createXmlResponseHeader(request, msisdnWithCountry);
	responseString = responseMessage.getXmlResponseString(request, xmlResponseBody, headerResponse);
	return responseString;
    }

    /**
     * 
     * @param msisdn
     * @param userProfile
     * @param inputTxt
     * @return
     */
    private UssdDecisionParserParamDTO getUssdDecisionParserParam(String msisdn, UserProfile userProfile, String inputTxt) {
	UssdDecisionParserParamDTO paramDTO = new UssdDecisionParserParamDTO();
	paramDTO.setServiceName(FIRST_LOGIN_CHANGE_PIN_SERVICE);
	paramDTO.setMsisdn(msisdn);
	paramDTO.setUserInput(inputTxt);
	paramDTO.setCountryCode(userProfile.getCountryCode());
	paramDTO.setBusinessId(userProfile.getBusinessId());
	paramDTO.setLang(userProfile.getLanguage());
	return paramDTO;
    }
}
