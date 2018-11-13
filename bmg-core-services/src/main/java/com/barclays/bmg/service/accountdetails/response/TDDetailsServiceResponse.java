package com.barclays.bmg.service.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TdAccountDTO;

public class TDDetailsServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 9122347354502391551L;
    private TdAccountDTO tdAccountDTO;

    public TdAccountDTO getTdAccountDTO() {
	return tdAccountDTO;
    }

    public void setTdAccountDTO(TdAccountDTO tdAccountDTO) {
	this.tdAccountDTO = tdAccountDTO;
    }
}