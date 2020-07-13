package com.barclays.bmg.mvc.validator.mobilewallet;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.MWalletResponseConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.mobilewallet.MWalletValidateCommand;
import com.barclays.bmg.mvc.validatotion.util.ValidationUtil;

public class MWalletPayeeValidateValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MWalletValidateCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		

		MWalletValidateCommand mWalletValidateCommandObject = (MWalletValidateCommand) target;
		

		String mobile = mWalletValidateCommandObject.getMblNo();

		

		ValidationUtils
				.rejectIfEmptyOrWhitespace(
						errors,
						"mblNo",
						ResponseIdConstants.M_WALLET_VALIDATION
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ MWalletResponseConstant.MOBILE_WALLET_MTP_MOBILE_NUMBER_EMPTY);

		/*
		 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refNmbr",
		 * ResponseIdConstants.M_WALLET_VALIDATION +
		 * ResponseIdConstants.HYPHEN_SEPERATOR +
		 * MWalletResponseConstant.MOBILE_WALLET_MTP_BILLER_ACCOUNT_NUMBER_EMPTY
		 * );
		 */

		

		if (mobile != null) {
			if (!ValidationUtil.validateMobileNo(mobile)) {
				errors
						.reject(ResponseIdConstants.M_WALLET_VALIDATION
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ MWalletResponseConstant.MOBILE_WALLET_MTP_MOBILE_NUMBER_INVALID);
			}
		}
		
		
		
		
		
	}

}
