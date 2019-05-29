package com.barclays.ussd.auth.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barclays.ussd.auth.bean.CasaAccount;
import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.auth.response.ResponseMessage;
import com.barclays.ussd.bean.CurrentRunningTransaction;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.Transaction;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingErrorCodes;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.parser.UssdDecisionParser;
import com.barclays.ussd.parser.UssdDecisionParserParamDTO;
import com.barclays.ussd.sessionMgmt.USSDSessionHandler;
import com.barclays.ussd.utils.ExceptionUtils;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.UssdResourceBundle;

public class USSDByPassXMLMenuManager {

    private static final String LABEL_SELECT = "label.select";

    @Autowired
    private UssdDecisionParser ussdDecisionParser;

    @Autowired
    USSDSessionHandler sessionHandler;

    /** The ussd resource bundle. */
    @Autowired
    UssdResourceBundle ussdResourceBundle;

    public MenuItemDTO handleByePassRequest(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement, List<String> errorCodes)
	    throws USSDBlockingException, USSDNonBlockingException, Exception {
	MenuItemDTO menuItemDTO = null;
	String msisdn = paramDTO.getMsisdn();
	UserProfile profile = ussdSessionManagement.getUserProfile();

	if (profile == null) {
	    profile = new UserProfile();
	    ussdSessionManagement.setMsisdnNumber(msisdn);
	    ussdSessionManagement.setUserProfile(profile);
	    Transaction transaction = new Transaction();
	    transaction.setCurrentRunningTransaction(new CurrentRunningTransaction());
	    ussdSessionManagement.setUserTransactionDetails(transaction);
	}

	paramDTO.setByPassRequest(true);
	paramDTO.setBusinessId(ussdSessionManagement.getBusinessId());
	ussdSessionManagement.setTransactionFlag(true);
	menuItemDTO = ussdDecisionParser.getMenuList(paramDTO, ussdSessionManagement);

	return menuItemDTO;
    }

    public MenuItemDTO handleWelcomeScreenRequest(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement,
	    List<String> errorCodes) throws USSDBlockingException, USSDNonBlockingException, Exception {
	MenuItemDTO menuItemDTO = null;
	try {
	    MenuItemDTO accntListMenuDTO = getPrimaryAccountBalance(paramDTO, ussdSessionManagement, errorCodes);

	    MenuItemDTO menuOptionMenuDTO = generateLandingPageMenu(paramDTO, ussdSessionManagement);

	    menuItemDTO = mergeXMLAndNonXMLDTO(accntListMenuDTO, menuOptionMenuDTO);
	    ussdSessionManagement.setPreviousRenderedScreen(menuItemDTO);
	} catch (USSDNonBlockingException nonBlockingException) {
	    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	} catch (USSDBlockingException blockingException) {
	    throw blockingException;
	}
	return menuItemDTO;
    }

    public MenuItemDTO generateLandingPageMenu(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement)
	    throws USSDBlockingException {
	ussdSessionManagement.setScreenId(USSDConstants.WELCOME_PAGE_SCREEN_ID);
	ussdSessionManagement.setCurrentScreenNodeId(USSDConstants.WELCOME_PAGE_NODE_ID);
	ussdSessionManagement.setTransactionFlag(false);
	paramDTO.setByPassRequest(false);
	paramDTO.setCurrentScreenId(USSDConstants.WELCOME_PAGE_SCREEN_ID);
	paramDTO.setCurrentScreenNodeId(USSDConstants.WELCOME_PAGE_NODE_ID);
	paramDTO.setBusinessId(ussdSessionManagement.getBusinessId());
	paramDTO.setCountryCode(ussdSessionManagement.getCountryCode());
	paramDTO.setLang(ussdSessionManagement.getUserProfile().getLanguage());
	paramDTO.setUserInput(USSDConstants.WELCOME_PAGE_DEFAULT_INPUT);
	MenuItemDTO menuOptionMenuDTO = ussdDecisionParser.getMenuList(paramDTO, ussdSessionManagement);
	return menuOptionMenuDTO;
    }

