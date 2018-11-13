package com.barclays.bmg.json.model.chequebook;

import com.barclays.bmg.json.response.BMBPayloadData;

public class ChequeBookTypeJsonModel extends BMBPayloadData{


	private static final long serialVersionUID = -7095848653641668753L;
	String key;
	String val;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}

}
