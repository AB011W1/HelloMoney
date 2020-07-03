package com.barclays.ussd.validation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.CurrentRunningTransaction;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.UssdMenuBuilder;

public final class USSDInputValidator {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(USSDInputValidator.class);

	private static final String TRUE = "true";
	private static final String CSMOBNUM = "csmsisdn";

	// CHANGE THIS TO ADD OR REMOVE ANY ALLOWED CHARACTERS.
	private static final String ALLOWED_CHARS = "^[\\pL\\pN*#]+$";
	private static final Pattern INPUT_VALIDATION_PATTERN = Pattern.compile(ALLOWED_CHARS);

	// CHANGE THIS TO ADD OR REMOVE ANY ALLOWED CHARACTERS FOR KEBRB.
	// CODE ADDED TO INCLUDE "-" IN BILLER REF NO. FOR KEBRB START
	private static final String ALLOWED_CHARS_KEBRB = "^[\\pL\\pN*#-]+$";
	private static final Pattern INPUT_VALIDATION_PATTERN_KEBRB = Pattern.compile(ALLOWED_CHARS_KEBRB);
	private static final String ALLOWED_CHARS_KITS_REASON = "^[\\pL\\pN\\s*#-]+$";
	private static final Pattern INPUT_VALIDATION_PATTERN_KITS = Pattern.compile(ALLOWED_CHARS_KITS_REASON);
	@Autowired
	UssdMenuBuilder ussdMenuBuilder;

	private USSDInputValidator() {
		// TODO Auto-generated constructor stub
	}

