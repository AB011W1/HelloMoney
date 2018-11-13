package com.barclays.bmg.type;

/**
 * DTO class contains key value pair.
 * 
 * @author
 */

public class KeyPair {
    private String key;

    private String value;

    private String screen;

    /**
     * Construct key pair.
     * 
     * @param key
     * @param value
     */
    public KeyPair(String key, String value) {
	this.key = key;
	this.value = value;
    }

    public KeyPair(String key, String value, String screen) {
	this.key = key;
	this.value = value;
	this.screen = screen;
    }

    /**
     * @return the key
     */
    public String getKey() {
	return key;
    }

    /**
     * @return the value
     */
    public String getValue() {
	return value;
    }

    /**
     * @param screen
     *            the screen to set
     */
    public KeyPair setScreen(String screen) {
	this.screen = screen;
	return this;
    }

    /**
     * @return the screen
     */
    public String getScreen() {
	return screen;
    }

    @Override
    public String toString() {
	return "key=" + key + " value=" + value + " screen=" + screen;
    }
}
