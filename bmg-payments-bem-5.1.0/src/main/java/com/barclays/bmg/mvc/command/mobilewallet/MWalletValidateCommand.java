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
 * Package name is com.barclays.bmg.mvc.command.mobilewallet
 * /
 */
package com.barclays.bmg.mvc.command.mobilewallet;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-payments-bem-5.1.0</b> </br> The file name is
 * <b>MWalletValidateCommand.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class MWalletValidateCommand created using JRE 1.6.0_10
 */
public class MWalletValidateCommand {
	/**
     * The instance variable named "billerCreditDTO" is created.
     */
	private  String actionCode;
	 /**
     * The instance variable named "billerCreditDTO" is created.
     */
	private  String storeNumber;
	 /**
     * The instance variable named "identifier" is created.
     */
	 private String CrditCardFlag;
    /**
     * The instance variable named "actNo" is created.
     */
    private String actNo;

    /**
     * The instance variable named "txnAmt" is created.
     */
    private String txnAmt;

    /**
     * The instance variable named "mblNo" is created.
     */
    private String mblNo;

    /**
     * The instance variable named "refNmbr" is created.
     */
    private String refNmbr;

    /**
     * The instance variable named "billerId" is created.
     */
    private String billerId;

    private String txnNot;

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
     * Gets the ref nmbr.
     * 
     * @return the RefNmbr
     */
    public String getRefNmbr() {
	return refNmbr;
    }

    /**
     * Sets values for RefNmbr.
     * 
     * @param refNmbr
     *            the ref nmbr
     */
    public void setRefNmbr(String refNmbr) {
	this.refNmbr = refNmbr;
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
	public String getCrditCardFlag() {
		return CrditCardFlag;
	}

	public void setCrditCardFlag(String crditCardFlag) {
		CrditCardFlag = crditCardFlag;
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