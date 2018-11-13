package com.barclays.ussd.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.CurrentRunningTransaction;
import com.barclays.ussd.bean.MenuItem;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.Screen;
import com.barclays.ussd.bean.Transaction;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.bmg.factory.request.IUSSDRequestFactory;
import com.barclays.ussd.bmg.factory.response.BmgBaseResponseBuilder;
import com.barclays.ussd.bmg.requesDecisionMaker.CallBMGAccordingRequestInter;
import com.barclays.ussd.bmg.requesDecisionMaker.UssdServiceInvoker;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.svc.context.USSDBaseResponse;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.IUSSDJsonParserFactory;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdParser;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.UssdServiceEnabler;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.validation.USSDInputValidator;

public class UssdDecisionParser {

    private static final String EMPTY_USER_INPUT = "";

    private static final String LIMITED_ACCESS = "limited";

    private static final String TRUE = "true";

    @Autowired
    UssdParser ussdParser;

    @Autowired
    IUSSDRequestFactory ussdRequestFactory;

    @Autowired
    private UssdResourceBundle ussdResourceBundle;

    @Autowired
    BmgBaseResponseBuilder bmgBaseResponseBuilder;

    @Autowired
    UssdServiceInvoker ussdServiceInvoker;

    @Autowired
    IUSSDJsonParserFactory ussdJsonParserFactory;

    @Autowired
    UssdServiceEnabler ussdServiceEnabler;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(UssdDecisionParser.class);

