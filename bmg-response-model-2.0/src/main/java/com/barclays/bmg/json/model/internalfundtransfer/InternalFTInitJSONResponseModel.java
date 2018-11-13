package com.barclays.bmg.json.model.internalfundtransfer;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.fundtransfer.CategorizedPayeeJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class InternalFTInitJSONResponseModel extends BMBPayloadData {

	/**
	 *
	 */
	private static final long serialVersionUID = -2596119926398689784L;
	private List<CasaAccountJSONModel> srcLst;
	private List<CategorizedPayeeJSONModel> payLst = new ArrayList<CategorizedPayeeJSONModel>();
	private AmountJSONModel txnLmt;

	public List<CasaAccountJSONModel> getSrcLst() {
		return srcLst;
	}

	public void setSrcLst(List<CasaAccountJSONModel> srcLst) {
		this.srcLst = srcLst;
	}




	public List<CategorizedPayeeJSONModel> getPayLst() {
		return payLst;
	}

	public void setPayLst(List<CategorizedPayeeJSONModel> payLst) {
		this.payLst = payLst;
	}

	public void addCategorizedPayeeBean(CategorizedPayeeJSONModel categorizedPayeeBean)
	{
		payLst.add(categorizedPayeeBean);
	}

	public AmountJSONModel getTxnLmt() {
		return txnLmt;
	}

	public void setTxnLmt(AmountJSONModel txnLmt) {
		this.txnLmt = txnLmt;
	}

}
