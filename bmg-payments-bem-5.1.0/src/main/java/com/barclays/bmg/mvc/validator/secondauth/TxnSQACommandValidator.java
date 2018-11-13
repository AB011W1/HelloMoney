package com.barclays.bmg.mvc.validator.secondauth;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.secondauth.TxnSQASecondAuthCommand;
import com.barclays.bmg.mvc.validator.auth.SQACommandValidator;


public class TxnSQACommandValidator extends SQACommandValidator{

	@Override
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {

		return TxnSQASecondAuthCommand.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object obj, Errors e) {
		super.validate(obj, e);
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "txnRefNo",
				ResponseIdConstants.TXN_SECOND_AUTH_SQA_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFEREXECUTECOMMAND_TXREFKEY_EMPTY);


	}
}
