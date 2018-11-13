package com.barclays.bmg.mvc.validator.offer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.PromotionalOfferResponseCodeConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.offer.EipOfferDetlsCommand;

public class EipOfferDetailsValidator implements Validator {

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {

		return EipOfferDetlsCommand.class.isAssignableFrom(clazz);

	}


	public void validate(Object obj, Errors e) {

		String respID = ResponseIdConstants.EIP_OFFER_DETLS + "-";

		ValidationUtils.rejectIfEmptyOrWhitespace(e, "offerId",
				respID+PromotionalOfferResponseCodeConstant.EIP_OFFER_ID_EMPTY);


	}
}
