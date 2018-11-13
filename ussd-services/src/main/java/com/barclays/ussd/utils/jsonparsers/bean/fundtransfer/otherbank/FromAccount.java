/**
 *FromAccount.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FromAccount {

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
	 * @return the mkdActNo
	 */
	public String getMkdActNo() {
		return mkdActNo;
	}
	/**
	 * @param mkdActNo the mkdActNo to set
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
	 * @param curr the curr to set
	 */
	public void setCurr(String curr) {
		this.curr = curr;
	}


}
