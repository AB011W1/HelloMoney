package com.barclays.bmg.cashsend.dao.impl;

import java.util.Properties;


import org.apache.log4j.Logger;

import com.barclays.bcag.BCAGClient;
import com.barclays.bmg.cashsend.dao.CashSendEncryptionDAO;
import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.cashsend.service.response.CashSendOneTimeExecuteServiceResponse;
import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.service.request.dto.EncryptionConstants;

public class CashSendEncryptionDAOImpl implements CashSendEncryptionDAO {

    private static final Logger LOGGER = Logger.getLogger(CashSendEncryptionDAOImpl.class);

    @Override
    public CashSendOneTimeExecuteServiceResponse encryptPin(CashSendOneTimeExecuteServiceRequest request) {
    	LOGGER.info("Entry CashSendOneTimeExecuteServiceResponse encryptPin");
    	if (LOGGER.isDebugEnabled()) {
    		LOGGER.debug("Entry CashSendOneTimeExecuteServiceResponse encryptPin");
    	}
	String encryptionType = "CS_UG";
	String PIN_LENGTH = "04"; // Make this configurable
	String PIN_PADDING = "FFFFFFFFFF"; // Make this configurable
	/*
	 * Changes as we have to make CashSned PIN 6 digit for ZM
	 */
	if(request.getContext().getBusinessId().equalsIgnoreCase(CommonConstants.ZMBRB_BUSINESS_ID)){
	 PIN_LENGTH = "06";
	 PIN_PADDING = "FFFFFFFF";
	}

	System.setProperty("HsmPortal.api.ssl.keymanager.algorithm.override", "IbmX509");
	System.setProperty("HsmPortal.api.ssl.keymanager.provider.override", "IBMJSSE2");

	String pin = request.getCashSendRequestDTO().getVPin();

	BCAGClient client = null;
	String cipherText = "";
	CashSendOneTimeExecuteServiceResponse response = null;
	try {
		LOGGER.info("CashSendOneTimeExecuteServiceResponse encryptPin() before BCAGClient getClient() method call");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CashSendOneTimeExecuteServiceResponse encryptPin() before BCAGClient getClient() method call");
		}
		System.out.println("CashSendOneTimeExecuteServiceResponse encryptPin() before BCAGClient getClient() method call");
	    client = BCAGClient.getClient(encryptionType, null, getBcagProperties(request));

	    LOGGER.info("CashSendOneTimeExecuteServiceResponse encryptPin() after BCAGClient getClient() method call");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CashSendOneTimeExecuteServiceResponse encryptPin() after BCAGClient getClient() method call");
		}
		System.out.println("CashSendOneTimeExecuteServiceResponse encryptPin() after BCAGClient getClient() method call");
	    cipherText = client.encrypt(PIN_LENGTH + pin + PIN_PADDING);
	    LOGGER.info("CashSendOneTimeExecuteServiceResponse encryptPin() cipherText encryption done");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CashSendOneTimeExecuteServiceResponse encryptPin() cipherText encryption done");
		}
		System.out.println("CashSendOneTimeExecuteServiceResponse encryptPin() cipherText encryption done");
	    client.close();
	    response = new CashSendOneTimeExecuteServiceResponse();
	    response.setPin(cipherText);

	} catch (Exception e) {
	    LOGGER.error("Unable to create BCAG Client Or Encrypt PIN" + e);
	    LOGGER.info("Unable to create BCAG Client Or Encrypt PIN" + e);
	    System.out.println("Unable to create BCAG Client Or Encrypt PIN" + e);
	}

	return response;
    }

    private Properties getBcagProperties(CashSendOneTimeExecuteServiceRequest request) {

	Properties requestProperties = request.getBcagProperties();
	Properties bcagProperties = new Properties();
	bcagProperties.setProperty("SERVERS", requestProperties.getProperty(EncryptionConstants.CASHSENDSERVERS));
	bcagProperties.setProperty("KEYSTORE", requestProperties.getProperty(EncryptionConstants.CASHSENDKEYSTORE));
	bcagProperties.setProperty("KEYSTOREKEY", requestProperties.getProperty(EncryptionConstants.CASHSENDKEYSTOREKEY));
	bcagProperties.setProperty("ENCRYPTLOGIC", requestProperties.getProperty(EncryptionConstants.CASHSENDENCRYPTLOGIC));
	bcagProperties.setProperty("DECRYPTLOGIC", requestProperties.getProperty(EncryptionConstants.CASHSENDENCRYPTLOGIC));
	bcagProperties.setProperty("UN", requestProperties.getProperty(EncryptionConstants.CASHSENDUSERNAME));
	bcagProperties.setProperty("KEY", requestProperties.getProperty(EncryptionConstants.CASHSENDUSERKEY));

		System.out.println("CashSendOneTimeExecuteServiceResponse getBcagProperties bcagProperties SERVERS:"+bcagProperties.getProperty("SERVERS"));
		System.out.println("CashSendOneTimeExecuteServiceResponse getBcagProperties bcagProperties KEYSTORE:"+bcagProperties.getProperty("KEYSTORE"));
		System.out.println("CashSendOneTimeExecuteServiceResponse getBcagProperties bcagProperties KEYSTOREKEY:"+bcagProperties.getProperty("KEYSTOREKEY"));
		System.out.println("CashSendOneTimeExecuteServiceResponse getBcagProperties bcagProperties ENCRYPTLOGIC:"+bcagProperties.getProperty("ENCRYPTLOGIC"));
		System.out.println("CashSendOneTimeExecuteServiceResponse getBcagProperties bcagProperties DECRYPTLOGIC:"+bcagProperties.getProperty("DECRYPTLOGIC"));
		System.out.println("CashSendOneTimeExecuteServiceResponse getBcagProperties bcagProperties UN:"+bcagProperties.getProperty("UN"));

		if(bcagProperties.getProperty("KEY")!=null && bcagProperties.getProperty("KEY").length() >0)
		{
			System.out.println("CashSendOneTimeExecuteServiceResponse getBcagProperties bcagProperties KEY :  ******************");

		}


	return bcagProperties;
    }

}
