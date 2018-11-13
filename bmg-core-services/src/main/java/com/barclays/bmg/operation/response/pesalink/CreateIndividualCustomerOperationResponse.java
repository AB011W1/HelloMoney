package com.barclays.bmg.operation.response.pesalink;


import com.barclays.bmg.context.ResponseContext;

public class CreateIndividualCustomerOperationResponse extends ResponseContext {

	private static final long serialVersionUID = 6017509621703548165L;
	private String txnRefNo;

	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

}
