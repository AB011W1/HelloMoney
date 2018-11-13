/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author E20042299
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayReasonDetails {
    @JsonProperty
    private String payRsonKey;

    @JsonProperty
    private String payRsonVal;

    @JsonProperty
    private List<PayDetails> payDtlsLst;

    /**
     * @return the payRsonKey
     */
    public String getPayRsonKey() {
	return payRsonKey;
    }

    /**
     * @param payRsonKey
     *            the payRsonKey to set
     */
    public void setPayRsonKey(String payRsonKey) {
	this.payRsonKey = payRsonKey;
    }

    /**
     * @return the payRsonVal
     */
    public String getPayRsonVal() {
	return payRsonVal;
    }

    /**
     * @param payRsonVal
     *            the payRsonVal to set
     */
    public void setPayRsonVal(String payRsonVal) {
	this.payRsonVal = payRsonVal;
    }

    /**
     * @return the payDtlsLst
     */
    public List<PayDetails> getPayDtlsLst() {
	return payDtlsLst;
    }

    /**
     * @param payDtlsLst
     *            the payDtlsLst to set
     */
    public void setPayDtlsLst(List<PayDetails> payDtlsLst) {
	this.payDtlsLst = payDtlsLst;
    }

}
