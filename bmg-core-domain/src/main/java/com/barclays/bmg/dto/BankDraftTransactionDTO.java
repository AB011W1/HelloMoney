package com.barclays.bmg.dto;

public class BankDraftTransactionDTO extends TransactionDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = -6271644848698546819L;
    private String deliveryType;
    private String draftType;
    private ContactInfoDTO remitterContactInfoDTO;
    private String payableAtCode;
    private String payableAtName;
    private String branchCode;
    private String branchName;

    public String getDeliveryType() {
	return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
	this.deliveryType = deliveryType;
    }

    public String getDraftType() {
	return draftType;
    }

    public void setDraftType(String draftType) {
	this.draftType = draftType;
    }

    public ContactInfoDTO getRemitterContactInfoDTO() {
	return remitterContactInfoDTO;
    }

    public void setRemitterContactInfoDTO(ContactInfoDTO contactInfoDTO) {
	this.remitterContactInfoDTO = contactInfoDTO;
    }

    public String getPayableAtCode() {
	return payableAtCode;
    }

    public void setPayableAtCode(String payableAtCode) {
	this.payableAtCode = payableAtCode;
    }

    public String getPayableAtName() {
	return payableAtName;
    }

    public void setPayableAtName(String payableAtName) {
	this.payableAtName = payableAtName;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getBranchName() {
	return branchName;
    }

    public void setBranchName(String branchName) {
	this.branchName = branchName;
    }

}
