package com.barclays.bmg.json.model.accounts;

import java.io.Serializable;

public class EmailMessageJSONModel implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 5236520208533672274L;
	private String sub = "";
	private String msg = "";

	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
