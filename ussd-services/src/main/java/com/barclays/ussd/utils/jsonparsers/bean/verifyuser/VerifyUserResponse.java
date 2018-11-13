package com.barclays.ussd.utils.jsonparsers.bean.verifyuser;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyUserResponse {
    /**
     * payHdr
     */
    @JsonProperty
    private PayHdr payHdr;
    /**
     * payData
     */
    @JsonProperty
    private VerifyUserPayData payData;

    /**
     * @return the payHdr
     */
    public PayHdr getPayHdr() {
	return payHdr;
    }

    /**
     * @param payHdr
     *            the payHdr to set
     */
    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    public VerifyUserPayData getPayData() {
	return payData;
    }

    public void setPayData(VerifyUserPayData payData) {
	this.payData = payData;
    }

}
