package com.barclays.bmg.mvc.command.billpayment;

public class BillPaymentExecuteCommand {

    private String txnRefNo;

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

}
