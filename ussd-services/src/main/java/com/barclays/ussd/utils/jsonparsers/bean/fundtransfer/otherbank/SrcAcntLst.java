/**
 * SrcAcntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SrcAcntLst {

	/**
	 * custActs
	 */
	@JsonProperty
	private List<AccountDetails> custActs;

	/**
	 * @param custActs the custActs to set
	 */
	public void setCustActs(List<AccountDetails> custActs) {
		this.custActs = custActs;
	}

	/**
	 * @return the custActs
	 */
	public List<AccountDetails> getCustActs() {
		return custActs;
	}
}
