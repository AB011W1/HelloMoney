package com.barclays.ussd.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.barclays.ussd.auth.USSDresquest.USSDBaseRequestMapper;
import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.controller.USSDAbstractController;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.svc.httpclient.HttpReqResContextHolder;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSessionConstants;
import com.barclays.ussd.utils.UssdMenuBuilder;

/**
 * The Class BaseController.
 */
@Controller
public class BaseController extends USSDAbstractController {

    private static final String TRUE = "true";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(BaseController.class);

    /** The Constant FORWARD_NAVIGATION. */
    private static final String FORWARD_NAVIGATION = ConfigurationManager.getString("forwardNevigation");

    /** The Constant FORWARD_PASSWORD_CHECK. */
    private static final String FORWARD_PASSWORD_CHECK = ConfigurationManager.getString("forwardPwdChk");

    /** The Constant FORWARD_SESSION_INVALID. */
    private static final String FORWARD_SESSION_INVALID = ConfigurationManager.getString("forwardSessionInvld");

    /** The forward auth controller. */
    private final String FORWARD_AUTH_CONTROLLER = ConfigurationManager.getString("forwardAuth");

    /** The forward changepin handler. */
    private final String FORWARD_CHANGE_PIN_CONTROLLER = ConfigurationManager.getString("forwardToChangePin");

    private static final String FORWARD_HELLO_MONEY = ConfigurationManager.getString("forwardToHelloMoney");

    private final String FORWARD_TO_CHANGE_LANGUAGE_CONTROLLER = ConfigurationManager.getString("forwardToChangeLanuageController");

    private static final String FORWARD_TO_USER_MIGRATION_CONTROLLER = ConfigurationManager.getString("forwardToUserMigrationController");

    private static final String LOGIN_ACTION = "LOGIN";

    /** The configuration manager. */
    protected ConfigurationManager configurationManager;

    @Autowired
    protected UssdMenuBuilder ussdMenuBuilder;

    @Autowired
    protected USSDBaseRequestMapper requestMapperObj;

    /**
     * Handler.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the string
     * @throws USSDBlockingException
     *             the uSSD blocking exception
     */
    @RequestMapping(value = "/hellomoney", method = { RequestMethod.POST })
    public String handler(HttpServletRequest request, HttpServletResponse response) throws USSDBlockingException {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Base handler starts...");
	}
	String msisdn = null;
	String page = FORWARD_AUTH_CONTROLLER;
	String inputTxt = null;
	HttpReqResContextHolder.setRequest(request);
	HttpReqResContextHolder.setResponse(response);
	HttpSession httpSession = null;
	String action = null;
	try {
	    final USSDRequest ussdRequest = requestMapperObj.getRequestBean(request);
	    responseEncoding(response);
	    msisdn = ussdRequest.getMsisdn();
	    request.setAttribute(USSDConstants.REQOBJ, ussdRequest);
	    inputTxt = ussdRequest.getInput();
	    action = (String) request.getAttribute(USSDSessionConstants.INPUT_ACTION);
	    request.setAttribute("optionId", inputTxt);

	    if (isLoginAction(action) || sessionHandler.isNewRequest(request)) {
		return FORWARD_AUTH_CONTROLLER;
	    } else {
		httpSession = sessionHandler.getSessionRequest(request);
		USSDSessionManagement ussdSessionMgmt = (USSDSessionManagement) httpSession.getAttribute(msisdn);
		String isNotHMCustomer = null;
		if (ussdSessionMgmt != null) {
		    isNotHMCustomer = ussdSessionMgmt.getNonHMCustomerFlag();
		}

		String isLanguageChangeFlow = null;
		if (ussdSessionMgmt != null) {
		    isLanguageChangeFlow = ussdSessionMgmt.getChangeLanguageMode();
		}
		/*
		 * if (ussdSessionMgmt.isUserMigration()) { return FORWARD_TO_USER_MIGRATION_CONTROLLER; }
		 */

		if (StringUtils.equalsIgnoreCase(isLanguageChangeFlow, TRUE)) {
		    return FORWARD_TO_CHANGE_LANGUAGE_CONTROLLER;
		}

		if (StringUtils.equalsIgnoreCase(isNotHMCustomer, TRUE)) {
		    return FORWARD_NAVIGATION;
		}

		if (ussdSessionMgmt == null || ussdSessionMgmt.isFirstRequest()) {
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Forward to Auth Controller for authentication of the user:");
		    }
		    if(ussdRequest.getInput().equals("1")&&!ussdSessionMgmt.getBusinessId().equals("MZBRB")){
		    	return "/hellomoney/forgotpinpage";
		    }
		    return FORWARD_PASSWORD_CHECK;
		} else if (ussdSessionMgmt.getUserProfile().isValidPassword()) {
		    if (ussdSessionMgmt.isPinChangeReq()) {
			return FORWARD_CHANGE_PIN_CONTROLLER;
		    }
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Forward to menu navigation; user:");
		    }
		    return FORWARD_NAVIGATION;
		}

	    }
	} catch (final USSDBlockingException ube) {
	    LOGGER.fatal("USSDBlockingException occured", ube);
	    throw ube;
	} catch (final IllegalStateException ilste) {
	    LOGGER.fatal("IllegalStateException occured", ilste);
	    return FORWARD_SESSION_INVALID;
	} catch (final Exception e) {
	    LOGGER.error("Exception occured" + msisdn, e);
	    //if (e instanceof USSDBlockingException) {
		//throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    //} else {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    //}

	}

	return page;

    }

    /**
     * Check whether Action is LOGIN
     *
     * @param action
     * @return
     */
    private boolean isLoginAction(String action) {
	return StringUtils.equalsIgnoreCase(LOGIN_ACTION, action);
    }

    /**
     * Set the response encoding that is special character.
     *
     * @param response
     */
    private void responseEncoding(final HttpServletResponse response) {
	response.setHeader("Access-Control-Allow-Origin", "*");
	response.setCharacterEncoding("UTF-8");
    }

    @RequestMapping(value = "/selcom", method = { RequestMethod.POST })
    public String selcomHandler(HttpServletRequest request, HttpServletResponse response) throws USSDBlockingException {
	request.setAttribute(USSDConstants.AGGREGATOR, "selcom");
	HttpReqResContextHolder.setRequest(request);
	HttpReqResContextHolder.setResponse(response);
	return FORWARD_HELLO_MONEY;
    }

    @RequestMapping(value = "/cellulant", method = { RequestMethod.POST })
    public String cellulantHandler(HttpServletRequest request, HttpServletResponse response) throws USSDBlockingException {
	request.setAttribute(USSDConstants.AGGREGATOR, "cellulant");
	return FORWARD_HELLO_MONEY;
    }

    @RequestMapping(value = "/etransact", method = { RequestMethod.POST })
    public String etransactHandler(HttpServletRequest request, HttpServletResponse response) throws USSDBlockingException {
	request.setAttribute(USSDConstants.AGGREGATOR, "etransact");
	return FORWARD_HELLO_MONEY;
    }

    @RequestMapping(value = "/trueafrican", method = { RequestMethod.POST })
    public String trueafricanHandler(HttpServletRequest request, HttpServletResponse response) throws USSDBlockingException {
	request.setAttribute(USSDConstants.AGGREGATOR, "trueafrican");
	return FORWARD_HELLO_MONEY;
    }

    public void setRequestMapperObj(USSDBaseRequestMapper requestMapperObj) {
	this.requestMapperObj = requestMapperObj;
    }
}
