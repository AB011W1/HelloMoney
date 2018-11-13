package com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class RetrieveInternalNonRegisteredPayeeInfoOperationRequest extends RequestContext {

    private String accountNumber;
    private String branchCode;
    private String bankName;
    private String beneficiaryName;
    private String beneficiaryNickName;
    private BeneficiaryDTO beneficiaryDTO;
    private String scndLvlAuthTyp;
    private boolean scndLvlauthReq;

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

}
