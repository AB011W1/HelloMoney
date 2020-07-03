package com.barclays.bmg.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class InvoiceDetails implements Serializable {

    private static final long serialVersionUID = 1L;


	@JsonProperty
	private LinkedHashMap<String, String> probaseDetails = new LinkedHashMap<String, String>();
	private List<String> bundleLife = new ArrayList<String>();
	//Ghana Data Bundle
	private LinkedHashMap<String, String>  invoiceRefNo = new LinkedHashMap<String, String>();


	public LinkedHashMap<String, String> getInvoiceRefNo() {
		return invoiceRefNo;
	}

	public void setInvoiceRefNo(LinkedHashMap<String, String> invoiceRefNo) {
		this.invoiceRefNo = invoiceRefNo;
	}

	public List<String> getBundleLife() {
		return bundleLife;
	}

	public void setBundleLife(List<String> bundleLife) {
		this.bundleLife = bundleLife;
	}

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
