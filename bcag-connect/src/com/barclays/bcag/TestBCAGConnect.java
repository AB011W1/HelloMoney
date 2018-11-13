package com.barclays.bcag;

import java.io.InputStream;
import java.util.Properties;

public class TestBCAGConnect {
    public static void main(String[] args) throws Exception {
	System.setProperty("HsmPortal.api.ssl.keymanager.algorithm.override", "IbmX509");
	System.setProperty("HsmPortal.api.ssl.keymanager.provider.override", "IBMJSSE2");

	String keyStoreFilename = "bcagstore.jks";
	String keyStorePassword = "123456";
	String propertiesFile = "THM_KE_CONFIG.properties";
	String encryptionType = "THM_KE";
	Properties properties = new Properties();

	InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile);
	properties.load(in);
	in.close();
	BCAGClient.getClient(encryptionType, propertiesFile, properties);
	// ClientApi bcagclient = BCAGClient.getBCAGClient(filename, password, serverIPList, 5000); // setting timeout to 30 seconds

	// **** Log on to the BCAG ****
	// TODO update the password to logon to BCAG
	// bcagclient.logon("LOCAL:SHM_CASHSEND_test", "0123456789Qwertyuiop!");

	// String plainTextStr = "Beware the Jabberwock, my son! The jaws that bite, the claws that catch!";
	String PIN_LENGTH = "05"; // Make this configurable
	String PIN_PADDING = "FFFFFFFFF"; // Make this configurable
	String plainTextStr = "1234";

	byte[] plaintext = plainTextStr.getBytes();
	System.out.println("\nPlaintext String : \"" + plainTextStr + "\"");

	// Encrypt

	// System.out.println("\nEncrypt with UG_Encrypt");
	// String cipherText = client.encrypt(plainTextStr);
	// System.out.println("Encrypted Plaintext String : \"" + cipherText + "\"");

	// Decrypt

	// String cipherText = "01303032303134303631373134343235347C4C5EBC3F9A010100497EA0F5023E7CB41DC1435D468FC77B5027B051F5F12039F5598B44CD8876BC";
	// String cipherTest = "01303032303134303631373134343235347C4C5EBC3F9A0101004E545C6EEEC4677AAF15714741038590343692A9F43EA990D5D159746928C939";
	String Test1234 = "01303032303134303631373134343235347C4C177840150101006AFDF30040F117E4176966C5C65C14CD20E58309DA0E4335A1BDF9FB6EC00023";
	String plaintextStr = THMBCAGClient.decryptPin(Test1234);
	System.out.println("Decrypted Plaintext String : \"" + plaintextStr + "\"");

	// **** Close BCAG ****
	THMBCAGClient.closeBCAGClient();
	// bcagclient.shutdown();
    }
}
