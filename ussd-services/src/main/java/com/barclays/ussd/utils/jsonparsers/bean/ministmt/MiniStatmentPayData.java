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
public class MiniStatmentPayData {

    /**
     * lstAccntActvty
     */
    @JsonProperty
    private List<AccountActivity> actActvLst;

    @JsonProperty
    private String accountNumber;
    /**
     * @return the lstAccntActvty
     */

    public List<AccountActivity> getActActvLst() {
	return actActvLst;
    }

    public void setActActvLst(List<AccountActivity> actActvLst) {
	this.actActvLst = actActvLst;
    }

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
