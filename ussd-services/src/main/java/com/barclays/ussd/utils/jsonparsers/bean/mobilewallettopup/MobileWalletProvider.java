package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileWalletProvider {

	/**
	 * billerCode
	 */
	@JsonProperty
	private String billerCode;
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
	/**
	 * billerType
	 */
	@JsonProperty
	private String billerType;
	/**
	 * billerId
	 */
	@JsonProperty
	private String billerId;

	/**
	 * @return the billerCode
	 */
	public String getBillerCode() {
		return billerCode;
	}

	/**
	 * @param billerCode
	 *            the billerCode to set
	 */
	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	/**
	 * @return the billerName
	 */
	public String getBillerName() {
		return billerName;
	}

	/**
	 * @param billerName
	 *            the billerName to set
	 */
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	/**
	 * @return the billerRefNo
	 */
	public String getBillerRefNo() {
		return billerRefNo;
	}

	/**
	 * @param billerRefNo
	 *            the billerRefNo to set
	 */
	public void setBillerRefNo(String billerRefNo) {
		this.billerRefNo = billerRefNo;
	}

	/**
	 * @return the billerType
	 */
	public String getBillerType() {
		return billerType;
	}

	/**
	 * @param billerType
	 *            the billerType to set
	 */
	public void setBillerType(String billerType) {
		this.billerType = billerType;
	}

	/**
	 * @param billerId the billerId to set
	 */
	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	/**
	 * @return the billerId
	 */
	public String getBillerId() {
		return billerId;
	}
}
