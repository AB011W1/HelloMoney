package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

/**
 * @author BTCI
 * 
 */
public class DeleteBeneficiaryServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    private String txnReferenceNumber;

    /**
     * @return String
     */
    public String getTxnReferenceNumber() {
	return txnReferenceNumber;
    }

    /**
     * @param txnReferenceNumber
     */
    public void setTxnReferenceNumber(String txnReferenceNumber) {
	this.txnReferenceNumber = txnReferenceNumber;
    }

}
