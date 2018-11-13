package com.barclays.bmg.audit.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ScreenDataDTO implements Serializable {
    private String screenId;

    private List<FieldDataDTO> fieldList = new ArrayList<FieldDataDTO>();

    public void addField(FieldDataDTO field) {
	fieldList.add(field);
    }

    public String getScreenId() {
	return screenId;
    }

    public void setScreenId(String screenId) {
	this.screenId = screenId;
    }

    public List<FieldDataDTO> getFieldList() {
	return fieldList;
    }

    public void setFieldList(List<FieldDataDTO> fieldList) {
	this.fieldList = fieldList;
    }
}
