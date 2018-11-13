package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SessionActivityJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1825364067955722116L;


	private String txnTyp;

	private Date txnTm;

	private List<KeyValueJSONModel> txnDetls;

	private String sta;

	private String refNo;

	public String getTxnTyp() {
		return txnTyp;
	}

	public void setTxnTyp(String txnTyp) {
		this.txnTyp = txnTyp;
	}

	public Date getTxnTm() {
		return (Date) txnTm.clone();
	}

	public void setTxnTm(Date txnTm) {
		this.txnTm = (Date) txnTm.clone();
	}

	public List<KeyValueJSONModel> getTxnDetls() {
		return txnDetls;
	}

	public void setTxnDetls(List<KeyValueJSONModel> txnDetls) {
		this.txnDetls = txnDetls;
	}

	public String getSta() {
		return sta;
	}

	public void setSta(String sta) {
		this.sta = sta;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}



}
