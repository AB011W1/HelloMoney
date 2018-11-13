package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileWalletTxConfirmPayData {
	@JsonProperty
	private String bemRefNo;

	@JsonProperty
	private String txnResRefNo;

	@JsonProperty
	private String txnRefNo;

	@JsonProperty
	private String resDtTm;

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

}
