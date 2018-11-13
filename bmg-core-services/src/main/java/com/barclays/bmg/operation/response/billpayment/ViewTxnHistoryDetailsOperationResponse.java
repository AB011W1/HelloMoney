package com.barclays.bmg.operation.response.billpayment;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TransactionHistoryDTO;

public class ViewTxnHistoryDetailsOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 9076210168362079854L;
    private TransactionHistoryDTO transactionHistoryDTO;

    public TransactionHistoryDTO getTransactionHistoryDTO() {
	return transactionHistoryDTO;
    }

    public void setTransactionHistoryDTO(TransactionHistoryDTO transactionHistoryDTO) {
	this.transactionHistoryDTO = transactionHistoryDTO;
    }
}