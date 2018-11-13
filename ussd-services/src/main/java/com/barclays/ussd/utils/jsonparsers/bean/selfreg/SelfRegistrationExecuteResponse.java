/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.selfreg;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.login.SelfRegistrationExecuteData;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfRegistrationExecuteResponse {

	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private SelfRegistrationExecuteData payData;

	/**
	 * @param payHdr the payHdr to set
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

	public void setPayData(SelfRegistrationExecuteData payData) {
		this.payData = payData;
	}

	public SelfRegistrationExecuteData getPayData() {
		return payData;
	}

}
