package com.barclays.ussd.auth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.barclays.ussd.auth.USSDresquest.USSDBaseRequestMapper;
import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.auth.response.ResponseMessage;
import com.barclays.ussd.auth.service.UserProfileService;
import com.barclays.ussd.bean.LanguageDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.controller.USSDAbstractController;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.session.handler.USSDUserSessionManager;
import com.barclays.ussd.svc.httpclient.CommonHttpClientExecutor;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSessionConstants;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.UssdServiceEnabler;

/**
 * This controller may have number of method; if method uses the session object and get the profile then throws the new SystemException or if method
 * uses the session then throws SystemException.
 */
@Controller
public class AuthController extends USSDAbstractController implements ServletContextAware {
	//FreeDialUSSD
	private static final String FORWARD_FREE_DIAL ="/hellomoney/freedialpage";
	private static final String FREE_DIAL_AIRTIME_TOPUP = ConfigurationManager.getString("FREE_DIAL_AIRTIME_TOPUP");
	private static final String FREE_DIAL_AIRTIME = ConfigurationManager.getString("FREE_DIAL_AIRTIME");
	//added for Zambia FreeDialUSSD
	private static final String FREE_DIAL_AIRTIME_ZM = ConfigurationManager.getString("FREE_DIAL_AIRTIME_ZM");
	private static final String FREE_DIAL_AIRTIME_TOPUP_ERRORMSG = "FREE_DIAL_AIRTIME_TOPUP_ERRORMSG";

	//FreeDialUSSD FOR MWALLET
	private static final String FREE_DIAL_MWALLET = ConfigurationManager.getString("FREE_DIAL_MWALLET");
	private static final String FREE_DIAL_MWALLET_ERRORMSG = "FREE_DIAL_MWALLET_ERRORMSG";

	//Forgot Pin Change
	private static final String LABEL_FORGOT_PIN_PRESS="LABEL_FORGOT_PIN_PRESS";
	private static final String FORWARD_FORGOT_PIN="/hellomoney/forgotpinpage";
    private static final String LBL_PASS = "LBL_PASS";

    private static final String WELCOME_MSG = "WELCOME_MSG";

    private static final String WELCOME_MSG_MIGRATED = "WELCOME_MSG_MIGRATED";
    
    private static final String BANNER_WELCOME_MSG = "BANNER_WELCOME_MSG";

    private static final String MIGRATED = "MIGRATED";

    private static final String EMPTY_STRING = "";

    /** Servlet context for the forwarding the user to self registration */
    private ServletContext ussdContext;

    /** The Constant LBL_PASS_BLCK. */
    // private static final String LBL_PASS_BLCK = "LBL_PASS_BLCK";
    /** The Constant FORWARD_AUTHENTICATION. */
    private static final String FORWARD_AUTHENTICATION = ConfigurationManager.getString("forwardAuth");

    /** The Constant FORWARD_NAVIGATION. */
    private static final String FORWARD_NAVIGATION = ConfigurationManager.getString("forwardNevigation");

    /** The Constant USSDUserSessionController. */
    private static final String FORWARD_TO_USER_SESSION_CONTROLLER = ConfigurationManager.getString("forwardUssdSessionController");

    /** The Constant FORWARD_USSDHOME. */
    private static final String FORWARD_USSDHOME = ConfigurationManager.getString("forwardMenu");

    /** The Constant FORWARD_WELCOME_SCREEN. */
    private static final String FORWARD_WELCOME_SCREEN = ConfigurationManager.getString("forwardToWelcomeScreen");

    /** The Constant FORWARD_SESSION_INVALID. */
    private static final String FORWARD_SESSION_INVALID = ConfigurationManager.getString("forwardSessionInvld");

    /** The Constant FORWARD_WELCOME_SCREEN. */
    private static final String FORWARD_TO_CHANGE_LANGUAGE_CONTROLLER = ConfigurationManager.getString("forwardToChangeLanuageController");

    private static final String FORWARD_TO_USER_MIGRATION_CONTROLLER = ConfigurationManager.getString("forwardToUserMigrationController");

