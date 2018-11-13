/**
 *  BillerLstData.java
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
public class BillerLstData {
	/**
	 * catzedPayLst
	 */
	@JsonProperty
	private List<CatPayeeLst> catzedPayLst;

	/**
	 * @param catzedPayLst the catzedPayLst to set
	 */
	public void setCatzedPayLst(List<CatPayeeLst> catzedPayLst) {
		this.catzedPayLst = catzedPayLst;
	}

	/**
	 * @return the catzedPayLst
	 */
	public List<CatPayeeLst> getCatzedPayLst() {
		return catzedPayLst;
	}

}
