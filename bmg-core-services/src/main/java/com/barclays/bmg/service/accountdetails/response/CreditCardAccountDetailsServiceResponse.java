package com.barclays.bmg.service.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;

public class CreditCardAccountDetailsServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -6864330300290211284L;
    private CreditCardAccountDTO creditCardAccountDTO;

    public CreditCardAccountDTO getCreditCardAccountDTO() {
	return creditCardAccountDTO;
    }

    public void setCreditCardAccountDTO(CreditCardAccountDTO creditCardAccountDTO) {
	this.creditCardAccountDTO = creditCardAccountDTO;
    }
}