package com.barclays.bmg.helper;

public class PCIMaskingHelper {

    public static final int CARD_NUMBER_LENGTH = 16;
    public static final int PRE_UNMASK_LENGTH = 6;
    public static final int SUFIX_UNMASK_LENGTH = 4;
    public final static boolean PCI_MASKING_ENABLED = true;
    public final static String CARD_MASKING_CHARACTER = "x";

    /**
     * This method used to check whether the number is a card number or not according to the length of the number.
     * 
     * @return boolean
     */
    public static boolean isCardNumber(String text) {

	if (text != null && text.trim().length() == CARD_NUMBER_LENGTH) {
	    return true;
	}

	return false;
    }
}
