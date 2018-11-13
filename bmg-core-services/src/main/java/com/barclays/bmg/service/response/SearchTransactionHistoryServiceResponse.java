package com.barclays.bmg.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TransactionHistoryDTO;

public class SearchTransactionHistoryServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 683656061606109183L;
    private List<TransactionHistoryDTO> transactionHistoryDTOList;

    public List<TransactionHistoryDTO> getTransactionHistoryDTOList() {
	return transactionHistoryDTOList;
    }

    public void setTransactionHistoryDTOList(List<TransactionHistoryDTO> transactionHistoryDTOList) {
	this.transactionHistoryDTOList = transactionHistoryDTOList;
    }

}
