package com.barclays.bmg.dto;

import java.io.Serializable;

public class UBPBusinessIdsDTO implements Serializable {
	private static final long serialVersionUID = 3496218980546099162L;

	private String paramValue;

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
