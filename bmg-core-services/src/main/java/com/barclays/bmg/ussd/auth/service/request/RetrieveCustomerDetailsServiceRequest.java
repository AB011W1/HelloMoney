package com.barclays.bmg.ussd.auth.service.request;

import com.barclays.bmg.context.RequestContext;

public class RetrieveCustomerDetailsServiceRequest extends RequestContext {

    private String bankCIF;

    public String getBankCIF() {
	return bankCIF;
    }

    public void setBankCIF(String bankCIF) {
	this.bankCIF = bankCIF;
    }

}
