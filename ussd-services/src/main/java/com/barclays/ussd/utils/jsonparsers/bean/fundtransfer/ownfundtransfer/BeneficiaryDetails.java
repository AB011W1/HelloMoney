package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryDetails
{
	@JsonProperty
	private String actNo;

	@JsonProperty
	private String txnTyp;

	@JsonProperty
	private String payId;

	@JsonProperty
	private String disLbl;

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getTxnTyp() {
		return txnTyp;
	}

	public void setTxnTyp(String txnTyp) {
		this.txnTyp = txnTyp;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getDisLbl() {
		return disLbl;
	}

	public void setDisLbl(String disLbl) {
		this.disLbl = disLbl;
	}



}
