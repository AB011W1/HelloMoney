package com.barclays.bmg.service.response;

import java.math.BigDecimal;
import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class PayBillServiceResponse extends ResponseContext {

    private static final long serialVersionUID = -4180928426029171633L;

    private String txnRefNo;
    private Date txnTime;
    private BigDecimal availBalance;
    private String pymntRefNo;
    private String voucherNo;
    private String rcptNo;
    private String tokenNo;
    private String txnMsg;
    // UBP-BRR1325-Changes
    private String noOfUnits;

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public Date getTxnTime() {
	return txnTime;
    }

    public void setTxnTime(Date txnTime) {
	this.txnTime = txnTime;
    }

    public BigDecimal getAvailBalance() {
	return availBalance;
    }

    public void setAvailBalance(BigDecimal availBalance) {
	this.availBalance = availBalance;
    }

    public String getPymntRefNo() {
	return pymntRefNo;
    }

    public void setPymntRefNo(String pymntRefNo) {
	this.pymntRefNo = pymntRefNo;
    }

    public String getVoucherNo() {
	return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
	this.voucherNo = voucherNo;
    }

    public String getRcptNo() {
	return rcptNo;
    }

    public void setRcptNo(String rcptNo) {
	this.rcptNo = rcptNo;
    }

    public String getTokenNo() {
	return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
	this.tokenNo = tokenNo;
    }

    public String getTxnMsg() {
	return txnMsg;
    }

    public void setTxnMsg(String txnMsg) {
	this.txnMsg = txnMsg;
    }

    public String getNoOfUnits() {
	return noOfUnits;
    }

    public void setNoOfUnits(String noOfUnits) {
	this.noOfUnits = noOfUnits;
    }

}
