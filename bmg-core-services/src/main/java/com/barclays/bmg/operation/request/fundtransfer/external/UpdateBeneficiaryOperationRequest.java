package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class UpdateBeneficiaryOperationRequest extends RequestContext {
    private String beneficiaryId;
    private String payeeId;
    private String payeeType;
    private String accountNumber;
    private String branchCode;
    private String bankName;
    private String beneficiaryName;
    private String beneficiaryNickName;
    private BeneficiaryDTO beneficiaryDTO;
    private String scndLvlAuthTyp;
    private boolean scndLvlauthReq;
    private String bankCode;
    private String branchName;
    private String address;
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String beneficiaryOldNickName;


    public String getBranchName() {
	return branchName;
    }

    public void setBranchName(String branchName) {
	this.branchName = branchName;
    }

    public String getBankCode() {
	return bankCode;
    }

    public void setBankCode(String bankCode) {
	this.bankCode = bankCode;
    }

    public boolean isScndLvlauthReq() {
	return scndLvlauthReq;
    }

    public void setScndLvlauthReq(boolean scndLvlauthReq) {
	this.scndLvlauthReq = scndLvlauthReq;
    }

    public String getScndLvlAuthTyp() {
	return scndLvlAuthTyp;
    }

    public void setScndLvlAuthTyp(String scndLvlAuthTyp) {
	this.scndLvlAuthTyp = scndLvlAuthTyp;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public String getPayeeId() {
	return payeeId;
    }

    public void setPayeeId(String payeeId) {
	this.payeeId = payeeId;
    }

    public String getPayeeType() {
	return payeeType;
    }

    public void setPayeeType(String payeeType) {
	this.payeeType = payeeType;
    }

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getBankName() {
	return bankName;
    }

    public void setBankName(String bankName) {
	this.bankName = bankName;
    }

    public String getBeneficiaryName() {
	return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
	this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryNickName() {
	return beneficiaryNickName;
    }

    public void setBeneficiaryNickName(String beneficiaryNickName) {
	this.beneficiaryNickName = beneficiaryNickName;
    }

    public String getBeneficiaryId() {
	return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
	this.beneficiaryId = beneficiaryId;
    }

	public String getBeneficiaryOldNickName() {
		return beneficiaryOldNickName;
	}

	public void setBeneficiaryOldNickName(String beneficiaryOldNickName) {
		this.beneficiaryOldNickName = beneficiaryOldNickName;
	}



}
