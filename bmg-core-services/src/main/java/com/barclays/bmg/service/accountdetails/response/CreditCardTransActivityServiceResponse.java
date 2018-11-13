package com.barclays.bmg.service.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardTransactionHistoryListDTO;

public class CreditCardTransActivityServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -5422459165448984307L;
    CreditCardTransactionHistoryListDTO creditCardTransactionHistoryListDTO;

    public CreditCardTransactionHistoryListDTO getCreditCardTransactionHistoryListDTO() {
	return creditCardTransactionHistoryListDTO;
    }

    public void setCreditCardTransactionHistoryListDTO(CreditCardTransactionHistoryListDTO creditCardTransactionHistoryListDTO) {
	this.creditCardTransactionHistoryListDTO = creditCardTransactionHistoryListDTO;
    }
}