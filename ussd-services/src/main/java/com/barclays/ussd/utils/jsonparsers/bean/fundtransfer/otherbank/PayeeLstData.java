/**
 * PayeeLstData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayeeLstData {
	/**
	 * payLst
	 */
	@JsonProperty
	private List<ToList> payLst;
	/**
	 * srcLst
	 */
	@JsonProperty
	private List<AccountDetails> srcLst;
	/**
	 * @return the payLst
	 */
	public List<ToList> getPayLst() {
		return payLst;
	}
	/**
	 * @param payLst the payLst to set
	 */
	public void setPayLst(List<ToList> payLst) {
		this.payLst = payLst;
	}
	/**
	 * @param srcLst the srcLst to set
	 */
	public void setSrcLst(List<AccountDetails> srcLst) {
		this.srcLst = srcLst;
	}
	/**
	 * @return the srcLst
	 */
	public List<AccountDetails> getSrcLst() {
		return srcLst;
	}




}
