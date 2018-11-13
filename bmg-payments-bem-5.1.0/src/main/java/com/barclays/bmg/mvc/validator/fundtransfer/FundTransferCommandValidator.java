package com.barclays.bmg.mvc.validator.fundtransfer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.fundtransfer.own.FundTransferValidateCommand;

public class FundTransferCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {

		return FundTransferValidateCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frActNo",
				ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID
					+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_SOURCEACCOUNT_EMPTY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toActNo",
				ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TARGETACCOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnAmt",
				ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TXAMOUNT_EMPTY);

		FundTransferValidateCommand command = (FundTransferValidateCommand) target;
		String txnAmt = command.getTxnAmt();
		if(txnAmt!=null){
			if(!txnAmt.matches("^\\d+$|^\\d*\\.\\d{1,2}$")){
				errors
				.reject(ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID+ ResponseIdConstants.HYPHEN_SEPERATOR
						+FundTransferResponseCodeConstants.INVALID_TXN_AMT);
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnDt",
				ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TXDATE_EMPTY);

		if (command.getFrActNo() != null
				&& command.getFrActNo().equals(command.getToActNo())) {
			errors
					.reject(ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID+ ResponseIdConstants.HYPHEN_SEPERATOR
							+FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_FORBID_SAME_ACCOUNT);
		}
	}

}
