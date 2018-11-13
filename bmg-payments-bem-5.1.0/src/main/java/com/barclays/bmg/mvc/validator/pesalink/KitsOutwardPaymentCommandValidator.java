package com.barclays.bmg.mvc.validator.pesalink;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.MessageCodeConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.TransactionHistoryResponseCodeConstants;
import com.barclays.bmg.mvc.command.billpayment.ViewTxnHistoryDetailsCommand;
import com.barclays.bmg.mvc.command.pesalink.KitsOutwardPaymentCommand;
import com.barclays.bmg.mvc.command.pesalink.SearchIndividualCustforDeDupCheckCommand;
import com.barclays.bmg.ussd.mvc.command.auth.SelfRegistrationDebitCardCommand;

public class KitsOutwardPaymentCommandValidator  implements Validator {


	    @Override
	    public boolean supports(Class clazz) {
		return KitsOutwardPaymentCommand.class.isAssignableFrom(clazz);
	    }

	    @Override
	    public void validate(Object target, Errors errors) {

/*	    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnRefNo",
					MessageCodeConstant.BILLPAYMENTEXECUTECOMMAND_TXREFKEY_EMPTY);*/

	    }

}



