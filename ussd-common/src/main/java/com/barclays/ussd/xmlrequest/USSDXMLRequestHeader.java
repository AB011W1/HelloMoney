package com.barclays.ussd.xmlrequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HEADER")
public class USSDXMLRequestHeader {

    private String msisdn;
    private String businessId;
    private String nonce;
    private String sessionId;
    private String imei;
    private String mno;
    private String imsi;

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
    @XmlElement(name = "BUSINESSID")
    public void setBusinessId(String businessId) {
	this.businessId = businessId;
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
    @XmlElement(name = "MNO")
    public void setMno(String mno) {
	this.mno = mno;
    }

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
    @XmlElement(name = "MSISDN")
    public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
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
    @XmlElement(name = "IMEI")
    public void setImei(String imei) {
	this.imei = imei;
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
    @XmlElement(name = "SESSIONID")
    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
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
    @XmlElement(name = "NONCE")
    public void setNonce(String nonce) {
	this.nonce = nonce;
    }

    @Override
    public String toString() {
	return new StringBuilder("msisdn: ").append(msisdn).append(" || businessId: ").append(businessId).append("  || nonce: ").append(nonce)
		.append(" || sessionId: ").append(sessionId).append("  || imei: ").append(imei).append("  || imsi: ").append(imsi).toString();
    }

    public String getImsi() {
	return imsi;
    }

    @XmlElement(name = "IMSI")
    public void setImsi(String imsi) {
	this.imsi = imsi;
    }
}
