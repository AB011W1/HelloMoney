package com.barclays.ussd.bmg.creditcard.statement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardStmtData {
    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private CreditCardStmtPayData payData;

    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    public CreditCardStmtPayData getPayData() {
	return payData;
    }

    public void setPayData(CreditCardStmtPayData payData) {
	this.payData = payData;
    }

}
