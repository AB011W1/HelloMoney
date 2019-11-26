package com.barclays.ussd.bmg.auth.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequestDetails {

	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private AuthRequestDetailsPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public AuthRequestDetailsPayData getPayData() {
		return payData;
	}

	public void setPayData(AuthRequestDetailsPayData payData) {
		this.payData = payData;
	}

}
