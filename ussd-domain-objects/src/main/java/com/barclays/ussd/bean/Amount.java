package com.barclays.ussd.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class Amount.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Amount {
	
	/** The amt. */
	@JsonProperty
	private String amt;

	/** The curr. */
	@JsonProperty
	private String curr;

	/**
     * Gets the curr.
     * 
     * @return the curr
     */
	public String getCurr() {
		return curr;
	}

	/**
     * Sets the curr.
     * 
     * @param curr
     *            the new curr
     */
	public void setCurr(String curr) {
		this.curr = curr;
	}

	/**
     * Gets the amt.
     * 
     * @return the amt
     */
	public String getAmt() {
		return amt;
	}

	/**
     * Sets the amt.
     * 
     * @param amt
     *            the new amt
     */
	public void setAmt(String amt) {
		this.amt = amt;
	}
}
