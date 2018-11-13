package com.barclays.bmg.json.model;

import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.operation.response.pesalink.IndividualCustomerBasicOpRes;
import com.barclays.bmg.operation.response.pesalink.SearchIndividualCustforDeDupCheckOperationResponse;


@SuppressWarnings("serial")
public class SearchIndividualCustforDeDupCheckJsonModel extends BMBPayloadData{


	private List<BankModel> bankResList;
	//Deregistration Info
	private String customerAccountNumber;
	private String primaryFlag;
	//Added by G01022861 on 30/09/2016
	private String individualName;


	public String getIndividualName() {
		return individualName;
	}

	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}

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

	public List<BankModel> getBankResList() {
		return bankResList;
	}

	public void setBankResList(List<BankModel> bankResList) {
		this.bankResList = bankResList;
	}






}