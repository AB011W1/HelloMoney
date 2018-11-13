package com.barclays.bmg.json.model.fundtransfer.nonregistered.internal;

import java.util.List;

import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class InternalNonRegisteredFundTransferInitJSONModel extends BMBPayloadData {


	private static final long serialVersionUID = 3579232640710622846L;
	private List<CasaAccountJSONModel> srcLst;

	public List<CasaAccountJSONModel> getSrcLst() {
		return srcLst;
	}
	public void setSrcLst(List<CasaAccountJSONModel> srcLst) {
		this.srcLst = srcLst;
	}


}
