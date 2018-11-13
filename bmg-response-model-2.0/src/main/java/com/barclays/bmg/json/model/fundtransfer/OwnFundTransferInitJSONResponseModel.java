package com.barclays.bmg.json.model.fundtransfer;

import java.util.List;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class OwnFundTransferInitJSONResponseModel extends BMBPayloadData {


	private static final long serialVersionUID = -5244024001242313808L;

	private List<CasaAccountJSONModel> payLst;
//	private String minDate = "";
	private AmountJSONModel txnLmt;
	private List<CasaAccountJSONModel> srcLst;


	public OwnFundTransferInitJSONResponseModel(
			List<CasaAccountJSONModel> payeeList) {
		super();
		this.payLst = payeeList;

	}

	public OwnFundTransferInitJSONResponseModel() {
		super();
	}

	public List<CasaAccountJSONModel> getPayLst() {
		return payLst;
	}

	public void setPayLst(List<CasaAccountJSONModel> payLst) {
		this.payLst = payLst;
	}


	public AmountJSONModel getTxnLmt() {
		return txnLmt;
	}

	public void setTxnLmt(AmountJSONModel txnLmt) {
		this.txnLmt = txnLmt;
	}

	public List<CasaAccountJSONModel> getSrcLst() {
		return srcLst;
	}

	public void setSrcLst(List<CasaAccountJSONModel> srcLst) {
		this.srcLst = srcLst;
	}



}
