package com.barclays.bmg.audit.builder.impl;

import com.barclays.bmg.audit.dto.TransactionAuditDTO;

public class MakeFundTransferTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	// DomesticFundTransferExecuteOperationRequest request =
	// (DomesticFundTransferExecuteOperationRequest)args[0];

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();

	return transactionAuditDTO;
    }

}
