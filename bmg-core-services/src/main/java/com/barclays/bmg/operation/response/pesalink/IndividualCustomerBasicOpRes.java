package com.barclays.bmg.operation.response.pesalink;

import java.util.List;

public class IndividualCustomerBasicOpRes {

	private String IndividualName;

    private List<BankOpRes> bankOpResList;

	public String getIndividualName() {
		return IndividualName;
	}

	public List<BankOpRes> getBankOpResList() {
		return bankOpResList;
	}

	public void setBankOpResList(List<BankOpRes> bankOpResList) {
		this.bankOpResList = bankOpResList;
	}


	//Adding Customer name
	// Added By G01022861 on 30/09/2016

	public void setIndividualName(String individualName) {
		IndividualName = individualName;
	}
	// Ended





}
