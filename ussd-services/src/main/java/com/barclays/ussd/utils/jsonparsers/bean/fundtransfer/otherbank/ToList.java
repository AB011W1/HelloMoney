/**
 * ToList.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToList {
	/**
	 * bnfLst
	 */
	@JsonProperty
	private List<BeneData> bnfLst;

	/**
	 * @param bnfLst the bnfLst to set
	 */
	public void setBnfLst(List<BeneData> bnfLst) {
		this.bnfLst = bnfLst;
	}

	/**
	 * @return the bnfLst
	 */
	public List<BeneData> getBnfLst() {
		return bnfLst;
	}

}
