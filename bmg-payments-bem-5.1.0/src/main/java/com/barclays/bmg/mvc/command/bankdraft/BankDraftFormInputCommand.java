package com.barclays.bmg.mvc.command.bankdraft;

import com.barclays.bmg.utils.BMBCommonUtility;

public class BankDraftFormInputCommand {

	private String txnAmt;
	private String txnNot;
	private String curr;
	private String drfTypSel;
	private String delTypSel;
	private String brnNme;
	private String brnCde;
	private String payAt;

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
	public String getDrfTypSel() {
		return drfTypSel;
	}
	public void setDrfTypSel(String drfTypSel) {
		this.drfTypSel = drfTypSel;
	}
	public String getDelTypSel() {
		return delTypSel;
	}
	public void setDelTypSel(String delTypSel) {
		this.delTypSel = delTypSel;
	}
	public String getBrnNme() {
		return brnNme;
	}
	public void setBrnNme(String brnNme) {
		this.brnNme = brnNme;
	}
	public String getBrnCde() {
		return brnCde;
	}
	public void setBrnCde(String brnCde) {
		this.brnCde = brnCde;
	}
	public String getPayAt() {
		return payAt;
	}
	public void setPayAt(String payAt) {
		this.payAt = payAt;
	}


}
