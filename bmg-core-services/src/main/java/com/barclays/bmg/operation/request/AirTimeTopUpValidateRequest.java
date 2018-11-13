/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
/**
 * Package name is com.barclays.bmg.operation.request
 * /
 */
package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>AirTimeTopUpValidateRequest.java</b> </br> Description is <b>V 1.1</b> </br> Updated Date is <b>May 27, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class AirTimeTopUpValidateRequest created using JRE 1.6.0_10
 */
public class AirTimeTopUpValidateRequest extends RequestContext {

    /**
     * The instance variable named "billerId" is created.
     */
    private String billerId;

    /**
     * The instance variable named "billerName" is created.
     */
    private String billerName;

    /**
     * The instance variable named "billerCatName" is created.
     */
    private String billerCatName;

    /**
     * The instance variable named "actNo" is created.
     */
    private String actNo;

    /**
     * The instance variable named "billerCatId" is created.
     */
    private String billerCatId;

    /**
     * The instance variable named "amt" is created.
     */
    private String amt;

    /**
     * The instance variable named "mblNo" is created.
     */
    private String mblNo;

    /**
     * The instance variable named "prvdAcctNmbr" is created.
     */
    private String prvdAcctNmbr;

    /**
     * The instance variable named "billHolderNam" is created.
     */
    private String billHolderNam;

    /**
     * Gets the bill holder nam.
     * 
     * @return the BillHolderNam
     */
    public String getBillHolderNam() {
	return billHolderNam;
    }

    /**
     * Sets values for BillHolderNam.
     * 
     * @param billHolderNam
     *            the bill holder nam
     */
    public void setBillHolderNam(String billHolderNam) {
	this.billHolderNam = billHolderNam;
    }

    /**
     * Gets the act no.
     * 
     * @return the ActNo
     */
    public String getActNo() {
	return actNo;
    }

    /**
     * Sets values for ActNo.
     * 
     * @param actNo
     *            the act no
     */
    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    /**
     * Gets the biller cat id.
     * 
     * @return the BillerCatId
     */
    public String getBillerCatId() {
	return billerCatId;
    }

    /**
     * Sets values for BillerCatId.
     * 
     * @param billerCatId
     *            the biller cat id
     */
    public void setBillerCatId(String billerCatId) {
	this.billerCatId = billerCatId;
    }

    /**
     * Gets the amt.
     * 
     * @return the Amt
     */
    public String getAmt() {
	return amt;
    }

    /**
     * Sets values for Amt.
     * 
     * @param amt
     *            the amt
     */
    public void setAmt(String amt) {
	this.amt = amt;
    }

    /**
     * Gets the mbl no.
     * 
     * @return the MblNo
     */
    public String getMblNo() {
	return mblNo;
    }

    /**
     * Sets values for MblNo.
     * 
     * @param mblNo
     *            the mbl no
     */
    public void setMblNo(String mblNo) {
	this.mblNo = mblNo;
    }

    /**
     * Gets the prvd acct nmbr.
     * 
     * @return the PrvdAcctNmbr
     */
    public String getPrvdAcctNmbr() {
	return prvdAcctNmbr;
    }

    /**
     * Sets values for PrvdAcctNmbr.
     * 
     * @param prvdAcctNmbr
     *            the prvd acct nmbr
     */
    public void setPrvdAcctNmbr(String prvdAcctNmbr) {
	this.prvdAcctNmbr = prvdAcctNmbr;
    }

    /**
     * Gets the biller id.
     * 
     * @return the BillerId
     */
    public String getBillerId() {
	return billerId;
    }

    /**
     * Sets values for BillerId.
     * 
     * @param billerId
     *            the biller id
     */
    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    /**
     * Gets the biller name.
     * 
     * @return the BillerName
     */
    public String getBillerName() {
	return billerName;
    }

    /**
     * Sets values for BillerName.
     * 
     * @param billerName
     *            the biller name
     */
    public void setBillerName(String billerName) {
	this.billerName = billerName;
    }

    /**
     * Gets the biller cat name.
     * 
     * @return the BillerCatName
     */
    public String getBillerCatName() {
	return billerCatName;
    }

    /**
     * Sets values for BillerCatName.
     * 
     * @param billerCatName
     *            the biller cat name
     */
    public void setBillerCatName(String billerCatName) {
	this.billerCatName = billerCatName;
    }
}