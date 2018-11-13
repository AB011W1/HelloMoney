package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class PasswordChangeServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 4969906612801150250L;

    private String txnRefNumber;

    public String getTxnRefNumber() {
	return txnRefNumber;
    }

    public void setTxnRefNumber(String txnRefNumber) {
	this.txnRefNumber = txnRefNumber;
    }

}
