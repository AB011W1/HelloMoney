package com.barclays.bmg.json.model.billpayment;

import com.barclays.bmg.json.model.AmountJSONModel;

public class CCAccountJSONBean extends AccountJSONBean{

	/**
	 *
	 */
	private static final long serialVersionUID = -356068793670329545L;
	private String crdNo;
	private String mkdCrdNo;
	private AmountJSONModel crLmt;
	private AmountJSONModel avlCrLmt;
	private AmountJSONModel curBal;
	public String getCrdNo() {
		return crdNo;
	}
	public void setCrdNo(String crdNo) {
		this.crdNo = crdNo;
	}
	public String getMkdCrdNo() {
		return mkdCrdNo;
	}
	public void setMkdCrdNo(String mkdCrdNo) {
		this.mkdCrdNo = mkdCrdNo;
	}
	public AmountJSONModel getCrLmt() {
		return crLmt;
	}
	public void setCrLmt(AmountJSONModel crLmt) {
		this.crLmt = crLmt;
	}
	public AmountJSONModel getAvlCrLmt() {
		return avlCrLmt;
	}
	public void setAvlCrLmt(AmountJSONModel avlCrLmt) {
		this.avlCrLmt = avlCrLmt;
	}
	public AmountJSONModel getCurBal() {
		return curBal;
	}
	public void setCurBal(AmountJSONModel curBal) {
		this.curBal = curBal;
	}

}
