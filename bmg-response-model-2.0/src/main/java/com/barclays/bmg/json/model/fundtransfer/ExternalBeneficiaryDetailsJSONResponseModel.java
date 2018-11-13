package com.barclays.bmg.json.model.fundtransfer;

import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class ExternalBeneficiaryDetailsJSONResponseModel extends BMBPayloadData {

	private static final long serialVersionUID = 3956541105662272206L;
	private ExternalBeneficiaryJSONModel payInfo;
	private String txnRefNo;

	public ExternalBeneficiaryJSONModel getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(ExternalBeneficiaryJSONModel payInfo) {
		this.payInfo = payInfo;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

}
