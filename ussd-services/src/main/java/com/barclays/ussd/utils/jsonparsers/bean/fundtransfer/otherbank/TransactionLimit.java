/**
 * PayeeInfo.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionLimit {
	/**
	 * payHdr
	 */
	@JsonProperty
	private String curr;
	/**
	 * payData
	 */
	@JsonProperty
	private String amt;
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}


}
