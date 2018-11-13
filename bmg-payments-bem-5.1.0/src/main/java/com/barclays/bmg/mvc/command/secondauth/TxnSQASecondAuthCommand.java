package com.barclays.bmg.mvc.command.secondauth;

import com.barclays.bmg.mvc.command.auth.SQACommand;

public class TxnSQASecondAuthCommand extends SQACommand {

	private String txnRefNo;

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

}
