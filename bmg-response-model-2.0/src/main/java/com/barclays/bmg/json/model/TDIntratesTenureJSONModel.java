package com.barclays.bmg.json.model;

import java.io.Serializable;

public class TDIntratesTenureJSONModel implements Serializable {

	private static final long serialVersionUID = -4726371070262902012L;

	private String tenType = "";
	private String tenMonth = "";
	private String tenDay = "";
	private String intrate = "";

	public String getTenType() {
		return tenType;
	}

	public void setTenType(String tenType) {
		this.tenType = tenType;
	}

	public String getTenMonth() {
		return tenMonth;
	}

	public void setTenMonth(String tenMonth) {
		this.tenMonth = tenMonth;
	}

	public String getTenDay() {
		return tenDay;
	}

	public void setTenDay(String tenDay) {
		this.tenDay = tenDay;
	}

	public String getIntrate() {
		return intrate;
	}

	public void setIntrate(String intrate) {
		this.intrate = intrate;
	}

}
