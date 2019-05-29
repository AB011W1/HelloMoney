package com.barclays.ussd.xmlresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "HEADER")
@XmlType(propOrder = { "msisdn", "nonceValue", "sessionId" })
@XmlAccessorType(XmlAccessType.FIELD)
public class USSDXMLResponseHeader {

    @XmlElement(name = "MSISDN")
    private String msisdn;

    @XmlTransient
    private String msisdnWithCountry;

    @XmlElement(name = "NONCE")
    private String nonceValue;

    @XmlElement(name = "SESSIONID")
    private String sessionId;

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
     * @return the sessionId
     */
    public String getSessionId() {
	return sessionId;
    }

    /**
     * @param sessionId
     *            the sessionId to set
     */
    public void setSessionId(String session) {
	this.sessionId = session;
    }

    /**
     * @return the msisdnWithCountry
     */
    public String getMsisdnWithCountry() {
	return msisdnWithCountry;
    }

    /**
     * @param msisdnWithCountry
     *            the msisdnWithCountry to set
     */
    public void setMsisdnWithCountry(String msisdnWithCountry) {
	this.msisdnWithCountry = msisdnWithCountry;
    }

    /**
     * @return the nonceValue
     */
    public String getNonceValue() {
	return nonceValue;
    }

    /**
     * @param nonceValue
     *            the nonceValue to set
     */
    public void setNonceValue(String nonceValue) {
	this.nonceValue = nonceValue;
    }

}
