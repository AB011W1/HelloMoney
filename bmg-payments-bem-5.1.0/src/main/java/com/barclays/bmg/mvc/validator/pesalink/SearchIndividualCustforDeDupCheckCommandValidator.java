package com.barclays.bmg.mvc.validator.pesalink;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.TransactionHistoryResponseCodeConstants;
import com.barclays.bmg.mvc.command.billpayment.ViewTxnHistoryDetailsCommand;
import com.barclays.bmg.mvc.command.pesalink.SearchIndividualCustforDeDupCheckCommand;
import com.barclays.bmg.ussd.mvc.command.auth.SelfRegistrationDebitCardCommand;

public class SearchIndividualCustforDeDupCheckCommandValidator  implements Validator {


	    @Override
	    public boolean supports(Class clazz) {
		return SearchIndividualCustforDeDupCheckCommand.class.isAssignableFrom(clazz);
	    }

	    @Override
	    public void validate(Object target, Errors errors) {

	    }

}



