/**
 * BillerData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerData {
	/**
	 * bilrNam
	 */
	@JsonProperty
	private String bilrNam;

	/**
	 * @param bilrNam the bilrNam to set
	 */
	public void setBilrNam(String bilrNam) {
		this.bilrNam = bilrNam;
	}

	/**
	 * @return the bilrNam
	 */
	public String getBilrNam() {
		return bilrNam;
	}

}
