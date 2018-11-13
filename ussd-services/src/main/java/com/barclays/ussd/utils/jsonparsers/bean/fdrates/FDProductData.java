package com.barclays.ussd.utils.jsonparsers.bean.fdrates;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FDProductData {

	@JsonProperty
	private List<FDProduct> intRatesList;

	/**
	 * @return the intRatesList
	 */
	public List<FDProduct> getIntRatesList() {
		return intRatesList;
	}

	/**
	 * @param intRatesList
	 *            the intRatesList to set
	 */
	public void setIntRatesList(List<FDProduct> intRatesList) {
		this.intRatesList = intRatesList;
	}

}
