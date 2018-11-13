/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.selfreg;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.login.SelfRegistrationValidateData;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfRegistrationValidateResponse {

    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private SelfRegistrationValidateData payData;

    public PayHdr getPayHdr() {
	return payHdr;
    }

    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    public SelfRegistrationValidateData getPayData() {
	return payData;
    }

    public void setPayData(SelfRegistrationValidateData payData) {
	this.payData = payData;
    }

}
