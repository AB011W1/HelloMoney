/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author E20042299
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayDetails {
    @JsonProperty
    private String payDtlsKey;

    @JsonProperty
    private String payDtlsVal;

    /**
     * @return the payDtlsKey
     */
    public String getPayDtlsKey() {
	return payDtlsKey;
    }

    /**
     * @param payDtlsKey
     *            the payDtlsKey to set
     */
    public void setPayDtlsKey(String payDtlsKey) {
	this.payDtlsKey = payDtlsKey;
    }

    /**
     * @return the payDtlsVal
     */
    public String getPayDtlsVal() {
	return payDtlsVal;
    }

    /**
     * @param payDtlsVal
     *            the payDtlsVal to set
     */
    public void setPayDtlsVal(String payDtlsVal) {
	this.payDtlsVal = payDtlsVal;
    }

}
