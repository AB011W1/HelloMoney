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
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * @author BTCI
 *
 */
public class TransactionHistoryJSONBean extends BMBPayloadData implements
Serializable  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<TransactionHistoryJSONModel> transactionHistoryList=new ArrayList<TransactionHistoryJSONModel>();
	private TransactionHistoryJSONModel transactionHistory;

	/**
	 * @return TransactionHistoryJSONModel List
	 */
	public List<TransactionHistoryJSONModel> getTransactionHistoryList() {
		return transactionHistoryList;
	}

	/**
	 * @param transactionHistoryJSONModel
	 */
	public void addToTransactionHistoryJSONModelList(TransactionHistoryJSONModel transactionHistoryJSONModel) {
		transactionHistoryList.add(transactionHistoryJSONModel);
	}

	/**
	 * @return TransactionHistoryJSONModel
	 */
	public TransactionHistoryJSONModel getTransactionHistory() {
		return transactionHistory;
	}

	/**
	 * @param transactionHistory
	 */
	public void setTransactionHistory(TransactionHistoryJSONModel transactionHistory) {
		this.transactionHistory = transactionHistory;
	}




}
