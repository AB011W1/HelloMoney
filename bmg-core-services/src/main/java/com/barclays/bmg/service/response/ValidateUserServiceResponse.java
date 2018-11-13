package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ValidateUserDTO;

public class ValidateUserServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 6209242037732272894L;
    private ValidateUserDTO validateUserDTO;

    public ValidateUserDTO getValidateUserDTO() {
	return validateUserDTO;
    }

    public void setValidateUserDTO(ValidateUserDTO validateUserDTO) {
	this.validateUserDTO = validateUserDTO;
    }

}
