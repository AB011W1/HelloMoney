package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveAccList
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private AccListPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}
	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}
	public AccListPayData getPayData() {
		return payData;
	}
	public void setPayData(AccListPayData payData) {
		this.payData = payData;
	}
}
