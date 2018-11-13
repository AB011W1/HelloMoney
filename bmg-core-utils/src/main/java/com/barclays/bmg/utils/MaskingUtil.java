package com.barclays.bmg.utils;

public class MaskingUtil {

    public static String getMaskedCreditCardNumber(String creditCardNo) {
	String result;
	if (creditCardNo == null || creditCardNo.trim().length() < 1) {
	    result = creditCardNo;
	} else {
	    String maskPattern = getMaskCreditCardPattern();
	    result = maskCreditCardNumber(creditCardNo.trim(), maskPattern);
	}
	return result;
    }

    private static String getMaskCreditCardPattern() {
	String creditCardMaskPattern = "0000xxxxxxxx0000";
	return creditCardMaskPattern;
    }

    private static String maskCreditCardNumber(String toMaskCCDNumber, String creditCardMaskPattern) {
	String updatedmaskPattern = creditCardMaskPattern;
	updatedmaskPattern = creditCardMaskPattern;// constructMaskPattern(toMaskNumber, updatedmaskPattern);
	int maskPatternLength = updatedmaskPattern.length();

	// Check Pattern length and Number length
	if (maskPatternLength < toMaskCCDNumber.length()) {
	    return updatedmaskPattern;
	}

	char[] cArray = updatedmaskPattern.toCharArray();
	int startIndex = 0;
	int endIndex = 0;

	for (int i = 0; i < maskPatternLength; i++) {
	    char c = cArray[i];
	    if (!Character.isDigit(c)) {
		break;
	    }
	    startIndex++;
	}

	for (int i = maskPatternLength - 1; i > startIndex; i--) {
	    char c = cArray[i];
	    if (!Character.isDigit(c)) {
		break;
	    }
	    endIndex++;
	}

	if (startIndex == 0 || endIndex == 0) {
	    return updatedmaskPattern;
	}

	if (toMaskCCDNumber.length() <= startIndex || toMaskCCDNumber.length() <= endIndex) {
	    return toMaskCCDNumber;
	}
	if ((toMaskCCDNumber.length() - endIndex) < startIndex) {
	    return toMaskCCDNumber;
	}
	String finalMaskNumber = toMaskCCDNumber;
	String maskString = updatedmaskPattern.substring(startIndex, toMaskCCDNumber.length() - endIndex);
	String startString = toMaskCCDNumber.substring(0, startIndex);
	String endString = toMaskCCDNumber.substring((toMaskCCDNumber.length() - endIndex), toMaskCCDNumber.length());
	finalMaskNumber = startString + maskString + endString;

	boolean maskSplit = false;

	if (maskSplit) {
	    char[] arry = finalMaskNumber.toCharArray();
	    StringBuffer buf = new StringBuffer();
	    for (int i = 0; i < arry.length; i++) {
		buf.append(arry[i]);
		if ((i + 1) % 4 == 0 && i != arry.length - 1) {
		    buf.append(" ");
		}
	    }
	    return buf.toString();
	}
	return finalMaskNumber;
    }
}
