package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import com.barclays.bmg.json.response.BMBPayloadData;

public class PaymentConfirmationJSONBean extends BMBPayloadData implements
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3303270007959862290L;

	String bemRefNo;
	String txnResRefNo;
	String txnRefNo;
	String resDtTm;
	String txnDtTm;
	String pymntRefNo;
	String voucherNo;
	String rcptNo;
	String tokenNo;
	String txnMsg;

	public String getBemRefNo() {
		return bemRefNo;
	}

	public void setBemRefNo(String bemRefNo) {
		this.bemRefNo = bemRefNo;
	}

	public String getTxnResRefNo() {
		return txnResRefNo;
	}

	public void setTxnResRefNo(String txnResRefNo) {
		this.txnResRefNo = txnResRefNo;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public String getResDtTm() {
		return resDtTm;
	}

	public void setResDtTm(String resDtTm) {
		this.resDtTm = resDtTm;
	}

	public String getTxnDtTm() {
		return txnDtTm;
	}

	public void setTxnDtTm(String txnDtTm) {
		this.txnDtTm = txnDtTm;
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

}
