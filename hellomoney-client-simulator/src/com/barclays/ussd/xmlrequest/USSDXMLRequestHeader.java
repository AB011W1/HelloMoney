package com.barclays.ussd.xmlrequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "HEADER")
@XmlType(propOrder = { "businessId", "mno", "msisdn", "imei", "imsi", "sessionId", "nonce" })
@XmlAccessorType(XmlAccessType.FIELD)
public class USSDXMLRequestHeader {

    @XmlElement(name = "BUSINESSID")
    String businessId;

    @XmlElement(name = "MNO")
    String mno;

    @XmlElement(name = "MSISDN")
    String msisdn;

    @XmlElement(name = "IMEI")
    String imei;

    @XmlElement(name = "SESSIONID")
    String sessionId;

    @XmlElement(name = "NONCE")
    String nonce;

    @XmlElement(name = "IMSI")
    String imsi;

    /**
     * @return the msisdn
     */
    public String getMsisdn() {
	return msisdn;
    }

    /**
     * @param msisdn
     *            the msisdn to set
     */
    public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
    }

    /**
     * @return the businessId
     */
    public String getBusinessId() {
	return businessId;
    }

    /**
     * @param businessId
     *            the businessId to set
     */
    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    /**
     * @return the nonce
     */
    public String getNonce() {
	return nonce;
    }

    /**
     * @param nonce
     *            the nonce to set
     */
    public void setNonce(String nonce) {
	this.nonce = nonce;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
	return sessionId;
    }

    /**
     * @param sessionId
     *            the sessionId to set
     */
    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    /**
     * @return the imei
     */
    public String getImei() {
	return imei;
    }

    /**
     * @param imei
     *            the imei to set
     */
    public void setImei(String imei) {
	this.imei = imei;
    }

    @Override
    public String toString() {
	return new StringBuilder("msisdn: ").append(msisdn).append(" //businessId: ").append(businessId).append("  //nonce: ").append(nonce).append(
		" //sessionId: ").append(sessionId).append("  //imei: ").append(imei).append("  //imsi: ").append(imsi).toString();
    }

    /**
     * @return the mno
     */
    public String getMno() {
	return mno;
    }

    /**
     * @param mno
     *            the mno to set
     */
    public void setMno(String mno) {
	this.mno = mno;
    }

    public String getImsi() {
	return imsi;
    }

    public void setImsi(String imsi) {
	this.imsi = imsi;
    }
}