	private static boolean validateHomeBackOptions(final String userInput, CurrentRunningTransaction currentTransaction,
			final List<String> errorCodes, String backOption, String homeOption) {
		boolean homeBackOpt = false;
		if (userInput != null && backOption.equalsIgnoreCase(userInput.trim())) {
			if (TRUE.equalsIgnoreCase(currentTransaction.getBackOptionReq())) {
				homeBackOpt = true;
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Invalid user input as the back option is not available");
				}
				// The back button is not available on the screen
				errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
				homeBackOpt = false;
			}
			// do the back navigation
		} else if (userInput != null && homeOption.equalsIgnoreCase(userInput.trim())) {
			if (TRUE.equalsIgnoreCase(currentTransaction.getHomeOptionReq())) {
				homeBackOpt = true;
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Invalid user input as the home option is not available");
				}
				// home button is not available on the screen
				errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
				homeBackOpt = false;
			}
			// do the home screen navigation
		}
		return homeBackOpt;
	}

	public static boolean validate(CurrentRunningTransaction currentTransaction, final String userInput, USSDSessionManagement ussdSessionMgmt,
			final List<String> errorCodes, String backOption, String homeOption) {
		boolean blnResult = false;
		String type = currentTransaction.getType();
		String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if (userInput == null) {
			blnResult = true;
		} else {
			// SKIP_SCREEN_INPUT
			String userInputTrimmed = userInput.trim();
			if (StringUtils.isEmpty(userInputTrimmed) && !StringUtils.equalsIgnoreCase(currentTransaction.getOptional(), TRUE)
					&& !(StringUtils.equalsIgnoreCase(currentTransaction.getType(), CSMOBNUM))) {
				errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
				blnResult = false;
			}
			if(USSDConstants.DATA_TYPE_NIB.equalsIgnoreCase(type)){

				/*	if (homeOption.equalsIgnoreCase(userInputTrimmed) || backOption.equalsIgnoreCase(userInputTrimmed)) {
	    	 	blnResult = validateHomeBackOptions(userInput, currentTransaction, errorCodes, backOption, homeOption);
	    	}
	    	else if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
	    	    errorCodes.add(USSDExceptions.USSD_INVALID_NIB_NO.getUssdErrorCode());
	    	    LOGGER.error("Invalid user input : " + userInputTrimmed);
	    	    return false;
	    	}else if(userInput.length()==21)
	    		return true;
	    	else if(userInput.length()!=21){
	    		errorCodes.add(USSDExceptions.USSD_NIB_ERROR.getUssdErrorCode());
			    LOGGER.error("Invalid user input : " + userInputTrimmed);
	    		return false;
	    	}*/
			}
			// CODE ADDED TO INCLUDE "-" IN BILLER REF NO. FOR KEBRB START
			// CHECK IF THE USER HAS INPUT THE VALUES AS ALLOWED BY THE APPLICATION.
			if (USSDConstants.BUSINESS_ID_KEBRB.equalsIgnoreCase(ussdSessionMgmt.getUserProfile().getBusinessId())) {

				if(currentTransaction.getTranDataId().equalsIgnoreCase("SP005") || currentTransaction.getTranDataId().equalsIgnoreCase("SA005"))
				{
					if (!INPUT_VALIDATION_PATTERN_KITS.matcher(userInputTrimmed).matches()) {
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
						LOGGER.error("Invalid user input : " + userInputTrimmed);
						return false;
					}

				}else if(USSDConstants.DATA_TYPE_RESON_OF_PAYMENT.equalsIgnoreCase(type)){
					if (backOption.equalsIgnoreCase(userInput) || homeOption.equalsIgnoreCase(userInput)) {
						blnResult = true;
					}  else if (!StringUtils.isEmpty(userInput) && StringUtils.isNumeric(userInput) && Integer.parseInt(userInput.trim())>6) {
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
						return false;
					}
					else if (!StringUtils.isEmpty(userInput) && StringUtils.isNumeric(userInput)) {
						blnResult = true;
					}
					else{
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
						return false;
					}
				}else if(USSDConstants.DATA_TYPE_ADDRESS.equalsIgnoreCase(type)){
					if(userInput.equals("*"))
						return true;
					else if (userInput.length()>100) {
						errorCodes.add(USSDExceptions.USSD_ADDRESS_LENGTH_ERROR.getUssdErrorCode());
						LOGGER.error("Invalid user input : " + userInputTrimmed);
						return false;
					}
					else if(!validateNoSpecialCharsReason(userInput.trim(),errorCodes)){
						errorCodes.add(USSDExceptions.USSD_ADDRESS_SPECIAL_CHARS_ERROR.getUssdErrorCode());
						LOGGER.error("Invalid user input : " + userInputTrimmed);
						return false;
					}
					else
						return true;
				}else if (!INPUT_VALIDATION_PATTERN_KEBRB.matcher(userInputTrimmed).matches()) {
					//CR-86 Back flow changes FD/AP
					if(currentTransaction.getTranDataId().equalsIgnoreCase("FDR003") ){
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID_FD_AP.getUssdErrorCode());

					} else if(currentTransaction.getTranDataId().equalsIgnoreCase("LG003")){
						errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());

					} else {
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
					}
					LOGGER.error("Invalid user input : " + userInputTrimmed);
					return false;
				}
			}// CODE ADDED TO INCLUDE "-" IN BILLER REF NO. FOR KEBRB END
			else if(USSDConstants.GHIPPS_DATA_TYPE_RESON_OF_PAYMENT.equalsIgnoreCase(type)){
				if (backOption.equalsIgnoreCase(userInput) || homeOption.equalsIgnoreCase(userInput)) {
					blnResult = true;
				}  else if (!StringUtils.isEmpty(userInput) && StringUtils.isNumeric(userInput) && (Integer.parseInt(userInput.trim())>7 || Integer.parseInt(userInput.trim())<1 )) {
					errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
					return false;
				}
				else if (!StringUtils.isEmpty(userInput) && StringUtils.isNumeric(userInput)) {
					blnResult = true;
				}
				else{
					errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
					return false;
				}
			}
			else {
				// CHECK IF THE USER HAS INPUT THE VALUES AS ALLOWED BY THE APPLICATION.

				if (!INPUT_VALIDATION_PATTERN.matcher(userInputTrimmed).matches()&&!transNodeId.equals("ussd0.4.3.4.2")) {
					//CR-86 Back flow changes FD/AP
					if(currentTransaction.getTranDataId().equalsIgnoreCase("FDR003") ){
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID_FD_AP.getUssdErrorCode());

					} else if(currentTransaction.getTranDataId().equalsIgnoreCase("LG003")){
						errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());

					} else {
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
					}
					LOGGER.error("Invalid user input : " + userInputTrimmed);
					return false;
				}
			}

			if (USSDConstants.BUSINESS_ID_GHBRB.equalsIgnoreCase(ussdSessionMgmt.getUserProfile().getBusinessId())) {
				if(transNodeId!=null && transNodeId.equalsIgnoreCase("ussd4.00") && currentTransaction.getTranDataId().equalsIgnoreCase("MWTU003"))
				{
					if (userInputTrimmed.equals("#")) {
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
						LOGGER.error("Invalid user input : " + userInputTrimmed);
						return false;
					}
				}
			}
			if (StringUtils.equalsIgnoreCase(currentTransaction.getOptional(), TRUE)
					&& (StringUtils.equalsIgnoreCase(userInputTrimmed, USSDConstants.SKIP_SCREEN_INPUT) || StringUtils.isEmpty(userInputTrimmed))) {
				blnResult = true;
			} else if (homeOption.equalsIgnoreCase(userInputTrimmed) || backOption.equalsIgnoreCase(userInputTrimmed)) {
				blnResult = validateHomeBackOptions(userInput, currentTransaction, errorCodes, backOption, homeOption);

			} else if (USSDConstants.DATA_TYPE_LIST.equalsIgnoreCase(type)) {
				blnResult = validateList(userInput, currentTransaction.getTranDataId(), ussdSessionMgmt, errorCodes);
			} else if (USSDConstants.DATA_TYPE_AMOUNT.equalsIgnoreCase(type)) {
				blnResult = validateAmount(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_CONFIRM.equalsIgnoreCase(type)) {
				blnResult = validateConfirmation(userInput, errorCodes, currentTransaction);//CR-86
			} else if (USSDConstants.DATA_TYPE_END_TRAN.equalsIgnoreCase(type)) {
				blnResult = validateEndTrans(userInput, errorCodes, backOption, homeOption, currentTransaction);//CR-86
			} else if (USSDConstants.DATA_TYPE_NA.equalsIgnoreCase(type)) {
				String tran=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId();
				if(tran!=null && tran.equals("MWGP0029")){
					int input=Integer.parseInt(userInput.trim());
					Set<String> tranList=ussdSessionMgmt.getFinalTransactionList();
					if(tranList!=null && input>tranList.size()){
						errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
						blnResult=false;
					}
					else
						blnResult=true;
				}else{
				if(null != ussdSessionMgmt && null != ussdSessionMgmt.getUserTransactionDetails() && null != ussdSessionMgmt.getUserTransactionDetails().getUserInputMap()
						&& ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().containsKey("selectedBillerOtbp")){
					String strSelectedBillerOtbp = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("selectedBillerOtbp");
					if(strSelectedBillerOtbp!= null && strSelectedBillerOtbp !="" && strSelectedBillerOtbp.equals("WUC-2")
							&& !currentTransaction.getTranDataId().equals("OTBPSB011")){
						blnResult = validateWUCRefNo(userInput, errorCodes );
					}
					else{
						blnResult = true;
					}
				}else{
					blnResult = true;
				}
				}
			} else if (USSDConstants.DATA_TYPE_PWRD.equalsIgnoreCase(type)) {
				blnResult = validatePassword(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_LOGIN_PWRD.equalsIgnoreCase(type)) {
				blnResult = validateloginPwd(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_TWO_FACT_ANS.equalsIgnoreCase(type)) {
				blnResult = validateTwoFactAns(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_TWO_FACT_ANS_DOB.equalsIgnoreCase(type)) {
				blnResult = validateTwoFactAnsDOB(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_MSISDN.equalsIgnoreCase(type)) {
				blnResult = validateMSISDN(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_ACCTNO.equalsIgnoreCase(type)) {
				blnResult = validateAccountNumber(userInput, errorCodes);

			} else if (USSDConstants.DATA_TYPE_MOBILE_NUMBER.equalsIgnoreCase(type)) {
				blnResult = validateMobileNumber(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_BRANCH_CODE.equalsIgnoreCase(type)) {
				blnResult = validateBranchCode(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_ATM_PIN.equalsIgnoreCase(type)) {
				/*
				 * Changes as we have to make CashSned PIN 6 digit for ZM
				 */
				if (USSDConstants.BUSINESS_ID_ZMBRB.equalsIgnoreCase(ussdSessionMgmt.getUserProfile().getBusinessId())) {
					blnResult = validateATMPinZMBRB(userInput, errorCodes);
				}else{
					blnResult = validateATMPin(userInput, errorCodes);
				}
			} else if (USSDConstants.DATA_TYPE_SEARCHER_ALPHA_NUM.equalsIgnoreCase(type)) {
				blnResult = validateSearcherAlphaNum(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_SEARCHER_ALPHA_NUM_CHAR2.equalsIgnoreCase(type)) {
				blnResult = validateSearcherAlphaNumChar2(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_SEARCHER.equalsIgnoreCase(type)) {
				blnResult = validateSearcher(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_REF_NO.equalsIgnoreCase(type) &&
					!ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("selectedBillerRegb").equals("WUC-2")) {
				blnResult = validateRefNo(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_CASHSEND_MOBILE.equalsIgnoreCase(type)) {
				blnResult = validateCSMobileNum(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_KE_REF_NO.equalsIgnoreCase(type)) {// CODE ADDED TO INCLUDE "-" IN BILLER REF NO. FOR KEBRB START
				blnResult = validateKeRefNo(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_DEBIT_CARD_NO.equalsIgnoreCase(type)) {// Validate Debit Card no during Self-Registration
				blnResult = validateDebitCardNo(userInput, errorCodes);
			} else if (USSDConstants.DATA_TYPE_DEBIT_CARD_EXPIRY_DATE.equalsIgnoreCase(type)) {// Validate Debit Card Expiry Date during Self-Registration
				blnResult = validateDebitCardExpiryDate(userInput, errorCodes);
			}else if (USSDConstants.DATA_TYPE_REF_NO_ZW.equalsIgnoreCase(type)) {
				blnResult = validateZwRefNo(userInput, errorCodes, ussdSessionMgmt );
			}else if (USSDConstants.DATA_TYPE_CREDIT_CARD_NUMBER.equalsIgnoreCase(type)) {// CR#46 Validate Credit Card
				blnResult = validateCreditCardNumber(userInput, errorCodes);
			}else if (USSDConstants.DATA_TYPE_REF_NO_NBC.equalsIgnoreCase(type)) {
				blnResult = validateNbcRefNo(userInput, errorCodes, ussdSessionMgmt );
			}else if (USSDConstants.DATA_TYPE_KITS_REASON.equalsIgnoreCase(type)) {
				blnResult = validateReason(userInput, errorCodes );
			}else if (USSDConstants.DATA_TYPE_REF_NO.equalsIgnoreCase(type) && ussdSessionMgmt.getBusinessId().equals("BWBRB") &&
					ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("selectedBillerRegb").equals("WUC-2")) {
				blnResult = validateWUCRefNo(userInput, errorCodes);
			}else if (USSDConstants.DATA_TYPE_WUC_REF_NO.equalsIgnoreCase(type)) {
				blnResult = validateWUCRefNo(userInput, errorCodes );
			}else if (USSDConstants.DATA_TYPE_MZ_REF_NO.equalsIgnoreCase(type) && ussdSessionMgmt.getBusinessId().equals("MZBRB")) {
				blnResult = validateMZRefNo(userInput, errorCodes);
			}
			//Ghana Databundle confirm/cancel
			else if (USSDConstants.DATA_TYPE_CONFIRM_CANCEL.equalsIgnoreCase(type)) {
				blnResult = validateConfirmationCancel(userInput, errorCodes, currentTransaction);
			}
			else if(USSDConstants.DATA_TYPE_ACCTNO_MSISDN.equalsIgnoreCase(type)) {
				blnResult = validateMobileAccountNo(userInput, errorCodes, ussdSessionMgmt);
			}

		}

		return blnResult;
	}

	private static boolean validateNoSpecialCharsReason(String userInput,
			List<String> errorCodes) {
		// TODO Auto-generated method stub

		Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9 ]+");
		Matcher m = p.matcher(userInput);
		if(userInput.length()==1 && Character.isDigit(userInput.charAt(0))&&Integer.parseInt(userInput)>=0)
			return true;
		if(userInput.length()==1 && Character.isLetter((userInput.charAt(0))))
			return true;
		return m.matches();
	}

	private static boolean validateRefNo(String userInput, List<String> errorCodes) {
		boolean returnVal = true;

		if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
			errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}

	//CR-57
	private static boolean validateZwRefNo(String userInput, List<String> errorCodes, USSDSessionManagement ussdSessionMgmt) {
		boolean returnVal = true;
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		String dstvBoSelected = userInputMap.get(USSDConstants.SELECTED_DSTV_BO);
		if( ussdSessionMgmt.getBusinessId()!=null && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("ZWBRB")&& userInputMap!=null && userInputMap.get(USSDConstants.SELECTED_BILLER_REGB).equalsIgnoreCase("DSTVZIM-2")
				&& dstvBoSelected != null && dstvBoSelected.equalsIgnoreCase("DSTV BO")){
			return true;
		}
		if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
			errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}
	private static boolean validateNbcRefNo(String userInput, List<String> errorCodes, USSDSessionManagement ussdSessionMgmt) {
		boolean returnVal = true;
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

		if(ussdSessionMgmt.getBusinessId()!=null && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC")&& userInputMap!=null && userInputMap.get(USSDConstants.SELECTED_BILLER_REGB).equalsIgnoreCase("PW-3"))
		{
			if (StringUtils.isEmpty(userInput) || !StringUtils.isAlphanumeric(userInput)) {
				errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
				returnVal = false;
			}
		}else{

			if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
				errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
				returnVal = false;
			}
		}
		return returnVal;

	}

	// CODE ADDED TO INCLUDE "-" IN BILLER REF NO. FOR KEBRB START
	private static boolean validateKeRefNo(String userInput, List<String> errorCodes) {
		boolean returnVal = true;

		if (StringUtils.isEmpty(userInput) || !isAlphanumericHyphen(userInput)) {
			errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}

	/*
	 * Checks if the String contains only unicode letters, digits or Hyphen '-'
	 */
	public static boolean isAlphanumericHyphen(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isDigit(str.charAt(i)) == false) && (str.charAt(i) != '-')) {
				return false;
			}
		}
		return true;
	}

	private static boolean validateMobileNumber(String userInput, List<String> errorCodes) {
		boolean returnVal = true;
		if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
			errorCodes.add(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode());
			returnVal = false;
		}/*
		 * else { ussdMenuBuilder.getDefaultLocale(ussdSessionMgmt.getCountryCode(),ussdSessionMgmt.getBusinessId()); MsisdnDTO msisdnDTO =
		 * mobileLength.getLength(ussdSessionMgmt); int mobileLength = msisdnDTO.getSnlen(); if (userInput.length() >= mobileLength) {
		 * userInput.substring(userInput.length() - msisdnDTO.getSnlen()); } else {
		 * errorCodes.add(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode()); } }
		 */
		return returnVal;
	}

	private static boolean validateSearcherAlphaNum(String userInput, List<String> errorCodes) {
		boolean returnVal = true;
		if (StringUtils.isEmpty(userInput) || !StringUtils.isAlphanumeric(userInput)) {
			errorCodes.add(USSDExceptions.USSD_INVALID_SEARCHER.getUssdErrorCode());
			returnVal = false;
		}
		/*
		 * else if (StringUtils.isNumeric(userInput) ) { errorCodes.add(USSDExceptions.USSD_INVALID_SEARCHER.getUssdErrorCode()); returnVal = false; }
		 */
		return returnVal;
	}

	private static boolean validateSearcher(String userInput, List<String> errorCodes) {
		boolean returnVal = true;
		if (StringUtils.isEmpty(userInput)) { /*|| !StringUtils.isAlphanumeric(userInput)*/
			errorCodes.add(USSDExceptions.USSD_INVALID_SEARCHER.getUssdErrorCode());
			returnVal = false;
		}else if(userInput.matches("\\d+") && userInput.length() <2){
			errorCodes.add(USSDExceptions.USSD_INVALID_BRANCH_LENGTH.getUssdErrorCode());
			returnVal = false;
		}
		/*
		 * else if (StringUtils.isNumeric(userInput) ) { errorCodes.add(USSDExceptions.USSD_INVALID_SEARCHER.getUssdErrorCode()); returnVal = false; }
		 */
		return returnVal;
	}

	private static boolean validateMSISDN(String userInput, List<String> errorCodes) {

		boolean returnVal = true;
		if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
			errorCodes.add(USSDExceptions.USSD_MSISDN_INVALID.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}

	private static boolean validateCSMobileNum(String userInput, List<String> errorCodes) {

		boolean returnVal = true;
		//CR-86 Back Flow changes
		/*if (!StringUtils.isNumeric(userInput)) {
	    errorCodes.add(USSDExceptions.USSD_MSISDN_INVALID.getUssdErrorCode());
	    returnVal = false;
	}*/
		return returnVal;
	}

	private static boolean validateBranchCode(String userInput, List<String> errorCodes) {
		boolean returnVal = true;
		if (StringUtils.isEmpty(userInput) || userInput.length() > 3 || !StringUtils.isNumeric(userInput)) {
			errorCodes.add(USSDExceptions.USSD_BRANCH_CODE_INVALID.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}

	private static boolean validatePassword(final String userInput, final List<String> errorCodes) {
		String sequence = "0123456789";
		boolean validate = true;
		if (userInput == null || userInput.equals("") || userInput.length() != 4 || sequence.contains(userInput)
				|| isSameChars(userInput.toCharArray()) || isNotDigits(userInput.toCharArray())
				|| sequence.contains(new StringBuffer(userInput).reverse().toString())) {
			errorCodes.add(USSDExceptions.USSD_PASS_INVALID.getUssdErrorCode());
			validate = false;
		}
		return validate;
	}

	private static boolean validateloginPwd(final String userInput, final List<String> errorCodes) {
		boolean validate = true;
		if(userInput.length() == 1 ){
			return validate;
		}
		if (userInput == null || userInput.equals("") || userInput.length() != 4 || isNotDigits(userInput.toCharArray())) {
			errorCodes.add(USSDExceptions.USSD_INVALID_PASSWORD.getUssdErrorCode());
			validate = false;
		}
		return validate;
	}

	private static boolean validateATMPin(final String userInput, final List<String> errorCodes) {
		boolean validate = true;
		//CR-86 Back flow changes
		/*if (userInput == null || StringUtils.isEmpty(userInput) || userInput.length() != 4 || isNotDigits(userInput.toCharArray())) {
	    errorCodes.add(USSDExceptions.USSD_INVALID_ATM_PIN.getUssdErrorCode());
	    validate = false;
	}*/
		return validate;
	}
	/*
	 * Changes as we have to make CashSned PIN 6 digit for ZM
	 */
	private static boolean validateATMPinZMBRB(final String userInput, final List<String> errorCodes) {
		boolean validate = true;
		//CR-86 Back flow changes
		/*if (userInput == null || StringUtils.isEmpty(userInput) || userInput.length() != 6 || isNotDigits(userInput.toCharArray())) {
    	    errorCodes.add(USSDExceptions.USSD_INVALID_ATM_PIN.getUssdErrorCode());
    	    validate = false;
    	}*/
		return validate;
	}

	private static boolean isSameChars(char[] sequence) {
		boolean validate = true;
		int sequenceLength = sequence.length - 1;
		for (int i = 0; i < sequenceLength; i++) {
			if (sequence[i] != sequence[i + 1]) {
				validate = false;
				break;
			}
		}
		return validate;
	}

	private static boolean isNotDigits(char[] sequence) {
		boolean isNotDigits = false;
		int sequenceLength = sequence.length;
		for (int i = 0; i < sequenceLength; i++) {
			if (!Character.isDigit(sequence[i])) {
				isNotDigits = true;
				break;
			}

		}
		return isNotDigits;
	}

	private static boolean validateTwoFactAns(final String userInput, final List<String> errorCodes) {
		boolean validate = true;
		if (userInput == null || StringUtils.isEmpty(userInput) || userInput.length() != 2) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID&V ans is invalid ");
			}
			errorCodes.add(USSDExceptions.USSD_IDV_ANS_INVALID.getUssdErrorCode());
			validate = false;
		}
		return validate;
	}

	private static boolean validateTwoFactAnsDOB(final String userInput, final List<String> errorCodes) {
		boolean validate = true;
		if (userInput == null || StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID&V ans for DOB is invalid");
			}
			errorCodes.add(USSDExceptions.USSD_INVALID_DOB.getUssdErrorCode());
			validate = false;
		} else {
			validate = validateTwoFactAns(userInput, errorCodes);
		}
		return validate;
	}

	private static boolean validateEndTrans(final String userInput, final List<String> errorCodes, String backOption, String homeOption, CurrentRunningTransaction currentTransaction) {
		boolean returnVal = false;
		if (StringUtils.isNotEmpty(userInput) && (homeOption.equalsIgnoreCase(userInput) && !backOption.equalsIgnoreCase(userInput))) {
			returnVal = true;
		} else {
			//CR-86 Back flow changes FD/AP
			if(currentTransaction.getTranDataId().equalsIgnoreCase("FDR003") ){
				errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID_FD_AP.getUssdErrorCode());

			} else if(currentTransaction.getTranDataId().equalsIgnoreCase("LG003")){
				errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());

			} else {
				errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
			}
		}
		return returnVal;
	}

	private static boolean validateConfirmation(final String userInput, final List<String> errorCodes,CurrentRunningTransaction currentTransaction) {
		boolean returnVal = true;
		
		//CR-86
		if (StringUtils.isEmpty(userInput) || !IntegerValidator.getInstance().isValid(userInput)
				|| !USSDConstants.CONFIRM_ACTION_OPTION_CODE.equalsIgnoreCase(userInput.trim())) {

			//CR-86 Back Flow changes
			/*if(currentTransaction != null && (currentTransaction.getTranDataId().equalsIgnoreCase("ATT005") ||
    				currentTransaction.getTranDataId().equalsIgnoreCase("ATT006") || currentTransaction.getTranDataId().equalsIgnoreCase("MWTU005") ||
    				currentTransaction.getTranDataId().equalsIgnoreCase("MWTU006")) || currentTransaction.getTranDataId().equalsIgnoreCase("OTBP007") ||
    				currentTransaction.getTranDataId().equalsIgnoreCase("OTBP008")) {
    			errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED_AT_MW.getUssdErrorCode());
    			returnVal = false;
    		}*/
			//FreeDialUSSD
			
			if(currentTransaction != null && isBackErrorScreen(currentTransaction)){
				errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED_AT_MW.getUssdErrorCode());
				returnVal = false;
			}else {
				errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
				returnVal = false;
			}
		}
		return returnVal;
	}
	
	//Ghana databundle change
	private static boolean validateConfirmationCancel(final String userInput, final List<String> errorCodes,CurrentRunningTransaction currentTransaction) {
		boolean returnVal = true;
		
		//CR-86
		if (StringUtils.isEmpty(userInput) || !IntegerValidator.getInstance().isValid(userInput)
				||( !USSDConstants.CONFIRM_ACTION_OPTION_CODE.equalsIgnoreCase(userInput.trim()) 
				&& !USSDConstants.CANCEL_ACTION_OPTION_CODE.equalsIgnoreCase(userInput.trim()))) {			
			
			if(currentTransaction != null && isBackErrorScreen(currentTransaction)){
				errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED_AT_MW.getUssdErrorCode());
				returnVal = false;
			}else {
				errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
				returnVal = false;
			}
		}
		return returnVal;
	}
	
	private static boolean validateMobileAccountNo(final String userInput, final List<String> errorCodes,USSDSessionManagement ussdSessionMgmt) {
		boolean returnVal = true;		
		String mobileActLabel = ussdSessionMgmt.getPreviousRenderedScreen().getPageBody();
		if(mobileActLabel.toLowerCase().contains("account")) {
			if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
				errorCodes.add(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
				returnVal = false;
			}
		}
		else if(mobileActLabel.toLowerCase().contains("mobile")) {
			if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput)) {
				errorCodes.add(USSDExceptions.USSD_MSISDN_INVALID.getUssdErrorCode());
				returnVal = false;
			}
		}
		return returnVal;
	}

	

	//FreeDialUSSD
	private static boolean isBackErrorScreen(CurrentRunningTransaction currentTransaction) {
		boolean result= false;
		String transIdCurr=currentTransaction.getTranDataId();
		String[] transac = {"ATT005", "ATT006", "MWTU005","MWTU006","OTBP007","OTBP008","FDU004"};

		for (String s: transac) {
			if(s.equalsIgnoreCase(transIdCurr)){
				result= true;
				break;

			}
		}

		return  result;

	}
	private static boolean validateAmount(String txnAmt, final List<String> errorCodes) {
		boolean returnVal = true;
		//CR-86 back flow changes
		/*if (StringUtils.isEmpty(txnAmt) || !LongValidator.getInstance().isValid(txnAmt) || isNotDigits(txnAmt.toCharArray())) {
		    errorCodes.add(USSDExceptions.USSD_INVALID_AMOUNT.getUssdErrorCode());
		    returnVal = false;
		}
		char zero = '0';
		if (txnAmt.length() > 1 && zero == txnAmt.charAt(0)) {
		    errorCodes.add(USSDExceptions.USSD_INVALID_AMOUNT.getUssdErrorCode());
		    returnVal = false;
		}*/


		return returnVal;
	}

	private static boolean validateList(final String userInput, final String tranId, USSDSessionManagement ussdSessionMgmt,
			final List<String> errorCodes) {
		boolean blnResult = false;

		if(tranId.equalsIgnoreCase("ATT007")|| tranId.equalsIgnoreCase("OTBP014")
				|| tranId.equalsIgnoreCase("BP007")|| tranId.equalsIgnoreCase("MWTU007")
				|| tranId.equalsIgnoreCase("OBAFTNRC00") || tranId.equalsIgnoreCase("OBAFT018") || tranId.equalsIgnoreCase("ATR000") || tranId.equalsIgnoreCase("ATR002")
				){
			if (!StringUtils.isEmpty(userInput) && StringUtils.isNumeric(userInput)) {
				blnResult = LongValidator.getInstance().isInRange(Long.parseLong(userInput), 1, 2);
			}
		}else{
			List<?> lstSessionData = (List<?>) ussdSessionMgmt.getTxSessions().get(tranId);
			if (lstSessionData == null || lstSessionData.isEmpty()) {
				LOGGER.error("Source list is empty, please check if the Key used in txSessionMap is correct!");
			}

			if (!StringUtils.isEmpty(userInput) && StringUtils.isNumeric(userInput)) {
				blnResult = LongValidator.getInstance().isInRange(Long.parseLong(userInput), 1, lstSessionData.size());
			}
		}
		if (!blnResult) {
			//CR-86 Back flow apply product list navigation
			if(ussdSessionMgmt.getUserTransactionDetails()!= null &&
					ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId().equalsIgnoreCase("LG000")){
				errorCodes.add(USSDExceptions.USSD_INVALID_PRODUCT_OPTSELECTED.getUssdErrorCode());
			} else {
				errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
			}

		}
		return blnResult;
	}

	public static boolean validateMenuInput(final String userInput, final List<String> errorCodes, String backOption, String homeOption) {
		boolean blnResult = false;

		if (backOption.equalsIgnoreCase(userInput) || homeOption.equalsIgnoreCase(userInput)) {
			blnResult = true;
		} else if (!StringUtils.isEmpty(userInput) && StringUtils.isNumeric(userInput)) {
			blnResult = true;
		}
		if (!blnResult) {
			errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
		}
		return blnResult;
	}

	private static boolean validateAccountNumber(String accountNo, final List<String> errorCodes) {
		boolean returnVal = true;

		if (StringUtils.isEmpty(accountNo) || !StringUtils.isNumeric(accountNo)) {
			errorCodes.add(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}

	/**
	 * Added Validation method for Debit Card No. The user needs to enter 4 digits only.
	 * @param userInput
	 * @param errorCodes
	 * @return
	 */
	private static boolean validateDebitCardNo(final String userInput,
			final List<String> errorCodes) {
		boolean validate = true;
		if (userInput == null || !StringUtils.isNumeric(userInput)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID&V: Debit Card ans is invalid numeric");
			}
			errorCodes.add(USSDExceptions.USSD_IDV_DEBIT_CARD_ANS_INVALID_NUMERIC
					.getUssdErrorCode());
			validate = false;
		}else if (userInput == null || StringUtils.isEmpty(userInput)
				|| userInput.length() != 4) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID&V: Debit Card ans is invalid ");
			}
			errorCodes.add(USSDExceptions.USSD_IDV_DEBIT_CARD_ANS_INVALID
					.getUssdErrorCode());
			validate = false;
		}

		return validate;
	}

	private static boolean validateDebitCardExpiryDate(final String userInput,
			final List<String> errorCodes) {
		boolean validate = true;
		if (userInput == null || !StringUtils.isNumeric(userInput)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID&V: Debit Card Expiry Date ans is invalid numeric");
			}
			errorCodes.add(USSDExceptions.USSD_IDV_DEBIT_CARD_EXPIRY_ANS_INVALID_NUMERIC
					.getUssdErrorCode());
			validate = false;
		}else if (userInput == null || StringUtils.isEmpty(userInput)
				|| userInput.length() != 4 ) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID&V: Debit Card Expiry Date ans is invalid ");
			}
			errorCodes.add(USSDExceptions.USSD_IDV_DEBIT_CARD_EXPIRY_ANS_INVALID
					.getUssdErrorCode());
			validate = false;
		}
		return validate;
	}
	/**
	 * Added Validation method for Debit Card No. The user needs to enter 4 digits only.
	 * @param userInput
	 * @param errorCodes
	 * @return
	 */
	private static boolean validateCreditCardNumber(final String userInput,
			final List<String> errorCodes) {
		boolean validate = true;
		if (userInput == null || !StringUtils.isNumeric(userInput)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID&V: Credit Card ans is invalid numeric");
			}
			errorCodes.add(USSDExceptions.CREDIT_CARD_ANS_INVALID_NUMERIC
					.getUssdErrorCode());
			validate = false;
		}
		else if (userInput == null || StringUtils.isEmpty(userInput)
				|| userInput.length() != 4) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID&V: Credit Card ans is invalid ");
			}
			errorCodes.add(USSDExceptions.CREDIT_CARD_ANS_INVALID
					.getUssdErrorCode());
			validate = false;
		}

		return validate;
	}

	private static boolean validateReason(final String userInput,
			final List<String> errorCodes) {
		boolean validate = true;
		String userInputTrimmed = userInput.trim();
		if (!INPUT_VALIDATION_PATTERN_KITS.matcher(userInputTrimmed).matches()) {
			errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
			LOGGER.error("Invalid user input : " + userInputTrimmed);
			return false;
		}
		return validate;
	}

	private static boolean validateSearcherAlphaNumChar2(String userInput, List<String> errorCodes) {
		boolean returnVal = true;
		if (StringUtils.isEmpty(userInput) || !StringUtils.isAlphanumeric(userInput)) {
			errorCodes.add(USSDExceptions.USSD_INVALID_SEARCHER.getUssdErrorCode());
			returnVal = false;
		}else if(userInput.length() <2){
			errorCodes.add(USSDExceptions.USSD_INVALID_BANK_LENGTH.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}

	// WUC-2 Biller change - Botswana 04/07/2017
	private static boolean validateWUCRefNo(String userInput, List<String> errorCodes) {
		boolean returnVal = true;

		if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput) || userInput.length() < 4 || userInput.length() > 6) {
			errorCodes.add(USSDExceptions.USSD_WUC_REFNUM_USER_INPUT_INVALID.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}

	private static boolean validateMZRefNo(String userInput, List<String> errorCodes) {
		boolean returnVal = true;

		if (StringUtils.isEmpty(userInput) || !StringUtils.isNumeric(userInput) || !isAlphanumericChar40(userInput)) {
			errorCodes.add(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
			returnVal = false;
		}
		return returnVal;
	}

	public static boolean isAlphanumericChar40(String str) {
		if (str == null) {
			return false;
		}
		if (str.length()>40) {
			return false;
		}
		return true;
	}
}