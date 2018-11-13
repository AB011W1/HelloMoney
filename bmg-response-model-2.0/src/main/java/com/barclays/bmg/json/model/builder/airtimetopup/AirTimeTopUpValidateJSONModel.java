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
 * Package name is com.barclays.bmg.json.model.builder.airtimetopup
 * /
 */
package com.barclays.bmg.json.model.builder.airtimetopup;

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
 * The file name is  <b>AirTimeTopUpValidateJSONModel.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class AirTimeTopUpValidateJSONModel created using JRE 1.6.0_10
 */
public class AirTimeTopUpValidateJSONModel extends BMBPayloadData {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CreditCardAccountJSONModel getCreditcardJsonModel() {
		return creditcardJsonModel;
	}

	public void setCreditcardJsonModel(
			CreditCardAccountJSONModel creditcardJsonModel) {
		this.creditcardJsonModel = creditcardJsonModel;
	}

	/**
	 * The instance variable named "srcAcct" is created.
	 */
	private CasaAccountJSONModel srcAcct;


	private CreditCardAccountJSONModel creditcardJsonModel ;
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
}