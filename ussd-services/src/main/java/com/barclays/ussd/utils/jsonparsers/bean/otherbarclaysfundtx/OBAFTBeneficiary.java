package com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OBAFTBeneficiary {

	@JsonProperty
	private String payId;

	@JsonProperty
	private String actNo;
	/**
	 * disLbl
	 */
	@JsonProperty
	String disLbl;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	/**
	 * @param disLbl the disLbl to set
	 */
	public void setDisLbl(String disLbl) {
		this.disLbl = disLbl;
	}

	/**
	 * @return the disLbl
	 */
	public String getDisLbl() {
		return disLbl;
	}

}
