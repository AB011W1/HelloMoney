package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class UpdateLanguagePrefServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -5286611039306073239L;
    private String txnRefNumber;

    public String getTxnRefNumber() {
	return txnRefNumber;
    }

    public void setTxnRefNumber(String txnRefNumber) {
	this.txnRefNumber = txnRefNumber;
    }

}
