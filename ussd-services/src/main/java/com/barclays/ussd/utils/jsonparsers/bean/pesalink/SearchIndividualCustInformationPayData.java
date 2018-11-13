package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIndividualCustInformationPayData {

    @JsonProperty
    private List<BankJsonRes> bankResList;

    @JsonProperty
	private String customerAccountNumber;

	@JsonProperty
	private String primaryFlag;

	//Added on 30/09/2016
	//Added By g01022861
	@JsonProperty
	private String individualName;


	public String getIndividualName() {
		return individualName;
	}
	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}
	//Ended
	public String getCustomerAccountNumber() {
		return customerAccountNumber;
	}
	public void setCustomerAccountNumber(String customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}
	public String getPrimaryFlag() {
		return primaryFlag;
	}
	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}
	public List<BankJsonRes> getBankResList() {
		return bankResList;
	}
	public void setBankResList(List<BankJsonRes> bankResList) {
		this.bankResList = bankResList;
	}


}
