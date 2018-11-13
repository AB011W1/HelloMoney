package com.barclays.bmg.service.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;

public class CASADetailsServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -607199191999002816L;
    CASAAccountDTO casaAccountDTO;

    public CASAAccountDTO getCasaAccountDTO() {
	return casaAccountDTO;
    }

    public void setCasaAccountDTO(CASAAccountDTO casaAccountDTO) {
	this.casaAccountDTO = casaAccountDTO;
    }
}