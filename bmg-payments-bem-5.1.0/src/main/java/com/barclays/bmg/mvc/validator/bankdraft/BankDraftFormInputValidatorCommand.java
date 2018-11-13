package com.barclays.bmg.mvc.validator.bankdraft;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.mvc.command.bankdraft.BankDraftFormInputCommand;

public class BankDraftFormInputValidatorCommand implements Validator {

	private List<String> bankDraftBRNCheckCountryList;

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return BankDraftFormInputCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils
				.rejectIfEmptyOrWhitespace(
						errors,
						"txnAmt",
						ResponseIdConstants.BANK_DRAFT_SECOND_STEP
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TXAMOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curr",
				ResponseIdConstants.BANK_DRAFT_SECOND_STEP
						+ ResponseIdConstants.HYPHEN_SEPERATOR
						+ FundTransferResponseCodeConstants.EMPTY_CURRENCY);

		/*
		 * ValidationUtils .rejectIfEmptyOrWhitespace( errors, "txnNot",
		 * ResponseIdConstants.BANK_DRAFT_SECOND_STEP +
		 * ResponseIdConstants.HYPHEN_SEPERATOR +
		 * FundTransferResponseCodeConstants.EMPTY_PAYMENT_REMARKS);
		 */

		/*
		 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "drfTypSel",
		 * ResponseIdConstants.BANK_DRAFT_SECOND_STEP +
		 * ResponseIdConstants.HYPHEN_SEPERATOR +
		 * FundTransferResponseCodeConstants.EMPTY_DRAFT_TYPE);
		 */
		ValidationUtils
				.rejectIfEmptyOrWhitespace(
						errors,
						"delTypSel",
						ResponseIdConstants.BANK_DRAFT_SECOND_STEP
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ FundTransferResponseCodeConstants.EMPTY_DELIVERY_TYPE);

		String delTypeSelected = ((BankDraftFormInputCommand) target)
				.getDelTypSel();
		String txnAmt = ((BankDraftFormInputCommand) target).getTxnAmt();

		if ("BRN".equals(delTypeSelected)
				&& !bankDraftBRNCheckCountryList.contains(BMBContextHolder
						.getContext().getBusinessId())) {

			ValidationUtils
					.rejectIfEmptyOrWhitespace(
							errors,
							"brnNme",
							ResponseIdConstants.BANK_DRAFT_SECOND_STEP
									+ ResponseIdConstants.HYPHEN_SEPERATOR
									+ FundTransferResponseCodeConstants.EMPTY_BRANCH_NAME);

			ValidationUtils
					.rejectIfEmptyOrWhitespace(
							errors,
							"brnCde",
							ResponseIdConstants.BANK_DRAFT_SECOND_STEP
									+ ResponseIdConstants.HYPHEN_SEPERATOR
									+ FundTransferResponseCodeConstants.EMPTY_BRANCH_CODE);
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "payAt",
				ResponseIdConstants.BANK_DRAFT_SECOND_STEP
						+ ResponseIdConstants.HYPHEN_SEPERATOR
						+ FundTransferResponseCodeConstants.EMPTY_PAYABLE_AT);

		if (txnAmt != null) {
			if (!txnAmt.matches("^\\d+$|^\\d*\\.\\d{1,2}$")) {
				errors
						.reject(ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ FundTransferResponseCodeConstants.INVALID_TXN_AMT);
			}
		}

	}

	public List<String> getBankDraftBRNCheckCountryList() {
		return bankDraftBRNCheckCountryList;
	}

	public void setBankDraftBRNCheckCountryList(
			List<String> bankDraftBRNCheckCountryList) {
		this.bankDraftBRNCheckCountryList = bankDraftBRNCheckCountryList;
	}

}
