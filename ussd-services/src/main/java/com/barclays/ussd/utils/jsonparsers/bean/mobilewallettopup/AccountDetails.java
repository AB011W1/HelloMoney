package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails
{
	@JsonProperty
	private String actNo;

	@JsonProperty
	private String mkdActNo;
	/**
	 * curr
	 */
	@JsonProperty
	private String curr;
	/**
	 * curBal
	 */
	@JsonProperty
	private CurrBal curBal;
	/**
	 * avblBal
	 */
	@JsonProperty
	private AvailableBal avblBal;

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
	/**
	 * @return the curr
	 */
	public String getCurr() {
		return curr;
	}
	/**
	 * @param curr the curr to set
	 */
	public void setCurr(String curr) {
		this.curr = curr;
	}
	/**
	 * @return the curBal
	 */
	public CurrBal getCurBal() {
		return curBal;
	}
	/**
	 * @param curBal the curBal to set
	 */
	public void setCurBal(CurrBal curBal) {
		this.curBal = curBal;
	}
	/**
	 * @param avblBal the avblBal to set
	 */
	public void setAvblBal(AvailableBal avblBal) {
		this.avblBal = avblBal;
	}
	/**
	 * @return the avblBal
	 */
	public AvailableBal getAvblBal() {
		return avblBal;
	}





}
