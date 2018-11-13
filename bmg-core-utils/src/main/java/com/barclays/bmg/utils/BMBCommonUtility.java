package com.barclays.bmg.utils;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.barclays.bmg.context.Context;

public class BMBCommonUtility {

    private static SecureRandom random;
    private final static String ALGORITHM = "SHA1PRNG";
    private final static String DATE_FORMATTER = "yyyy-MM-dd";
    private final static DateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER);

    int  debitCardCount=1;

    private static final Logger LOGGER = Logger.getLogger(BMBCommonUtility.class);

    public static final String generateRefNo() {
	String refNo = randomDigit() + System.currentTimeMillis();
	return (refNo);
    }

    public static Calendar convertStringDtToCalendarDt(String dt) {
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATTER);
	Calendar cal;
	try {
	    Date strtDate = sdf.parse(dt);
	    cal = Calendar.getInstance();
	    cal.setTime(strtDate);
	} catch (Exception e) {
	    return null;
	}
	return cal;
    }

    public static DateFormat getDateFormat() {
	return dateFormat;
    }

    public static Calendar getCurrentBusinessCalendar(Context context) {
	Calendar cal = Calendar.getInstance();
	int offset = cal.get(Calendar.ZONE_OFFSET) / 3600000 + cal.get(Calendar.DST_OFFSET) / 3600000;
	Double bos = Double.valueOf(Double.valueOf(context.getTimezoneOffset()) * 60);
	cal.add(Calendar.MINUTE, (-offset * 60 + bos.intValue()));
	return cal;
    }

    public static String convertCalDateToString() {
	return getDateFormat().format(Calendar.getInstance().getTime());
    }

    private static String randomDigit() {
	SecureRandom random = getSecureRandom();
	int index = Math.round((float) (random.nextDouble() * 10));
	if (index > 9) {
	    index = 9;
	}
	return String.valueOf(index);
    }

    public static SecureRandom getSecureRandom() {

	try {
	    if (random == null) {
		random = SecureRandom.getInstance(ALGORITHM);
	    }
	} catch (Exception e) {
	    // log.error("Algorithm 'SHA1PRNG' doesn't exist when create a new SecureRandom instance",
	    // ErrorCode.SYS_SYSTEM_ERROR, e);
	}
	return random;
    }

    public static String paddingZeros(String originalAccountNumber, String fullLenth) {
	String updatedoriginalAccountNumber = originalAccountNumber;
	if (updatedoriginalAccountNumber == null) {
	    updatedoriginalAccountNumber = "";
	}
	if (fullLenth == null || "".equals(fullLenth)) {
	    return updatedoriginalAccountNumber;
	}
	int int_fullLength = Integer.valueOf(fullLenth);
	StringBuffer buf_originalAccountNumber = new StringBuffer(updatedoriginalAccountNumber);
	StringBuffer pad_zeros = new StringBuffer();
	int length = updatedoriginalAccountNumber.length();
	for (int i = 0; i < int_fullLength - length; i++) {
	    pad_zeros.append("0");
	}
	return pad_zeros.append(buf_originalAccountNumber).toString();
    }

    public static String convertStringToUnicode(String inputStr) {

	String result = "";
	String trnnote = inputStr;
	if (trnnote != null) {
	    HashMap<String, String> encodemap = new HashMap<String, String>();
	    encodemap.put("%26", "&");
	    encodemap.put("%27", "'");
	    encodemap.put("%2F", "/");
	    encodemap.put("%25", "%");
	    encodemap.put("%2A", "*");

	    encodemap.put("%21", "!");
	    encodemap.put("%23", "#");
	    encodemap.put("%5B", "[");
	    encodemap.put("%5D", "]");
	    encodemap.put("%3D", "=");

	    encodemap.put("%2B", "+");
	    encodemap.put("%24", "$");

	    encodemap.put("%28", "(");
	    encodemap.put("%29", ")");
	    encodemap.put("%3B", ";");
	    encodemap.put("%3A", ":");
	    encodemap.put("%40", "@");
	    encodemap.put("%2C", ",");
	    encodemap.put("%3F", "?");
	    encodemap.put("%5C", "\\"); // added
	    encodemap.put("%22", "\""); // added
	    encodemap.put("%20", " "); // added

	    /*
	     * for (String key : encodemap.keySet()) { result = trnnote.replace(key, encodemap.get(key)); trnnote = result; }
	     */

	    for (Entry<String, String> key : encodemap.entrySet()) {
		String key1 = key.getKey();
		result = trnnote.replace(key1, encodemap.get(key1));
		trnnote = result;
	    }
	}
	return result;
    }

    /**
     * Validate the debit cards positions from unser entered positions
     * @param positions
     * @param userEntereddebitCardNumbers
     * @param debitCardNumberList
     * @return
     * @throws USSDNonBlockingException
     */
    public  boolean validateRandomDebitCardDigits(
			List<Integer> positions,String userEntereddebitCardNumbers,List<String> debitCardNumberList)
			{
    	LOGGER.info("Entry USSDUtils validateRandomDebitCardDigits userEntereddebitCardNumbers:"+userEntereddebitCardNumbers);
    	LOGGER.info("Entry USSDUtils validateRandomDebitCardDigits debitCardNumberList size:"+debitCardNumberList.size());
    	ArrayList<Character> chars = new ArrayList<Character>();

    	for (char c : userEntereddebitCardNumbers.toCharArray()) {
    	  chars.add(c);
    	}
    	for(String debitCard:debitCardNumberList){
    		int count=0;

    		for(Integer pos:positions){
    			if(debitCard.charAt(pos-1)==((chars.get(count)))){

    				count++;
    				if(count==4){
    					LOGGER.info("USSDUtils validateRandomDebitCardDigits count==4");

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
    	LOGGER.info("Exit USSDUtils validateRandomDebitCardDigits");
    	return true;
    }

    /**
     * Validate the expiry date of debit card during self regsitration in MMyy format
     * @param debitCardExpiryDateList
     * @param userEnteredDebitCardExpiryDate
     * @return
     * @throws USSDNonBlockingException
     */
    public boolean validateDebitCardDigitsExpiryDate(
    		List<Date> debitCardExpiryDateList,String userEnteredDebitCardExpiryDate)
			 {
    	LOGGER.info("Entry USSDUtils validateDebitCardDigitsExpiryDate");
    	LOGGER.info("Entry USSDUtils validateDebitCardDigitsExpiryDate debitCardExpiryDateList size:"+debitCardExpiryDateList.size());
    	LOGGER.info("Entry USSDUtils validateDebitCardDigitsExpiryDate userEnteredDebitCardExpiryDate:"+userEnteredDebitCardExpiryDate);
    	SimpleDateFormat dateFormat=new SimpleDateFormat("MMyy");
    	Date date=debitCardExpiryDateList.get(debitCardCount-1);
    		LOGGER.info("USSDUtils validateDebitCardDigitsExpiryDate Formatted date"+dateFormat.format(date));
    		if(dateFormat.format(date).toString().equals(userEnteredDebitCardExpiryDate)){
    			return true;
    			}
    	LOGGER.info("Exit USSDUtils validateDebitCardDigitsExpiryDate");
    	return false;
    }
}