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
 * Package name is com.barclays.bmg.service.response
 * /
 */
package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>MWalletValidateResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class MWalletValidateResponse created using JRE 1.6.0_10
 */
public class MWalletValidateResponse extends ResponseContext {

    /**
     * The Constant named "serialVersionUID" is created.
     */
    private static final long serialVersionUID = -6935472520345598783L;

    /**
     * The instance variable named "billerTyp" is created.
     */
    private String billerTyp;

    /**
     * The instance variable named "billerDTO" is created.
     */
    private BillerDTO billerDTO;

    /**
     * The instance variable named "billerAcctNmbr" is created.
     */
    private String billerAcctNmbr;

    /**
     * The instance variable named "txnAmt" is created.
     */
    private String txnAmt;

    /**
     * The instance variable named "mblNo" is created.
     */
    private String mblNo;

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
     * Gets the biller typ.
     * 
     * @return the BillerTyp
     */
    public String getBillerTyp() {
	return billerTyp;
    }

    /**
     * Sets values for BillerTyp.
     * 
     * @param billerTyp
     *            the biller typ
     */
    public void setBillerTyp(String billerTyp) {
	this.billerTyp = billerTyp;
    }

    /**
     * Gets the biller dto.
     * 
     * @return the BillerDTO
     */
    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    /**
     * Sets values for BillerDTO.
     * 
     * @param billerDTO
     *            the biller dto
     */
    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

    /**
     * Gets the biller acct nmbr.
     * 
     * @return the BillerAcctNmbr
     */
    public String getBillerAcctNmbr() {
	return billerAcctNmbr;
    }

    /**
     * Sets values for BillerAcctNmbr.
     * 
     * @param billerAcctNmbr
     *            the biller acct nmbr
     */
    public void setBillerAcctNmbr(String billerAcctNmbr) {
	this.billerAcctNmbr = billerAcctNmbr;
    }
}