package com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateRegBenfIntPayData {

	@JsonProperty
	private String txnRefNo;

	@JsonProperty
	private BeneficiaryInfo beneficiaryInfo;

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public BeneficiaryInfo getBeneficiaryInfo() {
		return beneficiaryInfo;
	}

	public void setBeneficiaryInfo(BeneficiaryInfo beneficiaryInfo) {
		this.beneficiaryInfo = beneficiaryInfo;
	}


}
