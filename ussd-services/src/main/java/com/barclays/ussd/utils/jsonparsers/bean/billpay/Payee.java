package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payee {
    @JsonProperty
    private String bilrNam;

    @JsonProperty
    private String bilrTyp;

    @JsonProperty
    private String bilrId;

    @JsonProperty
    private String payNckNam;

    //CR82 Changes
    @JsonProperty
    ReferenceNumber refNo;
    /**
     * @return
     */
    public String getBilrNam() {
	return bilrNam;
    }

    public ReferenceNumber getRefNo() {
		return refNo;
	}

	public void setRefNo(ReferenceNumber refNo) {
		this.refNo = refNo;
	}

	/**
     * @param bilrNam
     */
    public void setBilrNam(String bilrNam) {
	this.bilrNam = bilrNam;
    }

    /**
     * @return
     */
    public String getBilrTyp() {
	return bilrTyp;
    }

    /**
     * @param bilrTyp
     */
    public void setBilrTyp(String bilrTyp) {
	this.bilrTyp = bilrTyp;
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
    public void setBilrId(String bilrId) {
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

}
