package com.barclays.bmg.json.model.fundtransfer;

import java.util.List;

import com.barclays.bmg.json.model.lookup.ATMVOJSONModel;
import com.barclays.bmg.json.model.lookup.BranchVOJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class AtmBranchJSONResponseModel extends BMBPayloadData {


	private static final long serialVersionUID = -7702876313258129276L;
	private List<BranchVOJSONModel> brnLst;
    private List<ATMVOJSONModel> atmLst;

    public List<BranchVOJSONModel> getBrnLst() {
	return brnLst;
    }

    public void setBrnLst(List<BranchVOJSONModel> brnLst) {
	this.brnLst = brnLst;
    }

    public List<ATMVOJSONModel> getAtmLst() {
	return atmLst;
    }

    public void setAtmLst(List<ATMVOJSONModel> atmLst) {
	this.atmLst = atmLst;
    }

}
