package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneTimeCashSendSubmitPayData {
    @JsonProperty
    private String voucherId;

    public String getVoucherId() {
	return voucherId;
    }

    public void setVoucherId(String voucherId) {
	this.voucherId = voucherId;
    }

}
