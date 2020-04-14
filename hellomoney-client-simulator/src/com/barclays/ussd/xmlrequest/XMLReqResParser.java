package com.barclays.ussd.xmlrequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.barclays.ussd.xmlresponse.USSDXMLResponse;

public class XMLReqResParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(XMLReqResParser.class);

    public static USSDXMLResponse getXMLResponseObject(String data) throws Exception {

	if (data == null || data.length() == 0 || data.trim().equals("null")) {
	    throw new Exception("XML Response is empty.");
	}

	// convert String into InputStream
	InputStream is = new ByteArrayInputStream(data.getBytes());

	USSDXMLResponse response = null;
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(USSDXMLResponse.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

	    response = (USSDXMLResponse) jaxbUnmarshaller.unmarshal(is);
	    // //System.out.println(requestObject.getRequestBody());
	    // //System.out.println(requestObject.getRequestHeader());
	} catch (Exception e) {
	    LOGGER.error(e.getMessage(), e);
	    throw new Exception("XML Unmarshal Exception - Unable to parse the XML Response [xml=" + data + "]", e);
	} finally {
	    try {
		is.close();
	    } catch (IOException e) {
		LOGGER.error(e.getMessage(), e);
	    }
	}
	return response;
    }

    public static String jaxbObjectToString(Object jaxbObj) {
	OutputStream os = new ByteArrayOutputStream();
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(jaxbObj.getClass());
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    // jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	    jaxbMarshaller.marshal(jaxbObj, os);
	} catch (JAXBException e) {
	    LOGGER.error(e.getMessage(), e);
	} finally {
	    try {
		os.close();
	    } catch (IOException e) {
		LOGGER.error(e.getMessage(), e);
	    }
	}
	return os.toString();
    }


}
