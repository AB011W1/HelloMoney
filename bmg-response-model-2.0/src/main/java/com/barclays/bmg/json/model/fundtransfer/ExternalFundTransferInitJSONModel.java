package com.barclays.bmg.json.model.fundtransfer;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class ExternalFundTransferInitJSONModel extends BMBPayloadData {


	private static final long serialVersionUID = -1039227711679795800L;
	private List<CasaAccountJSONModel> srcLst;
	private List<CategorizedPayeeJSONModel> payLst = new ArrayList<CategorizedPayeeJSONModel>();
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

}
