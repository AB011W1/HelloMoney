package com.barclays.bmg.service.response.pesalink;

import java.util.List;

public class IndividualCustomerBasicSerRes {

	private String IndividualName;
	private List<BankSerRes> bankSerResList;


	public String getIndividualName() {
		return IndividualName;
	}
	public void setIndividualName(String individualName) {
		IndividualName = individualName;
	}
	public List<BankSerRes> getBankSerResList() {
		return bankSerResList;
	}
	public void setBankSerResList(List<BankSerRes> bankSerResList) {
		this.bankSerResList = bankSerResList;
	}


}
