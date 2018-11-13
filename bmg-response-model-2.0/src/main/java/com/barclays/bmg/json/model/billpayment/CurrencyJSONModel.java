package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class CurrencyJSONModel implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -2037554492450032852L;
	private String curr;

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public CurrencyJSONModel(String currency){
		this.curr = currency;
	}
}
