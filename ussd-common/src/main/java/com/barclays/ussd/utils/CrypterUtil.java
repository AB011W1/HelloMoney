package com.barclays.ussd.utils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

/**
 * Class CrypterUtil.
 * 
 * @author
 */
public class CrypterUtil {

    /** random. */
    private static SecureRandom random;

    /** Constant ALGORITHM. */
    private final static String ALGORITHM = "SHA1PRNG";

    private final static String AES_ALGORITHM = "AES";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CrypterUtil.class);

    /**
     * Gets values using method random number.
     * 
     * @return random number
     */
    public static String getRandomNumber() {
	StringBuffer sbf = new StringBuffer();
	for (int i = 0; i <= 15; i++) {
	    sbf.append(randomDigit());
	}
	return sbf.toString();
    }

    /**
     * Random digit.
     * 
     * @return as String
     */
    public static String randomDigit() {
	SecureRandom random = getSecureRandom();
	int index = Math.round((float) (random.nextDouble() * 10));
	if (index > 9) {
	    index = 9;
	}

	return String.valueOf(index);
    }

    /**
     * Gets values using method secure random.
     * 
     * @return secure random
     */
    private static SecureRandom getSecureRandom() {
	try {
	    if (random == null) {
		random = SecureRandom.getInstance(ALGORITHM);
	    }
	} catch (NoSuchAlgorithmException nsae) {
	    LOGGER.error(nsae.getMessage(), nsae);
	}
	return random;
    }

    /**
     * Encrypt.
     * 
     * @param ivBytes
     *            is a iv bytes
     * @param keyBytes
     *            is a key bytes
     * @param mes
     *            is a mes
     * @return as byte[]
     * @throws NoSuchAlgorithmException
     *             while enconter exception scenario
     * @throws NoSuchPaddingException
     *             while enconter exception scenario
     * @throws InvalidKeyException
     *             while enconter exception scenario
     * @throws InvalidAlgorithmParameterException
     *             while enconter exception scenario
     * @throws IllegalBlockSizeException
     *             while enconter exception scenario
     * @throws BadPaddingException
     *             while enconter exception scenario
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static byte[] encrypt(byte[] ivBytes, byte[] keyBytes, byte[] mes) throws NoSuchAlgorithmException, NoSuchPaddingException,
	    InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
	AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	SecretKeySpec newKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
	Cipher cipher = null;
	cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
	return cipher.doFinal(mes);
    }

    /**
     * 
     * @param nonce
     * @param msisdn
     * @param sessionId
     * @param message
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    // public static byte[] encrypt(String nonce, String msisdn, String sessionId, String message) throws NoSuchAlgorithmException,
    // NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
    // IOException {
    // return encrypt(nonce.getBytes(), generateSecretKey(msisdn, sessionId).getBytes(), message.getBytes(UTF_ENCODING));
    // }
    /**
     * Decrypt.
     * 
     * @param ivBytes
     *            is a iv bytes
     * @param keyBytes
     *            is a key bytes
     * @param bytes
     *            is a bytes
     * @return as byte[]
     * @throws NoSuchAlgorithmException
     *             while enconter exception scenario
     * @throws NoSuchPaddingException
     *             while enconter exception scenario
     * @throws InvalidKeyException
     *             while enconter exception scenario
     * @throws InvalidAlgorithmParameterException
     *             while enconter exception scenario
     * @throws IllegalBlockSizeException
     *             while enconter exception scenario
     * @throws BadPaddingException
     *             while enconter exception scenario
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException
     *             while enconter exception scenario
     */
    public static byte[] decrypt(byte[] ivBytes, byte[] keyBytes, byte[] bytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
	    InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException,
	    ClassNotFoundException {
	LOGGER.info("START:CrypterUtil  decrypt()");
	AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	SecretKeySpec newKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
	LOGGER.info("END:CrypterUtil  decrypt()");
	return cipher.doFinal(bytes);
    }

    /**
     * 
     * @param nonce
     * @param msisdn
     * @param sessionId
     * @param message
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    // public static byte[] decrypt(String nonce, String msisdn, String sessionId, byte[] bytes) throws NoSuchAlgorithmException,
    // NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
    // IOException, ClassNotFoundException {
    // return decrypt(nonce.getBytes(), generateSecretKey(msisdn, sessionId).getBytes(), bytes);
    // }
    /**
     * This method generates secret key that can be represented as a byte array and have no "key" parameters associated with them.
     * 
     * @param key
     * @return
     * @throws Exception
     */

    // private static String generateSecretKey(String msisdn, String sessionId) {
    // StringBuilder sb = new StringBuilder(msisdn).append(sessionId);
    // sb.setLength(16);
    // return sb.toString();
    // }
    // private static byte[] getRawKey(byte[] seed) throws Exception {
    // KeyGenerator kgen = KeyGenerator.getInstance("AES");
    // SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    // sr.setSeed(seed);
    // kgen.init(128, sr); // 192 and 256 bits may not be available
    // SecretKey skey = kgen.generateKey();
    // byte[] raw = skey.getEncoded();
    // return raw;
    // }
    //
    // private static final String ALGORITHM1 = "AES";
    // private static final String myEncryptionKey = "ThisIsSecurityKeysss";
    //
    // public static void main(String args[]) {
    //
    // try {
    //
    // System.out.println("getRawKey=" + getRawKey("fsdfsdfsdfsd".getBytes()));
    // System.out.println("getRawKey=" + getRawKey("fsdfsdfsdfsd".getBytes()));
    // System.out.println("getRawKey=" + getRawKey("fsdfsdfsdfsd".getBytes()));
    // System.out.println("getRawKey=" + getRawKey("fsdfsdfsdfsd".getBytes()));
    //
    // byte[] keyAsBytes;
    // keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
    // Key key0 = new SecretKeySpec(keyAsBytes, ALGORITHM1);
    // System.out.println("key=" + key0);
    //
    // Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    // cipher.init(Cipher.DECRYPT_MODE, key0, new IvParameterSpec("1234567890123456".getBytes()));
    // byte[] text = cipher.doFinal("fsdfsdfsdfsd".getBytes());
    //
    // String salt = "";
    // System.out.println("random: " + randomDigit());
    // for (int i = 0; i <= 15; i++) {
    // salt += randomDigit();
    // }
    // System.out.println("nonce: " + salt);
    // byte[] key = "770A8A65DA156D24".getBytes("UTF-8");
    // byte[] key1 = "770A8A65DA156D24".getBytes("UTF-8");
    // //
    // // Key secretKey = generateSecretKey(Base64.encodeBytes(key));
    // //
    // // byte[] encrypt = encrypt(
    // // key,
    // // key,
    // //
    // "opCde=OP0100&serVer=2.0&accessAccount=9050324906&userNo=1&channelInd=S&enterpriseSessionID=5bc4873c9f92e44c5239&manufacturer=Apple&model=iPhone&imei=123123123123&msisdn=123456&nickname=nickname1"
    // // .getBytes("UTF-8"));
    // //
    // // String decordedValue = null; // Base64.encodeBytes(encrypt); System.out.println("encrypt: " + decordedValue);
    // //
    // // byte[] decrypt = decrypt(key, key1, Base64.decode(decordedValue));
    // // String decrypted = new String(decrypt, "UTF-8");
    // // System.out.println("decrypted: " + decrypted);
    //
    // } catch (Exception e) { // TODO Auto-generated catch block
    // 
    // }
    // }
    public static void main(String[] args) {
	try {
	    // Get IV and Key value from Static class
	    String IV = "770A8A65DA156D24";
	    String KEY = "990A8A65DA156D24";
	    String USERNAMETEXT = "LOCAL:SHM_CASHSEND_test";
	    String PASSWORDTEXT = "0123456789Qwertyuiop!";

	    byte[] encrypt = null;
	    encrypt = encrypt(IV.getBytes(), KEY.getBytes(), USERNAMETEXT.getBytes());
	    String hexEncodedUserName = String.valueOf(Hex.encodeHex(encrypt));
	    encrypt = encrypt(IV.getBytes(), KEY.getBytes(), PASSWORDTEXT.getBytes());
	    String hexEncodePassword = String.valueOf(Hex.encodeHex(encrypt));
	    System.out.println(" * hexEncodedUserName =" + hexEncodedUserName + " \n * hexEncodePassword=" + hexEncodePassword);

	    byte[] byteArrayToDecrypt = null;
	    byte[] decrypt = null;

	    // byteArrayToDecrypt = Hex.decodeHex(hexEncodedUserName.toCharArray());
	    byteArrayToDecrypt = Hex.decodeHex("5b8929589827e7d242765f36971f630d34c4f6fdfdeebf1748ff5b89651c834f".toCharArray());
	    decrypt = decrypt(IV.getBytes(), KEY.getBytes(), byteArrayToDecrypt);
	    System.out.println(" * decrypted UserName =" + new String(decrypt));

	    // byteArrayToDecrypt = Hex.decodeHex(hexEncodePassword.toCharArray());
	    byteArrayToDecrypt = Hex.decodeHex("affeafb19041a1e425441d85d4e9381189c502c604749de3f1a6e1e677de9ed7".toCharArray());
	    decrypt = decrypt(IV.getBytes(), KEY.getBytes(), byteArrayToDecrypt);
	    System.out.println(" * decrypt Password =" + new String(decrypt));

	} catch (Exception e) {
	    

	}

    }

}