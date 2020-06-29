package com.barclays.ussd.bmg.creditcard.statement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardActivityData {
	
	@JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private CreditCardStatement payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public CreditCardStatement getPayData() {
		return payData;
	}

	public void setPayData(CreditCardStatement payData) {
		this.payData = payData;
	}

}