package com.barclays.bmg.service.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AccountActivityListDTO;

public class CASAAccountActivityServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 2630766659424351752L;
    private AccountActivityListDTO accountActivityListDTO;

    public AccountActivityListDTO getAccountActivityListDTO() {
	return accountActivityListDTO;
    }

    public void setAccountActivityListDTO(AccountActivityListDTO accountActivityListDTO) {
	this.accountActivityListDTO = accountActivityListDTO;
    }
}