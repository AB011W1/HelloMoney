package com.barclays.ussd.utils.jsonparsers.bean.changepin;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePinBean
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private ChangePinPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public ChangePinPayData getPayData() {
		return payData;
	}

	public void setPayData(ChangePinPayData payData) {
		this.payData = payData;
	}


}
