package com.barclays.ussd.utils.jsonparsers.bean.fxrate;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FxRateDetails {

    @JsonProperty
    private PayHdr payHdr;
    /**
     * payData
     */
    @JsonProperty
    private FxRatePayData payData;

    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    public FxRatePayData getPayData() {
	return payData;
    }

    public void setPayData(FxRatePayData payData) {
	this.payData = payData;
    }

}
