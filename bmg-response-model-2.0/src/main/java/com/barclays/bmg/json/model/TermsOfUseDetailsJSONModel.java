package com.barclays.bmg.json.model;

import java.io.Serializable;

import com.barclays.bmg.json.response.BMBPayloadData;

public class TermsOfUseDetailsJSONModel extends BMBPayloadData
		implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = -351981933995203527L;

	protected String termsAnCond = "";

	public TermsOfUseDetailsJSONModel(String termsAnCond) {
		super();
		this.termsAnCond = termsAnCond;

	}

	public TermsOfUseDetailsJSONModel() {
		super();
	}

	public String getTermsAnCond() {
		return termsAnCond;
	}

	public void setTermsAnCond(String termsAnCond) {
		this.termsAnCond = termsAnCond;
	}


}
