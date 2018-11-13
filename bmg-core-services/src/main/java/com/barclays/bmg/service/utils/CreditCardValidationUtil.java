package com.barclays.bmg.service.utils;

import com.barclays.bmg.dto.CreditCardAccountDTO;

public class CreditCardValidationUtil {

    public static boolean validateCCDForAtAGlance(CreditCardAccountDTO creditCardAccountDTO, String[] ccdAtaGalnceBlockCodeAry,
	    String[] ccdAtaGalnceAccountBlockCodeAry) {
	boolean result = false;
	// validate Credit card Block code
	if ((findStatusInArray(creditCardAccountDTO.getPrimary().getCreditCardBlockCode(), ccdAtaGalnceBlockCodeAry))) {
	    // validate Account Code block1
	    if (findStatusInArray(creditCardAccountDTO.getAccountBlockCode1(), ccdAtaGalnceAccountBlockCodeAry)) {
		// validate Account Code block2
		if (findStatusInArray(creditCardAccountDTO.getAccountBlockCode2(), ccdAtaGalnceAccountBlockCodeAry)) {
		    result = true;
		}
	    }
	}
	return result;
    }

    public static boolean validateCCDForUnbilledStmtTrans(CreditCardAccountDTO creditCardAccountDTO, String[] ccdStatementBlockCodeAry,
	    String[] ccdStatementAccountBlockCodeAry) {
	boolean result = false;
	// Validate Credit card Block code
	if ((findStatusInArray(creditCardAccountDTO.getPrimary().getCreditCardBlockCode(), ccdStatementBlockCodeAry))) {
	    // validate Account Code block1
	    if (findStatusInArray(creditCardAccountDTO.getAccountBlockCode1(), ccdStatementAccountBlockCodeAry)) {
		// validate Account Code block2
		if (findStatusInArray(creditCardAccountDTO.getAccountBlockCode2(), ccdStatementAccountBlockCodeAry)) {
		    result = true;
		}
	    }
	}
	return result;
    }

    public static boolean validateCCDForPayment(CreditCardAccountDTO creditCardAccountDTO, String[] ccdPaymentBlockCodeAry,
	    String[] ccdPaymentAccountBlockCodeAry) {
	boolean result = false;
	// Validate Credit card Block code
	if ((findStatusInArray(creditCardAccountDTO.getPrimary().getCreditCardBlockCode(), ccdPaymentBlockCodeAry))) {
	    // validate Account Code block1
	    if (findStatusInArray(creditCardAccountDTO.getAccountBlockCode1(), ccdPaymentAccountBlockCodeAry)) {
		// validate Account Code block2
		if (findStatusInArray(creditCardAccountDTO.getAccountBlockCode2(), ccdPaymentAccountBlockCodeAry)) {
		    result = true;
		}
	    }
	}
	return result;
    }

    private static boolean findStatusInArray(String accountStatus, String arry[]) {
	boolean result = false;

	// Allow Space as a active status for credit card
	if (accountStatus == null || "".equals(accountStatus.trim())) {
	    return true;
	}

	if (arry != null && arry.length > 0) {
	    for (int i = 0; i < arry.length; i++) {
		if ((arry[i]).equalsIgnoreCase(accountStatus)) {
		    result = true;
		    break;
		}
	    }
	}
	return result;
    }
}
