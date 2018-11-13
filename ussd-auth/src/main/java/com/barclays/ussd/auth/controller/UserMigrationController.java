package com.barclays.ussd.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.auth.response.ResponseMessage;
import com.barclays.ussd.auth.response.USSDResponsePagination;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.LocaleDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.controller.USSDAbstractController;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.parser.UssdDecisionParserParamDTO;
import com.barclays.ussd.utils.LocaleScreenBuilder;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdParser;
import com.barclays.ussd.xmlresponse.USSDXMLResponseBody;
import com.barclays.ussd.xmlresponse.USSDXMLResponseHeader;

/**
 * The Class BaseController.
 */
@Controller
public class UserMigrationController extends USSDAbstractController implements ServletContextAware {
    private ServletContext ussdContext;
    private static final String EMPTY_STRING = "";

    private static final String USER_MIGRATION_SERVICE = "UserMigration";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(UserMigrationController.class);

    /** The Constant FORWARD_SESSION_INVALID. */
    private static final String FORWARD_SESSION_INVALID = ConfigurationManager.getString("forwardSessionInvld");

    /** The Constant FORWARD_WELCOME_SCREEN. */
    private static final String FORWARD_TO_UNREGISTERED_USER_MENU = ConfigurationManager.getString("forwardToUnregUserWelcomeScreen");

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

    @RequestMapping(value = "/userMigration", method = { RequestMethod.POST })
    @ResponseBody
    public String handler(HttpServletRequest request, HttpServletResponse response) throws Exception {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("ChangePINController Base handler starts...");
	}

	String msisdn = null;
	String inputTxt = null;
	HttpSession httpSession = null;
	USSDSessionManagement ussdSessionMgmt = null;
	try {
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setCharacterEncoding("UTF-8");
	    USSDRequest ussdRequest = (USSDRequest) request.getAttribute(USSDConstants.REQOBJ);
	    msisdn = ussdRequest.getMsisdn();
	    inputTxt = ussdRequest.getInput();
	    String msisdnWithCountry = ussdRequest.getMsisdnWithCountry();
	    request.setAttribute("optionId", inputTxt);

	    httpSession = sessionHandler.getSessionRequest(request);

	    ussdSessionMgmt = (USSDSessionManagement) httpSession.getAttribute(msisdn);

	    if (ussdSessionMgmt == null) {
		ussdSessionMgmt = new USSDSessionManagement();
		httpSession.setAttribute(msisdn, ussdSessionMgmt);
	    }
	    ussdSessionMgmt.setMsisdnWithCountry(ussdRequest.getMsisdnWithCountry());
	    UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	    if (userProfile == null) {
		userProfile = new UserProfile();
		LocaleDTO defaultLocaleDTO = ussdMenuBuilder.getDefaultLocaleDTO(USSDUtils.getCountryCode(ussdRequest), ussdRequest.getBusinessId());

		userProfile.setLanguage(defaultLocaleDTO.getLanguage());
		userProfile.setCountryCode(defaultLocaleDTO.getCountry());
		userProfile.setMsisdn(msisdn);
		userProfile.setBusinessId(ussdRequest.getBusinessId());
		ussdSessionMgmt.setUserProfile(userProfile);
		ussdSessionMgmt.setTransactionFlag(true);
	    }
	    ussdSessionMgmt.setMsisdnNumber(msisdn);
	    ussdSessionMgmt.setCountryCode(USSDUtils.getCountryCode(ussdRequest));
	    ussdSessionMgmt.setBusinessId(ussdRequest.getBusinessId());
	    ussdSessionMgmt.setUserMigration(true);

	    List<String> errorCodes = new ArrayList<String>();
	    UssdDecisionParserParamDTO paramDTO = getUssdDecisionParserParam(msisdn, userProfile, inputTxt);
	    MenuItemDTO localeSpecificDTO = null;

	    MenuItemDTO menuItemDTO = ussdByPassXMLMenuManager.handleUserMigrationRequest(paramDTO, ussdSessionMgmt, errorCodes);

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
	ResponseMessage responseMessage = new ResponseMessage();
	NavigationOptionsDTO backHomeOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getUserProfile().getBusinessId(), ussdSessionMgmt
		.getUserProfile().getCountryCode());
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
	paramDTO.setServiceName(USER_MIGRATION_SERVICE);
	paramDTO.setMsisdn(msisdn);
	paramDTO.setUserInput(inputTxt);
	paramDTO.setCountryCode(userProfile.getCountryCode());
	paramDTO.setBusinessId(userProfile.getBusinessId());
	paramDTO.setLang(userProfile.getLanguage());
	return paramDTO;
    }

    @Override
    public void setServletContext(ServletContext ussdContext) {
	this.ussdContext = ussdContext;

    }
}
