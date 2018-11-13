package com.barclays.bmg.operation.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AccountActivityListDTO;
import com.barclays.bmg.dto.CASAAccountDTO;

public class CASADetailsOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 965676777162719546L;

    CASAAccountDTO casaAccountDTO;
    AccountActivityListDTO accountActivityListDTO;

    public CASAAccountDTO getCasaAccountDTO() {
	return casaAccountDTO;
    }

    public void setCasaAccountDTO(CASAAccountDTO casaAccountDTO) {
	this.casaAccountDTO = casaAccountDTO;
    }

    public AccountActivityListDTO getAccountActivityListDTO() {
	return accountActivityListDTO;
    }

    public void setAccountActivityListDTO(AccountActivityListDTO accountActivityListDTO) {
	this.accountActivityListDTO = accountActivityListDTO;
    }

}
