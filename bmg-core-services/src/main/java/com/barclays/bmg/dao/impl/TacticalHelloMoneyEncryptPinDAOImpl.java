package com.barclays.bmg.dao.impl;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.barclays.bcag.BCAGClient;
import com.barclays.bmg.dao.TacticalHelloMoneyEncryptPinDAO;
import com.barclays.bmg.service.request.TacticalHelloMoneyEncryptPinServiceRequest;
import com.barclays.bmg.service.request.dto.EncryptionConstants;
import com.barclays.bmg.service.response.TacticalHelloMoneyEncryptPinServiceResponse;

public class TacticalHelloMoneyEncryptPinDAOImpl implements TacticalHelloMoneyEncryptPinDAO {

    private static final Logger LOGGER = Logger.getLogger(TacticalHelloMoneyEncryptPinDAOImpl.class);

    @Override
    public TacticalHelloMoneyEncryptPinServiceResponse encryptPin(TacticalHelloMoneyEncryptPinServiceRequest request) {
	String pin = request.getPin();

	System.setProperty("HsmPortal.api.ssl.keymanager.algorithm.override", "IbmX509");
	System.setProperty("HsmPortal.api.ssl.keymanager.provider.override", "IBMJSSE2");

	String propertiesFile = "THM_KE_CONFIG.properties";
	String encryptionType = "THM_KE";
	BCAGClient client = null;
	try {
	    client = BCAGClient.getClient(encryptionType, propertiesFile, getBcagProperties(request));
	} catch (Exception e) {
	    
	}

	// Encrypt
	LOGGER.debug("\nEncrypt with STHM_PIN_encrypt");
	String cipherText = "";
	try {
	    cipherText = client.encrypt(pin);
	} catch (Exception e) {
	    LOGGER.error(e.getMessage());
	}
	LOGGER.debug("Encrypted Plaintext String : \"" + cipherText + "\"");

	// Decrypt
	/*
	 * String plaintextStr = ""; try { plaintextStr = client.decrypt(cipherText); } catch (Exception e) { // TODO Auto-generated catch block
	 *  } System.out.println("Decrypted Plaintext String : \"" + plaintextStr + "\"");
	 */
	// **** Close BCAG ****
	client.close();
	TacticalHelloMoneyEncryptPinServiceResponse response = new TacticalHelloMoneyEncryptPinServiceResponse();
	response.setEncryptedPin(cipherText);
	return response;
    }

    private Properties getBcagProperties(TacticalHelloMoneyEncryptPinServiceRequest request) {

	Properties requestProperties = request.getBcagProperties();
	Properties bcagProperties = new Properties();
	bcagProperties.setProperty("SERVERS", requestProperties.getProperty(EncryptionConstants.THMPINENCRYPTSERVERS));
	bcagProperties.setProperty("KEYSTORE", requestProperties.getProperty(EncryptionConstants.THMPINENCRYPTKEYSTORE));
	bcagProperties.setProperty("KEYSTOREKEY", requestProperties.getProperty(EncryptionConstants.THMPINENCRYPTKEYSTOREKEY));
	bcagProperties.setProperty("ENCRYPTLOGIC", requestProperties.getProperty(EncryptionConstants.THMPINENCRYPTENCRYPTLOGIC));
	bcagProperties.setProperty("DECRYPTLOGIC", requestProperties.getProperty(EncryptionConstants.THMPINENCRYPTENCRYPTLOGIC));
	bcagProperties.setProperty("UN", requestProperties.getProperty(EncryptionConstants.THMPINENCRYPTUSERNAME));
	bcagProperties.setProperty("KEY", requestProperties.getProperty(EncryptionConstants.THMPINENCRYPTUSERKEY));

	System.out.println(bcagProperties);

	return bcagProperties;
    }
}
