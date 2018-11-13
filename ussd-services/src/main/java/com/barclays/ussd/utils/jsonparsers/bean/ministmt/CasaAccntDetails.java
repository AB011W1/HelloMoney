/**
 * CasaAccntDetails.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.ministmt;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.AvilableBalance;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.CurrentBalance;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasaAccntDetails {

	@JsonProperty
	private String mkdActNo;
	/**
	 * curr
	 */
	@JsonProperty
	private String curr;

	/* Added for the Balance Enquiry */
	@JsonProperty
	private String actNo;

	@JsonProperty
	private CurrentBalance curBal;

	@JsonProperty
	private AvilableBalance avblBal;

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getMkdActNo() {
		return mkdActNo;
	}

	public void setMkdActNo(String mkdActNo) {
		this.mkdActNo = mkdActNo;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public CurrentBalance getCurBal() {
		return curBal;
	}

	public void setCurBal(CurrentBalance curBal) {
		this.curBal = curBal;
	}

	public AvilableBalance getAvblBal() {
		return avblBal;
	}

	public void setAvblBal(AvilableBalance avblBal) {
		this.avblBal = avblBal;
	}

}
