/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.selfreg;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.login.RetrieveindividualCustCardListPayData;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveindividualCustCardListResponse {

    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private RetrieveindividualCustCardListPayData payData;

    /**
     * @param payHdr
     *            the payHdr to set
     */
    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    /**
     * @return the payHdr
     */
    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayData(RetrieveindividualCustCardListPayData payData) {
	this.payData = payData;
    }

    public RetrieveindividualCustCardListPayData getPayData() {
	return payData;
    }

}
