package com.barclays.ussd.bmg.creditcard.link;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardLinkData {
    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private CreditCardLinkPayData payData;

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayData(CreditCardLinkPayData payData) {
		this.payData = payData;
	}

	public CreditCardLinkPayData getPayData() {
		return payData;
	}


}
