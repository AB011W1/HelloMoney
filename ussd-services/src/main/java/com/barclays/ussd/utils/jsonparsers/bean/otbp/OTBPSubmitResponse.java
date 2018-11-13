/**
 * OTBPInitResponse
 */
package com.barclays.ussd.utils.jsonparsers.bean.otbp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTBPSubmitResponse {
	/**
	 * payHdr
	 */
	@JsonProperty
	private PayHdr payHdr;
	/**
	 * payData
	 */
	@JsonProperty
	private OTBPSubmitPayData payData;

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
	public OTBPSubmitPayData getPayData() {
		return payData;
	}

	/**
	 * @param payData
	 *            the payData to set
	 */
	public void setPayData(OTBPSubmitPayData payData) {
		this.payData = payData;
	}

}
