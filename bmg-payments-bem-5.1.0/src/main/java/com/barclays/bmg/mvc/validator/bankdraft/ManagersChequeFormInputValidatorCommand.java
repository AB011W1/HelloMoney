package com.barclays.bmg.mvc.validator.bankdraft;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.bankdraft.BankDraftFormInputCommand;

public class ManagersChequeFormInputValidatorCommand implements Validator {

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return BankDraftFormInputCommand.class
				.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BankDraftFormInputCommand command = (BankDraftFormInputCommand) target;
		ValidationUtils
				.rejectIfEmptyOrWhitespace(
						errors,
						"txnAmt",
						ResponseIdConstants.MANAGERS_CHEQUE_SECOND_STEP
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TXAMOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curr",
				ResponseIdConstants.MANAGERS_CHEQUE_SECOND_STEP
						+ ResponseIdConstants.HYPHEN_SEPERATOR
						+ FundTransferResponseCodeConstants.EMPTY_CURRENCY);
		ValidationUtils
				.rejectIfEmptyOrWhitespace(
						errors,
						"txnNot",
						ResponseIdConstants.MANAGERS_CHEQUE_SECOND_STEP
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ FundTransferResponseCodeConstants.EXTERNAL_PAYEE_REASON_DTLS_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "drfTypSel",
				ResponseIdConstants.MANAGERS_CHEQUE_THIRD_STEP
						+ ResponseIdConstants.HYPHEN_SEPERATOR
						+ FundTransferResponseCodeConstants.EMPTY_DRAFT_TYPE);

		ValidationUtils
				.rejectIfEmptyOrWhitespace(
						errors,
						"delTypSel",
						ResponseIdConstants.MANAGERS_CHEQUE_SECOND_STEP
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ FundTransferResponseCodeConstants.EMPTY_DELIVERY_TYPE);

		String delTypeSelected = command.getDelTypSel();

		if (delTypeSelected.equals("BRN")) {
			ValidationUtils
					.rejectIfEmptyOrWhitespace(
							errors,
							"brnNme",
							ResponseIdConstants.MANAGERS_CHEQUE_SECOND_STEP
									+ ResponseIdConstants.HYPHEN_SEPERATOR
									+ FundTransferResponseCodeConstants.EMPTY_BRANCH_NAME);

			ValidationUtils
					.rejectIfEmptyOrWhitespace(
							errors,
							"brnCde",
							ResponseIdConstants.MANAGERS_CHEQUE_SECOND_STEP
									+ ResponseIdConstants.HYPHEN_SEPERATOR
									+ FundTransferResponseCodeConstants.EMPTY_BRANCH_CODE);
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "payAt",
				ResponseIdConstants.MANAGERS_CHEQUE_SECOND_STEP
						+ ResponseIdConstants.HYPHEN_SEPERATOR
						+ FundTransferResponseCodeConstants.EMPTY_PAYABLE_AT);

	}

}
