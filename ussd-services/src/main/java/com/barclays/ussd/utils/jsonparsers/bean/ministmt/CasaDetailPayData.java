/**
 *CasaDetailPayData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.ministmt;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasaDetailPayData {

	/**
	 * actDetls
	 */
	@JsonProperty
	private CasaAccntDetails actDetls;

	/**
	 * lstAccntActvty
	 */
	@JsonProperty
	private List<AccountActivity> actActvLst;

	/**
	 * @return the lstAccntActvty
	 */

	/**
	 * @return the actDetls
	 */
	public CasaAccntDetails getActDetls() {
		return actDetls;
	}

	/**
	 * @param actDetls
	 *            the actDetls to set
	 */
	public void setActDetls(CasaAccntDetails actDetls) {
		this.actDetls = actDetls;
	}

	public List<AccountActivity> getActActvLst() {
		return actActvLst;
	}

	public void setActActvLst(List<AccountActivity> actActvLst) {
		this.actActvLst = actActvLst;
	}

}