    private static final String FORWARD_TO_UNREGISTERED_USER_MENU = ConfigurationManager.getString("forwardToUnregUserWelcomeScreen");

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AuthController.class);

    /** The forward changepin handler. */
    private final String FORWARD_CHANGE_PIN_CONTROLLER = ConfigurationManager.getString("forwardToChangePin");

    private static final String LOGIN_ACTION = "LOGIN";

    private static final String FREE_DIAL_BUSINESS_IDS = "BWBRBGHBRB";

    //CR-77
    private static final String WELCOME_MSG_CUSTOMERNAME = "WELCOME_MSG_CUSTOMERNAME";

    /** The user profile service. */
    @Autowired
    UserProfileService userProfileService;

    /** The common http client executer. */
    @Autowired
    CommonHttpClientExecutor commonHttpClientExecuter;

    @Autowired
    protected USSDBaseRequestMapper requestMapperObj;

    @Autowired
    UssdMenuBuilder ussdMenuBuilder;

    @Autowired
    USSDUserSessionManager ussdUserSessionManager;

    @Autowired
    UssdServiceEnabler ussdServiceEnabler;
    /**
     * Authenticate user.
     *
     * @param request
     *            the request
     * @param session
     *            the session
     * @return the string
     * @throws USSDBlockingException
     *             the uSSD blocking exception
     * @throws USSDNonBlockingException
     *
     */
    @RequestMapping(value = "/authentication", method = { RequestMethod.POST })
    @ResponseBody
    public String authenticateUser(final HttpServletRequest request, final HttpServletResponse response) throws USSDBlockingException {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("User authentication starts...");
	}

	String msisdn = null;
	String action = null;
	RequestDispatcher requestDispatcher = null;
	boolean sessioncheck = false;
	//FreeDialUssd
	String freeDialUssdErrorMsg = "";

	try {

	    USSDSessionManagement ussdSessionManagement = new USSDSessionManagement();
	    final USSDRequest ussdRequest = requestMapperObj.getRequestBean(request);
	    //Forgot Pin Change
	    //FreeDialUSSD
	    if(null != ussdRequest.getInput()  && ussdRequest.getInput().equals("1") && !ussdRequest.getBusinessId().equals("MZBRB")){
	    	return FORWARD_FORGOT_PIN;
	    }

	    ussdSessionManagement.setMsisdnWithCountry(ussdRequest.getMsisdnWithCountry());
	    msisdn = ussdRequest.getMsisdn();
	    // boolean inputIsNull = isInputNull(ussdRequest.getInput());
	    String msisdnWithCountry = ussdRequest.getMsisdnWithCountry();
	    if(null != request)
	    	action = (String) request.getAttribute(USSDSessionConstants.INPUT_ACTION);

	    // Get the user profile from the session if the user session is active

	    /*
	     * HttpSession session = sessionHandler.getSessionRequest(request); ussdSessionManagement =
	     * (USSDSessionManagement)session.getAttribute(msisdn); UserProfile profile2 = ussdSessionManagement.getUserProfile();
	     */

	    UserProfile profile = getUserProfile(msisdn, request);

	    if (profile != null) {
		sessioncheck = true;
	    } else {
		ussdSessionManagement.setCountryCode(USSDUtils.getCountryCode(ussdRequest));
		ussdSessionManagement.setBusinessId(ussdRequest.getBusinessId());
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Calling userProfileService.getProfile");
		}
		String extra=ussdRequest.getExtra();
		if(extra==null)
			extra="";
		String status=ussdServiceEnabler.getStatusFlag(ussdSessionManagement.getBusinessId(), extra);
		try {
			if(status.equals("N"))
				throw new USSDBlockingException("BMB90013");
		    profile = userProfileService.getProfile(msisdn, ussdSessionManagement);

		} catch (USSDBlockingException e) {
		    if (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_MOBILE_NOT_REG.getBmgCode(), e.getErrCode())) {
			LanguageDTO languages = ussdMenuBuilder.getLanguages(ussdSessionManagement.getUserProfile().getCountryCode(),
				ussdSessionManagement.getBusinessId());
			List<String> languagesList = languages.getLanguages();
			if(e.getErrCode().equals("BMB90013"))
				throw e;
			if (languagesList != null && languagesList.size() > 1) {
			    requestDispatcher = ussdContext.getRequestDispatcher(FORWARD_TO_CHANGE_LANGUAGE_CONTROLLER);
			} else {
				//FreeDialUSSD AIRTIME_TOPUP

				if(ussdRequest.getExtra()!=null && ussdRequest.getExtra().equalsIgnoreCase(FREE_DIAL_AIRTIME_TOPUP)&& ussdSessionManagement.getBusinessId().equalsIgnoreCase("BWBRB") ){
					if(null != languagesList.get(0)){
						freeDialUssdErrorMsg = ussdResourceBundle.getLabel(FREE_DIAL_AIRTIME_TOPUP_ERRORMSG, new Locale(languagesList.get(0),ussdSessionManagement.getCountryCode()));
						 e.setErrMsg(freeDialUssdErrorMsg);
						 e.setErrCode("FREE_DIAL_AIRTIME_TOPUP_ERRORMSG");
						 throw e;
					}
					//FreeDialUSSD MWALLET
				}else if(ussdRequest.getExtra()!=null && ussdRequest.getExtra().equalsIgnoreCase(FREE_DIAL_MWALLET)&& ussdSessionManagement.getBusinessId().equalsIgnoreCase("GHBRB") ){
					if(null != languagesList.get(0)){
						freeDialUssdErrorMsg = ussdResourceBundle.getLabel(FREE_DIAL_MWALLET_ERRORMSG, new Locale(languagesList.get(0),ussdSessionManagement.getCountryCode()));
						 e.setErrMsg(freeDialUssdErrorMsg);
						 e.setErrCode("FREE_DIAL_MWALLET_ERRORMSG");
						 throw e;
					}
					//FreeDialUSSD AIRTIME
				}else if(ussdRequest.getExtra()!=null && (ussdRequest.getExtra().equalsIgnoreCase(FREE_DIAL_AIRTIME) || ussdRequest.getExtra().equalsIgnoreCase(FREE_DIAL_AIRTIME_ZM))&& (ussdSessionManagement.getBusinessId().equalsIgnoreCase("GHBRB") || ussdSessionManagement.getBusinessId().equalsIgnoreCase("ZMBRB"))){
					if(null != languagesList.get(0)){
						freeDialUssdErrorMsg = ussdResourceBundle.getLabel(FREE_DIAL_MWALLET_ERRORMSG, new Locale(languagesList.get(0),ussdSessionManagement.getCountryCode()));
						 e.setErrMsg(freeDialUssdErrorMsg);
						 e.setErrCode("FREE_DIAL_MWALLET_ERRORMSG");
						 throw e;
					}
					//FreeDialUSSD MWALLET
				}else {
					 requestDispatcher = ussdContext.getRequestDispatcher(FORWARD_TO_UNREGISTERED_USER_MENU);
				}

			}
			if(null != request && null != response)
				requestDispatcher.forward(request, response);
			return EMPTY_STRING;
		    } else {
			throw e;
		    }
		}
		if (profile == null) {
		    profile = new UserProfile();
		}

		/*
		 * if (ussdSessionManagement.isUserMigration()) { requestDispatcher =
		 * ussdContext.getRequestDispatcher(FORWARD_TO_USER_MIGRATION_CONTROLLER); requestDispatcher.forward(request, response); return
		 * EMPTY_STRING; }
		 */profile.setCountryCode(USSDUtils.getCountryCode(ussdRequest));
		profile.setBusinessId(ussdRequest.getBusinessId());
		if (profile.getLanguage() == null) {
		    String languageCode = getDefaultCountryLanguage(ussdSessionManagement);
		    profile.setLanguage(languageCode);
		}
		profile.setMsisdn(msisdn);

	    }

	    // if (profile.isValidateMSISDNNumber() || (inputIsNull &&
	    // !sessioncheck)) {
	    if (StringUtils.equalsIgnoreCase(LOGIN_ACTION, action) && !sessioncheck) {
		if (msisdn.equals(profile.getMsisdn())) {

		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("sessionHandler.createSession");
		    }

		    initSession(profile, ussdSessionManagement);
		    setSessionAttributeFromMSISDN(msisdn, request, ussdSessionManagement, msisdnWithCountry);

		    final String responseString = responseMsg.generateXmlResponseString(request, profile, msisdnWithCountry);
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get the Response:  for the user:");
		    }
		    return responseString;
		}
	    } else {
		// if controller comes here that is user is not registered with
		// bank
	    //Forgot pin Issue 2295
	    String errorMessage = ussdResourceBundle.getLabel(profile.getErrorMsg(), new Locale(profile.getLanguage(), profile.getCountryCode()));
	    if(profile.getBusinessId().equalsIgnoreCase("MZBRB") && "BEM09032".equalsIgnoreCase(profile.getErrorMsg()))
	    {
		    profile.setErrorMsg(errorMessage  + USSDConstants.NEW_LINE);
	    }
	    else if("BEM09032".equalsIgnoreCase(profile.getErrorMsg()))
	    {
	    	String CUS_FORGOT_PIN_LABEL = ussdResourceBundle.getLabel("CUS_FORGOT_PIN_LABEL", new Locale(profile.getLanguage(), profile.getCountryCode()));
		    profile.setErrorMsg(errorMessage  + USSDConstants.NEW_LINE + CUS_FORGOT_PIN_LABEL);
	    }
	    else {
	    	if(profile.getBusinessId().equalsIgnoreCase("MZBRB")|| profile.getBusinessId().equalsIgnoreCase("TZNBC")){
	    		profile.setErrorMsg(errorMessage + USSDConstants.NEW_LINE);
	    	}
	    	else{
	    		String FORGOT_PIN_LABEL = ussdResourceBundle.getLabel("FORGOT_PIN_LABEL", new Locale(profile.getLanguage(), profile.getCountryCode()));
	    		profile.setErrorMsg(errorMessage  + USSDConstants.NEW_LINE + FORGOT_PIN_LABEL);
	    		}
	    }
		return responseMsg.generateXmlResponseString(request, profile, msisdnWithCountry);
	    }
	} catch (final USSDBlockingException ube) {
	    LOGGER.error("Blocking Exception occured for the User  : ", ube);
	    throw ube;
	} catch (final IllegalStateException ilste) {
	    LOGGER.error("Session is Expired for the User : ", ilste);
	    return FORWARD_SESSION_INVALID;
	} catch (final Exception e) {
	    LOGGER.error("Exception occured for the User : ", e);
	    if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Profile get the null : ");
	}
	return null;
    }

    /**
     * Authenticate.
     *
     * @param request
     *            the request
     * @param session
     *            the session
     * @return the string
     * @throws USSDBlockingException
     *             the uSSD blocking exception
     * @throws USSDNonBlockingException
     * @throws USSDNonBlockingException
     *             the uSSD non blocking exception
     */
    @RequestMapping(value = "/passwordCheck", method = { RequestMethod.POST })
    public String authenticate(final HttpServletRequest request, final HttpSession session) throws USSDBlockingException, USSDNonBlockingException {

	String page = null;
	String msisdn = null;
	UserProfile profile = null;
	USSDSessionManagement sessionMgmt = null;
	HttpSession httpSession = null;
	//FreeDialUSSD changes
	String extra = null;
	String freeDialArTopUp = "";

	try {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Entering to the authenication method for the user : ");
	    }

	    final USSDRequest ussdRequest = requestMapperObj.getRequestBean(request);
	    msisdn = ussdRequest.getMsisdn();
	    extra = ussdRequest.getExtra();//FreeDialUSSD changes

	    final String password = ussdRequest.getInput();

	    profile = getUserProfile(msisdn, request);
	    sessionMgmt = getUssdSessionMgmt(msisdn, request);
	    profile = userProfileService.authenticate(sessionMgmt, password);
	    if (profile.getErrorMsg() != null) {
		return FORWARD_AUTHENTICATION;
	    }
	    profile.setValidPassword(true);
	    /*
	     * sessionMgmt.setScreenId(USSDConstants.HOME_PAGE_SCREEN_ID); sessionMgmt.setCurrentScreenNodeId(USSDConstants.HOME_PAGE_SCREEN_NODE_ID);
	     */

	    httpSession = sessionHandler.getSessionRequest(request);
	    String businessId = profile.getBusinessId();
	    String countryCode = profile.getCountryCode();
	    boolean sessionCacheEnabled = USSDUtils.isSessionCacheEnabled(businessId, countryCode);

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("SESSION_CACHE_ENABLED=" + sessionCacheEnabled);
	    }
	    // String msisdnWithCountry = sessionMgmt.getMsisdnWithCountry();
	    // if (StringUtils.equalsIgnoreCase("255763210278", msisdnWithCountry) || StringUtils.equalsIgnoreCase("255789783842", msisdnWithCountry)
	    // || StringUtils.equalsIgnoreCase("255000184486", msisdnWithCountry)
	    // || StringUtils.equalsIgnoreCase("255000184481", msisdnWithCountry)) {

	    if (sessionCacheEnabled) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("WALKED INTO THE MAGIC FEATURE");
		}
		if (ussdUserSessionManager.oldSessionExists(ussdRequest.getMsisdnWithCountry(), businessId, countryCode)) {
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Forwarding to use session controller");
		    }
		    return FORWARD_TO_USER_SESSION_CONTROLLER;
		} else {
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Old session does not exist for the user");
		    }
		}

	    }
	    if(null != httpSession)
	    	sessionMgmt = (USSDSessionManagement) httpSession.getAttribute(msisdn);

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("On successful authentication, starting a new http session.");
		if (httpSession != null) {
		    LOGGER.debug("Before creating new session, sessionId=");
		}
	    }

	    // sessiontmp.invalidate();

	    // comment on date 19 Sept
	    // sessionHandler.removeSession(request);
	    // sessiontmp = request.getSession(true);

	    // session.setMaxInactiveInterval(USSDConstants.SESSION_TIME_OUT);
	    if(null != httpSession)
	    	httpSession.setAttribute("auth", "true");
	    if(null != sessionMgmt){
	    	sessionMgmt.setScreenId(USSDConstants.WELCOME_PAGE_SCREEN_ID);
		    sessionMgmt.setCurrentScreenNodeId(USSDConstants.WELCOME_PAGE_NODE_ID);
		    sessionMgmt.setFirstRequest(false);
		    sessionMgmt.setUserProfile(profile);
	    }


	    if (!StringUtils.isEmpty(msisdn)) {
		if (sessionMgmt == null) {
		    session.setAttribute(msisdn, null);
		} else {
		    httpSession.setAttribute(msisdn, sessionMgmt);
		}

	    }
	    ussdServiceEnabler.toString();
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Exit from authenication method for the user  ");
	    }
	    String status=ussdServiceEnabler.getStatusFlag(businessId, extra);
	    if (null != sessionMgmt && sessionMgmt.isPinChangeReq()) {
		page = FORWARD_CHANGE_PIN_CONTROLLER;

	    }else if (null != sessionMgmt && sessionMgmt.isForgotPinFlow()) {
			page = FORWARD_FORGOT_PIN;

	    }else if(status.equals("N"))
	    	throw new USSDBlockingException("BMB90013");
	    else if (extra != null&& status.equals("Y")){//FreeDialUSSD changes
	    	if(extra.equalsIgnoreCase(FREE_DIAL_AIRTIME_TOPUP) || extra.equalsIgnoreCase(FREE_DIAL_AIRTIME) || extra.equalsIgnoreCase(FREE_DIAL_AIRTIME_ZM) || extra.equalsIgnoreCase(FREE_DIAL_MWALLET)){
	    		page = FORWARD_FREE_DIAL;
	    	}else {
	    		page = FORWARD_WELCOME_SCREEN;
	    	}
	    } else {
	    	page = FORWARD_WELCOME_SCREEN;
	    }
	} /*catch(final USSDNonBlockingException nbe){
		 LOGGER.fatal("USSDBlockingException occured for the User  : ", nbe);
		 throw nbe;
	}*/
	catch (final USSDBlockingException ube) {
	    LOGGER.fatal("USSDBlockingException occured for the User  : ", ube);
	    throw ube;
	} catch (final IllegalStateException ilste) {
	    LOGGER.fatal("Session is Expired for the User : ", ilste);
	    return FORWARD_SESSION_INVALID;
	} catch (final Exception e) {
	    LOGGER.error("Exception occured for the User : ", e);
	    if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}

	return page;
    }

    /**
     * Error msg.
     *
     * @param profile
     *            the profile
     * @param ussdSessionMgmt
     *            the ussd session mgmt
     * @return the string
     * @throws Exception
     *             the exception
     */
    /*
     * @RequestMapping(method = { RequestMethod.POST })
     *
     * @ResponseBody public String errorMsg(final UserProfile profile, final USSDSessionManagement ussdSessionMgmt) throws Exception { try { if
     * (LOGGER.isInfoEnabled()) { LOGGER.info("Entering to the Error Message method...."); } setSessionAttributeFromMSISDN(profile, ussdSessionMgmt);
     * } catch (final IllegalStateException ilste) { if (LOGGER.isDebugEnabled()) { LOGGER.debug("Session is Expired for the User : " +
     * profile.getMsisdn()); } return FORWARD_SESSION_INVALID; } catch (final Exception e) { if (LOGGER.isDebugEnabled()) {
     * LOGGER.debug("Exception occured for the User : " + profile.getMsisdn()); } return FORWARD_SESSION_INVALID; }
     * LOGGER.info("Exit from Error Message method...."); return FORWARD_AUTHENTICATION; }
     */

    /**
     * Call ussd main controller.
     *
     * @param map
     *            the map
     * @param request
     *            the request
     * @param session
     *            the session
     * @return the string
     * @throws Exception
     *             the exception
     */
    @RequestMapping(value = "/navigation", method = { RequestMethod.POST })
    public String callUSSDMainController(final ModelMap map, final HttpServletRequest request, final HttpSession session) throws Exception {
	final USSDRequest ussdRequest = requestMapperObj.getRequestBean(request);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Entering to callUSSDMainController method for the User....");
	}
	final String inputTxt = ussdRequest.getInput();
	HttpSession sessiontmp = sessionHandler.getSessionRequest(request);
	// added for the test BMG
	if (null == sessiontmp) {
	    LOGGER.error("From callUSSDMainController method of Auth Controller for the User....  getting the Session Null.");
	    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Exit from callUSSDMainController method for the User.");
	}
	return FORWARD_USSDHOME + "?optionId=" + inputTxt;

    }

    /**
     * Error msg.
     *
     * @param msisdn
     *            the msisdn
     * @param levelId
     *            the level id
     * @return the string
     * @throws Exception
     *             the exception
     */

    // @RequestMapping(value = "errorMsg", method = { RequestMethod.POST })
    // @ResponseBody
    // public String errorMsg(final String msisdn, final String levelId) throws Exception {
    //
    // try {
    // if (levelId != null) {
    // final USSDSessionManagement sessionMgmt = profileSessionServInter.getUserFromSessionMgmt(msisdn);
    // final UserProfile profile = profileSessionServInter.loadUserFromSession(msisdn);
    // final String errorMessage = ussdResourceBundle.getLabel(levelId, new Locale(sessionMgmt.getUserProfile().getLanguage(), sessionMgmt
    // .getCountryCode()));
    // profile.setErrorMsg(errorMessage);
    // sessionMgmt.setUserProfile(profile);
    // setSessionAttributeFromMSISDN(profile, sessionMgmt);
    // }
    //
    // } catch (final IllegalStateException ilste) {
    // LOGGER.error("Session is Exprired for the User : " + msisdn, ilste);
    // return FORWARD_SESSION_INVALID;
    // } catch (final Exception e) {
    // LOGGER.error("Exception occured for the User : " + msisdn, e);
    // return FORWARD_SESSION_INVALID;
    // }
    // return FORWARD_AUTHENTICATION;
    // }
    /**
     * If User is block means user entered the wrong password multiple times.
     *
     * @param request
     *            the request
     * @return the string
     * @throws SystemException
     *             the system exception
     */

    // @RequestMapping(value = "pwdMsgErr", method = { RequestMethod.POST })
    // @ResponseBody
    // public String pwdMsgErr(final HttpServletRequest request) {
    // String errorMessage = null;
    // String msisdn = null;
    // try {
    // final USSDRequest ussdRequest = requestMapper.getRequestBean(request);
    // msisdn = ussdRequest.getMsisdn();
    // final UserProfile profile = profileSessionServInter.loadUserFromSession(msisdn);
    // if (profile.getErrorMsg() != null) {
    // errorMessage = ussdResourceBundle.getLabel(LBL_PASS_BLCK, new Locale(profile.getLanguage(), USSDUtils.getCountryCode(ussdRequest)));
    // profile.setErrorMsg(errorMessage);
    // } else {
    // errorMessage = ussdResourceBundle.getLabel(profile.getErrorMsg(), new Locale(profile.getLanguage(), USSDUtils
    // .getCountryCode(ussdRequest)));
    // profile.setErrorMsg(errorMessage);
    // }
    // sessionHandler.removeSession(msisdn, request);
    // } catch (final IllegalStateException ilste) {
    // if (LOGGER.isDebugEnabled()) {
    // LOGGER.debug("Session is Exprired for the User : " + msisdn);
    // }
    // return FORWARD_SESSION_INVALID;
    // } catch (final Exception e) {
    // LOGGER.error("Exception occured for the User : " + msisdn, e);
    // return FORWARD_SESSION_INVALID;
    // }
    // return errorMessage;
    // }
    /**
     * Session invailid error.
     *
     * @param request
     *            the request
     * @param session
     *            the session
     * @return the string
     * @throws USSDBlockingException
     */
    @RequestMapping(value = "/sessionInvalid", method = { RequestMethod.POST })
    @ResponseBody
    public String sessionInvailidError(final HttpServletRequest request, final HttpSession session) throws USSDBlockingException {
	final USSDRequest ussdRequest = requestMapperObj.getRequestBean(request);
	// String locale = USSDUtils.getLocale(this.ussdMenuBuilder,
	// this.readProperties.getDefaultCountryCode(ussdRequest.getMsisdnNumber()));
	// TODO: Take this from user session
	String locale;

	locale = ussdMenuBuilder.getDefaultLocale(USSDUtils.getCountryCode(ussdRequest), ussdRequest.getBusinessId());

	final String response = new ResponseMessage().responseMessageFromMenuList(ussdResourceBundle.getLabel(USSDConstants.USSD_SESSION_INVALID,
		new Locale(locale)), USSDConstants.STATUS_END);

	sessionHandler.removeSession(request);
	return responseMsg.generateXmlRespBlockingException(request, response, ussdRequest.getMsisdnWithCountry());
	// return response;
    }

    /**
     * Sets the session attribute from msisdn.
     *
     * @param profile
     *            the profile
     * @param ussdSessionMgmt
     *            the ussd session mgmt
     * @param msisdnWithCountry
     */
    private void setSessionAttributeFromMSISDN(String msisdn, HttpServletRequest request, USSDSessionManagement ussdSessionMgmt,
	    String msisdnWithCountry) {
	try {
	    HttpSession sessionTemp = sessionHandler.getSessionRequest(request);
	    sessionTemp.setAttribute(msisdn, ussdSessionMgmt);
	    sessionTemp.setAttribute(USSDSessionConstants.MSISDN_WITH_COUNTRY, msisdnWithCountry);
	} catch (final Exception e) {
	    LOGGER.error(e.getMessage(), e);
	}

    }

    /**
     * Inits the session.
     *
     * @param profile
     *            the profile
     * @param languageWithCountryCode
     *            the language with country code
     * @param ussdSessionMgmt
     *            the ussd session mgmt
     * @throws USSDBlockingException
     */
    private void initSession(final UserProfile profile, final USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	String welcomeMessage = null;
	String languageCode = null;
	
	//Change of welcome message for Botswana. INC INC0063990
	String bocBannerFlag = null;
	String bannerMessage = null;
	try {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Initializing the session for the User::  ");
	    }
	    if ((profile.getErrorMsg() != null) && !profile.getErrorMsg().equals("null")) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Error occured during the session initilization for the User::  ");
		}
		welcomeMessage = profile.getErrorMsg();
	    } else {
		if (profile.getLanguage() == null) {
		    languageCode = getDefaultCountryLanguage(ussdSessionMgmt);
		} else {
		    languageCode = profile.getLanguage();
		}

		if (null != ussdSessionMgmt && MIGRATED.equals(profile.getUsrSta())) {
		    welcomeMessage = ussdResourceBundle.getLabel(WELCOME_MSG_MIGRATED, new Locale(languageCode, ussdSessionMgmt.getCountryCode()));
		} else if(null != ussdSessionMgmt){
		    welcomeMessage = ussdResourceBundle.getLabel(WELCOME_MSG, new Locale(languageCode, ussdSessionMgmt.getCountryCode()));

		//Change of welcome message for Botswana. INC INC0063990
		 if(null != profile.getBocBannerFlag())
			 bocBannerFlag = profile.getBocBannerFlag();
		 
		 if(null != profile.getBannerMessage())
		     bannerMessage = profile.getBannerMessage();
		 
		 if(null != bocBannerFlag && null != bannerMessage && bocBannerFlag.equalsIgnoreCase("Y"))
		 {
			 welcomeMessage = ussdResourceBundle.getLabel(BANNER_WELCOME_MSG, new Locale(languageCode, ussdSessionMgmt.getCountryCode()));
			 welcomeMessage = welcomeMessage.concat(USSDConstants.NEW_LINE);
			 welcomeMessage = welcomeMessage.concat(bannerMessage);
					 
		 }
		 else
		 {
			// CR-77
				if(null != profile.getCustomerFirstName() && !profile.getCustomerFirstName().equalsIgnoreCase(" ") ) {
					String customerFirstName = profile.getCustomerFirstName();
					List<String> params = new ArrayList<String>(1);
					params.add(customerFirstName);
					String[] paramArray = params.toArray(new String[params.size()]);
					welcomeMessage =  ussdResourceBundle.getLabel(WELCOME_MSG_CUSTOMERNAME, paramArray,
							new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
				}
				 if(ussdSessionMgmt.getBusinessId().equals("TZNBC") && languageCode!=null && languageCode.equalsIgnoreCase("sw")){
					 welcomeMessage=welcomeMessage.concat(" ");
				 }else{
					 welcomeMessage = welcomeMessage.concat(USSDConstants.NEW_LINE);
				 }
				 if(null != ussdSessionMgmt)
					 welcomeMessage = welcomeMessage.concat(ussdResourceBundle.getLabel(LBL_PASS, new Locale(languageCode, ussdSessionMgmt
				    .getCountryCode())));
			    //Forgot Pin Change
			    if(null != ussdSessionMgmt && !ussdSessionMgmt.getBusinessId().equals("MZBRB"))
			    welcomeMessage = welcomeMessage.concat(" "+ussdResourceBundle.getLabel(LABEL_FORGOT_PIN_PRESS, new Locale(languageCode, ussdSessionMgmt
					    .getCountryCode())));
		 }
		    
		 
		}
	    }
	    if(null != ussdSessionMgmt)
	    	ussdSessionMgmt.setScreenId(USSDConstants.HOME_PAGE_SCREEN_ID);
	    //welcomeMessage = "Please enter your pin to continue. Forgot Pin ? Press 1" + USSDConstants.NEW_LINE + USSDConstants.NEW_LINE +"ENJOY DISCOUNTS" + USSDConstants.NEW_LINE + 
	    		//"Swipe at Monsieur and enjoy more Cashback rewards next month.";
	    profile.setMsg(welcomeMessage);
	    profile.setLanguage(languageCode);
	    if(null != ussdSessionMgmt)
	    	ussdSessionMgmt.setUserProfile(profile);

	} catch (final Exception e) {
	    LOGGER.error("Fatal Error occured during the session initilization for the User   and Exit from the method", e);
	    /*if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {*/
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    //}

	}
    }

    private String getDefaultCountryLanguage(USSDSessionManagement ussdSessionMgmt) {
	String countryLanguage = null;
	countryLanguage = ussdMenuBuilder.getDefaultLocale(ussdSessionMgmt.getCountryCode(), ussdSessionMgmt.getBusinessId());
	return countryLanguage.split(USSDConstants.UNDERSCORE_SEPERATOR)[0];
    }

    /**
     * Checks if is input null.
     *
     * @param parameter
     *            the parameter
     * @return true, if is input null
     */
    @SuppressWarnings("unused")
    private boolean isInputNull(final String parameter) {
	if ((null == parameter) || "null".equalsIgnoreCase(parameter)) {
	    return true;
	}

	return false;
    }

    /**
     * Gets the response msg.
     *
     * @return the response msg
     */
    public ResponseMessage getResponseMsg() {
	return responseMsg;
    }

    /**
     * Sets the response msg.
     *
     * @param responseMsg
     *            the new response msg
     */
    public void setResponseMsg(final ResponseMessage responseMsg) {
	this.responseMsg = responseMsg;
    }

    /**
     * Gets the resource bundle.
     *
     * @return the resource bundle
     */
    public UssdResourceBundle getResourceBundle() {
	return ussdResourceBundle;
    }

    /**
     * Sets the resource bundle.
     *
     * @param resourceBundle
     *            the new resource bundle
     */
    public void setResourceBundle(final UssdResourceBundle resourceBundle) {
	ussdResourceBundle = resourceBundle;
    }

    /**
     * Get the User Profile from the UssdSessionManagment.
     *
     * @param msdinNumber
     * @param request
     * @return UserProfile
     */
    private UserProfile getUserProfile(String msdinNumber, HttpServletRequest request) {
	USSDSessionManagement ussdSessionMgmt = getUssdSessionMgmt(msdinNumber, request);
	if (ussdSessionMgmt == null)
	    return null;
	return ussdSessionMgmt.getUserProfile();
    }

    private USSDSessionManagement getUssdSessionMgmt(String msdinNumber, HttpServletRequest request) {
	HttpSession httpSession = sessionHandler.getSessionRequest(request);
	return (USSDSessionManagement) httpSession.getAttribute(msdinNumber);
    }

    @Override
    public void setServletContext(ServletContext ussdContext) {
	this.ussdContext = ussdContext;

    }

    public void setRequestMapperObj(USSDBaseRequestMapper requestMapperObj) {
	this.requestMapperObj = requestMapperObj;
    }

    public void setUssdUserSessionManager(USSDUserSessionManager ussdUserSessionManager) {
	this.ussdUserSessionManager = ussdUserSessionManager;
    }
}