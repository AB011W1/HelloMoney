package com.barclays.bmg.json.model.chequebook;

import java.util.List;

import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class ChequeBookInitJsonModel extends BMBPayloadData{

	private static final long serialVersionUID = 7847960133821480667L;
	private List<CasaAccountJSONModel> srcLst;
	private List<ChequeBookSizeJsonModel> bkSizeLst;
	private List<ChequeBookTypeJsonModel> bkTypLst;

	public List<CasaAccountJSONModel> getSrcLst() {
		return srcLst;
	}
	public void setSrcLst(List<CasaAccountJSONModel> srcLst) {
		this.srcLst = srcLst;
	}
	public List<ChequeBookSizeJsonModel> getBkSizeLst() {
		return bkSizeLst;
	}
	public void setBkSizeLst(List<ChequeBookSizeJsonModel> bkSizeLst) {
		this.bkSizeLst = bkSizeLst;
	}
	public List<ChequeBookTypeJsonModel> getBkTypLst() {
		return bkTypLst;
	}
	public void setBkTypLst(List<ChequeBookTypeJsonModel> bkTypLst) {
		this.bkTypLst = bkTypLst;
	}

}
