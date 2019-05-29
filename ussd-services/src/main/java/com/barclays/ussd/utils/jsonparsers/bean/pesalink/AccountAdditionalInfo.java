package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountAdditionalInfo {
	@JsonProperty
	AcctAdditionalInf accountAdditionalInfo;

	public AcctAdditionalInf getAccountAdditionalInfo() {
		return accountAdditionalInfo;
	}

	public void setAccountAdditionalInfo(AcctAdditionalInf accountAdditionalInfo) {
		this.accountAdditionalInfo = accountAdditionalInfo;
	}
}
