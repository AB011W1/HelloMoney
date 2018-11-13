package com.barclays.bmg.mvc.command.fundtransfer.own;

public class FundTransferExecuteCommand {
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
