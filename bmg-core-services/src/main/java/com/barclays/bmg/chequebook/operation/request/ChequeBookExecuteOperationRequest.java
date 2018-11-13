package com.barclays.bmg.chequebook.operation.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.ChequeBookRequestDTO;

public class ChequeBookExecuteOperationRequest extends RequestContext {

    private ChequeBookRequestDTO chequeBookRequestDTO;

    public ChequeBookRequestDTO getChequeBookRequestDTO() {
	return chequeBookRequestDTO;
    }

    public void setChequeBookRequestDTO(ChequeBookRequestDTO chequeBookRequestDTO) {
	this.chequeBookRequestDTO = chequeBookRequestDTO;
    }

}
