package com.barclays.ussd.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
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
 * The Class BaseController.
 */
@Controller
public class FreeDialController extends USSDAbstractController {

    private static final String TRUE = "true";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(FreeDialController.class);

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
    @RequestMapping(value = "/freedialpage", method = { RequestMethod.POST })
    public String handler(final HttpServletRequest request, final HttpServletResponse response) throws USSDBlockingException {
	LOGGER.debug("FreedialController Base handler starts...");
	String msisdn = null;

	try {
	    final USSDRequest ussdRequest = requestMapperObj.getRequestBean(request);
	    responseEncoding(response);
	    msisdn = ussdRequest.getMsisdn();
	    //ussdRequest.setInput(USSDConstants.UNREG_USER_WELCOME_PAGE_DEFAULT_INPUT);
	    HttpSession session = request.getSession();
	    request.setAttribute(USSDConstants.REQOBJ, ussdRequest);

	    //session.setAttribute(USSDConstants.NON_HM_CUSTOMER_FLAG, false);
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
	    ussdSessionMgmt.setScreenId("300");
	    ussdSessionMgmt.setCurrentScreenNodeId("ussd4.00");

	    if(ussdRequest.getExtra().equals("FREEDIALAIRTEL")){
		    ussdSessionMgmt.setScreenId("400");
		    ussdSessionMgmt.setCurrentScreenNodeId("ussd4.01");
		    Map<String,Object> userInputMap=new HashMap<String,Object>();
		    userInputMap.put("extra", "FREEDIALAIRTEL");
		    userInputMap.put("txnAmt", ussdRequest.getAmount());
		    userInputMap.put("mblNo", msisdn);

		    ussdSessionMgmt.setTxSessions(userInputMap);
	    }
	  //added for Zambia (mani)
	    if(ussdRequest.getExtra().equals("FREEDIALAIRTELZM")){
		    ussdSessionMgmt.setScreenId("400");
		    ussdSessionMgmt.setCurrentScreenNodeId("ussd5.00");
		    Map<String,Object> userInputMap=new HashMap<String,Object>();
		    userInputMap.put("extra", "FREEDIALAIRTELZM");
		    userInputMap.put("txnAmt", ussdRequest.getAmount());
		    userInputMap.put("mblNo", msisdn);

		    ussdSessionMgmt.setTxSessions(userInputMap);
	    }

	    ussdSessionMgmt.setCustomerType(USSDConstants.CUSTOMER_TYPE_USER_SESSION_RETENTION);
	    ussdSessionMgmt.setTransactionFlag(false);
	    ussdSessionMgmt.setFreeDialUssdFlow(true);
	    //Added because of base controller check
	    ussdSessionMgmt.setFirstRequest(false);
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
