/**
 * 
 */
package com.barclays.ussd.utils.jsonparsers.bean.otbp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerInfo {

	@JsonProperty
	private String billerName;

	@JsonProperty
	private String billerType;

	@JsonProperty
	private String billerCode;

	@JsonProperty
	private String billerRefNo;

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

}
