package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneTimeCashSendSubmit {
    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private OneTimeCashSendSubmitPayData payData;

    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    public OneTimeCashSendSubmitPayData getPayData() {
	return payData;
    }

    public void setPayData(OneTimeCashSendSubmitPayData payData) {
	this.payData = payData;
    }

}
