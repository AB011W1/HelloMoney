package com.barclays.bmg.operation.request.fundtransfer;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.TransactionDTO;

public class DomesticFundTransferExecuteOperationRequest extends RequestContext {

    private TransactionDTO transactionDTO;

    public TransactionDTO getTransactionDTO() {
	return transactionDTO;
    }

    public void setTransactionDTO(TransactionDTO transactionDTO) {
	this.transactionDTO = transactionDTO;
    }

}
