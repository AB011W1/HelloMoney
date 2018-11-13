/**
 * VLPBBillerLstDet.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.vlpb;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VLPBBillerLstDet {

	/**
	 * transactionHistoryList
	 */
	@JsonProperty
	private List<TransHist> transactionHistoryList;

	/**
	 * @param transactionHistoryList the transactionHistoryList to set
	 */
	public void setTransactionHistoryList(List<TransHist> transactionHistoryList) {
		this.transactionHistoryList = transactionHistoryList;
	}

	/**
	 * @return the transactionHistoryList
	 */
	public List<TransHist> getTransactionHistoryList() {
		return transactionHistoryList;
	}

}
