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
public class BarclaysExtraInstallmentOffers {
    /**
     * payHdr
     */
    @JsonProperty
    private PayHdr payHdr;
    /**
     * payData
     */
    @JsonProperty
    private BarcaysExtraInstallmentOffersLstData payData;

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

    public BarcaysExtraInstallmentOffersLstData getPayData() {
	return payData;
    }

    public void setPayData(BarcaysExtraInstallmentOffersLstData payData) {
	this.payData = payData;
    }

}
