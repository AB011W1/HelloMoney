/**
 * AccountData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.otbp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTBPValidateAccount {

	/**
	 * actNo
	 */
	@JsonProperty
	private String actNo;

	/**
	 * mkdActNo
	 */
	@JsonProperty
	private String mkdActNo;

	/**
	 * curr
	 */
	@JsonProperty
	private String curr;

	/**
	 * typ
	 */
	@JsonProperty
	private String typ;

	/**
	 * avlbBal
	 */
	@JsonProperty
	private AvailBal avlbBal;

	/**
	 * @return the actNo
	 */
	public String getActNo() {
		return actNo;
	}

	/**
	 * @param actNo
	 *            the actNo to set
	 */
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	/**
	 * @return the mkdActNo
	 */
	public String getMkdActNo() {
		return mkdActNo;
	}

	/**
	 * @param mkdActNo
	 *            the mkdActNo to set
	 */
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
	 * @param curr
	 *            the curr to set
	 */
	public void setCurr(String curr) {
		this.curr = curr;
	}

	/**
	 * @return the typ
	 */
	public String getTyp() {
		return typ;
	}

	/**
	 * @param typ
	 *            the typ to set
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}

	/**
	 * @return the avlbBal
	 */
	public AvailBal getAvlbBal() {
		return avlbBal;
	}

	/**
	 * @param avlbBal
	 *            the avlbBal to set
	 */
	public void setAvlbBal(AvailBal avlbBal) {
		this.avlbBal = avlbBal;
	}

}
