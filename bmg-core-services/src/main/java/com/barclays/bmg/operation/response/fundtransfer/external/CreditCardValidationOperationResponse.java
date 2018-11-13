package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;

public class CreditCardValidationOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -6895908422454684732L;

    private CreditCardAccountDTO creditCardAccountDTO;

    public CreditCardAccountDTO getCreditCardAccountDTO() {
	return creditCardAccountDTO;
    }

    public void setCreditCardAccountDTO(CreditCardAccountDTO creditCardAccountDTO) {
	this.creditCardAccountDTO = creditCardAccountDTO;
    }

}
