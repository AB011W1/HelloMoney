package com.barclays.bmg.mvc.command.fundtransfer.nonregistered.internal;


public class InternalNonRegisteredFundTransferCommand {

	private String txnRefNo;

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public void setTxrefky(String txrefky) {
		this.txnRefNo = txrefky;
	}

}
