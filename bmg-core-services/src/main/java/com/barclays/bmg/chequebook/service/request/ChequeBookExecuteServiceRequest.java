package com.barclays.bmg.chequebook.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.ChequeBookRequestDTO;

public class ChequeBookExecuteServiceRequest extends RequestContext {
    private ChequeBookRequestDTO chequeBookRequestDTO;

    public ChequeBookRequestDTO getChequeBookRequestDTO() {
	return chequeBookRequestDTO;
    }

    public void setChequeBookRequestDTO(ChequeBookRequestDTO chequeBookRequestDTO) {
	this.chequeBookRequestDTO = chequeBookRequestDTO;
    }

}
