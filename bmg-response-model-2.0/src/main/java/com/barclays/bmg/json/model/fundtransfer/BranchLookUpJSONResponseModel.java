package com.barclays.bmg.json.model.fundtransfer;

import java.util.List;

import com.barclays.bmg.json.model.lookup.BranchJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class BranchLookUpJSONResponseModel extends BMBPayloadData {


	private static final long serialVersionUID = -5632402377130936467L;

	private boolean disLst = true;

	private List<BranchJSONModel> brnLst;

	public boolean isDisLst() {
		return disLst;
	}

	public void setDisLst(boolean disLst) {
		this.disLst = disLst;
	}

	public List<BranchJSONModel> getBrnLst() {
		return brnLst;
	}

	public void setBrnLst(List<BranchJSONModel> brnLst) {
		this.brnLst = brnLst;
	}



}
