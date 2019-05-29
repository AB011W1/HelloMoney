package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;

public class GetSelectedAccountOperationRequest extends RequestContext {

    private String acctNumber;

    private String creditCardNumber;

    public String getAcctNumber() {
	return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
	this.acctNumber = acctNumber;
    }

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

}