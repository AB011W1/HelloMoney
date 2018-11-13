package com.barclays.ussd.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.barclays.ussd.auth.USSDresquest.USSDBaseRequestMapper;
import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.LocaleDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.controller.USSDAbstractController;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDUtils;

/**
 * The Class NonHMCustomerController.
 */
@Controller
public class NonHMCustomerController extends USSDAbstractController {

    private static final String TRUE = "true";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(NonHMCustomerController.class);

    /** The Constant FORWARD_NAVIGATION. */
    private static final String FORWARD_NAVIGATION = ConfigurationManager.getString("forwardNevigation");

    /** The Constant FORWARD_SESSION_INVALID. */
    private static final String FORWARD_SESSION_INVALID = ConfigurationManager.getString("forwardSessionInvld");

    /** The configuration manager. */
    protected ConfigurationManager configurationManager;

    @Autowired
    protected USSDBaseRequestMapper requestMapperObj;

    /**
     * unregistered user.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the string
     * @throws USSDBlockingException
     * @throws Exception
     */
    @RequestMapping(value = "/unreguserwelcomepage", method = { RequestMethod.POST })
    public String unregisteredUserMenuHandler(final HttpServletRequest request, final HttpServletResponse response) throws USSDBlockingException {
	LOGGER.debug("Entering the unregisteredUserMenuHandler in Base controller class.");
	String msisdn = null;

	try {
	    final USSDRequest ussdRequest = requestMapperObj.getRequestBean(request);
	    responseEncoding(response);
	    msisdn = ussdRequest.getMsisdn();
	    ussdRequest.setInput(USSDConstants.UNREG_USER_WELCOME_PAGE_DEFAULT_INPUT);
	    HttpSession session = request.getSession();
	    request.setAttribute(USSDConstants.REQOBJ, ussdRequest);

	    session.setAttribute(USSDConstants.NON_HM_CUSTOMER_FLAG, TRUE);
	    HttpSession sessiontmp = sessionHandler.getSessionRequest(request);
	    USSDSessionManagement ussdSessionMgmt = (USSDSessionManagement) sessiontmp.getAttribute(msisdn);
	    ussdSessionMgmt = ussdSessionMgmt == null ? new USSDSessionManagement() : ussdSessionMgmt;
	    UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	    if (userProfile == null) {
		userProfile = new UserProfile();
		LocaleDTO defaultLocaleDTO = ussdMenuBuilder.getDefaultLocaleDTO(USSDUtils.getCountryCode(ussdRequest), ussdRequest.getBusinessId());

		userProfile.setLanguage(defaultLocaleDTO.getLanguage());
		userProfile.setCountryCode(defaultLocaleDTO.getCountry());
		userProfile.setMsisdn(msisdn);
		userProfile.setBusinessId(ussdRequest.getBusinessId());
		ussdSessionMgmt.setUserProfile(userProfile);
	    }
	    ussdSessionMgmt.setMsisdnNumber(msisdn);

	    ussdSessionMgmt.setCountryCode(USSDUtils.getCountryCode(ussdRequest));
	    ussdSessionMgmt.setBusinessId(ussdRequest.getBusinessId());
	    ussdSessionMgmt.setScreenId(USSDConstants.UNREG_USER_WELCOME_PAGE_SCREEN_ID);
	    ussdSessionMgmt.setCurrentScreenNodeId(USSDConstants.UNREG_USER_WELCOME_PAGE_NODE_ID);
	    ussdSessionMgmt.setCustomerType(USSDConstants.CUSTOMER_TYPE_NON_HM);
	    ussdSessionMgmt.setTransactionFlag(false);
	    ussdSessionMgmt.setNonHMCustomerFlag(TRUE);

	    session.setAttribute(msisdn, ussdSessionMgmt);

	    LOGGER.debug("Forwarding to the Main Menu Controller");
	} catch (final IllegalStateException ilste) {
	    LOGGER.fatal("Some exception occured while serving the request for the user : " + msisdn, ilste);
	    return FORWARD_SESSION_INVALID;
	} catch (Exception e) {
	    LOGGER.fatal("Some system exception occured for the user: " + msisdn, e);

	    if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }

	}

	return FORWARD_NAVIGATION;
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
}
