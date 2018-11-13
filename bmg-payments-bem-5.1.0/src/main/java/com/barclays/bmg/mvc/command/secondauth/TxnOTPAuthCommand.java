package com.barclays.bmg.mvc.command.secondauth;

import com.barclays.bmg.mvc.command.auth.OTPCommand;

public class TxnOTPAuthCommand extends OTPCommand {

	private String txnRefNo;

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

}
