/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.request.statementReq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.PayHeader;


/**
 * @author E20040496
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateStatementRequestJSON {

	@JsonProperty
	private ValidateStatementReqPayData payData;

	@JsonProperty
	private PayHeader payHdr;


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

	public ValidateStatementReqPayData getPayData() {
		return payData;
	}

	public void setPayData(ValidateStatementReqPayData payData) {
		this.payData = payData;
	}

}
