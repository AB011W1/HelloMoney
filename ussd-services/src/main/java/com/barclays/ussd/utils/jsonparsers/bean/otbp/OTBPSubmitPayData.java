/**
 * FromAcntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.otbp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTBPSubmitPayData {

	@JsonProperty
	private String bemRefNo;

	@JsonProperty
	private String txnResRefNo;

	@JsonProperty
	private String txnRefNo;

	@JsonProperty
	private String resDtTm;

	@JsonProperty
	private String txnDtTm;

	/**
	 * @return the bemRefNo
	 */
	public String getBemRefNo() {
		return bemRefNo;
	}

	/**
	 * @param bemRefNo
	 *            the bemRefNo to set
	 */
	public void setBemRefNo(String bemRefNo) {
		this.bemRefNo = bemRefNo;
	}

	/**
	 * @return the txnResRefNo
	 */
	public String getTxnResRefNo() {
		return txnResRefNo;
	}

	/**
	 * @param txnResRefNo
	 *            the txnResRefNo to set
	 */
	public void setTxnResRefNo(String txnResRefNo) {
		this.txnResRefNo = txnResRefNo;
	}

	/**
	 * @return the txnRefNo
	 */
	public String getTxnRefNo() {
		return txnRefNo;
	}

	/**
	 * @param txnRefNo
	 *            the txnRefNo to set
	 */
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	/**
	 * @return the resDtTm
	 */
	public String getResDtTm() {
		return resDtTm;
	}

	/**
	 * @param resDtTm
	 *            the resDtTm to set
	 */
	public void setResDtTm(String resDtTm) {
		this.resDtTm = resDtTm;
	}

	/**
	 * @return the txnDtTm
	 */
	public String getTxnDtTm() {
		return txnDtTm;
	}

	/**
	 * @param txnDtTm
	 *            the txnDtTm to set
	 */
	public void setTxnDtTm(String txnDtTm) {
		this.txnDtTm = txnDtTm;
	}
}
