package com.barclays.ussd.utils.jsonparsers.bean.login;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUserData
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private AuthenticateUserPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public AuthenticateUserPayData getPayData() {
		return payData;
	}

	public void setPayData(AuthenticateUserPayData payData) {
		this.payData = payData;
	}

}
