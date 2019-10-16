package com.barclays.bmg.cashsend.dao.impl;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.barclays.bcag.HSMClient;
import com.barclays.bmg.cashsend.dao.CashSendEncryptionDAO;
import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.cashsend.service.response.CashSendOneTimeExecuteServiceResponse;


public class CashSendEncryptionDAOImpl implements CashSendEncryptionDAO {

    private static final Logger LOGGER = Logger.getLogger(CashSendEncryptionDAOImpl.class);

    @Override
    public CashSendOneTimeExecuteServiceResponse encryptPin(CashSendOneTimeExecuteServiceRequest request) {
    	LOGGER.info("Entry CashSendOneTimeExecuteServiceResponse encryptPin");
    	if (LOGGER.isDebugEnabled()) {
    		LOGGER.debug("Entry CashSendOneTimeExecuteServiceResponse encryptPin");
    	}
	String PIN_LENGTH = request.getHSMProperties().getProperty("PINLENGTH").toString();
	LOGGER.debug("Pin lenght : " + PIN_LENGTH);
	String PIN_PADDING = request.getHSMProperties().getProperty("PINPADDING").toString();
	LOGGER.debug("Pin padding : " + PIN_PADDING);

	System.setProperty("HsmPortal.api.ssl.keymanager.algorithm.override", "IbmX509");
	System.setProperty("HsmPortal.api.ssl.keymanager.provider.override", "IBMJSSE2");

	String pin = request.getCashSendRequestDTO().getVPin();


	String cipherText = "";
	CashSendOneTimeExecuteServiceResponse response = null;
	try {
		LOGGER.debug("Get properties");
		Properties properties = request.getHSMProperties();
		LOGGER.info("CashSendOneTimeExecuteServiceResponse encryptPin() before HSMClient getKeystore() method call");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CashSendOneTimeExecuteServiceResponse encryptPin() before HSMClient getKeystore() method call");
		}
		System.out.println("CashSendOneTimeExecuteServiceResponse encryptPin() before HSMClient getKeystore() method call");

		LOGGER.debug("HSMClient call to set HSM properties");
		HSMClient.setProperties(properties);

	    LOGGER.info("CashSendOneTimeExecuteServiceResponse encryptPin() after HSMClient getKeystore() method call");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CashSendOneTimeExecuteServiceResponse encryptPin() after HSMClient getKeystore() method call");
		}
		System.out.println("CashSendOneTimeExecuteServiceResponse encryptPin() after HSMClient getKeystore() method call");
		LOGGER.debug("PIN_LENGTH: " + PIN_LENGTH + "pin: " + pin + "PIN_PADDING: " + PIN_PADDING);
		LOGGER.debug("HSMClient encrypt method call");
	    cipherText = HSMClient.encrypt(PIN_LENGTH + pin + PIN_PADDING);//client.encrypt(PIN_LENGTH + pin + PIN_PADDING);
	    LOGGER.info("CashSendOneTimeExecuteServiceResponse encryptPin() cipherText encryption done");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CashSendOneTimeExecuteServiceResponse encryptPin() cipherText encryption done");
		}
		System.out.println("CashSendOneTimeExecuteServiceResponse encryptPin() cipherText encryption done");
	    response = new CashSendOneTimeExecuteServiceResponse();
	    response.setPin(cipherText);

	} catch (Exception e) {
	    LOGGER.error("Unable to create HSM Client Or Encrypt PIN" + e);
	    LOGGER.info("Unable to create HSM Client Or Encrypt PIN" + e);
	    System.out.println("Unable to create HSM Client Or Encrypt PIN" + e);
	}

	return response;
    }
}
