/**
 * VLPBBillDetData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.vlpb;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VLPBBillDetData {
	/**
	 * transactionHistory
	 */
	@JsonProperty
	private TransHist transactionHistory;

	/**
	 * @param transactionHistory the transactionHistory to set
	 */
	public void setTransactionHistory(TransHist transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	/**
	 * @return the transactionHistory
	 */
	public TransHist getTransactionHistory() {
		return transactionHistory;
	}

}
