package com.barclays.ussd.auth.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.Transaction;
import com.barclays.ussd.parser.UssdDecisionParserParamDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDSessionManagement.
 */
public class USSDSessionManagement implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The msisdn number. */
	private String msisdnNumber;

	/** The screen id. */
	private String screenId;

	/** The user profile. */
	private UserProfile userProfile;

	/** The current screen node id. */
	private String currentScreenNodeId;

	/** The transaction flag. */
	private boolean transactionFlag = false;

	/** The user transaction details. */
	private Transaction userTransactionDetails;

	/** The is first request. */
	private boolean isFirstRequest = true;

	/** The previous rendered screen. */
	private MenuItemDTO previousRenderedScreen;

	/** The tx sessions. */
	private Map<String, Object> txSessions;

	/** The current page position. */
	private int currentPagePosition = 0; // Pagination related

	/** The paged. */
	private boolean paged = false; // Pagination related

	/** The page list. */
	private List<MenuItemDTO> pageList; // Pagination related

	/** The listed response. */
	private boolean listedResponse = false; // Pagination related

	/** The first self reg call. */
	private boolean firstSelfRegCall = true;// Self Registration related

	// Two Factor auth userInput variable
	/** The two fact user input. */
	private String twoFactUserInput;

	// change pin flag
	private boolean pinChangeReq = false;

	/** The business id. */
	private String businessId;

	/** The country code. */
	private String countryCode;

	/** Welcome Screen Bal Main Enq Object. */
	private Object userAuthObj;

	/** The errorPage param DTO . */
	private UssdDecisionParserParamDTO errorScreenParamDTO;

	/** No of 2fa attempts made by the user */
	private int twoFAattemptCount = 0;

	/** old pin placeholder for first time login **/
	private String firstLoginOldPin;

	/** boolean flag for customer invoking the demo **/
	boolean demoMode = false;

	/** customerType to identify HM USER **/
	private String customerType;

	/** customer flag to identify an unregistered HM customer **/
	private String nonHMCustomerFlag;

	/** customer flag to check if an unregistered customer is changing the language **/
	private String changeLanguageMode;

	private String msisdnWithCountry;

	private boolean userMigration = false;

	private String customerAccessStatusCode = "N";

	//CR-86
	private boolean backFlowErrorScreen = false ;

	//FreeDialUSSD
	private boolean isFreeDialUssdFlow = false ;

	//Forgot Pin Change
	private boolean isForgotPinFlow=false;

	//FreeDialUSSD
	public void setFreeDialUssdFlow(boolean isFreeDialUssdFlow) {
		this.isFreeDialUssdFlow = isFreeDialUssdFlow;
	}
	public boolean isFreeDialUssdFlow() {
		return isFreeDialUssdFlow;
	}
	//Forgot Pin Change
	public boolean isForgotPinFlow() {
		return isForgotPinFlow;
	}
	//Forgot Pin Change
	public void setForgotPinFlow(boolean isForgotPinFlow) {
		this.isForgotPinFlow = isForgotPinFlow;
	}

	public boolean isUserMigration() {
		return userMigration;
	}

	public void setUserMigration(boolean userMigration) {
		this.userMigration = userMigration;
	}

	public String getUnregCustLangPref() {
		return unregCustLangPref;
	}

	public void setUnregCustLangPref(String unregCustLangPref) {
		this.unregCustLangPref = unregCustLangPref;
	}

	private String unregCustLangPref;

	public boolean isDemoMode() {
		return demoMode;
	}

	public void setDemoMode(boolean demoMode) {
		this.demoMode = demoMode;
	}

	/**
	 * Gets the two fact user input.
	 *
	 * @return the two fact user input
	 */
	public String getTwoFactUserInput() {
		return twoFactUserInput;
	}

	/**
	 * Sets the two fact user input.
	 *
	 * @param twoFactUserInput
	 *            the new two fact user input
	 */
	public void setTwoFactUserInput(String twoFactUserInput) {
		this.twoFactUserInput = twoFactUserInput;
	}

	/**
	 * Gets the serial version uid.
	 *
	 * @return the serial version uid
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * Gets the msisdn number.
	 *
	 * @return the msisdn number
	 */
	public String getMsisdnNumber() {
		return msisdnNumber;
	}

	/**
	 * Sets the msisdn number.
	 *
	 * @param msisdnNumber
	 *            the new msisdn number
	 */
	public void setMsisdnNumber(String msisdnNumber) {
		this.msisdnNumber = msisdnNumber;
	}

	/**
	 * Gets the screen id.
	 *
	 * @return the screen id
	 */
	public String getScreenId() {
		return screenId;
	}

	/**
	 * Sets the screen id.
	 *
	 * @param screenId
	 *            the new screen id
	 */
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	/**
	 * Gets the user profile.
	 *
	 * @return the user profile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
	 * Sets the user profile.
	 *
	 * @param userProfile
	 *            the new user profile
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * Gets the current screen node id.
	 *
	 * @return the current screen node id
	 */
	public String getCurrentScreenNodeId() {
		return currentScreenNodeId;
	}

	/**
	 * Sets the current screen node id.
	 *
	 * @param currentScreenNodeId
	 *            the new current screen node id
	 */
	public void setCurrentScreenNodeId(String currentScreenNodeId) {
		this.currentScreenNodeId = currentScreenNodeId;
	}

	/**
	 * Gets the user transaction details.
	 *
	 * @return the user transaction details
	 */
	public Transaction getUserTransactionDetails() {
		return userTransactionDetails;
	}

	/**
	 * Sets the user transaction details.
	 *
	 * @param userTransactionDetails
	 *            the new user transaction details
	 */
	public void setUserTransactionDetails(Transaction userTransactionDetails) {
		this.userTransactionDetails = userTransactionDetails;
	}

	/**
	 * Checks if is transaction flag.
	 *
	 * @return true, if is transaction flag
	 */
	public boolean isTransactionFlag() {
		return transactionFlag;
	}

	/**
	 * Sets the transaction flag.
	 *
	 * @param transactionFlag
	 *            the new transaction flag
	 */
	public void setTransactionFlag(boolean transactionFlag) {
		this.transactionFlag = transactionFlag;
	}

	/**
	 * Checks if is first request.
	 *
	 * @return true, if is first request
	 */
	public boolean isFirstRequest() {
		return isFirstRequest;
	}

	/**
	 * Sets the first request.
	 *
	 * @param isFirstRequest
	 *            the new first request
	 */
	public void setFirstRequest(boolean isFirstRequest) {
		this.isFirstRequest = isFirstRequest;
	}

	/**
	 * Gets the previous rendered screen.
	 *
	 * @return the previous rendered screen
	 */
	public MenuItemDTO getPreviousRenderedScreen() {
		return previousRenderedScreen;
	}

	/**
	 * Sets the previous rendered screen.
	 *
	 * @param previousRenderedScreen
	 *            the new previous rendered screen
	 */
	public void setPreviousRenderedScreen(MenuItemDTO previousRenderedScreen) {
		this.previousRenderedScreen = previousRenderedScreen;
	}

	/**
	 * Sets the tx sessions.
	 *
	 * @param txSessions
	 *            the txSessions to set
	 */
	public void setTxSessions(Map<String, Object> txSessions) {
		this.txSessions = txSessions;
	}

	/**
	 * Gets the tx sessions.
	 *
	 * @return the txSessions
	 */
	public Map<String, Object> getTxSessions() {
		return txSessions;
	}

	/**
	 * Gets the current page position.
	 *
	 * @return the current page position
	 */
	public int getCurrentPagePosition() {
		return currentPagePosition;
	}

	/**
	 * Sets the current page position.
	 *
	 * @param currentPagePosition
	 *            the new current page position
	 */
	public void setCurrentPagePosition(int currentPagePosition) {
		this.currentPagePosition = currentPagePosition;
	}

	/**
	 * Gets the page list.
	 *
	 * @return the page list
	 */
	public List<MenuItemDTO> getPageList() {
		return pageList;
	}

	/**
	 * Sets the page list.
	 *
	 * @param pageList
	 *            the new page list
	 */
	public void setPageList(List<MenuItemDTO> pageList) {
		this.pageList = pageList;
	}

	/**
	 * Checks if is paged.
	 *
	 * @return true, if is paged
	 */
	public boolean isPaged() {
		return paged;
	}

	/**
	 * Sets the paged.
	 *
	 * @param paged
	 *            the new paged
	 */
	public void setPaged(boolean paged) {
		this.paged = paged;
	}

	/**
	 * Checks if is first self reg call.
	 *
	 * @return true, if is first self reg call
	 */
	public boolean isFirstSelfRegCall() {
		return firstSelfRegCall;
	}

	/**
	 * Sets the first self reg call.
	 *
	 * @param firstSelfRegCall
	 *            the new first self reg call
	 */
	public void setFirstSelfRegCall(boolean firstSelfRegCall) {
		this.firstSelfRegCall = firstSelfRegCall;
	}

	/**
	 * Checks if is listed response.
	 *
	 * @return true, if is listed response
	 */
	public boolean isListedResponse() {
		return listedResponse;
	}

	/**
	 * Sets the listed response.
	 *
	 * @param listedResponse
	 *            the new listed response
	 */
	public void setListedResponse(boolean listedResponse) {
		this.listedResponse = listedResponse;
	}

	public boolean isPinChangeReq() {
		return pinChangeReq;
	}

	public void setPinChangeReq(boolean pinChangeReq) {
		this.pinChangeReq = pinChangeReq;
	}

	/**
	 * Session cleanup.
	 *
	 * @param ussdSessionMgmt
	 *            the ussd session mgmt
	 */
	public void clean() {
		setTransactionFlag(false);
		setTxSessions(null);
		Transaction userTransaction = getUserTransactionDetails();
		if (userTransaction != null) {
			userTransaction.setUserInputMap(null);
			userTransaction.setScreenSequenceStack(null);
			userTransaction.setCurrentRunningTransaction(null);
			userTransaction.setBmgRespCont(null);
			userTransaction.setCurrentScreenIndex(0);
			setUserTransactionDetails(null);
		}
		setErrorScreenParamDTO(null);
		setFirstSelfRegCall(true);
		setTwoFactUserInput(null);
		setPinChangeReq(false);
		//Forgot Pin Change
		setForgotPinFlow(false);
	}

	/**
	 * @return the businessId
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId
	 *            the businessId to set
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the userAuthObj
	 */
	public Object getUserAuthObj() {
		return userAuthObj;
	}

	/**
	 * @param userAuthObj
	 *            the userAuthObj to set
	 */
	public void setUserAuthObj(Object userAuthObj) {
		this.userAuthObj = userAuthObj;
	}

	/**
	 * @return the errorScreenParamDTO
	 */
	public UssdDecisionParserParamDTO getErrorScreenParamDTO() {
		return errorScreenParamDTO;
	}

	/**
	 * @param errorScreenParamDTO
	 *            the errorScreenParamDTO to set
	 */
	public void setErrorScreenParamDTO(UssdDecisionParserParamDTO errorScreenParamDTO) {
		this.errorScreenParamDTO = errorScreenParamDTO;
	}

	/**
	 * @return the twoFAattemptCount
	 */
	public int getTwoFAattemptCount() {
		return twoFAattemptCount;
	}

	/**
	 * @param twoFAattemptCount
	 *            the twoFAattemptCount to set
	 */
	public void setTwoFAattemptCount(int twoFAattemptCount) {
		this.twoFAattemptCount = twoFAattemptCount;
	}

	/**
	 * @return the firstLoginOldPin
	 */
	public String getFirstLoginOldPin() {
		return firstLoginOldPin;
	}

	/**
	 * @param firstLoginOldPin
	 *            the firstLoginOldPin to set
	 */
	public void setFirstLoginOldPin(String firstLoginOldPin) {
		this.firstLoginOldPin = firstLoginOldPin;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getNonHMCustomerFlag() {
		return nonHMCustomerFlag;
	}

	public void setNonHMCustomerFlag(String nonHMCustomerFlag) {
		this.nonHMCustomerFlag = nonHMCustomerFlag;
	}

	public String getMsisdnWithCountry() {
		return msisdnWithCountry;
	}

	public void setMsisdnWithCountry(String msisdnWithCountry) {
		this.msisdnWithCountry = msisdnWithCountry;
	}

	public String getChangeLanguageMode() {
		return changeLanguageMode;
	}

	public void setChangeLanguageMode(String changeLanguageMode) {
		this.changeLanguageMode = changeLanguageMode;
	}

	public String getCustomerAccessStatusCode() {
		return customerAccessStatusCode;
	}

	public void setCustomerAccessStatusCode(String customerAccessStatusCode) {
		this.customerAccessStatusCode = customerAccessStatusCode;
	}
	//CR-86 Back flow screen
	public void setBackFlowErrorScreen(boolean backFlowErrorScreen) {
		this.backFlowErrorScreen = backFlowErrorScreen;
	}
	public boolean isBackFlowErrorScreen() {
		return backFlowErrorScreen;
	}

}
