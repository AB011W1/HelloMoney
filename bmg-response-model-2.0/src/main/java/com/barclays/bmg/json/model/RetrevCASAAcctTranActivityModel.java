package com.barclays.bmg.json.model;

import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.operation.accountdetails.response.CASAAccountTransactionOperation;

public class RetrevCASAAcctTranActivityModel extends BMBPayloadData {

	/**
	 *
	 */
	private static final long serialVersionUID = -6360669547838623782L;

	List<CASAAccountTransactionOperation> casaAccountTransactionList;

	public List<CASAAccountTransactionOperation> getCasaAccountTransactionList() {
		return casaAccountTransactionList;
	}

	public void setCasaAccountTransactionList(
			List<CASAAccountTransactionOperation> casaAccountTransactionList) {
		this.casaAccountTransactionList = casaAccountTransactionList;
	}
}
