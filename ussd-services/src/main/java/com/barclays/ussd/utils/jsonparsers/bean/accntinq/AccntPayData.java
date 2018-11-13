/**
 * AccntPayData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.accntinq;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccntPayData {
	/**
	 * custActs
	 */
	@JsonProperty
	private List<AccountDetails> custActs;

	/**
	 * @return the custActs
	 */
	public List<AccountDetails> getCustActs() {
		return custActs;
	}

	/**
	 * @param custActs the custActs to set
	 */
	public void setCustActs(List<AccountDetails> custActs) {
		this.custActs = custActs;
	}


}
