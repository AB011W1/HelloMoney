package com.barclays.bmg.ecrime;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import com.barclays.bmg.type.KeyPair;

/**
 * TODO Class/Interface description.
 * 
 * @author
 */
public abstract class EcrimeUtils {

    public static String getMaskValue(KeyPair keyPair, List<String> maskRules) {

	if (maskRules != null && maskRules.contains(keyPair.getKey())) {
	    return formatValue(keyPair.getValue());
	} else {
	    return keyPair.getValue();
	}
    }

    public static String hash(String text) {
	if (text != null) {
	    MessageDigest md;
	    try {
		md = MessageDigest.getInstance("SHA-512");
		md.update(text.getBytes());
		byte[] b = md.digest();
		return Arrays.toString(b);
	    } catch (NoSuchAlgorithmException e) {

	    }
	}
	return "****";
    }

    public static String formatValue(Object value) {

	return "******";
    }

}
