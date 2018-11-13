package com.barclays.bmg.json.model.fundtransfer;

import com.barclays.bmg.json.model.billpayment.BankDraftBeneficiaryJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class BankDraftDetailsJSONResponseModel extends BMBPayloadData{


	private static final long serialVersionUID = 7408757023550473414L;
	private BankDraftBeneficiaryJSONModel payInfo;

	public BankDraftBeneficiaryJSONModel getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(BankDraftBeneficiaryJSONModel payInfo) {
		this.payInfo = payInfo;
	}

}
