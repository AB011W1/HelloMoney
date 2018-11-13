/**
 * CasaDetail.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.balanceEnq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceEnquriy {
	/**
	 * payHdr
	 */
	@JsonProperty
	private PayHdr payHdr;
	/**
	 * payData
	 */
	@JsonProperty
	private BalanceEnquiryDetailPayData payData;

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

	public BalanceEnquiryDetailPayData getPayData() {
		return payData;
	}

	public void setPayData(BalanceEnquiryDetailPayData payData) {
		this.payData = payData;
	}

}
