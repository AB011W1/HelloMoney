package com.barclays.ussd.utils.jsonparsers.bean.login;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoggerData {
    @JsonProperty
    private PayHdr payHdr;

    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

}
