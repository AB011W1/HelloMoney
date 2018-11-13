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
package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;

/**
 * @author BTCI
 *
 */

public class TransactionHistoryJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private CasaAccountJSONModel fromAccountInfo;
	private CasaAccountJSONModel toAccountInfo;
	private String transactionDate;
	private String transactionRefNumber;
	private String status;
	private AmountJSONModel amountInfo;
	private BillerJSONModel billerInfo;
	private String transactionType;
	/**
	 * @return the fromAccountInfo
	 */
	public CasaAccountJSONModel getFromAccountInfo() {
		return fromAccountInfo;
	}
	/**
	 * @param fromAccountInfo the fromAccountInfo to set
	 */
	public void setFromAccountInfo(CasaAccountJSONModel fromAccountInfo) {
		this.fromAccountInfo = fromAccountInfo;
	}
	/**
	 * @return the toAccountInfo
	 */
	public CasaAccountJSONModel getToAccountInfo() {
		return toAccountInfo;
	}
	/**
	 * @param toAccountInfo the toAccountInfo to set
	 */
	public void setToAccountInfo(CasaAccountJSONModel toAccountInfo) {
		this.toAccountInfo = toAccountInfo;
	}
	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the transactionRefNumber
	 */
	public String getTransactionRefNumber() {
		return transactionRefNumber;
	}
	/**
	 * @param transactionRefNumber the transactionRefNumber to set
	 */
	public void setTransactionRefNumber(String transactionRefNumber) {
		this.transactionRefNumber = transactionRefNumber;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the amountInfo
	 */
	public AmountJSONModel getAmountInfo() {
		return amountInfo;
	}
	/**
	 * @param amountInfo the amountInfo to set
	 */
	public void setAmountInfo(AmountJSONModel amountInfo) {
		this.amountInfo = amountInfo;
	}
	/**
	 * @return the billerInfo
	 */
	public BillerJSONModel getBillerInfo() {
		return billerInfo;
	}
	/**
	 * @param billerInfo the billerInfo to set
	 */
	public void setBillerInfo(BillerJSONModel billerInfo) {
		this.billerInfo = billerInfo;
	}
	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}



}
