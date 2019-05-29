/**
 *
 */
package com.barclays.ussd.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.CurrentRunningTransaction;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.USSDLocale;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;


/**
 * The Class USSDUtils.
 *
 */
public final class USSDUtils {

    /** The Constant SEPERATOR. */
    private static final String SEPERATOR = "_";

    private static final String CACHE_ENABLED_PREFIX = "sessionCacheEnabled";

    private static final String SESSION_CACHE_ENABLED_FLAG = "sessionCacheEnabledForList";

    /** The Constant TRUE. */
    private static final String TRUE = "true";

    private static final Logger LOGGER = Logger.getLogger(USSDUtils.class);
    private USSDUtils() {
	// TODO Auto-generated constructor stub
    }

    /**
     * Append home and back option.
     *
     * @param menuItemDTO
     *            the menu item dto
     * @param responseBuilderParamsDTO
     *            the response builder params dto
     */
    public static void appendHomeAndBackOption(MenuItemDTO menuItemDTO, ResponseBuilderParamsDTO responseBuilderParamsDTO) {

	StringBuilder pageFooter = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	CurrentRunningTransaction currentRunningTransaction = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction();
	NavigationOptionsDTO backHomeOptions = responseBuilderParamsDTO.getBackHomeOptions();
	LOGGER.debug("Current Transaction : "+currentRunningTransaction);
	if (TRUE.equalsIgnoreCase(currentRunningTransaction.getBackOptionReq())
		&& TRUE.equalsIgnoreCase(currentRunningTransaction.getHomeOptionReq())) {
	    pageFooter.append(USSDConstants.GO_BACK_N_HOME_LABEL_ID);
	    pageFooter.append(USSDConstants.PIPE);
	    pageFooter.append(backHomeOptions.getHomeOption());
	    pageFooter.append(USSDConstants.PIPE);
	    pageFooter.append(backHomeOptions.getBackOption());

	} else if (TRUE.equalsIgnoreCase(currentRunningTransaction.getBackOptionReq())
		&& !TRUE.equalsIgnoreCase(currentRunningTransaction.getHomeOptionReq())) {
	    pageFooter.append(USSDConstants.GO_BACK_LABEL_ID);
	    pageFooter.append(USSDConstants.PIPE);
	    pageFooter.append(backHomeOptions.getBackOption());
	} else if (TRUE.equalsIgnoreCase(currentRunningTransaction.getHomeOptionReq())
		&& !TRUE.equalsIgnoreCase(currentRunningTransaction.getBackOptionReq())) {
	    pageFooter.append(USSDConstants.GO_TO_HOME_PAGE_LABEL_ID);
	    pageFooter.append(USSDConstants.PIPE);
	    pageFooter.append(backHomeOptions.getHomeOption());
	}

	menuItemDTO.setPageFooter(pageFooter.toString());
    }

    /**
     * Append home and back option.
     *
     * @param cntry
     * @param lang
     * @param ussdResourceBundle
     * @param backHomeOptionDTO
     *
     * @param menuItemDTO
     *            the menu item dto
     * @param responseBuilderParamsDTO
     *            the response builder params dto
     */
    public static void appendHomeAndBackOptionInMenu(StringBuffer buffer, boolean backOptionPresent, boolean homeOptionPresent,
	    UssdResourceBundle ussdResourceBundle, String lang, String cntry, NavigationOptionsDTO backHomeOptionDTO) {
	String labelId = null;
	String[] params = null;

	if (backOptionPresent && homeOptionPresent) {
	    labelId = USSDConstants.GO_BACK_N_HOME_LABEL_ID;
	    params = new String[2];
	    params[0] = backHomeOptionDTO.getHomeOption();
	    params[1] = backHomeOptionDTO.getBackOption();
	} else if (backOptionPresent && !homeOptionPresent) {
	    labelId = USSDConstants.GO_BACK_LABEL_ID;
	    params = new String[1];
	    params[0] = backHomeOptionDTO.getBackOption();
	} else if (homeOptionPresent && !backOptionPresent) {
	    labelId = USSDConstants.GO_TO_HOME_PAGE_LABEL_ID;
	    params = new String[1];
	    params[0] = backHomeOptionDTO.getHomeOption();
	}

	if (labelId != null) {
	    buffer.append(USSDConstants.NEW_LINE + ussdResourceBundle.getLabel(labelId, params, new Locale(lang, cntry)));
	}
    }

