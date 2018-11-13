package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AuthenticationDTO;

public class AuthenticationServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 4251245540928301631L;
    private AuthenticationDTO authenticationDTO;

    public AuthenticationDTO getAuthenticationDTO() {
	return authenticationDTO;
    }

    public void setAuthenticationDTO(AuthenticationDTO authenticationDTO) {
	this.authenticationDTO = authenticationDTO;
    }

}
