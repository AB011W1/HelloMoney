package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.TransactionDTO;

public class MakeBillPaymentOperationRequest extends RequestContext {

    private TransactionDTO transactionDTO;

    public TransactionDTO getTransactionDTO() {
	return transactionDTO;
    }

    public void setTransactionDTO(TransactionDTO transactionDTO) {
	this.transactionDTO = transactionDTO;
    }

}
