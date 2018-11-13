package com.barclays.bmg.mvc.validator.auth;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.UpdateLanguagePrefResponseCodeConstants;
import com.barclays.bmg.mvc.command.auth.UpdateLanguagePrefCommand;

/**
 * @author BTCI
 * 
 */
public class UpdateLanguagePrefCommandValidator implements Validator {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
	// TODO Auto-generated method stub
	return UpdateLanguagePrefCommand.class.isAssignableFrom(clazz);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object arg0, Errors errors) {

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prefLang", ResponseIdConstants.UPDATE_LANGUAGE_PREF + ResponseIdConstants.HYPHEN_SEPERATOR
		+ UpdateLanguagePrefResponseCodeConstants.PREF_LANGUAGE_EMPTY);

    }

}
