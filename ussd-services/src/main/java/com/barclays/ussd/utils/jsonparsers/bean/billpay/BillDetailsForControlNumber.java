/**
 * BillPayFrmAccntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillDetailsForControlNumber {
	/**
	 * payHdr
	 */
	@JsonProperty
	private PayHdr payHdr;
	/**
	 * payData
	 */
	@JsonProperty
	private BillDetails payData;
	/**
	 * @return the payHdr
	 */
	public PayHdr getPayHdr() {
		return payHdr;
	}
	/**
	 * @param payHdr the payHdr to set
	 */
	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}
	/**
	 * @return the payData
	 */
	public BillDetails getPayData() {
		return payData;
	}
	/**
	 * @param payData the payData to set
	 */
	public void setPayData(BillDetails payData) {
		this.payData = payData;
	}

}
