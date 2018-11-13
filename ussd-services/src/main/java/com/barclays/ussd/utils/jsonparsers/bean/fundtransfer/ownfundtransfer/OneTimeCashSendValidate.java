package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneTimeCashSendValidate {
    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private OneTimeCashSendValidatePayData payData;

    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    public OneTimeCashSendValidatePayData getPayData() {
	return payData;
    }

    public void setPayData(OneTimeCashSendValidatePayData payData) {
	this.payData = payData;
    }

}
