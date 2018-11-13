package com.barclays.bmg.mvc.command.fundtransfer.internal;

import com.barclays.bmg.utils.BMBCommonUtility;

public class InternalFundTransferCommand {

	private String frActNo;
	private String payId;
	private String txnAmt;
	private String txnDt;
	private String txnNot;
	private String curr;

	public String getFrActNo() {
		return frActNo;
	}

	public void setFrActNo(String frActNo) {
		this.frActNo = frActNo;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTxnDt() {
		return txnDt;
	}

	public void setTxnDt(String txnDt) {
		this.txnDt = txnDt;
	}

	public String getTxnNot() {
		txnNot = BMBCommonUtility.convertStringToUnicode(txnNot);
		return txnNot;
	}

	public void setTxnNot(String txnNot) {
		this.txnNot = txnNot;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

}
