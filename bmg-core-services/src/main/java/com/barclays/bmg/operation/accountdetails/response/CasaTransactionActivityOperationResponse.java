package com.barclays.bmg.operation.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AccountActivityListDTO;

public class CasaTransactionActivityOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 8197944054183419778L;
    AccountActivityListDTO accountActivityListDTO;

    public AccountActivityListDTO getAccountActivityListDTO() {
	return accountActivityListDTO;
    }

    public void setAccountActivityListDTO(AccountActivityListDTO accountActivityListDTO) {
	this.accountActivityListDTO = accountActivityListDTO;
    }

}
