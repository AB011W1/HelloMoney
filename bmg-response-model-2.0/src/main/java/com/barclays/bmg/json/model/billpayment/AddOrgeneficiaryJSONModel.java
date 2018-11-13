package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import com.barclays.bmg.dto.BeneficiaryDTO;

public class AddOrgeneficiaryJSONModel   implements Serializable {


	private String billerId;
	private String payNckNam;
	private String billerName;
 	private String actNo;
	private String addr;
	private String cntry;
	private String billerRefNum;

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getPayNckNam() {
		return payNckNam;
	}

	public void setPayNckNam(String payNckNam) {
		this.payNckNam = payNckNam;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}


	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCntry() {
		return cntry;
	}

	public void setCntry(String cntry) {
		this.cntry = cntry;
	}

	public String getBillerRefNum() {
		return billerRefNum;
	}

	public void setBillerRefNum(String billerRefNum) {
		this.billerRefNum = billerRefNum;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = -5436515665377036572L;

	public AddOrgeneficiaryJSONModel(BeneficiaryDTO beneficiaryDTO) {



		this.billerId = beneficiaryDTO.getBillerId();
		this.payNckNam= beneficiaryDTO.getPayeeNickname();
		this.billerName= beneficiaryDTO.getBillerName();
		//this.mkdActNo= beneficiaryDTO.getBillRefNo();
		this.actNo= beneficiaryDTO.getDestinationAccountNumber();
		this.addr= beneficiaryDTO.getDestinationAddressLine1();
		this.cntry= beneficiaryDTO.getBillerId();
		this.billerRefNum= beneficiaryDTO.getBillRefNo();
	}



}
