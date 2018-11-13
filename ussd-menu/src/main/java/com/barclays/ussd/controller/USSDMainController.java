package com.barclays.ussd.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
import org.springframework.web.context.ServletContextAware;

import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.auth.response.ResponseMessage;
import com.barclays.ussd.auth.response.USSDResponsePagination;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.parser.UssdDecisionParser;
import com.barclays.ussd.parser.UssdDecisionParserParamDTO;
import com.barclays.ussd.session.handler.USSDUserSessionManager;
import com.barclays.ussd.utils.LocaleScreenBuilder;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdParser;
import com.barclays.ussd.xmlresponse.USSDXMLResponseBody;
import com.barclays.ussd.xmlresponse.USSDXMLResponseHeader;

@Controller
public class USSDMainController extends USSDAbstractController implements ServletContextAware {

    /** Servlet context for the forwarding the welcome controller call at click back button from Main Menu screen */
    private ServletContext ussdContext;

    /** The Constant FORWARD_WELCOME_SCREEN. */
    private static final String FORWARD_WELCOME_SCREEN = ConfigurationManager.getString("welcomeScreen");

    private static final String FORWARD_MAIN_MENU_SCREEN = ConfigurationManager.getString("forwardUSSDMenu");

    private static final String FORWARD_CHANGE_PIN_CONTROLLER = ConfigurationManager.getString("changePinScreen");

    private static final String EMPTY_STRING = "";

    @Autowired
    UssdDecisionParser ussdDecisionParser;

    @Autowired
    LocaleScreenBuilder localeScreenBuilder;

    @Autowired
    USSDUserSessionManager ussdUserSessionManager;

    @Autowired
    UssdParser ussdParser;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(USSDMainController.class);

