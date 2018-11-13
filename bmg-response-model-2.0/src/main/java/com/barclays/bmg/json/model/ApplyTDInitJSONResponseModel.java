package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;

public class ApplyTDInitJSONResponseModel extends BMBPayloadData implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5400115824684843258L;
 	public List<CasaAccountJSONModel> getSrcLst() {
		return srcLst;
	}
	private List<CasaAccountJSONModel> srcLst;
	public void setSrcLst(List<CasaAccountJSONModel> srcLst) {
		this.srcLst = srcLst;
	}
}
