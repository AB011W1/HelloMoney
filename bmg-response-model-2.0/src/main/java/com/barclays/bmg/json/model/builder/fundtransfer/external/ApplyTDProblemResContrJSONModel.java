package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.io.Serializable;

import com.barclays.bmg.json.response.BMBPayloadData;

public class ApplyTDProblemResContrJSONModel extends BMBPayloadData
		implements Serializable {

	private static final long serialVersionUID = -8609218776855272317L;
	private String txnResRefNo;
	private String txnDtTm;
	private String resDtTm;
	private String txnRefNo;
	private String bemRefNo;
	public String getTxnResRefNo() {
		return txnResRefNo;
	}
	public void setTxnResRefNo(String txnResRefNo) {
		this.txnResRefNo = txnResRefNo;
	}
	public String getTxnDtTm() {
		return txnDtTm;
	}
	public void setTxnDtTm(String txnDtTm) {
		this.txnDtTm = txnDtTm;
	}
	public String getResDtTm() {
		return resDtTm;
	}
	public void setResDtTm(String resDtTm) {
		this.resDtTm = resDtTm;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getBemRefNo() {
		return bemRefNo;
	}
	public void setBemRefNo(String bemRefNo) {
		this.bemRefNo = bemRefNo;
	}


}
