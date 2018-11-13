/**
 * CatPayeeLst.java
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
public class CatPayeeLst {
	/**
	 * payCat
	 */
	@JsonProperty
	private String payCat;
	/**
	 * bnfLst
	 */
	@JsonProperty
	private List<Beneficiery> bnfLst;

	/**
	 * @param bnfLst the bnfLst to set
	 */
	public void setBnfLst(List<Beneficiery> bnfLst) {
		this.bnfLst = bnfLst;
	}

	/**
	 * @return the bnfLst
	 */
	public List<Beneficiery> getBnfLst() {
		return bnfLst;
	}

	/**
	 * @return the payCat
	 */
	public String getPayCat() {
		return payCat;
	}

	/**
	 * @param payCat the payCat to set
	 */
	public void setPayCat(String payCat) {
		this.payCat = payCat;
	}


}
