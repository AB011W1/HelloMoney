package com.barclays.ussd.utils.jsonparsers.bean.login;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVerifyData
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private VerifyUserPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public VerifyUserPayData getPayData() {
		return payData;
	}

	public void setPayData(VerifyUserPayData payData) {
		this.payData = payData;
	}
}
