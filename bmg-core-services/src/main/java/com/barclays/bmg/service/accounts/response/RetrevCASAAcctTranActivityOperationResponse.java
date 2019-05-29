package com.barclays.bmg.service.accounts.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.operation.accountdetails.response.CASAAccountTransactionOperation;

public class RetrevCASAAcctTranActivityOperationResponse extends
		ResponseContext {
	List<CASAAccountTransactionOperation> casaAccountTransactionList;

	public List<CASAAccountTransactionOperation> getCasaAccountTransactionList() {
		return casaAccountTransactionList;
	}

	public void setCasaAccountTransactionList(
			List<CASAAccountTransactionOperation> casaAccountTransactionList) {
		this.casaAccountTransactionList = casaAccountTransactionList;
	}
}
