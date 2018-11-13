package com.barclays.bmg.json.model.fundtransfer;

import java.util.List;

import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class InternalFTDetailsJSONResponseModel extends BMBPayloadData {
	private static final long serialVersionUID = -2292475610229894622L;
	private String payId;
	private String beneAcct;
	private String beneName;
	private String Curr;
	private String beneBrnCde;
	private List<ListValueCacheDTO> currLst;

	public String getCurr() {
		return Curr;
	}

	public void setCurr(String curr) {
		Curr = curr;
	}

	public String getBeneAcct() {
		return beneAcct;
	}

	public void setBeneAcct(String beneAcct) {
		this.beneAcct = beneAcct;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getBeneBrnCde() {
		return beneBrnCde;
	}

	public void setBeneBrnCde(String beneBrnCde) {
		this.beneBrnCde = beneBrnCde;
	}

	public List<ListValueCacheDTO> getCurrLst() {
		return currLst;
	}

	public void setCurrLst(List<ListValueCacheDTO> currLst) {
		this.currLst = currLst;
	}

}
