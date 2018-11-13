package com.barclays.bmg.json.model;

import java.io.Serializable;

public class MaturityInstructionJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3004114459752099340L;

	private String pri = "";
	private String intrst = "";
	public String getPri() {
		return pri;
	}
	public void setPri(String pri) {
		this.pri = pri;
	}
	public String getIntrst() {
		return intrst;
	}
	public void setIntrst(String intrst) {
		this.intrst = intrst;
	}

}
