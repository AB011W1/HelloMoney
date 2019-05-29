package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NonPersonalAccountDetails {
	@JsonProperty
    private PayHdr payHdr;

	@JsonProperty
	NonPersonalAccountDetailsPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public NonPersonalAccountDetailsPayData getPayData() {
		return payData;
	}

	public void setPayData(NonPersonalAccountDetailsPayData payData) {
		this.payData = payData;
	}
}
