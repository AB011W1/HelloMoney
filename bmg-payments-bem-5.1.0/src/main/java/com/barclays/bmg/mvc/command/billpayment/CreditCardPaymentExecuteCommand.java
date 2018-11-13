package com.barclays.bmg.mvc.command.billpayment;

public class CreditCardPaymentExecuteCommand {

    private String orgCode;
    private String crdNo;
    private String crCardActNo;
    private String frActNo;// casaActNo;
    private String creditAccountTypeCode;
    private String beneficiaryName;
    private String beneficiaryBranchCode;

    private String actNo;

    private String txnRefNo;

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public String getOrgCode() {
	return orgCode;
    }

    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

    public String getCreditAccountTypeCode() {
	return creditAccountTypeCode;
    }

    public void setCreditAccountTypeCode(String creditAccountTypeCode) {
	this.creditAccountTypeCode = creditAccountTypeCode;
    }

    public String getBeneficiaryName() {
	return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
	this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryBranchCode() {
	return beneficiaryBranchCode;
    }

    public void setBeneficiaryBranchCode(String beneficiaryBranchCode) {
	this.beneficiaryBranchCode = beneficiaryBranchCode;
    }

    public String getCrdNo() {
	return crdNo;
    }

    public void setCrdNo(String crdNo) {
	this.crdNo = crdNo;
    }

    public String getCrCardActNo() {
	return crCardActNo;
    }

    public void setCrCardActNo(String crCardActNo) {
	this.crCardActNo = crCardActNo;
    }

    public String getFrActNo() {
	return frActNo;
    }

    public void setFrActNo(String frActNo) {
	this.frActNo = frActNo;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

}
