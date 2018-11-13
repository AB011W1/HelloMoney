package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class FxRateJSONBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3985612521934818600L;

	private String effFxRate;
	private String eqAmt;

	public String getEffFxRate() {
		return effFxRate;
	}
	public void setEffFxRate(String effFxRate) {
		this.effFxRate = effFxRate;
	}
	public String getEqAmt() {
		return eqAmt;
	}
	public void setEqAmt(String eqAmt) {
		this.eqAmt = eqAmt;
	}
}
