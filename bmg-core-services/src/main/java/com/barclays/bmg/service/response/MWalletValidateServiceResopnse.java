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
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>MWalletValidateServiceResopnse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class MWalletValidateServiceResopnse created using JRE 1.6.0_10
 */
public class MWalletValidateServiceResopnse extends ResponseContext {

    /**
     * The Constant named "serialVersionUID" is created.
     */
    private static final long serialVersionUID = -2073512406472471324L;

    /**
     * The instance variable named "creditCard" is created.
     */
    private CustomerAccountDTO creditCard;
 	/**
     * The instance variable named "account" is created.
     */
    private CASAAccountDTO account;

    /**
     * The instance variable named "billerDTO" is created.
     */
    private BillerDTO billerDTO;

    /**
     * The instance variable named "txnRefNo" is created.
     */
    private String txnRefNo;

    /**
     * The instance variable named "mblNmbr" is created.
     */
    private String mblNmbr;

    /**
     * The instance variable named "amount" is created.
     */
    private String amount;

    /**
     * Gets the account.
     * 
     * @return the Account
     */
    public CASAAccountDTO getAccount() {
	return account;
    }

    /**
     * Sets values for Account.
     * 
     * @param account
     *            the account
     */
    public void setAccount(CASAAccountDTO account) {
	this.account = account;
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
     * Gets the txn ref no.
     * 
     * @return the TxnRefNo
     */
    public String getTxnRefNo() {
	return txnRefNo;
    }

    /**
     * Sets values for TxnRefNo.
     * 
     * @param txnRefNo
     *            the txn ref no
     */
    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    /**
     * Gets the mbl nmbr.
     * 
     * @return the MblNmbr
     */
    public String getMblNmbr() {
	return mblNmbr;
    }

    /**
     * Sets values for MblNmbr.
     * 
     * @param mblNmbr
     *            the mbl nmbr
     */
    public void setMblNmbr(String mblNmbr) {
	this.mblNmbr = mblNmbr;
    }

    /**
     * Gets the amount.
     * 
     * @return the Amount
     */
    public String getAmount() {
	return amount;
    }

    /**
     * Sets values for Amount.
     * 
     * @param amount
     *            the amount
     */
    public void setAmount(String amount) {
	this.amount = amount;
    }
	public CustomerAccountDTO getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CustomerAccountDTO creditCard) {
		this.creditCard = creditCard;
	}
}