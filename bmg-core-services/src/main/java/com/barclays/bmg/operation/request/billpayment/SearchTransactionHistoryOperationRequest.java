package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.TransactionHistoryDTO;

public class SearchTransactionHistoryOperationRequest extends RequestContext {

    private TransactionHistoryDTO transactionHistoryDTO;

    public TransactionHistoryDTO getTransactionHistoryDTO() {
	return transactionHistoryDTO;
    }

    public void setTransactionHistoryDTO(TransactionHistoryDTO transactionHistoryDTO) {
	this.transactionHistoryDTO = transactionHistoryDTO;
    }

}
