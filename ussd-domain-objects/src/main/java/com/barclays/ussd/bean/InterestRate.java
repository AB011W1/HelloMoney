/**
 * 
 */
package com.barclays.ussd.bean;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class InterestRate.
 * 
 * @author BTCI
 */
public class InterestRate {

	/** The from. */
	private Amount from;

	/** The to. */
	private Amount to;

	/** The tenure. */
	private List<Tenure> tenure;

	/**
     * Gets the from.
     * 
     * @return the from
     */
	public Amount getFrom() {
		return from;
	}

	/**
     * Sets the from.
     * 
     * @param from
     *            the from to set
     */
	public void setFrom(Amount from) {
		this.from = from;
	}

	/**
     * Gets the to.
     * 
     * @return the to
     */
	public Amount getTo() {
		return to;
	}

	/**
     * Sets the to.
     * 
     * @param to
     *            the to to set
     */
	public void setTo(Amount to) {
		this.to = to;
	}

	/**
     * Gets the tenure.
     * 
     * @return the tenure
     */
	public List<Tenure> getTenure() {
		return tenure;
	}

	/**
     * Sets the tenure.
     * 
     * @param tenure
     *            the tenure to set
     */
	public void setTenure(List<Tenure> tenure) {
		this.tenure = tenure;
	}

}
