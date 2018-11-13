/**
 * Beneficiery.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Beneficiery {
    /**
     * payId
     */
    @JsonProperty
    private String payId;

    @JsonProperty
    private String actNo;

    @JsonProperty
    private String disLbl;

    @JsonProperty
    private String areaCode;

    @JsonProperty
    private String billerId;

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    /**
     * @param payId
     *            the payId to set
     */
    public void setPayId(String payId) {
	this.payId = payId;
    }

    /**
     * @return the payId
     */
    public String getPayId() {
	return payId;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    /**
     * @param disLbl
     *            the disLbl to set
     */
    public void setDisLbl(String disLbl) {
	this.disLbl = disLbl;
    }

    /**
     * @return the disLbl
     */
    public String getDisLbl() {
	return disLbl;
    }

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

}
