package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualCustomerBasicJsonRes {

	@JsonProperty
	private String IndividualName;
	@JsonProperty
    private List<BankJsonRes> BankJsonResList;

	public String getIndividualName() {
		return IndividualName;
	}

	public void setIndividualName(String individualName) {
		IndividualName = individualName;
	}

	public List<BankJsonRes> getBankJsonResList() {
		return BankJsonResList;
	}

	public void setBankJsonResList(List<BankJsonRes> bankJsonResList) {
		BankJsonResList = bankJsonResList;
	}








}
