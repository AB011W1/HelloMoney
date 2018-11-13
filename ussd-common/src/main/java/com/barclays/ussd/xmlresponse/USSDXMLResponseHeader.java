package com.barclays.ussd.xmlresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "HEADER")
@XmlType(propOrder = { "msisdn", "nonceValue", "session" })
@XmlAccessorType(XmlAccessType.FIELD)
public class USSDXMLResponseHeader {

    @XmlElement(name = "MSISDN")
    private String msisdn;

    @XmlTransient
    private String msisdnWithCountry;

    @XmlElement(name = "NONCE")
    private String nonceValue;

    @XmlElement(name = "SESSIONID")
    private String session;

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
     * @return the session
     */
    public String getSession() {
	return session;
    }

    /**
     * @param session
     *            the session to set
     */
    public void setSession(String session) {
	this.session = session;
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

    @Override
    public String toString() {
	return new StringBuilder("USSDXMLResponseHeader : msisdn: ").append(msisdn).append(" | nonceValue: ").append(nonceValue).append(
		" | session: ").append(session).toString();
    }

}
