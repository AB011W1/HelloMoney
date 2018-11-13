package com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChequeBookTypeList {
	@JsonProperty
	private String key;
	
	@JsonProperty
	private String val;

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
