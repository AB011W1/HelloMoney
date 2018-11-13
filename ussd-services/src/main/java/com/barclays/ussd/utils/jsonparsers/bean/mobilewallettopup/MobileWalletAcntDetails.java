package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileWalletAcntDetails {
	@JsonProperty
	private String actNo;
	@JsonProperty
	private String mkdActNo;

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

}
