/**
 *CasaDetailPayData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.balanceEnq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceEnquiryDetailPayData {

	/**
	 * actDetls
	 */
	@JsonProperty
	private BalanceEnquiryDetails actDetls;

	public BalanceEnquiryDetails getActDetls() {
		return actDetls;
	}

	public void setActDetls(BalanceEnquiryDetails actDetls) {
		this.actDetls = actDetls;
	}

}
