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
 * Package name is com.barclays.bmg.json.model.builder.mobilewallet
 * /
 */
package com.barclays.bmg.json.model.builder.mobilewallet;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BillerJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-response-model-2.0</b>
 * </br>
 * The file name is  <b>MwaletValidateJSONModel.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class MwaletValidateJSONModel created using JRE 1.6.0_10
 */
public class MwaletValidateJSONModel extends BMBPayloadData {


	private static final long serialVersionUID = 6853277975941100901L;
	/**
	 * The instance variable named "creditCardJsonMod" is created.
	 */
	private CreditCardAccountJSONModel creditcardJsonMod;
	/**
	 * The instance variable named "srcAcct" is created.
	 */
	private CasaAccountJSONModel srcAcct;

	/**
	 * The instance variable named "prvder" is created.
	 */
	private BillerJSONModel prvder ;

	/**
	 * The instance variable named "txnRefNo" is created.
	 */
	private String txnRefNo;

	/**
	 * The instance variable named "txnAmt" is created.
	 */
	private AmountJSONModel txnAmt;

	/**
	 * The instance variable named "mblNo" is created.
	 */
	private String mblNo;


	// Fields for MWallet MakeBillPayment request CPB - 12/05/2017
	private Double chargeAmount;
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;

	/**
	 * Gets the txn amt.
	 *
	 * @return the TxnAmt
	 */
	public AmountJSONModel getTxnAmt() {
		return txnAmt;
	}

	/**
	 * Sets values for TxnAmt.
	 *
	 * @param txnAmt the txn amt
	 */
	public void setTxnAmt(AmountJSONModel txnAmt) {
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
	 * @param mblNo the mbl no
	 */
	public void setMblNo(String mblNo) {
		this.mblNo = mblNo;
	}

	/**
	 * Gets the src acct.
	 *
	 * @return the SrcAcct
	 */
	public CasaAccountJSONModel getSrcAcct() {
		return srcAcct;
	}

	/**
	 * Sets values for SrcAcct.
	 *
	 * @param srcAcct the src acct
	 */
	public void setSrcAcct(CasaAccountJSONModel srcAcct) {
		this.srcAcct = srcAcct;
	}

	/**
	 * Gets the prvder.
	 *
	 * @return the Prvder
	 */
	public BillerJSONModel getPrvder() {
		return prvder;
	}

	/**
	 * Sets values for Prvder.
	 *
	 * @param prvder the prvder
	 */
	public void setPrvder(BillerJSONModel prvder) {
		this.prvder = prvder;
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
	 * @param txnRefNo the txn ref no
	 */
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	
	
	public CreditCardAccountJSONModel getCreditcardJsonMod() {
		return creditcardJsonMod;
	}

	public void setCreditcardJsonMod(CreditCardAccountJSONModel creditcardJsonMod) {
		this.creditcardJsonMod = creditcardJsonMod;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getFeeGLAccount() {
		return feeGLAccount;
	}

	public void setFeeGLAccount(String feeGLAccount) {
		this.feeGLAccount = feeGLAccount;
	}

	public String getChargeNarration() {
		return chargeNarration;
	}

	public void setChargeNarration(String chargeNarration) {
		this.chargeNarration = chargeNarration;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxGLAccount() {
		return taxGLAccount;
	}

	public void setTaxGLAccount(String taxGLAccount) {
		this.taxGLAccount = taxGLAccount;
	}

	public String getExciseDutyNarration() {
		return ExciseDutyNarration;
	}

	public void setExciseDutyNarration(String exciseDutyNarration) {
		ExciseDutyNarration = exciseDutyNarration;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}