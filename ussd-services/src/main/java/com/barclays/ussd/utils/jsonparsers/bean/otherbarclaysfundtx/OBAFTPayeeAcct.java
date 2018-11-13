package com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OBAFTPayeeAcct
{
	@JsonProperty
	private String payCat;

	@JsonProperty
	private List<OBAFTBeneficiary> bnfLst;

	public List<OBAFTBeneficiary> getBnfLst() {
		return bnfLst;
	}

	public void setBnfLst(List<OBAFTBeneficiary> bnfLst) {
		this.bnfLst = bnfLst;
	}

	public String getPayCat() {
		return payCat;
	}

	public void setPayCat(String payCat) {
		this.payCat = payCat;
	}

}
