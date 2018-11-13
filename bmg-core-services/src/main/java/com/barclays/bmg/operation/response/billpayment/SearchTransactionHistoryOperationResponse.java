package com.barclays.bmg.operation.response.billpayment;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TransactionHistoryDTO;

public class SearchTransactionHistoryOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 8164810458975424851L;
    private List<TransactionHistoryDTO> transactionHistoryDTOList;
    private String fundTransferType;

    public String getFundTransferType() {
	return fundTransferType;
    }

    public void setFundTransferType(String fundTransferType) {
	this.fundTransferType = fundTransferType;
    }

    public List<TransactionHistoryDTO> getTransactionHistoryDTOList() {
	return transactionHistoryDTOList;
    }

    public void setTransactionHistoryDTOList(List<TransactionHistoryDTO> transactionHistoryDTOList) {
	this.transactionHistoryDTOList = transactionHistoryDTOList;
    }

}
