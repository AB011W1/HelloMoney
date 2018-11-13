package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CpbPesaLinkValidate {
    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private CpbPesaLinkValidatePayData payData;

    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    public CpbPesaLinkValidatePayData getPayData() {
	return payData;
    }

    public void setPayData(CpbPesaLinkValidatePayData payData) {
	this.payData = payData;
    }

}
