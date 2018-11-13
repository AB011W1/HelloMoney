package com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OBAFTValidatePayData {

	@JsonProperty
	private String frActNo;

	@JsonProperty
	private String payId;

	@JsonProperty
	private String txnAmt;

	@JsonProperty
	private String txnRefNo;

	public String getFrActNo() {
		return frActNo;
	}

	public void setFrActNo(String frActNo) {
		this.frActNo = frActNo;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

}
