/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.ussd.mvc.validator.auth;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.barclays.bmg.ussd.mvc.command.auth.SelfRegistrationExecutionCommand;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationExecutionController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */

public class SelfRegistrationExecutionCommandValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
	return SelfRegistrationExecutionCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	/*
	 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnRefNo", ResponseIdConstants.FUND_TRANSFER_SECONDAUTH_INIT_RESPONSE_ID +
	 * ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.TRANSACTION_SECOND_AUTHCOMMAND_TXREFKEY_EMPTY);
	 */

	/*
	 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerId", ResponseIdConstants.FUND_TRANSFER_SECONDAUTH_INIT_RESPONSE_ID +
	 * ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.TRANSACTION_SECOND_AUTHCOMMAND_POSITION1_EMPTY);
	 */

	/*
	 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position2", ResponseIdConstants.FUND_TRANSFER_SECONDAUTH_INIT_RESPONSE_ID +
	 * ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.TRANSACTION_SECOND_AUTHCOMMAND_POSITION2_EMPTY);
	 */

    }
}
