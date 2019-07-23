package com.barclays.bmg.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.codehaus.jackson.annotate.JsonProperty;

public class InvoiceDetails implements Serializable {

    private static final long serialVersionUID = 1L;


	@JsonProperty
	private LinkedHashMap<String, String> probaseDetails = new LinkedHashMap<String, String>();



	public LinkedHashMap<String, String> getProbaseDetails() {
		return probaseDetails;
	}

	public void setProbaseDetails(LinkedHashMap<String, String> probaseDetails) {
		this.probaseDetails = probaseDetails;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


}

