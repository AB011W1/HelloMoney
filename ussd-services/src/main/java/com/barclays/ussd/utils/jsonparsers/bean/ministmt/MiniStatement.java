/**
 * CasaDetail.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.ministmt;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiniStatement {
    /**
     * payHdr
     */
    @JsonProperty
    private PayHdr payHdr;
    /**
     * payData
     */
    @JsonProperty
    private MiniStatmentPayData payData;

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
    public MiniStatmentPayData getPayData() {
	return payData;
    }

    /**
     * @param payData
     *            the payData to set
     */
    public void setPayData(MiniStatmentPayData payData) {
	this.payData = payData;
    }

}