    /**
     * Gets the uSSD locale.
     *
     * @param strLocale
     *            the str locale
     * @return the uSSD locale
     */
    public static USSDLocale getUSSDLocale(String strLocale) {
	USSDLocale locale = null;
	if (strLocale != null) {
	    locale = new USSDLocale();
	    String[] splittedArr = strLocale.split(SEPERATOR);
	    if (splittedArr != null && splittedArr.length <= 1) {
		locale.setLang(splittedArr[0]);
		locale.setCountryCode(splittedArr[1]);
	    }
	}
	return locale;
    }

    /**
     * Generate random number.
     *
     * @param rangeLimit
     *            the range limit
     * @return the int
     * @throws USSDNonBlockingException
     *             the uSSD non blocking exception
     */
    public static int generateRandomNumber(int rangeLimit) throws USSDNonBlockingException {
	Random random = null;
	int randNum = 0;

	try {
	    random = SecureRandom.getInstance("SHA1PRNG");
	    randNum = random.nextInt(rangeLimit);
	} catch (NoSuchAlgorithmException nsae) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TWO_FACT_EXCEP_FAIL.getUssdErrorCode());
	}
	return randNum;
    }

    public static String getCountryCode(USSDRequest ussdRequest) {
	String cntryCode = null;
	String businessId = ussdRequest.getBusinessId();
	String countryCdeBusinessId = null;
	countryCdeBusinessId = ConfigurationManager.getString(businessId);
	if (countryCdeBusinessId != null) {
	    String contryCde[] = countryCdeBusinessId.split(USSDConstants.UNDERSCORE_SEPERATOR);
	    cntryCode = contryCde[0];
	}
	return cntryCode;
    }

    /**
     * Get the business Id from the request. It takes the businessId as BBTZ etc, and return the desired business Id for the BMG Like TZBRB, MUBRB
     * etc. It required for the BMG as request Parameter.
     *
     *
     * @param ussdRequest
     * @return
     * @throws USSDBlockingException
     */
    public static String getBMGReqParamBusinessID(String reqParamBMGBusinessId) throws USSDBlockingException {
	String countryCdeBusinessId = null;
	countryCdeBusinessId = ConfigurationManager.getString(reqParamBMGBusinessId);
	if (countryCdeBusinessId == null || StringUtils.isEmpty(countryCdeBusinessId)) {
	    throw new USSDBlockingException(USSDExceptions.USSD_DOWN.getUssdErrorCode());
	}

	String bizId[] = countryCdeBusinessId.split(USSDConstants.UNDERSCORE_SEPERATOR);
	return bizId[1];
    }

    public static String getCustomerReferenceId(UssdResourceBundle ussdResourceBundle, Locale locale, String billerID) {
	String custRefID = USSDConstants.CUST_REF_ID_DEFAULT;
	String billerIDtoCheck = new StringBuilder(USSDConstants.CUST_REF_ID).append(billerID).toString();
	if (!StringUtils.equalsIgnoreCase(ussdResourceBundle.getLabel(billerIDtoCheck, locale), USSDConstants.UNKNOWN_LABEL)) {
	    custRefID = billerIDtoCheck;
	}
	return custRefID;
    }

    public static String getConfCustRefId(UssdResourceBundle ussdResourceBundle, Locale locale, String billerID) {
	String custRefID = USSDConstants.CONF_CR_ID_DEFAULT;
	String billerIDtoCheck = new StringBuilder(USSDConstants.CONF_CR_ID).append(billerID).toString();
	if (!StringUtils.equalsIgnoreCase(ussdResourceBundle.getLabel(billerIDtoCheck, locale), USSDConstants.UNKNOWN_LABEL)) {
	    custRefID = billerIDtoCheck;
	}
	return custRefID;
    }

    /**
     * Append country code to mobile number
     */
    public static String appendCountryCode(String businessId, String userInputMobileNo, Integer snlength, Integer ccvalue) {
	StringBuffer requestParamMobilenumber = new StringBuffer(userInputMobileNo);

	if (userInputMobileNo.length() == snlength) {
	    requestParamMobilenumber.insert(0, ccvalue);

	} else {
	    requestParamMobilenumber.deleteCharAt(0).insert(0, ccvalue);
	}
	return requestParamMobilenumber.toString();
    }

    public static boolean isSessionCacheEnabled() {
	boolean sessionFlag = false;
	List<String> businessList = null;
	businessList = getSessionCacheBusinessList();
	if (businessList != null) {
	    if (!businessList.isEmpty()) {
		if (!StringUtils.isEmpty(businessList.get(0))) {
		    sessionFlag = true;
		}
	    }
	}
	return sessionFlag;

    }

    private static List<String> getSessionCacheBusinessList() {
	return ConfigurationManager.getList(SESSION_CACHE_ENABLED_FLAG);
    }

    public static boolean isSessionCacheEnabled(String businessId, String countryCode) {
	boolean result = false;
	List<String> sessionCacheBusinessList = getSessionCacheBusinessList();
	String business = getCacheKey(businessId, countryCode);
	if (sessionCacheBusinessList != null && !sessionCacheBusinessList.isEmpty()) {
	    result = sessionCacheBusinessList.contains(business);
	}
	return result;
    }

    // private static String getCacheKey(String businessId, String countryCode) {
    // return (new StringBuffer(CACHE_ENABLED_PREFIX).append(USSDConstants.UNDERSCORE_SEPERATOR).append(businessId).append(
    // USSDConstants.UNDERSCORE_SEPERATOR).append(countryCode)).toString();
    // }

    private static String getCacheKey(String businessId, String countryCode) {
	return (new StringBuffer(businessId).append(USSDConstants.UNDERSCORE_SEPERATOR).append(countryCode)).toString();
    }

    public static String getNextTransactionStep(String serviceName, int sequenceNo) {
	StringBuffer nextStep = new StringBuffer();
	nextStep.append(serviceName).append(USSDConstants.DOT_SEPERATOR).append(sequenceNo);
	return ConfigurationManager.getString(nextStep.toString());
    }

    public static void main(String args[]) throws USSDNonBlockingException{
    	List<Date> debitCardExpiryDateList=new ArrayList<Date>();
    	debitCardExpiryDateList.add(new Date());
    	String userEnteredDebitCardExpiryDate="0520";
    	USSDUtils.validateDebitCardDigitsExpiryDate(debitCardExpiryDateList, userEnteredDebitCardExpiryDate);
    	USSDUtils.generateRandomDebitCardPositions();
    	List<Integer> positions = new ArrayList<Integer>();
    	positions.add(7);
    	positions.add(8);
    	positions.add(9);
    	positions.add(10);
    	String userEntereddebitCardNumbers = "7891";

    	List<String> debitCardNumberList = new ArrayList<String>();

    	debitCardNumberList.add("3243434342343343");
    	debitCardNumberList.add("1234567841123456");
    	debitCardNumberList.add("1234566891123456");
    	debitCardNumberList.add("1234567891123456");

    	Set<Integer> randomPositions = new HashSet<Integer>();
    	List<Integer> firstSixDigitsList = new ArrayList<Integer>();//this list holds next 6 digits after the 6th position of the valid debit card nos i.e. with status code='C'
    	firstSixDigitsList.add(7);
    	firstSixDigitsList.add(8);
    	firstSixDigitsList.add(9);
    	firstSixDigitsList.add(10);
    	firstSixDigitsList.add(11);
    	firstSixDigitsList.add(12);


    		for(int i=0; i < 6; i++){
    			if (randomPositions.size() < 3){
    			int pos = getRandomList(firstSixDigitsList) ;
    			randomPositions.add(pos);
    			}
    	}
    	List<Integer> lastFourDigitsList = new ArrayList<Integer>();//this list holds next 6 digits after the 6th position of the valid debit card nos i.e. with status code='C'
    	lastFourDigitsList.add(13);
    	lastFourDigitsList.add(14);
    	lastFourDigitsList.add(15);
    	lastFourDigitsList.add(16);


    		for(int i=0; i < 4; i++){
    			if (randomPositions.size() < 4){
    			int pos = getRandomList(lastFourDigitsList) ;
    			randomPositions.add(pos);
    			}
    	}
    }

    /**
     * Populaqte 4 random positions
     * @return random positions for 16 digit debit card as per KEBRB CR-35 specification
     * @throws USSDNonBlockingException
     */
    public static List<Integer>  generateRandomDebitCardPositions()//Set<Integer> distinctPositionsSet, int maxPositions,int positionsRequired, List<Integer> listOfDigits
			throws USSDNonBlockingException {
		Set<Integer> randomPositions = new HashSet<Integer>();
    	List<Integer> firstSixDigitsList = new ArrayList<Integer>();//this list holds next 6 digits after the 6th position of the valid debit card nos i.e. with status code='C'
    	firstSixDigitsList.add(7);
    	firstSixDigitsList.add(8);
    	firstSixDigitsList.add(9);
    	firstSixDigitsList.add(10);
    	firstSixDigitsList.add(11);
    	firstSixDigitsList.add(12);
    		for(int i=0; i < 6; i++){
    			if (randomPositions.size() < 3){
    			int pos = getRandomList(firstSixDigitsList) ;
    			randomPositions.add(pos);
    			}
    	}
    	List<Integer> lastFourDigitsList = new ArrayList<Integer>();//this list holds next 6 digits after the 6th position of the valid debit card nos i.e. with status code='C'
    	lastFourDigitsList.add(13);
    	lastFourDigitsList.add(14);
    	lastFourDigitsList.add(15);
    	lastFourDigitsList.add(16);
    		for(int i=0; i < 4; i++){
    			if (randomPositions.size() < 4){
    			int pos = getRandomList(lastFourDigitsList) ;
    			randomPositions.add(pos);
    			}
    	}
    		Iterator<Integer> itr = randomPositions.iterator();
    		while (itr.hasNext()){
    		LOGGER.info("USSDUtils generateRandomDebitCardPositions Random no."+itr.next());
    	}
    		List<Integer> randomPositionsList = new ArrayList<Integer>(randomPositions);
    		Collections.sort( randomPositionsList );
    		return randomPositionsList;
	}
    /**
     * Validate the debit cards positions from unser entered positions
     * @param positions
     * @param userEntereddebitCardNumbers
     * @param debitCardNumberList
     * @return
     * @throws USSDNonBlockingException
     */
    public static boolean validateRandomDebitCardDigits(
			List<Integer> positions,String userEntereddebitCardNumbers,List<String> debitCardNumberList)
			throws USSDNonBlockingException {
    	LOGGER.info("Entry USSDUtils validateRandomDebitCardDigits userEntereddebitCardNumbers:"+userEntereddebitCardNumbers);
    	LOGGER.info("Entry USSDUtils validateRandomDebitCardDigits debitCardNumberList size:"+debitCardNumberList.size());
    	ArrayList<Character> chars = new ArrayList<Character>();
    	int debitCardCount=1;
    	for (char c : userEntereddebitCardNumbers.toCharArray()) {
    	  chars.add(c);
    	}
    	for(String debitCard:debitCardNumberList){
    		int count=0;

    		for(Integer pos:positions){
    			if(debitCard.charAt(pos-1)==((chars.get(count)))){

    				count++;
    				if(count==4){
    					//LOGGER.info("USSDUtils validateRandomDebitCardDigits count==4");
    					return true;
    				}
    				continue;
    			}else{

    				if(debitCardCount==debitCardNumberList.size())
    				return false;
    				else
    					continue;
    			}
    		}
    		debitCardCount++;

    	}
    	//LOGGER.info("Exit USSDUtils validateRandomDebitCardDigits");
    	return true;
    }
    public static int getRandomList(List<Integer> list) throws USSDNonBlockingException {

	    int index = generateRandomNumber(list.size());
	    return list.get(index);

	}
    /**
     * Validate the expiry date of debit card during self regsitration in MMyy format
     * @param debitCardExpiryDateList
     * @param userEnteredDebitCardExpiryDate
     * @return
     * @throws USSDNonBlockingException
     */
    public static boolean validateDebitCardDigitsExpiryDate(
    		List<Date> debitCardExpiryDateList,String userEnteredDebitCardExpiryDate)
			throws USSDNonBlockingException {
    	LOGGER.info("Entry USSDUtils validateDebitCardDigitsExpiryDate");
    	LOGGER.info("Entry USSDUtils validateDebitCardDigitsExpiryDate debitCardExpiryDateList size:"+debitCardExpiryDateList.size());
    	LOGGER.info("Entry USSDUtils validateDebitCardDigitsExpiryDate userEnteredDebitCardExpiryDate:"+userEnteredDebitCardExpiryDate);
    	SimpleDateFormat dateFormat=new SimpleDateFormat("MMyy");
    	for(Date date:debitCardExpiryDateList){

    		LOGGER.info("USSDUtils validateDebitCardDigitsExpiryDate Formatted date"+dateFormat.format(date));
    		if(dateFormat.format(date).equals(userEnteredDebitCardExpiryDate)){
    			return true;}
    	}

    	LOGGER.info("Exit USSDUtils validateDebitCardDigitsExpiryDate");
    	return false;
    }
}
