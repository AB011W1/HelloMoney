package com.barclays.bcag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

import org.apache.log4j.Logger;

import com.cryptomathic.hsmportal.util.Hex;
import com.safenetinc.luna.LunaSlotManager;

public class HSMClient {
	private static int slot = 0;
	private static String passwd = null;
	private static String provider = null;
	private static String keystoreProvider = null;
	private static String alias = null;
	private static String encryptionMethod = null;
	public static SealedObject so = null;
	private static final Logger LOGGER = Logger.getLogger(HSMClient.class);
	private static final String ALGORITHM = "DESede";
	private static LunaSlotManager slotManager;
	//TODO

	private static final String partitionLabel = "SHM3DES1";//prod-SHM3DES1,o.Env - SHM-3DES

	public static void setProperties(Properties properties) {
		try {
			LOGGER.debug("Set HSM properties");
			passwd = properties.getProperty("CASHSENDPASS");
			provider = properties.getProperty("CASHSENDPROVIDER");
			keystoreProvider = properties.getProperty("CASHKEYSTPROVIDER");
			alias = properties.getProperty("CASHSENDALIAS");
			encryptionMethod = properties.getProperty("ENCRYPTIONMETHOD");
			slot = Integer.parseInt(properties.getProperty("SLOT"));
		} catch (Exception e) {
			LOGGER.error("Exception: " + e.getStackTrace());
		}
		LOGGER.debug("Exit from HSM properties");
	}

	public static String encrypt(String pin) {
		LOGGER.debug("In HSMClient encrypt method pin is : " + pin);
		Key desKey = null;
		Cipher desEncCipher = null;
		byte[] iv = null;
		boolean needParms = true;
		AlgorithmParameters lunaParams = null;
		String encryptedValue = null;
		KeyStore myStore = null;

		try {
			LOGGER.debug("LunaSlotManager.getInstance() call");
			slotManager = LunaSlotManager.getInstance();
			LOGGER.debug("slotmanager = " + slotManager);
	    	int slotNumber = 0;
	    	if(null != slotManager)
	    		slotNumber = slotManager.findSlotFromLabel(partitionLabel);
	    	if (slotNumber < 0) {
	            throw new KeyStoreException("Unable to find slot by label.");
	        }
			LOGGER.debug("Get byte array input stream");
			ByteArrayInputStream is1 = new ByteArrayInputStream(("slot:" + slotNumber).getBytes());

			LOGGER.debug("Slot" + slotNumber + "found.Get instance of keystoreprovider");
			myStore = KeyStore.getInstance(keystoreProvider);
			LOGGER.debug("Load store");
			myStore.load(is1, passwd.toCharArray());
			desKey = myStore.getKey(alias, passwd.toCharArray());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("DESKey is " + desKey);
			}

			desEncCipher = Cipher.getInstance(encryptionMethod, provider);
			LOGGER.debug("Getting cipher instance");
			needParms = false;
			iv = desEncCipher.getIV();
			if (iv == null && needParms) {
				lunaParams = AlgorithmParameters
						.getInstance(ALGORITHM, provider);
				IvParameterSpec IV8 = new IvParameterSpec(new byte[8]);
				lunaParams.init(IV8);
			}
			LOGGER.debug("Call init method of Cipher with ENCRYPT_MODE "
					+ Cipher.ENCRYPT_MODE);
			desEncCipher.init(Cipher.ENCRYPT_MODE, desKey, lunaParams);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Plaintext bytes");
			}
			byte[] encryptedbytes = null;
			encryptedbytes = desEncCipher.doFinal(Hex.decode(pin));
			LOGGER.debug("encrypted bytes : " + encryptedbytes);
			encryptedValue = Hex.encode(encryptedbytes);
			LOGGER.debug("encrypted pin: " + encryptedValue);
		} catch (KeyStoreException kse) {
			LOGGER.error("Unable to create keystore object");
			System.out.println("Unable to create keystore object");

		} catch (NoSuchAlgorithmException nsae) {
			LOGGER.error("Unexpected NoSuchAlgorithmException while loading keystore");
			System.out
					.println("Unexpected NoSuchAlgorithmException while loading keystore");

		} catch (CertificateException e) {
			LOGGER.error("Unexpected CertificateException while loading keystore");
			System.out
					.println("Unexpected CertificateException while loading keystore");

		} catch (IOException e) {
			// this should never happen
			LOGGER.error("Unexpected IOException while loading keystore");
			System.out
					.println("Unexpected IOException while loading keystore");

		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return encryptedValue;
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
				lunaParams = AlgorithmParameters
						.getInstance(ALGORITHM, provider);
				IvParameterSpec IV8 = new IvParameterSpec(new byte[] { 0x00,
						0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 });
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