    private MenuItemDTO getPrimaryAccountBalance(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement,
	    List<String> errorCodes) throws USSDBlockingException, USSDNonBlockingException, Exception {
	Transaction transaction = new Transaction();
	transaction.setCurrentRunningTransaction(new CurrentRunningTransaction());

	ussdSessionManagement.setUserTransactionDetails(transaction);
	CasaAccount mainAcctObj = ussdSessionManagement.getUserProfile().getMainAccount();
	MenuItemDTO accntListMenuDTO = null;
	if (mainAcctObj != null) {
	    accntListMenuDTO = handleByePassRequest(paramDTO, ussdSessionManagement, errorCodes);

	    /* Appending select header for welcome screen menu */
	    StringBuffer pageBody = new StringBuffer(accntListMenuDTO.getPageBody());
	    pageBody.append(ussdResourceBundle.getLabel(LABEL_SELECT, new Locale(ussdSessionManagement.getUserProfile().getLanguage(),
		    ussdSessionManagement.getUserProfile().getCountryCode())));
	    accntListMenuDTO.setPageBody(pageBody.toString());
	}
	return accntListMenuDTO;
    }

    private MenuItemDTO mergeXMLAndNonXMLDTO(MenuItemDTO accntListMenuDTO, MenuItemDTO menuOptionMenuDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	if (accntListMenuDTO != null) {
	    BeanUtils.copyProperties(accntListMenuDTO, menuItemDTO);
	    menuItemDTO.setPageHeader(menuOptionMenuDTO.getPageHeader());
	} else {
	    /* If the main account is not found for the user */
	    menuItemDTO.setPageHeader(LABEL_SELECT);
	    menuItemDTO.setPageError(USSDNonBlockingErrorCodes.MAIN_ACCT_NT_FOUND.getUssdErrorCode());
	}
	menuItemDTO.setMenuItemList(menuOptionMenuDTO.getMenuItemList());

	// TODO Auto-generated method stub
	return menuItemDTO;
    }

    public MenuItemDTO handleChangePinRequest(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement,
	    List<String> errorCodes) throws USSDBlockingException, USSDNonBlockingException, Exception {

	Transaction transaction = null;
	if (ussdSessionManagement.getUserTransactionDetails() == null) {
	    transaction = new Transaction();
	    paramDTO.setUserInput(null);
	} else {
	    transaction = ussdSessionManagement.getUserTransactionDetails();
	}
	if (ussdSessionManagement.getUserTransactionDetails() == null
		|| ussdSessionManagement.getUserTransactionDetails().getCurrentRunningTransaction() == null) {
	    paramDTO.setUserInput(null);
	    ussdSessionManagement.setTransactionFlag(true);
	    transaction.setCurrentRunningTransaction(new CurrentRunningTransaction());
	}
	ussdSessionManagement.setUserTransactionDetails(transaction);

	// MenuItemDTO menuItemDTO = null;
	MenuItemDTO menuDTO = handleChangePinByePassRequest(paramDTO, ussdSessionManagement, errorCodes);

	return menuDTO;
    }

    private MenuItemDTO handleChangePinByePassRequest(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement,
	    List<String> errorCodes) throws USSDBlockingException, USSDNonBlockingException, Exception {
	MenuItemDTO menuItemDTO = null;
	String msisdn = paramDTO.getMsisdn();
	UserProfile profile = ussdSessionManagement.getUserProfile();

	if (profile == null) {
	    profile = new UserProfile();
	    ussdSessionManagement.setMsisdnNumber(msisdn);
	    ussdSessionManagement.setUserProfile(profile);
	    Transaction transaction = new Transaction();
	    transaction.setCurrentRunningTransaction(new CurrentRunningTransaction());
	    ussdSessionManagement.setUserTransactionDetails(transaction);
	    ussdSessionManagement.setTransactionFlag(true);
	}

	paramDTO.setByPassRequest(true);
	paramDTO.setBusinessId(ussdSessionManagement.getBusinessId());
	menuItemDTO = ussdDecisionParser.getMenuList(paramDTO, ussdSessionManagement);

	return menuItemDTO;
    }

