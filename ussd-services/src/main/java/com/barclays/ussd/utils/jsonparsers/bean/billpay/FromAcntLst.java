/**
 * FromAcntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FromAcntLst {
	/**
	 * acntData
	 */
	@JsonProperty
	private List<AccountData> frActLst;

	@JsonProperty
	private Payee pay;

	public List<AccountData> getFrActLst() {
		return frActLst;
	}

	public void setFrActLst(List<AccountData> frActLst) {
		this.frActLst = frActLst;
	}

	public Payee getPay() {
		return pay;
	}

	public void setPay(Payee pay) {
		this.pay = pay;
	}


}
