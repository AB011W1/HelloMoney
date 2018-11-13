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
 * Package name is com.barclays.bmg.mvc.command.airtimetopup
 * /
 */
package com.barclays.bmg.mvc.command.airtimetopup;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-payments-bem-5.1.0</b> </br> The file name is
 * <b>AirTimeTopUpValidateCommand.java</b> </br> Description is <b>V 1.1</b> </br> Updated Date is <b>May 27, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class AirTimeTopUpValidateCommand created using JRE 1.6.0_10
 */
public class AirTimeTopUpValidateCommand {

    /**
     * The instance variable named "actNo" is created.
     */
    private String actNo;

    /**
     * The instance variable named "billerCreditDTO" is created.
     */
	private  String actionCode;
	 /**
     * The instance variable named "billerCreditDTO" is created.
     */
	private  String storeNumber;
    /**
     * The instance variable named "txnAmt" is created.
     */
    private String txnAmt;

    /**
     * The instance variable named "mblNo" is created.
     */
    private String mblNo;

    /**
     * The instance variable named "billerId" is created.
     */
    private String billerId;

    /**
     * The instance variable named "billHolderNam" is created.
     */
    private String billHolderNam;

    private String txnNot;
	/**
     * The instance variable named "creditcardNo" is created.
     */
    private String creditcardNo;

    /**
     * The instance variable named "creditcardNo" is created.
     */
    private String creditCardFlag;

    private String extra;

    public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getCreditCardFlag() {
		return creditCardFlag;
	}

	public void setCreditCardFlag(String creditCardFlag) {
		this.creditCardFlag = creditCardFlag;
	}

   /**
     * The instance variable named "accountType" is created.
     */
    private String accountType;
    /**
     * The instance variable named "activityId" is created.
     */
    private String activityId;

    public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getCreditcardNo() {
		return creditcardNo;
	}

	public void setCreditcardNo(String creditcardNo) {
		this.creditcardNo = creditcardNo;
	}

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
     * Gets the txn amt.
     *
     * @return the TxnAmt
     */
    public String getTxnAmt() {
	return txnAmt;
    }

    /**
     * Sets values for TxnAmt.
     *
     * @param txnAmt
     *            the txn amt
     */
    public void setTxnAmt(String txnAmt) {
	this.txnAmt = txnAmt;
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

    public String getTxnNot() {
	return txnNot;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
    }

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
}