package com.barclays.bmg.json.model.chequebook;

import com.barclays.bmg.json.response.BMBPayloadData;

public class ChequeBookExecuteJsonModel extends BMBPayloadData {

	private static final long serialVersionUID = -1171060426484121719L;
	private String bemRefNo;
	private String txnResRefNo;
	private String txnRefNo;
	private String resDtTm;
	private String txnDtTm;
	private String txnMsg;

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

	public String getTxnMsg() {
		return txnMsg;
	}

	public void setTxnMsg(String txnMsg) {
		this.txnMsg = txnMsg;
	}

}
