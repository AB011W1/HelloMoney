package com.barclays.bmg.json.model.billpayment;

import java.util.List;

import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class StatmentInitJsonModel extends BMBPayloadData{

	private List<CasaAccountJSONModel> srcLst;

	/**
	 * @return the srcLst
	 */
	public List<CasaAccountJSONModel> getSrcLst() {
		return srcLst;
	}

	/**
	 * @param srcLst the srcLst to set
	 */
	public void setSrcLst(List<CasaAccountJSONModel> srcLst) {
		this.srcLst = srcLst;
	}

}
