package com.barclays.ussd.xmlrequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "REQUEST")
@XmlType(propOrder = {"requestHeader", "requestBody"})
@XmlAccessorType(XmlAccessType.FIELD)
public class USSDXMLRequest {

    @XmlElement(name = "HEADER")
    USSDXMLRequestHeader requestHeader;

    @XmlElement(name = "BODY")
    USSDXMLReqBody requestBody;

    /**
     * @return the requestHeader
     */
    public USSDXMLRequestHeader getRequestHeader() {
	return requestHeader;
    }

    /**
     * @param requestHeader
     *            the requestHeader to set
     */
    public void setRequestHeader(USSDXMLRequestHeader requestHeader) {
	this.requestHeader = requestHeader;
    }

    /**
     * @return the requestBody
     */
    public USSDXMLReqBody getRequestBody() {
	return requestBody;
    }

    /**
     * @param requestBody
     *            the requestBody to set
     */
    public void setRequestBody(USSDXMLReqBody requestBody) {
	this.requestBody = requestBody;
    }
}
