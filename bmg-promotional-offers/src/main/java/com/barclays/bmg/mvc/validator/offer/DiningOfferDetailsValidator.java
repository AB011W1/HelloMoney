package com.barclays.bmg.mvc.validator.offer;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.barclays.bmg.mvc.command.offer.DiningOfferDetlsCommand;

public class DiningOfferDetailsValidator implements Validator {

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {

	return DiningOfferDetlsCommand.class.isAssignableFrom(clazz);

    }

    public void validate(Object obj, Errors e) {/*
						 * 
						 * String respID = ResponseIdConstants.DINE_OFFER_DETLS + "-";
						 * 
						 * ValidationUtils.rejectIfEmptyOrWhitespace(e, "offerId",
						 * respID+PromotionalOfferResponseCodeConstant.DINE_OFFER_ID_EMPTY);
						 */
    }
}
