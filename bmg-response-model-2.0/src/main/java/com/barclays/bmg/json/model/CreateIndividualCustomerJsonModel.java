package com.barclays.bmg.json.model;

import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.operation.response.pesalink.IndividualCustomerBasicOpRes;
import com.barclays.bmg.operation.response.pesalink.SearchIndividualCustforDeDupCheckOperationResponse;


public class CreateIndividualCustomerJsonModel extends BMBPayloadData{

	private String txnRefNo;

	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
}