    public MenuItemDTO getMenuList(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	List<String> errorCodes = new ArrayList<String>();
	MenuItemDTO menuItemDTO = null;
	MenuItemDTO previousMenutItemDTO = ussdSessionMgmt.getPreviousRenderedScreen();
	NavigationOptionsDTO navigationOptions = ussdParser.getNavigationOptions(paramDTO.getBusinessId(), paramDTO.getCountryCode());

	// clear the pagination related data from the session
	clearPaginationFromSession(ussdSessionMgmt);

	try {
	    if (!ussdSessionMgmt.isTransactionFlag()) {

		if (previousMenutItemDTO != null) {
		    String status = previousMenutItemDTO.getStatus();
		    if (!paramDTO.isBackOnErrorScreen()) {
			if (StringUtils.equalsIgnoreCase(status, USSDConstants.STATUS_END)) {
			    if (LOGGER.isDebugEnabled()) {
				if (!ussdSessionMgmt.getScreenId().equalsIgnoreCase(USSDConstants.WELCOME_PAGE_SCREEN_ID)) {
				    LOGGER.debug("Render home screen to the user.");
				}
			    }
			    //FreeDialUSSD
			    if (ussdSessionMgmt.isFreeDialUssdFlow()){
			    	 return goBack(paramDTO, ussdSessionMgmt);
			    }
			    return handleHomeOption(paramDTO, ussdSessionMgmt);
			} else if (StringUtils.equalsIgnoreCase(status, USSDConstants.STATUS_ERROR) && !paramDTO.isByPassRequest()) {
			    if (LOGGER.isDebugEnabled()) {
				if (!ussdSessionMgmt.getScreenId().equalsIgnoreCase(USSDConstants.WELCOME_PAGE_SCREEN_ID)) {
				    LOGGER.debug("Render home/back screen to the user for error page.");
				}
			    }
			    return handleHomeAndBackForErrorPage(paramDTO, ussdSessionMgmt);
			} else if (StringUtils.equalsIgnoreCase(status, USSDConstants.STATUS_ERROR) && paramDTO.isByPassRequest()) {
			    return handleBackForByePassErrorPage(paramDTO, ussdSessionMgmt);
			}
		    }
		}
		paramDTO.setCurrentScreenNodeId(ussdSessionMgmt.getCurrentScreenNodeId());

		if (USSDInputValidator.validateMenuInput(paramDTO.getUserInput(), errorCodes, navigationOptions.getBackOption(), navigationOptions
			.getHomeOption())) {

			//FreeDialUSSD
			 if(ussdSessionMgmt.isFreeDialUssdFlow()){
				 paramDTO.setUserInput("1");
			 }
		    menuItemDTO = callTheXMLParser(paramDTO, errorCodes);

		    if (menuItemDTO != null && menuItemDTO.getNextScreenNodeId() != null) {
			ussdSessionMgmt.setCurrentScreenNodeId(menuItemDTO.getNextScreenNodeId());
		    }

		    // Checking if the BMG service call is required and call the
		    // service if required
		    if (menuItemDTO.getTranId() != null) {
			ussdSessionMgmt.setCurrentScreenNodeId(null);
			if (checkAccessPermissions(menuItemDTO, ussdSessionMgmt, errorCodes)) {
				Map<String,Object> txSession=ussdSessionMgmt.getTxSessions();
			    ussdSessionMgmt.clean();
			    ussdSessionMgmt.setTxSessions(txSession);
			    Transaction userTransaction = createTransaction(menuItemDTO);

			    ussdSessionMgmt.setUserTransactionDetails(userTransaction);
			    ussdSessionMgmt.setTransactionFlag(true);
			    String serviceName = ussdParser.getServiceName(paramDTO, menuItemDTO.getTranId());
			    //Fixed for Deadlock on Production
			   /* boolean serviceEnabled = false; ussdServiceEnabler.checkServiceEnabled(serviceName, ussdSessionMgmt.getUserProfile()
				    .getBusinessId(), ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile()
				    .getCountryCode());*/
			    //userTransaction.setServiceEnabled(serviceEnabled);
			    if(StringUtils.equalsIgnoreCase(ussdSessionMgmt.getCustomerAccessStatusCode(), "Y")){
				    boolean serviceEnabled = ussdServiceEnabler.checkServiceEnabled(serviceName, ussdSessionMgmt.getUserProfile()
					    .getBusinessId(), ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile()
					    .getCountryCode());
				    userTransaction.setServiceEnabled(serviceEnabled);
			    }
			    paramDTO.setServiceName(serviceName);
			    paramDTO.setUserTransactionDetails(userTransaction);

			    if (menuItemDTO.getTranId().equals(USSDConstants.GO_TO_HOME_PAGE_TRAN_ID)) {
				return goHome(paramDTO, ussdSessionMgmt);
			    } else if (menuItemDTO.getTranId().equals(USSDConstants.GO_BACK_TRAN_ID)) {
				return goBack(paramDTO, ussdSessionMgmt);
			    }

			    paramDTO.setUserInput(null);
			    if (LOGGER.isInfoEnabled()) {
				if (menuItemDTO.getLabelId() != null) {
				    UserProfile userProfile = ussdSessionMgmt.getUserProfile();
				    Locale locale = new Locale(userProfile.getLanguage(), userProfile.getCountryCode());
				    LOGGER.info("User action->" + ussdResourceBundle.getLabel(menuItemDTO.getLabelId(), locale));
				}
			    }

			    menuItemDTO = callTheNonXMLParser(paramDTO, ussdSessionMgmt, errorCodes, navigationOptions);
			}
		    } else {
			menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
			ussdSessionMgmt.setScreenId(menuItemDTO.getNextScreenId());
		    }

		}
		if (!errorCodes.isEmpty() && previousMenutItemDTO != null) {
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Populate the errors on the screen.");
		    }
		    menuItemDTO = previousMenutItemDTO;
		    menuItemDTO.setPageError(errorCodes.get(0));
		}
	    } else {

		menuItemDTO = callTheNonXMLParser(paramDTO, ussdSessionMgmt, errorCodes, navigationOptions);
	    }
	    // setting the currently rendered screen to the session
	    ussdSessionMgmt.setPreviousRenderedScreen(menuItemDTO);
	} catch (USSDBlockingException blockingExp) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Throwing USSDBlockingException");
	    }
	    throw blockingExp;
	}

	return menuItemDTO;
    }

    private boolean checkAccessPermissions(MenuItemDTO menuItemDTO, USSDSessionManagement ussdSessionMgmt, List<String> errorCodes) {
	boolean retVal = true;
	String accessType = menuItemDTO.getAccessType();
	if (LIMITED_ACCESS.equalsIgnoreCase(accessType)
		&& USSDConstants.USER_STATUS_LIMITED.equalsIgnoreCase(ussdSessionMgmt.getUserProfile().getUsrSta())) {
	    errorCodes.add(USSDExceptions.USSD_LIMITED_ACCESS.getUssdErrorCode());
	    retVal = false;
	}
	return retVal;
    }

    private Transaction createTransaction(MenuItemDTO menuItemDTO) {
	Transaction userTransaction = new Transaction();

	CurrentRunningTransaction currentRunningTransaction = new CurrentRunningTransaction();
	currentRunningTransaction.setTranId(menuItemDTO.getTranId());
	currentRunningTransaction.setTranNodeId(menuItemDTO.getTranNodeId());

	userTransaction.setCurrentRunningTransaction(currentRunningTransaction);
	return userTransaction;
    }

    private void clearPaginationFromSession(USSDSessionManagement ussdSessionMgmt) {
	ussdSessionMgmt.setPaged(false);
	ussdSessionMgmt.setCurrentPagePosition(0);
	ussdSessionMgmt.setPageList(null);
	ussdSessionMgmt.setListedResponse(false);
    }

    private MenuItemDTO goBack(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Handle back action during menu navigation.");
	}
	UssdDecisionParserParamDTO newParamDTO = ussdParser.performBackOperation(paramDTO);
	ussdSessionMgmt.setTransactionFlag(false);
	ussdSessionMgmt.setCurrentScreenNodeId(newParamDTO.getCurrentScreenNodeId());
	ussdSessionMgmt.clean();
	MenuItemDTO menuItemDTO = getMenuList(newParamDTO, ussdSessionMgmt);
	menuItemDTO.setTranId(null);
	return menuItemDTO;
    }

    /**
     * @param paramDTO
     * @param ussdSessionMgmt
     * @param status
     * @return MenuItemDTO
     * @throws USSDBlockingException
     * @throws USSDNonBlockingException
     * @throws Exception
     */
    private MenuItemDTO handleHomeOption(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	MenuItemDTO menuItemDTO = null;
	NavigationOptionsDTO backHomeOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getUserProfile().getBusinessId(), ussdSessionMgmt
		.getUserProfile().getCountryCode());
	if (backHomeOptions.getHomeOption().equalsIgnoreCase(paramDTO.getUserInput())) {
	    return goHome(paramDTO, ussdSessionMgmt);
	} else {
	    menuItemDTO = ussdSessionMgmt.getPreviousRenderedScreen();
	    menuItemDTO.setPageError(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
	    return menuItemDTO;
	}
    }

    /**
     * @param paramDTO
     * @param ussdSessionMgmt
     * @param status
     * @return MenuItemDTO
     * @throws USSDBlockingException
     * @throws USSDNonBlockingException
     * @throws Exception
     */
    private MenuItemDTO handleHomeAndBackForErrorPage(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt)
	    throws USSDBlockingException {
	MenuItemDTO menuItemDTO = null;
	NavigationOptionsDTO backHomeOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getUserProfile().getBusinessId(), ussdSessionMgmt
		.getUserProfile().getCountryCode());

	if (backHomeOptions.getHomeOption().equalsIgnoreCase(paramDTO.getUserInput())) {
		//FreeDialUSSD
		if(ussdSessionMgmt.isFreeDialUssdFlow()){
			 menuItemDTO = ussdSessionMgmt.getPreviousRenderedScreen();
			    menuItemDTO.setPageError(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
			    return menuItemDTO;
		} else {
			 return goHome(paramDTO, ussdSessionMgmt);
		}
		//End

	} else if (backHomeOptions.getBackOption().equalsIgnoreCase(paramDTO.getUserInput())) {
	    ussdSessionMgmt.setTransactionFlag(true);
	    UssdDecisionParserParamDTO errorScreenParamDTO = ussdSessionMgmt.getErrorScreenParamDTO();
	    return handleBackActionOnErrorScreen(errorScreenParamDTO, ussdSessionMgmt);
	} else {
	    menuItemDTO = ussdSessionMgmt.getPreviousRenderedScreen();
	    menuItemDTO.setPageError(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
	    return menuItemDTO;
	}
    }

    private MenuItemDTO handleBackForByePassErrorPage(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt)
	    throws USSDBlockingException {
	MenuItemDTO menuItemDTO = null;
	NavigationOptionsDTO backHomeOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getUserProfile().getBusinessId(), ussdSessionMgmt
		.getUserProfile().getCountryCode());
	if (backHomeOptions.getBackOption().equalsIgnoreCase(paramDTO.getUserInput())) {
	    ussdSessionMgmt.setTransactionFlag(true);
	    UssdDecisionParserParamDTO errorScreenParamDTO = ussdSessionMgmt.getErrorScreenParamDTO();
	    return handleBackActionOnErrorScreen(errorScreenParamDTO, ussdSessionMgmt);
	} else {
	    menuItemDTO = ussdSessionMgmt.getPreviousRenderedScreen();
	    menuItemDTO.setPageError(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
	    return menuItemDTO;
	}
    }

    /**
     * @param paramDTO
     * @param ussdSessionMgmt
     * @return MenuItemDTO
     *
     * @throws USSDBlockingException
     *
     * @throws Exception
     */
    private MenuItemDTO goHome(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Handle home action during menu navigation.");
	}
	//Forgot Pin Change
	String tranDataId=null;
	try{
	    tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	}catch(Exception e){
	}
	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
		MenuItemDTO menuItemDTO = null;
		menuItemDTO = ussdSessionMgmt.getPreviousRenderedScreen();
	    menuItemDTO.setPageError(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
	    return menuItemDTO;
	}
	if (StringUtils.equalsIgnoreCase(ussdSessionMgmt.getNonHMCustomerFlag(), TRUE) && !ussdSessionMgmt.isDemoMode()) {
	    paramDTO.setCurrentScreenId(USSDConstants.UNREG_USER_WELCOME_PAGE_SCREEN_ID);
	    paramDTO.setUserInput(USSDConstants.UNREG_USER_WELCOME_PAGE_DEFAULT_INPUT);
	} else {
	    MenuItem mainMenuNode = ussdParser.getMainMenuNode(ussdSessionMgmt);
	    paramDTO.setCurrentScreenId(mainMenuNode.getScreenId());
	    paramDTO.setUserInput(mainMenuNode.getOptionId());
	    // paramDTO.setCurrentScreenId(USSDConstants.HOME_PAGE_SCREEN_ID);
	    // paramDTO.setUserInput(USSDConstants.HOME_PAGE_DEFAULT_INPUT);
	}
	ussdSessionMgmt.setTransactionFlag(false);
	if (ussdSessionMgmt.getUserTransactionDetails() != null) {
	    ussdSessionMgmt.clean();
	}
	ussdSessionMgmt.setPreviousRenderedScreen(null);
	MenuItemDTO menuItemDTO = getMenuList(paramDTO, ussdSessionMgmt);
	menuItemDTO.setTranId(null);

	return menuItemDTO;
    }

    public MenuItemDTO callTheNonXMLParser(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt, List<String> errorCodes,
	    NavigationOptionsDTO backHomeOptions) throws USSDBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	boolean isErrorBackScreen = false;	//CR-86
	CurrentRunningTransaction currentTransaction = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction();

	if (USSDInputValidator.validate(currentTransaction, paramDTO.getUserInput(), ussdSessionMgmt, errorCodes, backHomeOptions.getBackOption(),
		backHomeOptions.getHomeOption())) {
	    if (LOGGER.isDebugEnabled()) {
		if (ussdSessionMgmt.getScreenId() != null) {
		    boolean ignorePaswwordLog = !ussdSessionMgmt.getScreenId().equalsIgnoreCase(USSDConstants.WELCOME_PAGE_SCREEN_ID)
			    && !ussdSessionMgmt.getScreenId().equalsIgnoreCase(USSDConstants.HOME_PAGE_SCREEN_ID);
		    if (ignorePaswwordLog) {
			LOGGER.debug("Perform the desired operation if the user input is valid");
		    }
		}
	    }
	    //CR-86 Back flow validation screen changes
	    if (ussdSessionMgmt.isBackFlowErrorScreen()) {
	    	ussdSessionMgmt.setBackFlowErrorScreen(false);
	    	ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("false");
	    }
	    return performOperation(paramDTO, ussdSessionMgmt, backHomeOptions);
	} else {

		//CR-86 Back flow validation
		if(currentTransaction !=null){
			isErrorBackScreen= backErrorScreen(currentTransaction);
			if(isErrorBackScreen){
				return performOperationBackFlow(paramDTO, ussdSessionMgmt, backHomeOptions);
			}
		}
		//End
	    if (!errorCodes.isEmpty() && ussdSessionMgmt.getPreviousRenderedScreen() != null) {
		menuItemDTO = ussdSessionMgmt.getPreviousRenderedScreen();
		menuItemDTO.setPageError(errorCodes.get(0));
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Populate the screen with error message if the input is invalid.");
		}
	    }
	}
	return menuItemDTO;
    }

    //CR-86 Back error screen validadation
    private boolean backErrorScreen(CurrentRunningTransaction currentTransaction) {
    	boolean result= false;
    	String transIdCurr=currentTransaction.getTranDataId();
    	String[] transac = {"CSOTP004", "CSOTP005", "CSOTP001","RBOBA05","RBB008","OBAFTNR011","OBAFTNR018","FOGP002","SLR002","RBOBA011","ROTHBB005"};
    	if(currentTransaction.getType().equalsIgnoreCase(USSDConstants.TRANSACTION_TYPE_AMOUNT) && !currentTransaction.getTranId().equals("GH_FREE_DIAL_USSD") ){
    		result= true;
    	} else {
    		for (String s: transac) {
                if(s.equalsIgnoreCase(transIdCurr)){
                	result= true;
                	break;

                }
            }
    	}
    	return  result;

    }

    //CR-86 Back flow validation special character
    private MenuItemDTO performOperationBackFlow(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt,
    	    NavigationOptionsDTO backHomeOptions) throws USSDBlockingException {
    	MenuItemDTO menuItemDTO = null;
    	 // To go thru stand transactional BMG operations get the handle to
	    // the previous running transaction from the session
	    CurrentRunningTransaction currentRunningTransaction = null;
	    do {
		CurrentRunningTransaction previousTransaction = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction();
		checkCustomScreenFlow(ussdSessionMgmt, paramDTO, previousTransaction);
		currentRunningTransaction = getNextTransaction(ussdSessionMgmt, paramDTO);

		if (currentRunningTransaction == null) {
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Cleaning the session on the last step of the transaction...");
		    }
		    // this means the transaction has ended so clear the session
		    ussdSessionMgmt.clean();
		    break;
		} else {
		    menuItemDTO = getMenuItemDTOFromBMGResponse(ussdSessionMgmt, currentRunningTransaction, paramDTO, previousTransaction);
		}
	    } while (checkForNEGUIRENDER(menuItemDTO, currentRunningTransaction));
	    return menuItemDTO;
    }

    private MenuItemDTO performOperation(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt,
	    NavigationOptionsDTO backHomeOptions) throws USSDBlockingException {
	MenuItemDTO menuItemDTO = null;
	String userInput = paramDTO.getUserInput();

	if (userInput != null && backHomeOptions.getBackOption().equalsIgnoreCase(userInput)) {
	    if (LOGGER.isInfoEnabled()) {
		if (!StringUtils.equalsIgnoreCase(ussdSessionMgmt.getScreenId(), USSDConstants.WELCOME_PAGE_SCREEN_ID)) {
		    LOGGER.info("Handle the back option within a transaction");
		}
	    }
	    UssdDecisionParserParamDTO newParamDTO = null;
	    if (paramDTO.isByPassRequest()) {
		newParamDTO = ussdParser.performBackOperationForByePassTx(paramDTO, ussdSessionMgmt);
	    } else {
		newParamDTO = ussdParser.performBackOperationForTx(paramDTO, ussdSessionMgmt);
	    }
	    newParamDTO.setErrorneousPage(true);
	    menuItemDTO = getMenuList(newParamDTO, ussdSessionMgmt);
	    if (menuItemDTO != null) {
		menuItemDTO.setTranId(null);
	    } else {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	    }
	} else if (userInput != null && backHomeOptions.getHomeOption().equalsIgnoreCase(userInput)) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Handle the home option within a transaction");
	    }
	    paramDTO.setCurrentScreenId(USSDConstants.HOME_PAGE_SCREEN_ID);
	    paramDTO.setUserInput(backHomeOptions.getHomeOption());
	    ussdSessionMgmt.setTransactionFlag(false);
	    ussdSessionMgmt.clean();
	    // menuItemDTO = getMenuList(paramDTO, ussdSessionMgmt);
	    menuItemDTO = handleHomeOption(paramDTO, ussdSessionMgmt);
	    menuItemDTO.setTranId(null);
	} else {
	    // To go thru stand transactional BMG operations get the handle to
	    // the previous running transaction from the session
	    CurrentRunningTransaction currentRunningTransaction = null;
	    do {
		CurrentRunningTransaction previousTransaction = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction();
		checkCustomScreenFlow(ussdSessionMgmt, paramDTO, previousTransaction);
		currentRunningTransaction = getNextTransaction(ussdSessionMgmt, paramDTO);

		if (currentRunningTransaction == null) {
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Cleaning the session on the last step of the transaction...");
		    }
		    // this means the transaction has ended so clear the session
		    ussdSessionMgmt.clean();
		    break;
		} else {
		    menuItemDTO = getMenuItemDTOFromBMGResponse(ussdSessionMgmt, currentRunningTransaction, paramDTO, previousTransaction);
		}
	    } while (checkForNEGUIRENDER(menuItemDTO, currentRunningTransaction));
	}
	return menuItemDTO;
    }

    private void checkCustomScreenFlow(USSDSessionManagement ussdSessionMgmt, UssdDecisionParserParamDTO paramDTO,
	    CurrentRunningTransaction previousTransaction) throws USSDBlockingException {
	BmgBaseJsonParser bmgBaseJsonParser = ussdJsonParserFactory.getParser(previousTransaction.getTranDataId());
	if (bmgBaseJsonParser instanceof ScreenSequenceCustomizer) {
	    int nextScreenSequenceNo = ((ScreenSequenceCustomizer) bmgBaseJsonParser).getCustomNextScreen(paramDTO.getUserInput(), ussdSessionMgmt);
	    previousTransaction.setNextScreenSequenceNumber(nextScreenSequenceNo);
	}
    }

    private boolean checkForNEGUIRENDER(MenuItemDTO menuItemDTO, CurrentRunningTransaction currentRunningTransaction) {
	boolean retVal = true;
	if (menuItemDTO != null) {
	    String pageError = menuItemDTO.getPageError();
	    if (pageError != null || menuItemDTO.isErrorPage()) {
		return false;
	    }
	}
	retVal = StringUtils.equalsIgnoreCase(currentRunningTransaction.getTransactionDataOpCode(), USSDConstants.NEGATE_UI_RENDERING);
	return retVal;
    }

    private CurrentRunningTransaction getNextTransaction(USSDSessionManagement ussdSessionMgmt, UssdDecisionParserParamDTO paramDTO) {
	Transaction transaction = ussdSessionMgmt.getUserTransactionDetails();
	CurrentRunningTransaction currentRunningTransaction = null;

	if (paramDTO.isByPassRequest()) {
	    // for byepass requests
	    currentRunningTransaction = ussdParser.getNextTransactionForByePass(ussdSessionMgmt, paramDTO.getServiceName());
	} else {
	    currentRunningTransaction = ussdParser.getNextTransaction(transaction, paramDTO.getServiceName(), ussdSessionMgmt);
	}

	return currentRunningTransaction;
    }

    private MenuItemDTO getMenuItemDTOFromBMGResponse(USSDSessionManagement ussdSessionMgmt, CurrentRunningTransaction currentRunningTransaction,
	    UssdDecisionParserParamDTO paramDTO, CurrentRunningTransaction previousTransaction) throws USSDBlockingException {
	MenuItemDTO menuItemDTO = null;
	USSDBaseResponse response = null;
	USSDBaseRequest ussdBaseRequest = null;
	try {
		if(StringUtils.equalsIgnoreCase(ussdSessionMgmt.getCustomerAccessStatusCode(), "Y")){
			ussdServiceEnabler.blockServiceIfDisabled(ussdSessionMgmt.getUserTransactionDetails().isServiceEnabled());
		}

	    // set the transaction into the session
	    ussdSessionMgmt.getUserTransactionDetails().setCurrentRunningTransaction(currentRunningTransaction);
	    performSystemPreferenceValidation(ussdSessionMgmt, paramDTO, previousTransaction);
	    persistUserInputIntoSession(paramDTO, ussdSessionMgmt, previousTransaction);
	    ussdBaseRequest = getUSSDRequest(paramDTO, ussdSessionMgmt, currentRunningTransaction, previousTransaction);
	    response = getBMGResponse(ussdSessionMgmt, currentRunningTransaction, ussdBaseRequest, paramDTO.isErrorneousPage());

	    /* Set the pagination type flag */
	    if (USSDConstants.DATA_TYPE_LIST.equalsIgnoreCase(currentRunningTransaction.getType())) {
		ussdSessionMgmt.setListedResponse(true);
	    } else {
		ussdSessionMgmt.setListedResponse(false);
	    }

	    menuItemDTO = response.getMenuItemDTO();

	    saveScreenSequenceInSession(previousTransaction, ussdSessionMgmt);

	    if (USSDInputParamsEnum.TWO_FACTOR_VERIFY.getTranId().equalsIgnoreCase(currentRunningTransaction.getTranDataId())) {
		paramDTO.setUserInput(ussdSessionMgmt.getTwoFactUserInput());
	    }

	} catch (USSDNonBlockingException ussdnbe) {
	    LOGGER.error("Non-Blocking exception has occured.", ussdnbe);

	    UssdDecisionParserParamDTO newParamDTO = null;

	    AuthUserData userAuthObj = (AuthUserData) ussdSessionMgmt.getUserAuthObj();
	    boolean pinChangeReq = false;
	    if (userAuthObj != null) {
		pinChangeReq = StringUtils.equalsIgnoreCase(userAuthObj.getPayData().getCustProf().getPinSta(), USSDConstants.PIN_CHANGE_REQ);
	    }
	    // else if (StringUtils.equalsIgnoreCase(ussdSessionMgmt.getCustomerType(), USSDConstants.CUSTOMER_TYPE_NON_HM)
	    // && !ussdSessionMgmt.isDemoMode()) {
	    // newParamDTO = ussdParser.performBackOperationForTx(paramDTO, ussdSessionMgmt);
	    // newParamDTO.setErrorneousPage(true);
	    // menuItemDTO = getMenuList(newParamDTO, ussdSessionMgmt);
	    // menuItemDTO.setTranId(null);
	    // String errorCode = USSDExceptions.getUssdErrorCodeforBMG(ussdnbe.getErrorCode());
	    // menuItemDTO.setPageError(errorCode);
	    // menuItemDTO.setErrorParams(ussdnbe.getErrorParams());
	    // }
	    if (ussdSessionMgmt.isFirstRequest()
		    && !StringUtils.equalsIgnoreCase(ussdSessionMgmt.getCustomerType(), USSDConstants.CUSTOMER_TYPE_NON_HM)) {
		newParamDTO = ussdParser.performBackOperationForByePassTx(paramDTO, ussdSessionMgmt);
		newParamDTO.setErrorneousPage(true);
		menuItemDTO = getMenuList(newParamDTO, ussdSessionMgmt);
		menuItemDTO.setTranId(null);
		String errorCode = USSDExceptions.getUssdErrorCodeforBMG(ussdnbe.getErrorCode());
		menuItemDTO.setPageError(errorCode);
		menuItemDTO.setErrorParams(ussdnbe.getErrorParams());
	    } else if (paramDTO.isByPassRequest() || pinChangeReq) {
		ussdSessionMgmt.setErrorScreenParamDTO(paramDTO);
		menuItemDTO = createErrorScreenForByePass(ussdnbe, ussdSessionMgmt);

	    } else {
		//CR-86 Back Flow changes
		if(ussdnbe.isBackFlow()){
			ussdSessionMgmt.getUserTransactionDetails().setCurrentRunningTransaction(previousTransaction);
			ussdSessionMgmt.setErrorScreenParamDTO(paramDTO);
			//CR-86
			ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("true");

			menuItemDTO = createErrorScreen(ussdnbe, ussdSessionMgmt);
			ussdSessionMgmt.setBackFlowErrorScreen(true);
			ussdSessionMgmt.setTransactionFlag(true);
			menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
			ussdnbe.setBackFlow(false);
			menuItemDTO.setErrorCode(ussdnbe.getErrorCode());
			return menuItemDTO;
		}
		ussdSessionMgmt.setErrorScreenParamDTO(paramDTO);
		menuItemDTO = createErrorScreen(ussdnbe, ussdSessionMgmt);

	    }
	    menuItemDTO.setErrorCode(ussdnbe.getErrorCode());
	    ussdSessionMgmt.getUserTransactionDetails().setCurrentRunningTransaction(currentRunningTransaction);//CR-86 Back flow changes
	}

	return menuItemDTO;
    }

    private void saveScreenSequenceInSession(CurrentRunningTransaction previousTransaction, USSDSessionManagement ussdSessionMgmt) {
	Transaction userTransactionDetails = ussdSessionMgmt.getUserTransactionDetails();
	if (userTransactionDetails != null) {
	    Stack<Integer> screenSequenceStack = userTransactionDetails.getScreenSequenceStack();
	    if (screenSequenceStack == null) {
		screenSequenceStack = new Stack<Integer>();
	    }
	    screenSequenceStack.push(previousTransaction.getNextScreenSequenceNumber());

	    userTransactionDetails.setScreenSequenceStack(screenSequenceStack);
	}
    }

    private void performSystemPreferenceValidation(USSDSessionManagement ussdSessionMgmt, UssdDecisionParserParamDTO paramDTO,
	    CurrentRunningTransaction previousTransaction) throws USSDBlockingException, USSDNonBlockingException {
	BmgBaseJsonParser bmgBaseJsonParser = ussdJsonParserFactory.getParser(previousTransaction.getTranDataId());
	if (bmgBaseJsonParser instanceof SystemPreferenceValidator) {
	    ((SystemPreferenceValidator) bmgBaseJsonParser).validate(paramDTO.getUserInput(), ussdSessionMgmt);
	}
    }

    private void persistUserInputIntoSession(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt,
	    CurrentRunningTransaction previousTransaction) throws USSDBlockingException {
	String tranDataId = previousTransaction.getTranDataId();
	if (tranDataId != null) {
	    String ussdParam = USSDInputParamsEnum.getUssdParamForTran(tranDataId);

	    if (ussdParam == null || StringUtils.isEmpty(ussdParam)) {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }

	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	    if (userInputMap == null) {
		userInputMap = new HashMap<String, String>();
	    }
	    userInputMap.put(ussdParam, paramDTO.getUserInput());
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	}
    }

    private MenuItemDTO createErrorScreen(USSDNonBlockingException ussdnbe, USSDSessionManagement ussdSessionMgmt) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Generating the error screen...");
	}
	NavigationOptionsDTO navigationOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getBusinessId(), ussdSessionMgmt.getCountryCode());
	MenuItemDTO errorPageDTO = new MenuItemDTO();
	errorPageDTO.setStatus(USSDConstants.STATUS_ERROR);
	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	String errorCode = USSDExceptions.getUssdErrorCodeforBMG(ussdnbe.getErrorCode());
	errorPageDTO.setPageError(StringUtils.EMPTY);
	List<String> errorParams = ussdnbe.getErrorParams();
	//CR-86 back error screen for directly hitting the call button issue
	if(errorParams!=null){
	String blankErrorIssue = errorParams.get(0);
	if(blankErrorIssue != null && blankErrorIssue.contains("-") && blankErrorIssue.contains("*")){
		errorParams.remove(0);
		errorParams.add(0, "");

		}
	}
	String[] errors = new String[] {};
	if (errorParams != null && errorParams.size() > 0) {
	    errors = errorParams.toArray(new String[errorParams.size()]);

	}


	String errorMessage = ussdResourceBundle.getLabel(errorCode, errors, new Locale(userProfile.getLanguage(), userProfile.getCountryCode()));
	if (errorMessage == null || StringUtils.isEmpty(errorMessage) || StringUtils.equalsIgnoreCase(errorMessage, USSDConstants.UNKNOWN_LABEL)) {
	    errorMessage = ussdResourceBundle.getLabel(USSDConstants.GENERIC_ERROR_CODE, errors, new Locale(userProfile.getLanguage(), userProfile
		    .getCountryCode()));
	}
	errorPageDTO.setPageHeader(USSDConstants.LBL_BLANK_PAGE_HEADER);

	errorPageDTO.setPaginationType(PaginationEnum.SPACED);

	StringBuilder pageFooter = new StringBuilder();
	//Forgot Pin Change
    //Forgot pin Issue 2295
	String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
		pageFooter.append(USSDConstants.BACK_LBL);
	} else if(ussdSessionMgmt.isFreeDialUssdFlow()&& !"BMB90012BMB90013".contains(ussdnbe.getErrorCode())){//FreeDialUssdAirTupUP
		pageFooter.append(USSDConstants.BACK_LBL);
	}
	else if(!"BMB90012BMB90013".contains(ussdnbe.getErrorCode())){
	pageFooter.append(USSDConstants.GO_BACK_N_HOME_LABEL_ID);
	pageFooter.append(USSDConstants.PIPE);
	pageFooter.append(navigationOptions.getHomeOption());
	}

	errorPageDTO.setPageBody(errorMessage + USSDConstants.NEW_LINE);
	if(!"BMB90012BMB90013".contains(ussdnbe.getErrorCode())){
	pageFooter.append(USSDConstants.PIPE);
	pageFooter.append(navigationOptions.getBackOption());
	errorPageDTO.setPageFooter(pageFooter.toString());
	}

	errorPageDTO.setErrorPage(true);

	//Changes for MNO Initiated MWallet
	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd4.00") && ussdSessionMgmt.getBusinessId().equals("GHBRB")){
		errorPageDTO.setPageFooter("");
		errorPageDTO.setErrorPage(false);
	}


	ussdSessionMgmt.setTransactionFlag(false);
	return errorPageDTO;
    }

    private MenuItemDTO createErrorScreenForByePass(USSDNonBlockingException ussdnbe, USSDSessionManagement ussdSessionMgmt) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Generating the error screen...");
	}
	NavigationOptionsDTO navigationOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getBusinessId(), ussdSessionMgmt.getCountryCode());

	MenuItemDTO errorPageDTO = new MenuItemDTO();
	errorPageDTO.setStatus(USSDConstants.STATUS_ERROR);
	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	String errorCode = USSDExceptions.getUssdErrorCodeforBMG(ussdnbe.getErrorCode());
	errorPageDTO.setPageError(StringUtils.EMPTY);
	List<String> errorParams = ussdnbe.getErrorParams();
	String[] errors = new String[] {};
	if (errorParams != null && errorParams.size() > 0) {
	    errors = errorParams.toArray(new String[errorParams.size()]);
	}
	String errorMessage = ussdResourceBundle.getLabel(errorCode, errors, new Locale(userProfile.getLanguage(), userProfile.getCountryCode()));
	if (errorMessage == null || StringUtils.isEmpty(errorMessage) || StringUtils.equalsIgnoreCase(errorMessage, USSDConstants.UNKNOWN_LABEL)) {
	    errorMessage = ussdResourceBundle.getLabel(USSDConstants.GENERIC_ERROR_CODE, errors, new Locale(userProfile.getLanguage(), userProfile
		    .getCountryCode()));
	}
	errorPageDTO.setPageHeader(USSDConstants.LBL_BLANK_PAGE_HEADER);
	errorPageDTO.setPageBody(errorMessage + USSDConstants.NEW_LINE);
	errorPageDTO.setPaginationType(PaginationEnum.SPACED);
	StringBuilder pageFooter = new StringBuilder();
	pageFooter.append(USSDConstants.GO_BACK_LABEL_ID);
	pageFooter.append(USSDConstants.PIPE);
	pageFooter.append(navigationOptions.getBackOption());
	errorPageDTO.setPageFooter(pageFooter.toString());
	errorPageDTO.setErrorPage(true);

	ussdSessionMgmt.setTransactionFlag(false);
	return errorPageDTO;
    }

    private MenuItemDTO handleBackActionOnErrorScreen(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt)
	    throws USSDBlockingException {
	MenuItemDTO menuItemDTO = null;
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Handle the back action on the error screen...");
	}
	CurrentRunningTransaction errorCurrentRunningTransaction = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction();
	UssdDecisionParserParamDTO newParamDTO = null;
	if (paramDTO.isByPassRequest()) {
	    newParamDTO = ussdParser.performBackOperationForByePassErrorScreen(paramDTO, ussdSessionMgmt);
	} else {
	    newParamDTO = ussdParser.performBackOperationForErrorScreen(paramDTO, ussdSessionMgmt);
	}
	if (!StringUtils.equalsIgnoreCase(errorCurrentRunningTransaction.getTranDataId(), USSDInputParamsEnum.TWO_FACTOR_VERIFY.getTranId())) {
	    if (ussdSessionMgmt.getUserTransactionDetails() != null
		    && StringUtils.equalsIgnoreCase(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId(),
			    USSDInputParamsEnum.TWO_FACTOR_INIT.getTranId())) {
		newParamDTO = ussdParser.performBackOperationForTx(newParamDTO, ussdSessionMgmt);
	    }
	}
	if (newParamDTO != null) {
	    newParamDTO.setErrorneousPage(true);
	    newParamDTO.setBackOnErrorScreen(true);
	    menuItemDTO = getMenuList(newParamDTO, ussdSessionMgmt);
	    menuItemDTO.setTranId(null);
	}
	return menuItemDTO;
    }

    private USSDBaseResponse getBMGResponse(USSDSessionManagement ussdSessionMgmt, CurrentRunningTransaction currentRunningTransaction,
	    USSDBaseRequest ussdBaseRequest, boolean errorneousPage) throws USSDBlockingException, USSDNonBlockingException {
	String responseJSONString = "";

	if (!USSDConstants.BMG_CALL_NOT_REQUIRED.equalsIgnoreCase(currentRunningTransaction.getBmgOpCode())) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Call BMG service");
	    }
	    CallBMGAccordingRequestInter callBMGAccordingRequestInter = ussdServiceInvoker.getInvoker(currentRunningTransaction.getBmgOpCode());
	    responseJSONString = callBMGAccordingRequestInter.callBMGClient(ussdBaseRequest, ussdSessionMgmt.isDemoMode(), ussdSessionMgmt
		    .getUserProfile().getBusinessId(), ussdSessionMgmt.getUserProfile().getCountryCode());
	}

	// set the reponse builder params
	ResponseBuilderParamsDTO responseBuilderParamsDTO = populateResponseBuilderParams(ussdSessionMgmt, currentRunningTransaction,
		responseJSONString, errorneousPage);
	return bmgBaseResponseBuilder.getResponseObject(responseBuilderParamsDTO);
    }

    private USSDBaseRequest getUSSDRequest(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt,
	    CurrentRunningTransaction currentRunningTransaction, CurrentRunningTransaction previousTransaction) throws USSDNonBlockingException,
	    USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = null;
	// get the request builder object for the respective service from the
	// request factory
	if (StringUtils.equalsIgnoreCase(previousTransaction.getOptional(), TRUE)
		&& StringUtils.equalsIgnoreCase(paramDTO.getUserInput(), USSDConstants.SKIP_SCREEN_INPUT)) {
	    paramDTO.setUserInput(EMPTY_USER_INPUT);
	}
	BmgBaseRequestBuilder bmgBaseRequestBuilder = ussdRequestFactory.getBmgRequestBuilderObject(currentRunningTransaction.getTranDataId());

	RequestBuilderParamsDTO requestBuilderParamsDTO = new RequestBuilderParamsDTO();
	requestBuilderParamsDTO.setBmgOpCode(currentRunningTransaction.getBmgOpCode());
	requestBuilderParamsDTO.setHeaderId(currentRunningTransaction.getHeaderId());
	requestBuilderParamsDTO.setMsisdnNo(ussdSessionMgmt.getMsisdnNumber());
	if (previousTransaction.getSkipNode() != null && previousTransaction.getSkipNode().equalsIgnoreCase(TRUE)) {
	    paramDTO.setUserInput(previousTransaction.getDefaultValue());
	}
	requestBuilderParamsDTO.setUserInput(paramDTO.getUserInput());
	requestBuilderParamsDTO.setUssdSessionMgmt(ussdSessionMgmt);
	ussdBaseRequest = bmgBaseRequestBuilder.getRequestObject(requestBuilderParamsDTO);

	/* Sending addtional params to the bmg: channelId and businessId */
	if (!USSDConstants.BMG_CALL_NOT_REQUIRED.equalsIgnoreCase(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction()
		.getBmgOpCode())
		&& ussdBaseRequest != null && ussdBaseRequest.getRequestParamMap() != null) {
	    String reqParamBMGBusinessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	    ussdBaseRequest.getRequestParamMap().put(USSDConstants.BMG_BUSINESS_ID_PARAM_NAME,
		    USSDUtils.getBMGReqParamBusinessID(reqParamBMGBusinessId));
	    ussdBaseRequest.getRequestParamMap().put(USSDConstants.BMG_CHANNEL_ID_PARAM_NAME, USSDConstants.BMG_CHANNEL_ID_DEFAULT_VALUE);
	}

	return ussdBaseRequest;
    }

    /**
     * Below method sets the response builder params
     *
     * @param ussdSessionMgmt
     * @param currentRunningTransaction
     * @param responseJSONString
     * @param errorneousPage
     * @return
     */
    private ResponseBuilderParamsDTO populateResponseBuilderParams(USSDSessionManagement ussdSessionMgmt,
	    CurrentRunningTransaction currentRunningTransaction, String responseJSONString, boolean errorneousPage) {
	NavigationOptionsDTO backHomeOptions = ussdParser.getNavigationOptions(ussdSessionMgmt.getBusinessId(), ussdSessionMgmt.getCountryCode());

	ResponseBuilderParamsDTO responseBuilderParamsDTO = new ResponseBuilderParamsDTO();
	responseBuilderParamsDTO.setJsonString(responseJSONString);
	responseBuilderParamsDTO.setBmgOpCode(currentRunningTransaction.getBmgOpCode());
	responseBuilderParamsDTO.setTranDataId(currentRunningTransaction.getTranDataId());
	responseBuilderParamsDTO.setHeaderId(currentRunningTransaction.getHeaderId());
	responseBuilderParamsDTO.setUssdSessionMgmt(ussdSessionMgmt);
	responseBuilderParamsDTO.setTranDataOpCode(currentRunningTransaction.getTransactionDataOpCode());
	responseBuilderParamsDTO.setUssdResourceBundle(this.ussdResourceBundle);
	responseBuilderParamsDTO.setErrorneousPage(errorneousPage);
	responseBuilderParamsDTO.setBackHomeOptions(backHomeOptions);

	return responseBuilderParamsDTO;
    }

    public MenuItemDTO callTheXMLParser(UssdDecisionParserParamDTO paramDTO, List<String> errorCodes) {
	MenuItemDTO menuItemDTO = ussdParser.getMenuList(paramDTO, errorCodes);
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	return menuItemDTO;
    }

    public boolean isValidInput(Screen screen, String userInput) {
	Map<String, String> inputMap = screen.getScreenData();
	return inputMap.containsKey(userInput);
    }

    public void setUssdServiceInvoker(UssdServiceInvoker ussdServiceInvoker) {
	this.ussdServiceInvoker = ussdServiceInvoker;
    }

    public void setUssdParser(UssdParser ussdParser) {
	this.ussdParser = ussdParser;
    }

    public void setUssdRequestFactory(IUSSDRequestFactory ussdRequestFactory) {
	this.ussdRequestFactory = ussdRequestFactory;
    }

    public void setBmgBaseResponseBuilder(BmgBaseResponseBuilder bmgBaseResponseBuilder) {
	this.bmgBaseResponseBuilder = bmgBaseResponseBuilder;
    }

    public void setUssdResourceBundle(UssdResourceBundle ussdResourceBundle) {
	this.ussdResourceBundle = ussdResourceBundle;
    }

}
