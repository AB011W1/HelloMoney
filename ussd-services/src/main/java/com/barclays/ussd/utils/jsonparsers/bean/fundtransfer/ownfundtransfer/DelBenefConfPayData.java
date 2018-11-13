/**
 * DelBenefConfPayData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DelBenefConfPayData {
	/**
	 * txnRefNo
	 */
	@JsonProperty
	private String txnRefNo;

	/**
	 * @param txnRefNo the txnRefNo to set
	 */
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	/**
	 * @return the txnRefNo
	 */
	public String getTxnRefNo() {
		return txnRefNo;
	}

}
