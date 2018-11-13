package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class ChargeDescJSONModel implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -7524351354359247449L;
	private String key;
	private String desc;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
