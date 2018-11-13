package com.barclays.ussd.utils.jsonparsers.bean.selfreg;

/**
 *
 */

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfRegistrationInputResponse {

    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private PayData payData;

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

    /**
     * @return the payData
     */
    public PayData getPayData() {
	return payData;
    }

    /**
     * @param payData
     *            the payData to set
     */
    public void setPayData(PayData payData) {
	this.payData = payData;
    }

}
