package com.barclays.bmg.service.response.pesalink;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;




public class CreateIndividualCustomerServiceResponse extends ResponseContext {

	private static final long serialVersionUID = 6017509621703548165L;

	private String  txnRefNo;
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}


}


