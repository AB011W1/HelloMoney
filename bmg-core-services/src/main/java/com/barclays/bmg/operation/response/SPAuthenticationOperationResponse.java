package com.barclays.bmg.operation.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AuthenticationDTO;

public class SPAuthenticationOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 3502984354293667793L;
    private AuthenticationDTO authenticationDTO;
    private String secondAuthMethod;
    private boolean success;

    public AuthenticationDTO getAuthenticationDTO() {
	return authenticationDTO;
    }

    public void setAuthenticationDTO(AuthenticationDTO authenticationDTO) {
	this.authenticationDTO = authenticationDTO;
    }

    public String getSecondAuthMethod() {
	return secondAuthMethod;
    }

    public void setSecondAuthMethod(String secondAuthMethod) {
	this.secondAuthMethod = secondAuthMethod;
    }

    @Override
    public boolean isSuccess() {
	return success;
    }

    @Override
    public void setSuccess(boolean success) {
	this.success = success;
    }

}
