package com.barclays.bmg.service.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardTransactionHistoryListDTO;

public class CreditCardTransActivityServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -5422459165448984307L;
    CreditCardTransactionHistoryListDTO creditCardTransactionHistoryListDTO;
    private String currency;

    public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public CreditCardTransactionHistoryListDTO getCreditCardTransactionHistoryListDTO() {
	return creditCardTransactionHistoryListDTO;
    }

    public void setCreditCardTransactionHistoryListDTO(CreditCardTransactionHistoryListDTO creditCardTransactionHistoryListDTO) {
	this.creditCardTransactionHistoryListDTO = creditCardTransactionHistoryListDTO;
    }
}