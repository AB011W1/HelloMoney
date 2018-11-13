/**
 * PayInfo.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayInfo {
    /**
     * mkdActNo
     */
    @JsonProperty
    private String mkdActNo;
    /**
     * payNckNam
     */
    @JsonProperty
    private String payNckNam;

    @JsonProperty
    private String actNo;

    @JsonProperty
    private String payId;

    @JsonProperty
    private String bnkCode;

    @JsonProperty
    private String beneNam;

    /**
     * @param mkdActNo
     *            the mkdActNo to set
     */
    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    /**
     * @return the mkdActNo
     */
    public String getMkdActNo() {
	return mkdActNo;
    }

    /**
     * @param payNckNam
     *            the payNckNam to set
     */
    public void setPayNckNam(String payNckNam) {
	this.payNckNam = payNckNam;
    }

    /**
     * @return the payNckNam
     */
    public String getPayNckNam() {
	return payNckNam;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

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
     * @return the bnkCode
     */
	public String getBnkCode() {
		return bnkCode;
	}

	 /**
     * @param bnkCode
     *            the bnkCode to set
     */
	public void setBnkCode(String bnkCode) {
		this.bnkCode = bnkCode;
	}

	public String getBeneNam() {
		return beneNam;
	}

	public void setBeneNam(String beneNam) {
		this.beneNam = beneNam;
	}

}
