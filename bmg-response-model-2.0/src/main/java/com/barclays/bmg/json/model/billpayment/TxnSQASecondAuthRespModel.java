package com.barclays.bmg.json.model.billpayment;

import com.barclays.bmg.json.response.BMBPayloadData;


public class TxnSQASecondAuthRespModel extends BMBPayloadData {

	/**
	 *
	 */
	private static final long serialVersionUID = -1777646001039493716L;
	private String sqaQues;
	private String txnRefNo;

	public String getSqaQues() {
		return sqaQues;
	}
	public void setSqaQues(String sqaQues) {
		this.sqaQues = sqaQues;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

}
