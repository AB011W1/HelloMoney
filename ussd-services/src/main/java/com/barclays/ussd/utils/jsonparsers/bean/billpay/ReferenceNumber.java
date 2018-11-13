package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferenceNumber implements Serializable {

	/**
	 *CR82 Changes
	 */

	@JsonProperty
	private String lndLnNo;
	@JsonProperty
	private String phNo;
	@JsonProperty
	private String mobNo;
	@JsonProperty
	private String displayLabel;
	@JsonProperty
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
