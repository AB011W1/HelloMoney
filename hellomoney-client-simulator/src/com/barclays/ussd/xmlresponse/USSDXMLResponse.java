package com.barclays.ussd.xmlresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "RESPONSE")
@XmlType(propOrder = {"headerResponse", "bodyResponse"})
@XmlAccessorType(XmlAccessType.FIELD)
public class USSDXMLResponse {


	@XmlElement(name = "BODY")
    private USSDXMLResponseBody bodyResponse;

	@XmlElement(name = "HEADER")
    private USSDXMLResponseHeader headerResponse;

	/**
	 * @return the bodyResponse
	 */
	public USSDXMLResponseBody getBodyResponse() {
		return bodyResponse;
	}

	/**
	 * @param bodyResponse the bodyResponse to set
	 */
	public void setBodyResponse(USSDXMLResponseBody bodyResponse) {
		this.bodyResponse = bodyResponse;
	}

	/**
	 * @return the headerResponse
	 */
	public USSDXMLResponseHeader getHeaderResponse() {
		return headerResponse;
	}

	/**
	 * @param headerResponse the headerResponse to set
	 */
	public void setHeaderResponse(USSDXMLResponseHeader headerResponse) {
		this.headerResponse = headerResponse;
	}



}
