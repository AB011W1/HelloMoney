/**
 * 
 */
package com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * @author E20040496
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChequeBookRequestJSON {
	
	@JsonProperty
	private ChequeBookRequestBeanJSON payData;
	
	@JsonProperty
	private PayHeader payHdr;

	/**
	 * @return the payData
	 */
	public ChequeBookRequestBeanJSON getPayData() {
		return payData;
	}

	/**
	 * @param payData the payData to set
	 */
	public void setPayData(ChequeBookRequestBeanJSON payData) {
		this.payData = payData;
	}

	/**
	 * @return the payHdr
	 */
	public PayHeader getPayHdr() {
		return payHdr;
	}

	/**
	 * @param payHdr the payHdr to set
	 */
	public void setPayHdr(PayHeader payHdr) {
		this.payHdr = payHdr;
	}
	
}
