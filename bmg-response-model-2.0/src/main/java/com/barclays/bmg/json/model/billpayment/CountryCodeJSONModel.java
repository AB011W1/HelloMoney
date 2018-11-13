package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class CountryCodeJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8300237288909609055L;
	private String cntrCde;
	private String cntrNam;
	public String getCntrCde() {
		return cntrCde;
	}
	public void setCntrCde(String cntrCde) {
		this.cntrCde = cntrCde;
	}
	public String getCntrNam() {
		return cntrNam;
	}
	public void setCntrNam(String cntrNam) {
		this.cntrNam = cntrNam;
	}





}
