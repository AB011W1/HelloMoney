package com.barclays.bcag;

import java.io.InputStream;
import java.util.Properties;

public class THMBCAGClient {

    private static BCAGClient thmBcagClient;

    private static void getBCAGCleint() throws Exception {
	System.setProperty("HsmPortal.api.ssl.keymanager.algorithm.override", "IbmX509");
	System.setProperty("HsmPortal.api.ssl.keymanager.provider.override", "IBMJSSE2");

	String propertiesFile = "/THM_KE_CONFIG.properties";
	String encryptionType = "THM_KE";
	Properties properties = new Properties();
	InputStream resourceAsStream = THMBCAGClient.class.getResourceAsStream(propertiesFile);
	properties.load(resourceAsStream);
	resourceAsStream.close();
	thmBcagClient = BCAGClient.getClient(encryptionType, propertiesFile, properties);

    }

    public static String decryptPin(String encryptedUserPin) throws Exception {
	if (thmBcagClient == null) {
	    getBCAGCleint();
	}
	return thmBcagClient.decrypt(encryptedUserPin);
    }

    public static void closeBCAGClient() {
	thmBcagClient.close();
    }
}
