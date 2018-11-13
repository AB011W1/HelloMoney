package com.barclays.bmg.mvc.validator.fundtransfer.nonregistered.internal;
import com.barclays.bmg.context.BMBContextHolder;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.fundtransfer.nonregistered.internal.InternalNonRegisteredFTDetailsCommand;


public class InternalFTDetailsCommandValidator implements Validator {

	// START BRANCH CODE VALIDATION #UMESH
	private List<String> branchCodeCountryList;

	public List<String> getBranchCodeCountryList() {
		return branchCodeCountryList;
	}

	public void setBranchCodeCountryList(List<String> branchCodeCountryList) {
		this.branchCodeCountryList = branchCodeCountryList;
	}

	// END BRANCH CODE VALIDATION #UMESH


	@Override
	public boolean supports(Class clazz) {

		return InternalNonRegisteredFTDetailsCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//TODO: Change the below to actual response Id constants and Response Codes
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frActNo",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.INTERNAL_NON_REGISTERED_FUNDTRANSFERDATACOMMAND_FRMACCOUNT_EMPTY);

		// START BRANCH CODE VALIDATION #UMESH
		if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "beneficiaryBranchCode",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.INTERNAL_NON_REGISTERED_FUNDTRANSFERDATACOMMAND_EMPTY_BRANCH_CODE);
		}
		// END BRANCH CODE VALIDATION #UMESH

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "beneficiaryAccountNumber",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.INTERNAL_NON_REGISTERED_FUNDTRANSFERDATACOMMAND_BENEFICIARYACCOUNT_EMPTY);

		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "beneficiaryName",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.INTERNAL_NON_REGISTERED_FUNDTRANSFERDATACOMMAND_EMPTY_BENEFICIARY_NAME);*/

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curr",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.INTERNAL_NON_REGISTERED_FUNDTRANSFERDATACOMMAND_EMPTY_CURRENCY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnAmt",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.INTERNAL_NON_REGISTERED_FUNDTRANSFERDATACOMMAND_TXAMOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnDt",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.INTERNAL_NON_REGISTERED_FUNDTRANSFERDATACOMMAND_TXDATE_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "payDesc",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.INTERNAL_NON_REGISTERED_FUNDTRANSFERDATACOMMAND_EMPTY_PAYDESC);



	}


}
