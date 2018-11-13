package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.AddProspectPayData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestedProduct {
    /**
     * payHdr
     */
    @JsonProperty
    private PayHdr payHdr;
    /**
     * payData
     */
    @JsonProperty
    private AddProspectPayData payData;

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

    public AddProspectPayData getPayData() {
	return payData;
    }

    public void setPayData(AddProspectPayData payData) {
	this.payData = payData;
    }

}
