package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnFundTxValidate
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private OwnFundTxValidatePayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public OwnFundTxValidatePayData getPayData() {
		return payData;
	}

	public void setPayData(OwnFundTxValidatePayData payData) {
		this.payData = payData;
	}


}
