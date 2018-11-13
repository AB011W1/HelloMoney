package com.barclays.ussd.utils.jsonparsers.bean.regbiller;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateRegBillerBean
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private ValidateRegBillerPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}
	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}
	public ValidateRegBillerPayData getPayData() {
		return payData;
	}
	public void setPayData(ValidateRegBillerPayData payData) {
		this.payData = payData;
	}

}
