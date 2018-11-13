/**
 * DelBlrsValData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.delbillers;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DelBlrsValData {
	@JsonProperty
	private Payee pay;

	/**
	 * @param pay the pay to set
	 */
	public void setPay(Payee pay) {
		this.pay = pay;
	}

	/**
	 * @return the pay
	 */
	public Payee getPay() {
		return pay;
	}

}
