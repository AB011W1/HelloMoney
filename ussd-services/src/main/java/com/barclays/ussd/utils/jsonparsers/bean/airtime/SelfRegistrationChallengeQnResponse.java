/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.airtime;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.login.SelfRegistrationChallengeQnPayData;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfRegistrationChallengeQnResponse {

    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private SelfRegistrationChallengeQnPayData payData;

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

    public void setPayData(SelfRegistrationChallengeQnPayData payData) {
	this.payData = payData;
    }

    public SelfRegistrationChallengeQnPayData getPayData() {
	return payData;
    }

}
