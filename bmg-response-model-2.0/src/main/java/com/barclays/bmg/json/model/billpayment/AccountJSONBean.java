package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import com.barclays.bmg.json.model.AmountJSONModel;

public class AccountJSONBean implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 2581266138855531943L;

    private String actNo;

    private String prdTyp;

    private String curr;

    private AmountJSONModel avlbBal;

    private String mkdActNo;

    private String priInd;

    private String branchCode;

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getPrdTyp() {
	return prdTyp;
    }

    public void setPrdTyp(String prdTyp) {
	this.prdTyp = prdTyp;
    }

    public String getCurr() {
	return curr;
    }

    public void setCurr(String curr) {
	this.curr = curr;
    }

    public String getMkdActNo() {
	return mkdActNo;
    }

    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    public AmountJSONModel getAvlbBal() {
	return avlbBal;
    }

    public void setAvlbBal(AmountJSONModel avlbBal) {
	this.avlbBal = avlbBal;
    }

    public String getPriInd() {
	return priInd;
    }

    public void setPriInd(String priInd) {
	this.priInd = priInd;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

}
