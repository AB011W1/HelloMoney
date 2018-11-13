package com.barclays.bmg.json.model;

import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;

public class CashSendOneTimeJsonModel extends BMBPayloadData{

	private static final long serialVersionUID = 4269573888549715072L;
	private List<CasaAccountJSONModel> srcLst;

	public List<CasaAccountJSONModel> getSrcLst() {
		return srcLst;
	}
	public void setSrcLst(List<CasaAccountJSONModel> srcLst) {
		this.srcLst = srcLst;
	}


}
