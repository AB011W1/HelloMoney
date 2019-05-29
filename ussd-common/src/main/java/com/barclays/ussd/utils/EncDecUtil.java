package com.barclays.ussd.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ibm.xml.crypto.util.Base64;

public class EncDecUtil {
	private static final String key = "aesEncryptionKey";
	private static final String initVector = "encryptionIntVec";

	public String encrypt(String pass) throws Exception
	{
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(pass.getBytes());
			return Base64.encode(encrypted);//encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public String decrypt(String encryptString) throws Exception
	{
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decode(encryptString));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
