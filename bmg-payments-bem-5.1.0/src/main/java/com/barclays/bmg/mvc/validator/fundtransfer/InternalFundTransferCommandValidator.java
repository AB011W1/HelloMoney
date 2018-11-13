package com.barclays.bmg.mvc.validator.fundtransfer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.fundtransfer.internal.InternalFundTransferCommand;

public class InternalFundTransferCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {

		return InternalFundTransferCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frActNo",
				ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID
					+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_SOURCEACCOUNT_EMPTY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "payId",
				ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TARGETACCOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnAmt",
				ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TXAMOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnDt",
				ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TXDATE_EMPTY);

		InternalFundTransferCommand command = (InternalFundTransferCommand) target;
		String txnAmt = command.getTxnAmt();
		if(txnAmt!=null){
			if(!txnAmt.matches("^\\d+$|^\\d*\\.\\d{1,2}$")){
				errors
				.reject(ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID+ ResponseIdConstants.HYPHEN_SEPERATOR
						+FundTransferResponseCodeConstants.INVALID_TXN_AMT);
			}
		}
	}
}
