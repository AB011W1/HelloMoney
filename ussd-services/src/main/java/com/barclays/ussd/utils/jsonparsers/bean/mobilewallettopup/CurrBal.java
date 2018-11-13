/**
 * CurrBal.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrBal {
	/**
	 * amt
	 */
	@JsonProperty
	private String amt;

	/**
	 * @param amt the amt to set
	 */
	public void setAmt(String amt) {
		this.amt = amt;
	}

	/**
	 * @return the amt
	 */
	public String getAmt() {
		return amt;
	}

}
