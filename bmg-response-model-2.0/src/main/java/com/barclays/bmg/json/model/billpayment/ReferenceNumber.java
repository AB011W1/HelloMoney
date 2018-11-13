package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class ReferenceNumber implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7810943753128817616L;

	private String lndLnNo;

	private String phNo;

	private String mobNo;

	private String displayLabel;

	private String refNo;


	public String getLndLnNo() {
		return lndLnNo;
	}

	public void setLndLnNo(String lndLnNo) {
		this.lndLnNo = lndLnNo;
	}

	public String getPhNo() {
		return phNo;
	}

	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

}
