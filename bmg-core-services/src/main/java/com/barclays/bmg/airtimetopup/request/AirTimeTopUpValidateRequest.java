package com.barclays.bmg.airtimetopup.request;

import com.barclays.bmg.context.RequestContext;

public class AirTimeTopUpValidateRequest extends RequestContext {
    /** Biller Id */
    private String billerId;

    /** Biller Name */
    private String billerName;

    /** Biller Category Name */
    private String billerCatName;

    /** Account number */
    private String actNo;

    /** Biller Category Id */
    private String billerCatId;

    /** amount */
    private String amt;

    /** mobile number */
    private String mblNo;

    private String prvdAcctNmbr;

    /**
     * @return the actNo
     */
    public String getActNo() {
	return actNo;
    }

    /**
     * @param actNo
     *            the actNo to set
     */
    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    /**
     * @return the billerCatId
     */
    public String getBillerCatId() {
	return billerCatId;
    }

    /**
     * @param billerCatId
     *            the billerCatId to set
     */
    public void setBillerCatId(String billerCatId) {
	this.billerCatId = billerCatId;
    }

    /**
     * @return the amtount
     */
    public String getAmt() {
	return amt;
    }

    /**
     * @param amt
     *            the amt to set
     */
    public void setAmt(String amt) {
	this.amt = amt;
    }

    /**
     * @return the mblNo
     */
    public String getMblNo() {
	return mblNo;
    }

    /**
     * @param mblNo
     *            the mblNo to set
     */
    public void setMblNo(String mblNo) {
	this.mblNo = mblNo;
    }

    /**
     * @return the prvdAcctNmbr
     */
    public String getPrvdAcctNmbr() {
	return prvdAcctNmbr;
    }

    /**
     * @param prvdAcctNmbr
     *            the prvdAcctNmbr to set
     */
    public void setPrvdAcctNmbr(String prvdAcctNmbr) {
	this.prvdAcctNmbr = prvdAcctNmbr;
    }

    /**
     * @return the billerId
     */
    public String getBillerId() {
	return billerId;
    }

    /**
     * @param billerId
     *            the billerId to set
     */
    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    /**
     * @return the billerName
     */
    public String getBillerName() {
	return billerName;
    }

    /**
     * @param billerName
     *            the billerName to set
     */
    public void setBillerName(String billerName) {
	this.billerName = billerName;
    }

    /**
     * @return the billerCatName
     */
    public String getBillerCatName() {
	return billerCatName;
    }

    /**
     * @param billerCatName
     *            the billerCatName to set
     */
    public void setBillerCatName(String billerCatName) {
	this.billerCatName = billerCatName;
    }
}