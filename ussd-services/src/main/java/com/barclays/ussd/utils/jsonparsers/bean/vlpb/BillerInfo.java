/**
 * BillerInfo.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.vlpb;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerInfo {
	/**
	 * billerName
	 */
	@JsonProperty
	private String billerName;
	/**
	 * billerRefNo
	 */
	@JsonProperty
	private String billerRefNo;

	@JsonProperty
	private String billerId;

	@JsonProperty
	private Account fromAccountInfo;

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public Account getFromAccountInfo() {
		return fromAccountInfo;
	}

	public void setFromAccountInfo(Account fromAccountInfo) {
		this.fromAccountInfo = fromAccountInfo;
	}

	/**
	 * @param billerName the billerName to set
	 */
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	/**
	 * @return the billerName
	 */
	public String getBillerName() {
		return billerName;
	}

	/**
	 * @param billerRefNo the billerRefNo to set
	 */
	public void setBillerRefNo(String billerRefNo) {
		this.billerRefNo = billerRefNo;
	}

	/**
	 * @return the billerRefNo
	 */
	public String getBillerRefNo() {
		return billerRefNo;
	}

}
