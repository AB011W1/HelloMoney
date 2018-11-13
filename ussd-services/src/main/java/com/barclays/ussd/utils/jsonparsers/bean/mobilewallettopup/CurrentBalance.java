package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentBalance {

	/**
	 * curr
	 */
	@JsonProperty
	private String curr;

	/**
	 * amt
	 */
	@JsonProperty
	private String amt;

	/**
	 * @return the curr
	 */
	public String getCurr() {
		return curr;
	}

	/**
	 * @param curr
	 *            the curr to set
	 */
	public void setCurr(String curr) {
		this.curr = curr;
	}

	/**
	 * @return the amt
	 */
	public String getAmt() {
		return amt;
	}

	/**
	 * @param amt
	 *            the amt to set
	 */
	public void setAmt(String amt) {
		this.amt = amt;
	}

}
