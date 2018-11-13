package com.barclays.bmg.operation.response.billpayment;

import java.util.Date;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.Charge;

public class MakeBillPaymentOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -4047334699102583387L;

    private boolean scndLvlAuthReq;
    private Date trnDate;
    private String trnRef;
    private String pymntRefNo;
    private String voucherNo;
    private String rcptNo;
    private String tokenNo;
    private String txnMsg;
    // UBP-BRR1325-Changes
    private String noOfUnits;

    private List<Charge> charges;
    private Amount tranFee;
    private String feeAmntCurCde;

    public boolean isScndLvlAuthReq() {
	return scndLvlAuthReq;
    }

    public void setScndLvlAuthReq(boolean scndLvlAuthReq) {
	this.scndLvlAuthReq = scndLvlAuthReq;
    }

    public Date getTrnDate() {
	return trnDate;
    }

    public void setTrnDate(Date trnDate) {
	this.trnDate = trnDate;
    }

    public String getTrnRef() {
	return trnRef;
    }

    public void setTrnRef(String trnRef) {
	this.trnRef = trnRef;
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

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
    }

    public Amount getTranFee() {
	return tranFee;
    }

    public void setTranFee(Amount tranFee) {
	this.tranFee = tranFee;
    }

    public String getFeeAmntCurCde() {
	return feeAmntCurCde;
    }

    public void setFeeAmntCurCde(String feeAmntCurCde) {
	this.feeAmntCurCde = feeAmntCurCde;
    }

    public String getNoOfUnits() {
	return noOfUnits;
    }

    public void setNoOfUnits(String noOfUnits) {
	this.noOfUnits = noOfUnits;
    }

}
