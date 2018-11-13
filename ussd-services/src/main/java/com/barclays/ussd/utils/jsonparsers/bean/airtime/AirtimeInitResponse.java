/**
 * 
 */
package com.barclays.ussd.utils.jsonparsers.bean.airtime;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirtimeInitResponse {

	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private AirtimeInitPayData payData;

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

	/**
	 * @param payData the payData to set
	 */
	public void setPayData(AirtimeInitPayData payData) {
		this.payData = payData;
	}

	/**
	 * @return the payData
	 */
	public AirtimeInitPayData getPayData() {
		return payData;
	}
}
