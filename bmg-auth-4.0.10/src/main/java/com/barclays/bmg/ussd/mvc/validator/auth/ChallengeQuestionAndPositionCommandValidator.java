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
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.ussd.mvc.command.auth.ChallengeQuestionAndPositionCommand;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionCommandValidator.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b>
 * </br> ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionCommandValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
	return ChallengeQuestionAndPositionCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNumber", ResponseIdConstants.CHALLENGE_QUES_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.CHALLENGE_QUES_COMMAND_MOBILENO_EMPTY);

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "questionList", ResponseIdConstants.CHALLENGE_QUES_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.CHALLENGE_QUES_COMMAND_SCVID_EMPTY);

    }
}
