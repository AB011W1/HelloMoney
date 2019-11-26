package com.barclays.bcag;

import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.agl.cryptocommon.dom.CryptoGatewayRequest;
import com.agl.cryptocommon.dom.CryptoGatewayResponse;
import com.agl.cryptogateway.client.CryptoClientApi;
import com.agl.cryptogateway.client.ICryptoClient;
import com.cryptomathic.hsmportal.util.Hex;

public class HSMClient {
	private static String passwd = null;
	private static String provider = null;
	private static String alias = null;
	private static String encryptionMethod = null;
	public static SealedObject so = null;
	private static final Logger LOGGER = Logger.getLogger(HSMClient.class);
	private static final String ALGORITHM = "DESede";
	private static String cashHsmBocUrl = null;
	private static String businessID = null;
	private static String loginID = null;	
	private static KeyStore keyStore = null;
	private static KeyStore trustStore = null;
	private static char[] keystorePwdCharArray = null;
	private static char[] truststorePwdCharArray = null;
	private static String filename_keystore;
	private static String filename_truststore;

	public static void setProperties(Properties properties) {
		try {
			LOGGER.debug("Set HSM properties");
			cashHsmBocUrl = properties.getProperty("HSMCASHSENDBOCURL");
			businessID = properties.getProperty("BUSINESSID");
			loginID = properties.getProperty("LOGINID");
			filename_keystore = properties.getProperty("CASHSENDKEYSTORE");
			filename_truststore = properties.getProperty("CASHSENDTRUSTSTORE");
			keystorePwdCharArray = properties.getProperty("CASHSENDKEYSTOREKEY").toCharArray();
			truststorePwdCharArray = properties.getProperty("CASHSENDTRUSTSTOREKEY").toCharArray();
			if (LOGGER.isDebugEnabled()) {
			    LOGGER.debug("HSMClient(properties) fetching the keystore from classpath");
			}
			System.out.println("HSMClient --> Keystore filename:: "+filename_keystore);
			System.out.println("HSMClient --> Truststore filename:: "+filename_truststore);
			LOGGER.info("HSMClient(properties) fetching the keystore and truststore from classpath");
			System.out.println("HSMClient(properties) fetching the keystore from classpath");
			if(null == keyStore) {
				LOGGER.debug("Get Keystore object");
			    keyStore = getKeyStore(filename_keystore, keystorePwdCharArray);
			}
			if(null == trustStore) {
				LOGGER.debug("Get Truststore object");
			    trustStore = getKeyStore(filename_truststore, truststorePwdCharArray);
			}
		    
		} catch (Exception e) {
			LOGGER.error("Exception: " + e.getStackTrace().toString());
		}
		LOGGER.debug("Exit from HSM properties");
	}

	public static String encrypt(String pin) {
		LOGGER.debug("In HSMClient encrypt method pin is : " + pin);
		String encryptedValue = null;

		try {

			LOGGER.debug("Call getInstance method of CryptoClientApi");
			// TODO Generate Keystore and Truststore for higher environment
			ICryptoClient instance = CryptoClientApi.getInstance(keyStore, trustStore, cashHsmBocUrl,keystorePwdCharArray,truststorePwdCharArray);

			LOGGER.debug("Create object of CryptoGatewayRequest");
			CryptoGatewayRequest objCryptoGatewayRequest = new CryptoGatewayRequest();
			LOGGER.debug("BusinessID : " + businessID);
			objCryptoGatewayRequest.setBusinessId(businessID);
			LOGGER.debug("PIN" + pin);
			objCryptoGatewayRequest.setSubject(pin);
			objCryptoGatewayRequest.setSystemId("SHM");
			
			//Generate Unique reference number
			String urn = generateReferenceNumber();
			LOGGER.debug("URN : " + urn);
			objCryptoGatewayRequest.setUrn(urn);
			LOGGER.debug("Login ID : " + loginID);
			objCryptoGatewayRequest.setUserId(loginID);

			LOGGER.debug("Call encrypt method of CryptoGatewayRequest");
			CryptoGatewayResponse objCryptoGatewayResponse = instance.encrypt(objCryptoGatewayRequest);
			LOGGER.debug("Response from CryptoGateway : " + objCryptoGatewayResponse.getStatus());
			encryptedValue = objCryptoGatewayResponse.getSubject();

			LOGGER.debug("encrypted pin: " + encryptedValue);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return encryptedValue;
	}
	
	private static String generateReferenceNumber() {
			// Set<String> numbers = new HashSet<String>();
			String generatedString = RandomStringUtils.random(6,
					false, true);
			if (generatedString.length() != 6 || generatedString.startsWith("-")) {
				// System.out.println("failure at " + count);
				generateReferenceNumber();
			}
			return generatedString;
		}

	@SuppressWarnings("unused")
	private static KeyStore getKeyStore(String filenm, char[] keystorePwdCharArray)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Entry HSMClient getKeyStore()fetching the keystore from classpath");
		}
		LOGGER.info("Entry HSMClient getKeyStore() fetching the keystore from classpath");
		System.out.println("Entry HSMClient getKeyStore() fetching the keystore from classpath");
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = null;
		System.out.println("getKeyStore --> filenm:: " + filenm);
		try {

			resourceAsStream = HSMClient.class.getResourceAsStream(filenm);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("HSMClient getKeyStore() after getResourceAsStream");
			}
			LOGGER.info("HSMClient getKeyStore() after getResourceAsStream");
			System.out.println("HSMClient getKeyStore() after getResourceAsStream");
			ks.load(resourceAsStream, keystorePwdCharArray);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("HSMClient getKeyStore() kestore loaded successfully");
			}
			LOGGER.info("HSMClient getKeyStore() kestore loaded successfully");
			System.out.println("HSMClient getKeyStore() kestore loaded successfully");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		} finally {
			if (resourceAsStream != null) {
				resourceAsStream.close();
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Exit HSMClient getKeyStore()");
		}
		LOGGER.info("Exit HSMClient getKeyStore()");
		System.out.println("Exit HSMClient getKeyStore()");
		return ks;
	}

	public static String decrypt(KeyStore myStore, String encryptedString) {
		Key desKey = null;
		Cipher desDecCipher = null;
		byte[] iv = null;
		boolean needParms = true;
		AlgorithmParameters lunaParams = null;
		String decryptedValue = null;
		try {
			desKey = myStore.getKey(alias, passwd.toCharArray());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("DESKey is " + desKey);
			}
			desDecCipher = Cipher.getInstance(encryptionMethod, provider);
			iv = desDecCipher.getIV();
			if (iv == null && needParms) {
				// is AES ok for any secret key?
				lunaParams = AlgorithmParameters.getInstance(ALGORITHM, provider);
				IvParameterSpec IV8 = new IvParameterSpec(
						new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 });
				lunaParams.init(IV8);
			}
			desDecCipher.init(Cipher.DECRYPT_MODE, desKey, lunaParams);
			byte[] encryptedBytes = Hex.decode(encryptedString);
			byte[] decryptedbytes = desDecCipher.doFinal(encryptedBytes);
			decryptedValue = new String(decryptedbytes);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return decryptedValue;

	}

}
