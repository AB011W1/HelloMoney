package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.io.Serializable;

import com.barclays.bmg.json.model.billpayment.AddOrgeneficiaryJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class AddOrgBeneficiaryExecContrJSONModel extends BMBPayloadData
		implements Serializable {

	private AddOrgeneficiaryJSONModel billerInfo;
	private String txnRefNo;
	private String txnDate;
	public AddOrgeneficiaryJSONModel getBillerInfo() {
		return billerInfo;
	}
	public void setBillerInfo(AddOrgeneficiaryJSONModel billerInfo) {
		this.billerInfo = billerInfo;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
}
