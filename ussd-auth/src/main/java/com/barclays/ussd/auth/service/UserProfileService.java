package com.barclays.ussd.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.auth.controller.USSDByPassXMLMenuManager;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.parser.UssdDecisionParser;
import com.barclays.ussd.parser.UssdDecisionParserParamDTO;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerProfile;

@Service
public class UserProfileService {

    private static final String AUTH_N_VERIFY_SERVICE = "AuthNVerify";

    private static final String SELF_REGISTRATION_SERVICE = "SelfRegistration";

    @Autowired
    UssdDecisionParser ussdDecisionParser;

    @Autowired
    USSDByPassXMLMenuManager ussdByPassXMLMenuManager;

    private static final Logger LOGGER = Logger.getLogger(UserProfileService.class);

    public UserProfile getProfile(String msisdn, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	UserProfile profile = null;
	List<String> errorCodes = null;

	try {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Entering to DataUserProfile.");
	    }

	    errorCodes = new ArrayList<String>();

	    UssdDecisionParserParamDTO paramDTO = new UssdDecisionParserParamDTO();
	    paramDTO.setServiceName(AUTH_N_VERIFY_SERVICE);
	    paramDTO.setMsisdn(msisdn);
	    paramDTO.setCountryCode(ussdSessionMgmt.getCountryCode());
	    paramDTO.setBusinessId(ussdSessionMgmt.getBusinessId());

	    MenuItemDTO menuItemDTO = ussdByPassXMLMenuManager.handleByePassRequest(paramDTO, ussdSessionMgmt, errorCodes);
	    profile = ussdSessionMgmt.getUserProfile();

	    if (menuItemDTO == null) {
		LOGGER.fatal("SvcFulfilmentExceptionoccured as menuItemDTO is null.");
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	    }

	} catch (USSDBlockingException ube) {
	    LOGGER.fatal("USSDBlockingException occured: ");
	    String errorCode = (ube.getErrCode() == null || StringUtils.isEmpty(ube.getErrCode())) ? USSDExceptions.USSD_TECH_ISSUE.getBmgCode()
		    : ube.getErrCode();
	    throw new USSDBlockingException(errorCode, ube.getMessage());
	} catch (Exception e) {
	    LOGGER.fatal("Exception occured.", e);
	    throw new USSDBlockingException(USSDExceptions.AUTH_FAIL.getUssdErrorCode(), e.getMessage());
	}

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Exit from DataUserProfile.");
	}
	return profile;
    }

    /**
     * @param profile
     * @param ussdMenuBuilder
     * @param password
     * @param msisdn
     * @return UserProfile
     * @throws USSDBlockingException
     *
     * @throws USSDBlockingException
     *
     */
    public UserProfile authenticate(USSDSessionManagement sessionMgmt, String password) throws USSDBlockingException {
	List<String> errorCodes = new ArrayList<String>();
	Map<String, Object> authRespMap = null;
	MenuItemDTO menuItemDTO = null;
	UserProfile profile = null;

	try {

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Entering to DataUserProfile.");
	    }

	    UssdDecisionParserParamDTO paramDTO = new UssdDecisionParserParamDTO();
	    profile = sessionMgmt.getUserProfile();
	    paramDTO.setMsisdn(profile.getMsisdn());
	    paramDTO.setByPassRequest(true);
	    paramDTO.setUserInput(password);
	    paramDTO.setCountryCode(sessionMgmt.getCountryCode());
	    paramDTO.setBusinessId(sessionMgmt.getBusinessId());
	    paramDTO.setServiceName(AUTH_N_VERIFY_SERVICE);
	    menuItemDTO = ussdByPassXMLMenuManager.handleByePassRequest(paramDTO, sessionMgmt, errorCodes);
	    // menuItemDTO = ussdDecisionParser.callTheNonXMLParser(paramDTO,
	    // sessionMgmt, errorCodes);
	    if (menuItemDTO.getPageError() != null) {
		profile.setErrorMsg(menuItemDTO.getPageError());
		return profile;
	    }
	    authRespMap = menuItemDTO.getUserAuthRespMap();

	    AuthUserData authUserData = (AuthUserData) authRespMap.get(USSDConstants.AUTH_USR_RESP);

	    if (null == authUserData) {
		throw new USSDNonBlockingException(USSDExceptions.AUTH_FAIL.getUssdErrorCode());
	    } else {
		CustomerProfile custProf = authUserData.getPayData().getCustProf();
		if (custProf.getPrefLang() != null) {
		    profile.setLanguage(custProf.getPrefLang());
		}
		if (!StringUtils.equalsIgnoreCase(USSDConstants.USER_STATUS_MIGRATED, profile.getUsrSta()))
		    profile.setUsrSta(custProf.getUsrSta());

		if (USSDConstants.PIN_CHANGE_REQ.equalsIgnoreCase(custProf.getPinSta())) {
		    sessionMgmt.setPinChangeReq(true);
		}
		//Forgot Pin Change
		if (USSDConstants.FORGOT_PIN_REQ.equalsIgnoreCase(custProf.getPinSta())) {
		    sessionMgmt.setForgotPinFlow(true);
		}
		profile.setPinSta(custProf.getPinSta());
		profile.setErrorMsg(null);
		sessionMgmt.setFirstRequest(false);
	    }
	} catch (USSDBlockingException ube) {
	    // TODO: Error code assignment to be checked as to why we cannot pass the same error code coming from the exception.
	    LOGGER.fatal("USSDBlockingException occured: ", ube);
	    String errorCode = (ube.getErrCode() == null || StringUtils.isEmpty(ube.getErrCode())) ? USSDExceptions.USSD_TECH_ISSUE.getBmgCode()
		    : ube.getErrCode();
	    throw new USSDBlockingException(errorCode, ube.getMessage());

	} catch (Exception e) {
	    LOGGER.fatal("other exception occured: " + e.getMessage(), e);
	    throw new USSDBlockingException(USSDExceptions.AUTH_FAIL.getUssdErrorCode(), e.getMessage());
	}
	return profile;
    }

    public MenuItemDTO selfRegService(String msisdn, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	List<String> errorCodes = null;
	MenuItemDTO menuItemDTO = null;

	try {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Entering to DataUserProfile.");
	    }

	    errorCodes = new ArrayList<String>();

	    UssdDecisionParserParamDTO paramDTO = new UssdDecisionParserParamDTO();
	    paramDTO.setServiceName(SELF_REGISTRATION_SERVICE);
	    paramDTO.setMsisdn(msisdn);

	    menuItemDTO = ussdByPassXMLMenuManager.handleByePassRequest(paramDTO, ussdSessionMgmt, errorCodes);
	    if (menuItemDTO == null) {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	    }

	} catch (Exception e) {
	    LOGGER.fatal("Exception ocurred in selfRegService method ", e);
	}
	return menuItemDTO;
    }

    public MenuItemDTO selfRegistrationHandler(USSDSessionManagement sessionMgmt, String input) throws USSDBlockingException,
	    USSDNonBlockingException, Exception {
	List<String> errorCodes = new ArrayList<String>();
	MenuItemDTO menuItemDTO = null;
	UserProfile profile = null;

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Entering to DataUserProfile.");
	}

	UssdDecisionParserParamDTO paramDTO = new UssdDecisionParserParamDTO();
	profile = sessionMgmt.getUserProfile();
	paramDTO.setMsisdn(profile.getMsisdn());
	paramDTO.setServiceName(SELF_REGISTRATION_SERVICE);
	paramDTO.setByPassRequest(true);
	paramDTO.setUserInput(input);
	paramDTO.setCountryCode(sessionMgmt.getCountryCode());

	menuItemDTO = ussdByPassXMLMenuManager.handleByePassRequest(paramDTO, sessionMgmt, errorCodes);
	return menuItemDTO;
    }
}
