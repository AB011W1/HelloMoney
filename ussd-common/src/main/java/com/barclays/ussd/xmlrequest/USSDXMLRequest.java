package com.barclays.ussd.xmlrequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "REQUEST")
public class USSDXMLRequest {
    private USSDXMLRequestHeader requestHeader;
    private USSDXMLRequestBody requestBody;

    /**
     * @return the requestHeader
     */
    public USSDXMLRequestHeader getRequestHeader() {
	return this.requestHeader;
    }

    /**
     * @param requestHeader
     *            the requestHeader to set
     */
    @XmlElement(name = "HEADER")
    public void setRequestHeader(USSDXMLRequestHeader requestHeader) {
	this.requestHeader = requestHeader;
    }

    /**
     * @return the requestBody
     */
    public USSDXMLRequestBody getRequestBody() {
	return this.requestBody;
    }

    /**
     * @param requestBody
     *            the requestBody to set
     */
    @XmlElement(name = "BODY")
    public void setRequestBody(USSDXMLRequestBody requestBody) {
	this.requestBody = requestBody;
    }

    @Override
    public String toString() {
	return new StringBuilder("HEADER : ").append(requestHeader).append(" \n : ").append(requestBody).toString();
    }
}
