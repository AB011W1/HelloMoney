/**
 * FromAcntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.verifyuser;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyUserPayData {
    @JsonProperty
    private String cryptoStatusCode;

    @JsonProperty
    private String cryptoPinStatus;

    @JsonProperty
    private String userStatusInMCE;

    @JsonProperty
    private String langPref;

    @JsonProperty
    private String customerAccessStatusCode;

    //CR-77
    @JsonProperty
    private String customerFirstName;

    public String getLangPref() {
	return langPref;
    }

    public void setLangPref(String langPref) {
	this.langPref = langPref;
    }

    public String getCryptoStatusCode() {
	return cryptoStatusCode;
    }

    public void setCryptoStatusCode(String cryptoStatusCode) {
	this.cryptoStatusCode = cryptoStatusCode;
    }

    public String getCryptoPinStatus() {
        return cryptoPinStatus;
    }

    public void setCryptoPinStatus(String cryptoPinStatus) {
        this.cryptoPinStatus = cryptoPinStatus;
    }

    public String getUserStatusInMCE() {
	return userStatusInMCE;
    }

    public void setUserStatusInMCE(String userStatusInMCE) {
	this.userStatusInMCE = userStatusInMCE;
    }

	public String getCustomerAccessStatusCode() {
		return customerAccessStatusCode;
	}

	public void setCustomerAccessStatusCode(String customerAccessStatusCode) {
		this.customerAccessStatusCode = customerAccessStatusCode;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

}
