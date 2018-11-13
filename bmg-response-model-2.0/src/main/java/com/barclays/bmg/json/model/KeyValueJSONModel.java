package com.barclays.bmg.json.model;

import java.io.Serializable;

public class KeyValueJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3200258595676001039L;

	String key;

	String value;



	public KeyValueJSONModel(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



}
