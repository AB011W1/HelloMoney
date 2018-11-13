package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class AddBeneficiaryServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -3756864813040886887L;
    private String txnReferenceNumber;

    public String getTxnReferenceNumber() {
	return txnReferenceNumber;
    }

    public void setTxnReferenceNumber(String txnReferenceNumber) {
	this.txnReferenceNumber = txnReferenceNumber;
    }

}
