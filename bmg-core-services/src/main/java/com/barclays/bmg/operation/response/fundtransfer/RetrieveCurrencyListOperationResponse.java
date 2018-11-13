package com.barclays.bmg.operation.response.fundtransfer;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;

public class RetrieveCurrencyListOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -78086427874297156L;
    private List<String> currencyList;

    public List<String> getCurrencyList() {
	return currencyList;
    }

    public void setCurrencyList(List<String> currencyList) {
	this.currencyList = currencyList;
    }
}