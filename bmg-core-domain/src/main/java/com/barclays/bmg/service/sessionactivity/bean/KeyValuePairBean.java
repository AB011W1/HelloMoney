package com.barclays.bmg.service.sessionactivity.bean;

import java.io.Serializable;

public class KeyValuePairBean implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    private String key;
    private Object value;

    public KeyValuePairBean(String key, Object value) {
	this.key = key;
	this.value = value;
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public Object getValue() {
	return value;
    }

    public void setValue(Object value) {
	this.value = value;
    }

}
