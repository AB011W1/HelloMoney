/**
 * OTBnkFTExecData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTBnkFTExecData {
	/**
	 * txnResRefNo
	 */
	@JsonProperty
	private String txnResRefNo;

	/**
	 * @param txnResRefNo the txnResRefNo to set
	 */
	public void setTxnResRefNo(String txnResRefNo) {
		this.txnResRefNo = txnResRefNo;
	}

	/**
	 * @return the txnResRefNo
	 */
	public String getTxnResRefNo() {
		return txnResRefNo;
	}

}
