package com.barclays.bmg.json.model;

import java.io.Serializable;

public class TenureJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4726371070262902012L;

	private String year = "";
	private String mnth = "";
	private String days = "";

	public TenureJSONModel() {
		super();

	}

	public TenureJSONModel(String year, String mnth, String days) {
		super();
		this.year = year;
		this.mnth = mnth;
		this.days = days;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMnth() {
		return mnth;
	}
	public void setMnth(String mnth) {
		this.mnth = mnth;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}


}
