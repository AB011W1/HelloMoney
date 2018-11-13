package com.barclays.bmg.json.model;

import java.io.Serializable;

public class AmountJSONModel implements Serializable {

	private static final long serialVersionUID = -3009216108825661438L;


	String curr;
	String amt;

	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}

}
