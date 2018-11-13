/**
 * 
 */
package com.barclays.ussd.utils.jsonparsers.bean.fdrates;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestRate {

	@JsonProperty
	private Amount from;

	@JsonProperty
	private Amount to;

	@JsonProperty
	private List<Tenure> tenure;

	/**
	 * @return the from
	 */
	public Amount getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(Amount from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Amount getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(Amount to) {
		this.to = to;
	}

	/**
	 * @return the tenure
	 */
	public List<Tenure> getTenure() {
		return tenure;
	}

	/**
	 * @param tenure
	 *            the tenure to set
	 */
	public void setTenure(List<Tenure> tenure) {
		this.tenure = tenure;
	}

}
