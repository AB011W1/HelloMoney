/**
 * BeneData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneData {
    /**
     * payId
     */
    @JsonProperty
    private String payId;
    /**
     * disLbl
     */
    @JsonProperty
    private String disLbl;

    @JsonProperty
    private String actNo;

    @JsonProperty
    private String payNckNam;

    @JsonProperty
    private String beneNam;

    @JsonProperty
    private String ctrCde;

    /**
     * @return the payId
     */
    public String getPayId() {
	return payId;
    }

    /**
     * @param payId
     *            the payId to set
     */
    public void setPayId(String payId) {
	this.payId = payId;
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

    /**
     * @return the actNo
     */
    public String getActNo() {
	return actNo;
    }

    /**
     * @param actNo
     *            the actNo to set
     */
    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    /**
     * @return the payNckNam
     */
    public String getPayNckNam() {
	return payNckNam;
    }

    /**
     * @param payNckNam
     *            the payNckNam to set
     */
    public void setPayNckNam(String payNckNam) {
	this.payNckNam = payNckNam;
    }

    /**
     * @return the beneNam
     */
    public String getBeneNam() {
	return beneNam;
    }

    /**
     * @param beneNam
     *            the beneNam to set
     */
    public void setBeneNam(String beneNam) {
	this.beneNam = beneNam;
    }

    /**
     * @return the ctrCde
     */
    public String getCtrCde() {
	return ctrCde;
    }

    /**
     * @param ctrCde
     *            the ctrCde to set
     */
    public void setCtrCde(String ctrCde) {
	this.ctrCde = ctrCde;
    }

}
