package com.barclays.bmg.audit.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FieldDataDTO implements Serializable {

    private String fieldId;

    private String value;

    public FieldDataDTO() {
	super();
    }

    public FieldDataDTO(String fieldId, String value) {
	super();
	this.fieldId = fieldId;
	this.value = value;
    }

    public String getFieldId() {
	return fieldId;
    }

    public void setFieldId(String fieldId) {
	this.fieldId = fieldId;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }
}
