package com.barclays.bmg.json.model;

import java.util.List;

public class IndividualCustomerBasicModel {

	private String IndividualName;

    private List<BankModel> bankModelList;

	public String getIndividualName() {
		return IndividualName;
	}

	public List<BankModel> getBankModelList() {
		return bankModelList;
	}

	public void setBankModelList(List<BankModel> bankModelList) {
		this.bankModelList = bankModelList;
	}

	public void setIndividualName(String individualName) {
		IndividualName = individualName;
	}







}
