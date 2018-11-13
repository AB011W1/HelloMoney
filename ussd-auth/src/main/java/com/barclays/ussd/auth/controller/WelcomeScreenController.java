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
import com.barclays.ussd.auth.response.USSDResponsePagination;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.controller.USSDAbstractController;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.parser.UssdDecisionParserParamDTO;
import com.barclays.ussd.sessionMgmt.USSDSessionHandler;
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
public class WelcomeScreenController extends USSDAbstractController {
    private static final String WELCOME_SCREEN_SERVICE = "WelcomeScreen";
    @Autowired
    USSDByPassXMLMenuManager ussdByPassXMLMenuManager;

    @Autowired
    LocaleScreenBuilder localeScreenBuilder;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WelcomeScreenController.class);

    /** The Constant FORWARD_SESSION_INVALID. */
    private static final String FORWARD_SESSION_INVALID = ConfigurationManager.getString("forwardSessionInvld");

    /** The forward auth controller. */
    /*
     * private final String FORWARD_AUTH_CONTROLLER = ConfigurationManager.getString("forwardAuth");
     */
    /** The configuration manager. */
    ConfigurationManager configurationManager;

    /** The session track. */
    @Autowired
    USSDSessionHandler sessionTrack;

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

    @RequestMapping(value = "/welcomeScreen", method = { RequestMethod.POST })
    @ResponseBody
    public String handler(HttpServletRequest request, HttpServletResponse response) throws Exception {

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Base handler starts...");
	}
	// String page = null;

	String inputTxt = null;
	HttpSession httpSession = null;
	USSDSessionManagement ussdSessionMgmt = null;
	String msisdn = null;
	// final USSDRequest ussdRequest = requestMapper.getRequestBean(request);
	try {
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setCharacterEncoding("UTF-8");
	    USSDRequest ussdRequest = (USSDRequest) request.getAttribute(USSDConstants.REQOBJ);
	    // String msdinNumber = request.getParameter(USSDConstants.USER_MSISDN_NO_PARAM_NAME);
	    msisdn = ussdRequest.getMsisdn();
	    // msisdn = ussdRequest.getMsisdn();
	    String msisdnWithCountry = ussdRequest.getMsisdnWithCountry();
	    httpSession = sessionTrack.getSessionRequest(request);
	    // TODO to be removed

	    if (null != httpSession) {
		// sessiontmp = sessionM.get(msisdn);
		// if (!sessionTrack.isSessionValid(request)) {
		// if (LOGGER.isInfoEnabled()) {
		// LOGGER.info("Invalid session Base handler returns...");
		// }
		// return FORWARD_SESSION_INVALID;
		// }
		ussdSessionMgmt = (USSDSessionManagement) httpSession.getAttribute(msisdn);
	    }
	    if (null == ussdSessionMgmt) {
		throw new USSDBlockingException(USSDBlockingExceptions.AUTHENTICATION_ERROR.getErrorCode());
	    }
	    ussdSessionMgmt.clean();
	    String lang = ussdSessionMgmt.getUserProfile().getLanguage();
	    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	    String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	    List<String> errorCodes = new ArrayList<String>();
	    UssdDecisionParserParamDTO paramDTO = new UssdDecisionParserParamDTO();
	    paramDTO.setServiceName(WELCOME_SCREEN_SERVICE);
	    paramDTO.setMsisdn(ussdSessionMgmt.getMsisdnNumber());
	    paramDTO.setUserInput(inputTxt);
	    paramDTO.setCountryCode(countryCode);
	    paramDTO.setBusinessId(businessId);
	    paramDTO.setLang(lang);
	    MenuItemDTO localeSpecificDTO = null;

	    MenuItemDTO menuItemDTO = null;
	    List<String> category1 = new ArrayList<String>(5);
	    category1.add("TZBRB");
	    List<String> category2 = new ArrayList<String>(5);
	    category2.add("KEBRB");
	    category2.add("GHBRB");
	    category2.add("UGBRB");
	    category2.add("MZBRB");
	    category2.add("ZMBRB");
	    category2.add("ZWBRB");
	    category2.add("BWBRB");
	    category2.add("TZNBC");
	    if (category1.contains(businessId)) {
		menuItemDTO = ussdByPassXMLMenuManager.handleWelcomeScreenRequest(paramDTO, ussdSessionMgmt, errorCodes);
	    } else if (category2.contains(businessId)) {
		menuItemDTO = ussdByPassXMLMenuManager.generateLandingPageMenu(paramDTO, ussdSessionMgmt);
	    }

	    // get the locale specific response as per the lang code
	    if (menuItemDTO != null && menuItemDTO.getTranId() == null) {
		String delegatedPageError = (String) request.getAttribute("WelcomeScreenPageError");
		// Populate the error on the screen if user enters invalid input
		if (StringUtils.isNotEmpty(delegatedPageError)) {
		    menuItemDTO.setPageError(delegatedPageError);
		}
		localeSpecificDTO = localeScreenBuilder.getLocaleSpecificResponse(ussdSessionMgmt, ussdRequest.getMsisdnWithCountry(), menuItemDTO,
			lang, countryCode);

		NavigationOptionsDTO navigationOptions = ussdParser.getNavigationOptions(businessId, countryCode);
		// Apply the pagination if required
		MenuItemDTO pagedDTO = new USSDResponsePagination().handlePagination(localeSpecificDTO, ussdSessionMgmt,
			this.localeScreenBuilder.getUssdResourceBundle(), navigationOptions);
		/*
		 * return new ResponseMessage().responseMessageFromMenuList(pagedDTO, this.localeScreenBuilder.getUssdResourceBundle(), lang,
		 * countryCode);
		 */

		return xmlResponseStringFromMenuDTO(request, ussdSessionMgmt, lang, countryCode, pagedDTO, msisdnWithCountry);

	    }
	} catch (USSDBlockingException ube) {
	    LOGGER.error("Blocking Exception occured for the User  : " + msisdn, ube);
	    throw ube;
	} catch (final IllegalStateException ilste) {
	    LOGGER.fatal("Some exception occured while serving the request for the user : " + msisdn, ilste);
	    return FORWARD_SESSION_INVALID;
	} catch (Exception e) {

	    LOGGER.error("Exception occured for the User : " + msisdn, e);
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
	// ResponseMessage responseMessage = new ResponseMessage();
	USSDXMLResponseBody xmlResponseBody = responseMsg.responseMessageFromMenuList(pagedDTO, ussdResourceBundle, lang, countryCode,
		ussdSessionMgmt.isDemoMode(), backHomeOptions);
	USSDXMLResponseHeader headerResponse = responseMsg.createXmlResponseHeader(request, msisdnWithCountry);
	return responseMsg.getXmlResponseString(request, xmlResponseBody, headerResponse);
    }
}
