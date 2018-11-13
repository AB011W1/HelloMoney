package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;
import java.util.Date;

import com.barclays.bmg.json.model.AmountJSONModel;



public class TransactionInformationRestBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3419625982599344954L;

	private AmountJSONModel txnAmt;

	private String cur;

	private AmountJSONModel dlyTrnLmt;

	private AmountJSONModel availTrnLmt;

	private Date txnDt;





	public AmountJSONModel getDlyTrnLmt() {
		return dlyTrnLmt;
	}

	public void setDlyTrnLmt(AmountJSONModel dlyTrnLmt) {
		this.dlyTrnLmt = dlyTrnLmt;
	}

	public AmountJSONModel getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(AmountJSONModel txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public AmountJSONModel getAvailTrnLmt() {
		return availTrnLmt;
	}

	public void setAvailTrnLmt(AmountJSONModel availTrnLmt) {
		this.availTrnLmt = availTrnLmt;
	}

	public Date getTxnDt() {
		return (Date) txnDt.clone();
	}

	public void setTxnDt(Date txnDt) {
		this.txnDt = (Date) txnDt.clone();
	}







}
