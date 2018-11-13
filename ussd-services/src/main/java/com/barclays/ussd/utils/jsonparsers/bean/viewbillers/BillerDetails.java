/**
 * BillerDetails.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.viewbillers;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.vlpb.RefNo;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerDetails {
    /**
     * bilrTyp
     */
    @JsonProperty
    private String bilrTyp;
    /**
     * bilrNam
     */
    @JsonProperty
    private String bilrNam;
    /**
     * bilrId
     */
    @JsonProperty
    private String bilrId;

    @JsonProperty
    private String payNckNam;

    @JsonProperty
    private RefNo refNo;

    // WUC-2 Botswana Biller change - 03/07/2017
    @JsonProperty
    private RefNo wucContractRefNo;

    /**
     * biller area code
     */
    @JsonProperty
    private String billerAreaCode;

    /**
     * @return the billerAreaCode
     */
    public String getBillerAreaCode() {
		return billerAreaCode;
	}

    /**
     * @param billerAreaCode
     *            the billerAreaCode to set
     */
	public void setBillerAreaCode(final String billerAreaCode) {
		this.billerAreaCode = billerAreaCode;
	}

	/**
     * @return the bilrTyp
     */
    public String getBilrTyp() {
	return bilrTyp;
    }

    /**
     * @param bilrTyp
     *            the bilrTyp to set
     */
    public void setBilrTyp(final String bilrTyp) {
	this.bilrTyp = bilrTyp;
    }

    /**
     * @return the bilrNam
     */
    public String getBilrNam() {
	return bilrNam;
    }

    /**
     * @param bilrNam
     *            the bilrNam to set
     */
    public void setBilrNam(final String bilrNam) {
	this.bilrNam = bilrNam;
    }

    /**
     * @return the bilrId
     */
    public String getBilrId() {
	return bilrId;
    }

    /**
     * @param bilrId
     *            the bilrId to set
     */
    public void setBilrId(final String bilrId) {
	this.bilrId = bilrId;
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
     * @return the refNo
     */
    public RefNo getRefNo() {
	return refNo;
    }

    /**
     * @param refNo
     *            the refNo to set
     */
    public void setRefNo(RefNo refNo) {
	this.refNo = refNo;
    }

    /**
     * @return the wucContractRefNo
     */
	public RefNo getWucContractRefNo() {
		return wucContractRefNo;
	}

	/**
     * @param wucContractRefNo
     *            the wucContractRefNo to set
     */
	public void setWucContractRefNo(RefNo wucContractRefNo) {
		this.wucContractRefNo = wucContractRefNo;
	}

}
