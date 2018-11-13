package com.barclays.bmg.dto;

public class CreditCardPaymentDTO extends BillPaymentDTO {

    private static final long serialVersionUID = 8459464851243178373L;

    private CustomerAccountDTO creditCardDestAcct;

    public CustomerAccountDTO getCreditCardDestAcct() {
	return creditCardDestAcct;
    }

    public void setCreditCardDestAcct(CustomerAccountDTO creditCardDestAcct) {
	this.creditCardDestAcct = creditCardDestAcct;
    }
}
