package com.barclays.bmg.mvc.command.billpayment;

import com.barclays.bmg.utils.BMBCommonUtility;

public class ExternalFundTransferCommand {

    private String txnAmt;
    private String chDesc;
    private String txnNot;
    private String curr;

    private String payRson;
    private String payDtls;
    private String rem1;
    private String rem2;
    private String rem3;
    private String rsonOfPayment;
    private String txnType;

    public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getRsonOfPayment() {
		return rsonOfPayment;
	}

	public void setRsonOfPayment(String rsonOfPayment) {
		this.rsonOfPayment = rsonOfPayment;
	}

	public String getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
	this.txnAmt = txnAmt;
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

    public String getChDesc() {
	return chDesc;
    }

    public void setChDesc(String chDesc) {
	this.chDesc = chDesc;
    }

    public String getPayRson() {
	return payRson;
    }

    public void setPayRson(String payRson) {
	this.payRson = payRson;
    }

    public String getPayDtls() {
	return payDtls;
    }

    public void setPayDtls(String payDtls) {
	this.payDtls = payDtls;
    }

    public String getRem1() {
	rem1 = BMBCommonUtility.convertStringToUnicode(rem1);
	return rem1;
    }

    public void setRem1(String rem1) {
	this.rem1 = rem1;
    }

    public String getRem2() {
	rem2 = BMBCommonUtility.convertStringToUnicode(rem2);
	return rem2;
    }

    public void setRem2(String rem2) {
	this.rem2 = rem2;
    }

    public String getRem3() {
	rem3 = BMBCommonUtility.convertStringToUnicode(rem3);
	return rem3;
    }

    public void setRem3(String rem3) {
	this.rem3 = rem3;
    }

}
