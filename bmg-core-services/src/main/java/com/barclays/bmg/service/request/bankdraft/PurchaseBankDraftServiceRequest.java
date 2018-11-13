package com.barclays.bmg.service.request.bankdraft;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BankDraftTransactionDTO;

public class PurchaseBankDraftServiceRequest extends RequestContext {

    private BankDraftTransactionDTO bankDraftTransactionDTO;

    public BankDraftTransactionDTO getBankDraftTransactionDTO() {
	return bankDraftTransactionDTO;
    }

    public void setBankDraftTransactionDTO(BankDraftTransactionDTO bankDraftTransactionDTO) {
	this.bankDraftTransactionDTO = bankDraftTransactionDTO;
    }

}
