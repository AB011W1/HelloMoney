package com.barclays.ussd.bmg.creditcard.unbilled.transaction;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardUnbilledTranData {
    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private CreditCardUnbilledTranPayData payData;

    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    public CreditCardUnbilledTranPayData getPayData() {
	return payData;
    }

    public void setPayData(CreditCardUnbilledTranPayData payData) {
	this.payData = payData;
    }
}
