package com.barclays.bmg.mvc.validator.fundtransfer;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.AddBeneficiaryFormSubmissionCommand;
import com.barclays.bmg.context.BMBContextHolder;
public class AddBeneficiaryFormSubmissionCommandValidator implements Validator {

	//START BRANCH CODE VALIDATION #UMESH
	private List<String> branchCodeCountryList;



	public List<String> getBranchCodeCountryList() {
		return branchCodeCountryList;
	}

	public void setBranchCodeCountryList(List<String> branchCodeCountryList) {
		this.branchCodeCountryList = branchCodeCountryList;
	}
	//END BRANCH CODE VALIDATION #UMESH

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return AddBeneficiaryFormSubmissionCommand.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object arg0, Errors errors) {

		// TODO Auto-generated method stub

		AddBeneficiaryFormSubmissionCommand command = (AddBeneficiaryFormSubmissionCommand) arg0;


		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"accountNumber",
				ResponseIdConstants.ADD_BENEFICIARY_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BeneficiaryResponseCodeConstants.BENEFICIARY_ACC_NUMBER_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "payeeType",
				ResponseIdConstants.ADD_BENEFICIARY_FORM_SUBMIT
					+ ResponseIdConstants.HYPHEN_SEPERATOR + BeneficiaryResponseCodeConstants.BENEFICIARY_PAYEE_TYPE_EMPTY);

		if(command!=null && FundTransferConstants.FUND_TRANSFER_EXTERNAL.equals(command.getPayeeType()))
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bankCode",
				ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BeneficiaryResponseCodeConstants.BENEFICIARY_BANK_CODE_EMPTY);

		//START BRANCH CODE VALIDATION #UMESH
		if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
			if(command!=null && FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL.equals(command.getPayeeType())){

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "branchCode",
				ResponseIdConstants.ADD_BENEFICIARY_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BeneficiaryResponseCodeConstants.BENEFICIARY_BRANCH_CODE_EMPTY);
		}
	}
		//END BRANCH CODE VALIDATION #UMESH

		if(command!=null && FundTransferConstants.FUND_TRANSFER_EXTERNAL.equals(command.getPayeeType()))
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "beneficiaryName",
				ResponseIdConstants.ADD_BENEFICIARY_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BeneficiaryResponseCodeConstants.BENEFICIARY_BNF_NAME_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "beneficiaryNickName",
				ResponseIdConstants.ADD_BENEFICIARY_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BeneficiaryResponseCodeConstants.BENEFICIARY_BNF_NICK_NAME_EMPTY);

	}

}
