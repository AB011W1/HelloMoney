package com.barclays.bcag;

public class TestTHMDecrypt {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

	String encryptedUserPin = "01303032303134303631373134343235347C4C5EBC3F9A010100497EA0F5023E7CB41DC1435D468FC77B5027B051F5F12039F5598B44CD8876BC";

	System.out.println("\nDecrypt with THM_PIN_decrypt");

	String s = THMBCAGClient.decryptPin(encryptedUserPin);
	System.out.println("Decrypted Plaintext String : \"" + s + "\"");

	// **** Close BCAG ****
	THMBCAGClient.closeBCAGClient();

    }

}
