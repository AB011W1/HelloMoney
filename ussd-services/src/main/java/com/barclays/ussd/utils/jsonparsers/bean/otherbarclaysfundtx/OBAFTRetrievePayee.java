package com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OBAFTRetrievePayee
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private OBAFTInitPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public OBAFTInitPayData getPayData() {
		return payData;
	}

	public void setPayData(OBAFTInitPayData payData) {
		this.payData = payData;
	}
}
