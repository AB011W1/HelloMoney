package com.barclays.bmg.service.accounts.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.operation.accountdetails.response.CASAAccountTransactionService;

public class RetrevCASAAcctTranActivityServiceResponse extends ResponseContext {
	List<CASAAccountTransactionService> casaAccountTransactionList;

	public List<CASAAccountTransactionService> getCasaAccountTransactionList() {
		return casaAccountTransactionList;
	}

	public void setCasaAccountTransactionList(
			List<CASAAccountTransactionService> casaAccountTransactionList) {
		this.casaAccountTransactionList = casaAccountTransactionList;
	}

}
