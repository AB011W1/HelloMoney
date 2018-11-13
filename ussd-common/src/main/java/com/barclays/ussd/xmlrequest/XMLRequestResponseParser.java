package com.barclays.ussd.xmlrequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.barclays.ussd.xmlresponse.USSDXMLResponse;

public class XMLRequestResponseParser {

    /** xsd file location */
    private static final String RESPONSE_XSD = "xmlRequestResponse/USSDXMLResponse.xsd";

    /** xsd file location */
    private static final String REQUEST_XSD = "xmlRequestResponse/USSDXMLRequest.xsd";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(XMLRequestResponseParser.class);

    public static USSDXMLRequest getXMLRequestObject(String request, boolean enableXSDValidation) throws JAXBException {
	// convert String into InputStream
	InputStream is = new ByteArrayInputStream(request.getBytes());

	LOGGER.debug("creating USSDXMLRequestObject : ");
	USSDXMLRequest requestObject = new USSDXMLRequest();
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(USSDXMLRequest.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    URL resource = Thread.currentThread().getContextClassLoader().getResource(REQUEST_XSD);
	    Schema requestSchema = null;
	    if (enableXSDValidation) {
		try {
		    requestSchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(resource);
		} catch (SAXException e) {
		    LOGGER.error("Error while parsing the request xml. XML: " + request + ".\n Error : " + e.getMessage());
		    throw new JAXBException(e.getMessage());
		}
	    }
	    jaxbUnmarshaller.setSchema(requestSchema);
	    requestObject = (USSDXMLRequest) jaxbUnmarshaller.unmarshal(is);
	} catch (JAXBException e) {
	    LOGGER.error("Error while parsing the request xml. XML: " + request + ".\n Error : " + e.getMessage());
	    throw e;
	} finally {
	    try {
		is.close();
	    } catch (IOException e) {
		
	    }
	}
	// LOGGER.debug("Created USSDXMLRequestObject is : " + requestObject);
	return requestObject;
    }

    /**
     * @param responseObject
     * @return
     */
    public String getXMLResponseString(USSDXMLResponse responseObject, boolean enableXSDValidation) {
	OutputStream os = new ByteArrayOutputStream();

	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(USSDXMLResponse.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    URL resource = Thread.currentThread().getContextClassLoader().getResource(RESPONSE_XSD);
	    Schema responseSchema = null;
	    if (enableXSDValidation) {
		try {
		    responseSchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(resource);
		} catch (SAXException e) {
		    LOGGER.error("Error while parsing the object to xml. Object: " + responseObject + ".\n Error : " + e.getMessage());
		    throw new JAXBException(e.getMessage());
		}
	    }

	    jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	    jaxbMarshaller.setSchema(responseSchema);
	    jaxbMarshaller.marshal(responseObject, os);

	} catch (JAXBException e) {
	    LOGGER.error("Error while parsing the object to xml. Object: " + responseObject + ".\n Error : " + e.getMessage());
	    
	} finally {
	    try {
		os.close();
	    } catch (IOException e) {
		
	    }
	}
	// LOGGER.debug("Created XML String is : " + os.toString());
	return os.toString();
    }

}
