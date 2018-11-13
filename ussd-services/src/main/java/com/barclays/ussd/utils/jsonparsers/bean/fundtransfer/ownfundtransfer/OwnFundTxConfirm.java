package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnFundTxConfirm
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private OwnFundTxConfirmPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public OwnFundTxConfirmPayData getPayData() {
		return payData;
	}

	public void setPayData(OwnFundTxConfirmPayData payData) {
		this.payData = payData;
	}



}