    @RequestMapping(value = "/ussdhome", method = { RequestMethod.POST })
    public String ussdRequestHandler(HttpServletRequest request, HttpServletResponse response) throws USSDBlockingException {

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Entering USSDMainController with params");
	}
	return FORWARD_MAIN_MENU_SCREEN;
    }

    /**
     * Get the session mgmt from the msisdn number.
     *
     * @param msdinNumber
     * @return
     */
    private USSDSessionManagement getUssdSessionMgmt(String msdinNumber, HttpServletRequest request) {
	HttpSession httpSession = sessionHandler.getSessionRequest(request);
	return (USSDSessionManagement) httpSession.getAttribute(msdinNumber);
    }

    @RequestMapping(value = "/ussdmenuhome", method = { RequestMethod.POST })
    @ResponseBody
    public String ussdNavigationHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
	    USSDBlockingException, USSDNonBlockingException {

	MenuItemDTO menuItemDTO = null;
	MenuItemDTO pagedDTO = null;

	// Get the user data from the request
	USSDRequest ussdRequest = (USSDRequest) request.getAttribute(USSDConstants.REQOBJ);
	String msdinNumber = ussdRequest.getMsisdn();
	String userInput = ussdRequest.getInput();

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Menu navigation main controller starts.");
	}
	UserProfile userProfile = getUserProfile(msdinNumber, request);
	USSDSessionManagement ussdSessionMgmt = getUssdSessionMgmt(msdinNumber, request);
	HttpSession httpSession = sessionHandler.getSessionRequest(request);
	NavigationOptionsDTO navigationOptions = ussdParser.getNavigationOptions(userProfile.getBusinessId(), userProfile.getCountryCode());

	UssdDecisionParserParamDTO paramDTO = getUssdDecisionParserParamDTO(userInput, userProfile, ussdSessionMgmt.getScreenId());

	try {
	    if (ussdSessionMgmt.isPaged()
		    && (userInput.equalsIgnoreCase(navigationOptions.getScrollUpOption()) || userInput.equalsIgnoreCase(navigationOptions
			    .getScrollDownOption()))) {
		pagedDTO = new USSDResponsePagination().handleScrolling(ussdSessionMgmt, userInput, this.localeScreenBuilder.getUssdResourceBundle(),
			navigationOptions);

		return xmlResponseStringFromMenuDTO(request, ussdSessionMgmt, userProfile.getLanguage(), userProfile.getCountryCode(), pagedDTO,
			ussdRequest.getMsisdnWithCountry());

	    } else {
		try {
		    menuItemDTO = ussdDecisionParser.getMenuList(paramDTO, ussdSessionMgmt);
		} catch (USSDBlockingException e) {
		    if (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_DEMO_MODE.getBmgCode(), e.getErrCode())) {
			RequestDispatcher requestDispatcher = ussdContext.getRequestDispatcher(FORWARD_WELCOME_SCREEN);
			requestDispatcher.forward(request, response);
			return EMPTY_STRING;
		    } else if (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_CONTINUE_OLD_SESSION.getBmgCode(), e.getErrCode())) {
			if (LOGGER.isDebugEnabled()) {
			    LOGGER.debug("User opted to re-store the old session");
			}
			ussdSessionMgmt.setCustomerType(null);
			menuItemDTO = ussdUserSessionManager.getOldResponse(ussdRequest.getMsisdnWithCountry(), userProfile.getBusinessId(),
				userProfile.getCountryCode());
			if (menuItemDTO == null) {
			    if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("old menuItemDTO is null");
			    }
			    handleNewSessionAction(request, response, ussdRequest, userProfile, ussdSessionMgmt, httpSession);
			    return EMPTY_STRING;
			} else {
			    if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("old session setup");
			    }
			    ussdUserSessionManager.updateUserSession(ussdRequest.getMsisdnWithCountry(), httpSession, userProfile.getBusinessId(),
				    userProfile.getCountryCode());
			    httpSession.setAttribute(USSDConstants.OLD_SESSION_FLAG, false);
			    return generateResponse(request, menuItemDTO, ussdRequest, userProfile, ussdSessionMgmt, httpSession, navigationOptions);
			}
		    } else if (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_SWITCH_TO_NEW_SESSION.getBmgCode(), e.getErrCode())) {
			if (LOGGER.isDebugEnabled()) {
			    LOGGER.debug("User opted to switch to a new session");
			}
			handleNewSessionAction(request, response, ussdRequest, userProfile, ussdSessionMgmt, httpSession);
			return EMPTY_STRING;

		    } else {
			throw e;
		    }
		}
		String currentScreenNodeId = ussdSessionMgmt.getCurrentScreenNodeId();
		String pageError = menuItemDTO.getPageError();
		if (StringUtils.isNotEmpty(pageError)) {
		    request.setAttribute("WelcomeScreenPageError", pageError);
		}
		/* If the requested page is welcome screen then forward the control to welcomeScreenController */
		if (currentScreenNodeId != null && USSDConstants.WELCOME_PAGE_NODE_ID.equalsIgnoreCase(currentScreenNodeId)) {
		    RequestDispatcher requestDispatcher = ussdContext.getRequestDispatcher(FORWARD_WELCOME_SCREEN);
		    requestDispatcher.forward(request, response);
		}

	    }
	    String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	    String cntryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	    boolean sessionCacheEnabled = USSDUtils.isSessionCacheEnabled(businessId, cntryCode);
	    if (sessionCacheEnabled && !StringUtils.endsWithIgnoreCase(ussdSessionMgmt.getCustomerType(), USSDConstants.CUSTOMER_TYPE_NON_HM)
		    && !StringUtils.endsWithIgnoreCase(ussdSessionMgmt.getCustomerType(), USSDConstants.CUSTOMER_TYPE_USER_SESSION_RETENTION)) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Persisting the user session for each response");
		}
		httpSession.setAttribute(USSDConstants.OLD_SESSION_FLAG, false);
		ussdUserSessionManager.updateUserSession(ussdRequest.getMsisdnWithCountry(), httpSession, businessId, cntryCode);
	    }
	    return generateResponse(request, menuItemDTO, ussdRequest, userProfile, ussdSessionMgmt, httpSession, navigationOptions);

	} catch (USSDBlockingException ube) {
	    LOGGER.error("Unblocking exception occured for the user : " + msdinNumber, ube);
	    throw ube;
	}
    }

    private void handleNewSessionAction(HttpServletRequest request, HttpServletResponse response, USSDRequest ussdRequest, UserProfile userProfile,
	    USSDSessionManagement ussdSessionMgmt, HttpSession httpSession) throws ServletException, IOException {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("new session set-up");
	}
	ussdUserSessionManager.sessionCleanUp(httpSession, userProfile.getBusinessId(), userProfile.getCountryCode());
	ussdSessionMgmt.setCustomerType(null);
	httpSession.setAttribute(USSDConstants.OLD_SESSION_FLAG, false);
	ussdUserSessionManager.addUserSession(ussdRequest.getMsisdnWithCountry(), httpSession, userProfile.getBusinessId(),
		userProfile.getCountryCode());
	String forwardToPage = null;
	if (ussdSessionMgmt.isPinChangeReq()) {
	    forwardToPage = FORWARD_CHANGE_PIN_CONTROLLER;
	} else {
	    forwardToPage = FORWARD_WELCOME_SCREEN;
	}
	RequestDispatcher requestDispatcher = ussdContext.getRequestDispatcher(forwardToPage);
	requestDispatcher.forward(request, response);
    }

    private String generateResponse(HttpServletRequest request, MenuItemDTO menuItemDTO, USSDRequest ussdRequest, UserProfile userProfile,
	    USSDSessionManagement ussdSessionMgmt, HttpSession httpSession, NavigationOptionsDTO navigationOptions) throws USSDBlockingException,
	    IOException {
	MenuItemDTO localeSpecificDTO;
	MenuItemDTO pagedDTO;

	/*
	 * if (!StringUtils.equalsIgnoreCase(ussdSessionMgmt.getCustomerType(), USSDConstants.CUSTOMER_TYPE_USER_SESSION_RETENTION)) {
	 * USSDUserSessionManager.getInstance().updateUserSession(ussdRequest.getMsisdnWithCountry(), httpSession); }
	 */
	if (menuItemDTO != null && menuItemDTO.getTranId() == null) {
	    localeSpecificDTO = localeScreenBuilder.getLocaleSpecificResponse(ussdSessionMgmt, ussdRequest.getMsisdnWithCountry(), menuItemDTO,
		    userProfile.getLanguage(), userProfile.getCountryCode());
	    // Apply the pagination if required
	    pagedDTO = new USSDResponsePagination().handlePagination(localeSpecificDTO, ussdSessionMgmt,
		    this.localeScreenBuilder.getUssdResourceBundle(), navigationOptions);
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info("Menu navigation main controller returns...");
	    }
	    return xmlResponseStringFromMenuDTO(request, ussdSessionMgmt, userProfile.getLanguage(), userProfile.getCountryCode(), pagedDTO,
		    ussdRequest.getMsisdnWithCountry());
	    // return new ResponseMessage().responseMessageFromMenuList(pagedDTO, this.localeScreenBuilder.getUssdResourceBundle(), userProfile
	    // .getLanguage(), userProfile.getCountryCode());
	} else {
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info("Recieved null as service response...");
	    }
	    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}
    }

    private String xmlResponseStringFromMenuDTO(HttpServletRequest request, USSDSessionManagement ussdSessionMgmt, String lang, String countryCode,
	    MenuItemDTO pagedDTO, String msisdnWithCountry) {
	NavigationOptionsDTO backHomeOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getUserProfile().getBusinessId(), ussdSessionMgmt
		.getUserProfile().getCountryCode());
	ResponseMessage responseMessage = new ResponseMessage();

	//CR-86 Back flow changes Fixed deposit/apply product confirmation screen
	if(ussdSessionMgmt.getUserTransactionDetails()!=null &&
			(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId().equalsIgnoreCase("FDR003")||
					ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId().equalsIgnoreCase("LG003"))
			&& pagedDTO.getPageError()!=null && pagedDTO.getPageError()!=""){
		pagedDTO.setPageBody("");
		pagedDTO.setPageHeader("");
	}
	USSDXMLResponseBody xmlResponseBody = responseMessage.responseMessageFromMenuList(pagedDTO, this.localeScreenBuilder.getUssdResourceBundle(),
		lang, countryCode, ussdSessionMgmt.isDemoMode(), backHomeOptions);
	USSDXMLResponseHeader headerResponse = responseMessage.createXmlResponseHeader(request, msisdnWithCountry);
	return responseMessage.getXmlResponseString(request, xmlResponseBody, headerResponse);

    }

    private UssdDecisionParserParamDTO getUssdDecisionParserParamDTO(String userInput, UserProfile userProfile, String screenId) {
	UssdDecisionParserParamDTO paramDTO = new UssdDecisionParserParamDTO();
	paramDTO.setUserInput(userInput);
	paramDTO.setCurrentScreenId(screenId);
	paramDTO.setCountryCode(userProfile.getCountryCode());
	paramDTO.setLang(userProfile.getLanguage());
	paramDTO.setByPassRequest(false);
	paramDTO.setBusinessId(userProfile.getBusinessId());
	return paramDTO;
    }

    private UserProfile getUserProfile(String msdinNumber, HttpServletRequest request) {
	USSDSessionManagement ussdSessionMgmt = getUssdSessionMgmt(msdinNumber, request);
	return ussdSessionMgmt.getUserProfile();
    }

    public LocaleScreenBuilder getLocaleScreenBuilder() {
	return localeScreenBuilder;
    }

    public void setLocaleScreenBuilder(LocaleScreenBuilder localeScreenBuilder) {
	this.localeScreenBuilder = localeScreenBuilder;
    }

    public UssdDecisionParser getUssdDecisionParser() {
	return ussdDecisionParser;
    }

    public void setUssdDecisionParser(UssdDecisionParser ussdDecisionParser) {
	this.ussdDecisionParser = ussdDecisionParser;
    }

    @Override
    public void setServletContext(ServletContext ussdContext) {
	this.ussdContext = ussdContext;
    }

    public void setUssdUserSessionManager(USSDUserSessionManager ussdUserSessionManager) {
	this.ussdUserSessionManager = ussdUserSessionManager;
    }

}