    public MenuItemDTO handleChangeLanguageRequest(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement,
	    List<String> errorCodes) throws USSDBlockingException, USSDNonBlockingException, Exception {

	Transaction transaction = null;
	if (ussdSessionManagement.getUserTransactionDetails() == null) {
	    transaction = new Transaction();
	    paramDTO.setUserInput(null);
	} else {
	    transaction = ussdSessionManagement.getUserTransactionDetails();
	}

	if (transaction.getCurrentRunningTransaction() == null) {
	    paramDTO.setUserInput(null);
	    transaction.setCurrentRunningTransaction(new CurrentRunningTransaction());
	}
	ussdSessionManagement.setUserTransactionDetails(transaction);

	// MenuItemDTO menuItemDTO = null;
	MenuItemDTO menuDTO = handleByePassRequest(paramDTO, ussdSessionManagement, errorCodes);

	return menuDTO;
    }

    public MenuItemDTO handleUserMigrationRequest(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement,
	    List<String> errorCodes) throws USSDBlockingException, USSDNonBlockingException, Exception {

	Transaction transaction = null;
	if (ussdSessionManagement.getUserTransactionDetails() == null) {
	    transaction = new Transaction();
	    paramDTO.setUserInput(null);
	} else {
	    transaction = ussdSessionManagement.getUserTransactionDetails();
	}

	if (transaction.getCurrentRunningTransaction() == null) {
	    paramDTO.setUserInput(null);
	    transaction.setCurrentRunningTransaction(new CurrentRunningTransaction());
	}
	ussdSessionManagement.setUserTransactionDetails(transaction);

	// MenuItemDTO menuItemDTO = null;
	MenuItemDTO menuDTO = handleUserMigrantionByePassRequest(paramDTO, ussdSessionManagement, errorCodes);

	return menuDTO;
    }

    private MenuItemDTO handleUserMigrantionByePassRequest(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionManagement,
	    List<String> errorCodes) throws USSDBlockingException, USSDNonBlockingException, Exception {
	MenuItemDTO menuItemDTO = null;
	String msisdn = paramDTO.getMsisdn();
	UserProfile profile = ussdSessionManagement.getUserProfile();

	if (profile == null) {
	    profile = new UserProfile();
	    ussdSessionManagement.setMsisdnNumber(msisdn);
	    ussdSessionManagement.setUserProfile(profile);
	    Transaction transaction = new Transaction();
	    transaction.setCurrentRunningTransaction(new CurrentRunningTransaction());
	    ussdSessionManagement.setUserTransactionDetails(transaction);
	}

	paramDTO.setByPassRequest(true);
	paramDTO.setBusinessId(ussdSessionManagement.getBusinessId());
	menuItemDTO = ussdDecisionParser.getMenuList(paramDTO, ussdSessionManagement);

	return menuItemDTO;
    }

    @ExceptionHandler(USSDNonBlockingException.class)
    @ResponseBody
    public String handleUSSDNonBlockingException(final USSDNonBlockingException nbe, final HttpServletRequest request) {

	String response = null;
	final USSDRequest ussdRequest = (USSDRequest) request.getAttribute(USSDConstants.REQOBJ);
	final String msisdn_number = ussdRequest.getMsisdn();
	HttpSession sessiontmp = sessionHandler.getSessionRequest(request);
	if (sessiontmp != null) {
	    final USSDSessionManagement ussdSessionMgmt = (USSDSessionManagement) sessiontmp.getAttribute(msisdn_number);
	    final String countryCode = ussdSessionMgmt.getCountryCode();
	    final String lang = ussdSessionMgmt.getUserProfile().getLanguage();
	    response = ExceptionUtils.handleNonBlockingException(nbe, ussdResourceBundle, countryCode, lang);
	} else {
	    response = ExceptionUtils.handleNonBlockingException(nbe, ussdResourceBundle, ConfigurationManager.getString("defaultCountry"),
		    ConfigurationManager.getString("defaultLanguage"));
	}
	sessionHandler.removeSession(request);
	return new ResponseMessage().generateXmlRespNonBlockingException(request, response, ussdRequest.getMsisdnWithCountry());
    }

}