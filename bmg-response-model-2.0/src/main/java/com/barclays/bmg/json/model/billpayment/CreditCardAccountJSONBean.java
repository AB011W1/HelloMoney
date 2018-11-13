package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;
import java.util.Date;

import com.barclays.bmg.json.model.AmountJSONModel;

public class CreditCardAccountJSONBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 385786398692149622L;
	private String toAcctDisName;
	private String toAcct;
	private AmountJSONModel minPayAmt;
	private Date pymntDueDt;
	private String curr;
	private AmountJSONModel outStndBal;
	private AmountJSONModel curBal;




	public String getToAcctDisName() {
		return toAcctDisName;
	}
	public void setToAcctDisName(String toAcctDisName) {
		this.toAcctDisName = toAcctDisName;
	}
	public String getToAcct() {
		return toAcct;
	}
	public void setToAcct(String toAcct) {
		this.toAcct = toAcct;
	}
	public Date getPymntDueDt() {
		return (Date) pymntDueDt.clone();
	}
	public void setPymntDueDt(Date pymntDueDt) {
		this.pymntDueDt = (Date) pymntDueDt.clone();
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public AmountJSONModel getMinPayAmt() {
		return minPayAmt;
	}
	public void setMinPayAmt(AmountJSONModel minPayAmt) {
		this.minPayAmt = minPayAmt;
	}
	public AmountJSONModel getOutStndBal() {
		return outStndBal;
	}
	public void setOutStndBal(AmountJSONModel outStndBal) {
		this.outStndBal = outStndBal;
	}
	public AmountJSONModel getCurBal() {
		return curBal;
	}
	public void setCurBal(AmountJSONModel curBal) {
		this.curBal = curBal;
	}



}
