/**
 * BillPayConfirm.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BarclaysDiningOffers {
    /**
     * payHdr
     */
    @JsonProperty
    private PayHdr payHdr;
    /**
     * payData
     */
    @JsonProperty
    private BarcaysDiningOffersLstData payData;

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

    public BarcaysDiningOffersLstData getPayData() {
	return payData;
    }

    /**
     * @param payData
     *            the payData to set
     */

    public void setPayData(BarcaysDiningOffersLstData payData) {
	this.payData = payData;
    }

}
