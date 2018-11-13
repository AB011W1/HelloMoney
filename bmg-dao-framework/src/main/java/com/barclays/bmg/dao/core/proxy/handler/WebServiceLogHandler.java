package com.barclays.bmg.dao.core.proxy.handler;

import javax.xml.namespace.QName;
import javax.xml.rpc.Stub;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

import com.barclays.bmg.dao.core.proxy.util.IndentFormatter;
import com.barclays.bmg.helper.DisasterRecoveryHelper;
import com.barclays.bmg.logging.BMBLog;
import com.barclays.bmg.logging.util.BMBLogUtility;

public class WebServiceLogHandler extends GenericHandler {

    private final Logger logger = Logger.getLogger("SOAP_LOGGER");
    private final BMBLog remoteLogger = BMBLogUtility.getLogger(getClass());
    private static Boolean MQLogLevel;

    static{
    	MQLogLevel = DisasterRecoveryHelper.checkMQSOAPLOGLEVEL();
    }

    public boolean handleRequest(MessageContext context) {

	SOAPMessageContext smc = (SOAPMessageContext) context;
	try {
	    Object endpoint = smc.getProperty(Stub.ENDPOINT_ADDRESS_PROPERTY);
	    if(MQLogLevel){
	    	remoteLogger.debug("Send Request To: " + endpoint + IndentFormatter.format(smc.getMessage().getSOAPPart().getEnvelope().toString()));
	    }
		logger.debug("Send Request To: " + endpoint + IndentFormatter.format(smc.getMessage().getSOAPPart().getEnvelope().toString()));

	} catch (Exception e) {
	    logger.error("Error in handleRequest-" + e.getMessage(), e);
	}
	return true;
    }

    public boolean handleResponse(MessageContext context) {
	SOAPMessageContext smc = (SOAPMessageContext) context;
	try {
	    Object endpoint = smc.getProperty(Stub.ENDPOINT_ADDRESS_PROPERTY);
	    if(MQLogLevel){
	    	remoteLogger.debug("Receive Response from: " + endpoint + IndentFormatter.format(smc.getMessage().getSOAPPart().getEnvelope().toString()));
	    }
	    logger.debug("Receive Response from: " + endpoint + IndentFormatter.format(smc.getMessage().getSOAPPart().getEnvelope().toString()));
	} catch (Exception e) {
	    logger.error("Error in handleResponse-" + e.getMessage(), e);
	}
	return true;
    }

    public boolean handleFault(MessageContext context) {
	SOAPMessageContext smc = (SOAPMessageContext) context;
	try {
	    Object endpoint = smc.getProperty(Stub.ENDPOINT_ADDRESS_PROPERTY);
	    remoteLogger.error("Receive fault from: " + endpoint + IndentFormatter.format(smc.getMessage().getSOAPPart().getEnvelope().toString()));
	    logger.debug("Receive fault from: " + endpoint + IndentFormatter.format(smc.getMessage().getSOAPPart().getEnvelope().toString()));
	} catch (Exception e) {
	    logger.error("Error in handleFault-" + e.getMessage(), e);
	}
	return true;
    }

    public QName[] getHeaders() {
	QName[] arr = {};
	return arr;
    }
}
