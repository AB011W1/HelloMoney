/**
 * 
 */
package com.barclays.ussd.utils.jsonparsers.bean.airtime;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
public class Amount {

	@JsonProperty
	private String amt;

	@JsonProperty
	private String curr;

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
}
