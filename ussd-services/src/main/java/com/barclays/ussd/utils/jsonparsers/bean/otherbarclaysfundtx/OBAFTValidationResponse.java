package com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OBAFTValidationResponse
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private OBAFTValidatePayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public OBAFTValidatePayData getPayData() {
		return payData;
	}

	public void setPayData(OBAFTValidatePayData payData) {
		this.payData = payData;
	}


}
