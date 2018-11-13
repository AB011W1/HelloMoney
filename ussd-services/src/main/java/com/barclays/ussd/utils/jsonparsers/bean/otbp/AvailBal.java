/**
 * AvailBal.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.otbp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailBal {

	/**
	 * amt
	 */
	@JsonProperty
	private String amt;

	/**
	 * curr
	 */
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
