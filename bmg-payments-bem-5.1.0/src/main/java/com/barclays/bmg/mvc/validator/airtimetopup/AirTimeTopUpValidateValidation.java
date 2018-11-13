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
/**
 * Package name is com.barclays.bmg.mvc.validator.airtimetopup
 * /
 */
package com.barclays.bmg.mvc.validator.airtimetopup;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AirTimeTopUpResponseConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.airtimetopup.AirTimeTopUpValidateCommand;
import com.barclays.bmg.mvc.validatotion.util.ValidationUtil;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-payments-bem-5.1.0</b>
 * </br>
 * The file name is  <b>AirTimeTopUpValidateValidation.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class AirTimeTopUpValidateValidation created using JRE 1.6.0_10
 */
public class AirTimeTopUpValidateValidation implements  Validator{

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {

		return AirTimeTopUpValidateCommand.class.isAssignableFrom(clazz);
	}
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object trgObj, Errors errors)
	{
		AirTimeTopUpValidateCommand airTimeTopUpValidateCommand = (AirTimeTopUpValidateCommand) trgObj;

		String txtAmt = airTimeTopUpValidateCommand.getTxnAmt();

		String mobile = airTimeTopUpValidateCommand.getMblNo();

		String creditCard=airTimeTopUpValidateCommand.getCreditcardNo();

		String actNo=airTimeTopUpValidateCommand.getActNo();
		
		if(actNo!=null){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actNo",
				ResponseIdConstants.AIR_MTP_VALIDATION +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				AirTimeTopUpResponseConstant.ATP_MTP_ACCOUNT_NUMBER_NO_EMPTY);
	}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnAmt",
				ResponseIdConstants.AIR_MTP_VALIDATION +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				AirTimeTopUpResponseConstant.ATP_MTP_AMOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mblNo",
				ResponseIdConstants.AIR_MTP_VALIDATION +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				AirTimeTopUpResponseConstant.ATP_MTP_MOBILE_NUMBER_EMPTY);

		if(txtAmt != null){
			if(!ValidationUtil.validateAmount(txtAmt)){
				errors.reject(ResponseIdConstants.AIR_MTP_VALIDATION +
						ResponseIdConstants.HYPHEN_SEPERATOR +
						AirTimeTopUpResponseConstant.ATP_MTP_TXTAMT_INVALID);
			}
		}
		if(mobile != null){
			if(!ValidationUtil.validateMobileNo(mobile)){
				errors.reject(ResponseIdConstants.AIR_MTP_VALIDATION +
						ResponseIdConstants.HYPHEN_SEPERATOR +
						AirTimeTopUpResponseConstant.ATP_MTP_MOBILE_NUMBER_INVALID);
			}
		}
	}
}