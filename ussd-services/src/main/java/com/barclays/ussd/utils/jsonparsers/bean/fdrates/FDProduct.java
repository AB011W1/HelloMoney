package com.barclays.ussd.utils.jsonparsers.bean.fdrates;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FDProduct {

	
	@JsonProperty
	private String prodCode;

	@JsonProperty
	private String prodDesc;

	@JsonProperty
	private List<InterestRate> intRateSlab;

	/**
	 * @return the prodCode
	 */
	public String getProdCode() {
		return prodCode;
	}

	/**
	 * @param prodCode
	 *            the prodCode to set
	 */
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	/**
	 * @return the prodDesc
	 */
	public String getProdDesc() {
		return prodDesc;
	}

	/**
	 * @param prodDesc
	 *            the prodDesc to set
	 */
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	/**
	 * @return the intRateSlab
	 */
	public List<InterestRate> getIntRateSlab() {
		return intRateSlab;
	}

	/**
	 * @param intRateSlab
	 *            the intRateSlab to set
	 */
	public void setIntRateSlab(List<InterestRate> intRateSlab) {
		this.intRateSlab = intRateSlab;
	}

}
