package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionActivityDetails {
	@JsonProperty
    private PayHdr payHdr;

	@JsonProperty
	TransactionActivityDetailsPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public TransactionActivityDetailsPayData getPayData() {
		return payData;
	}

	public void setPayData(TransactionActivityDetailsPayData payData) {
		this.payData = payData;
	}
}
