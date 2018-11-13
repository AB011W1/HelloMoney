package com.barclays.bmg.dto;

import java.util.Date;

public abstract class TransactionHistoryDTO extends BaseDomainDTO {

    private String transactionType;
    private Integer historyPeriod = Integer.valueOf(-3);
    private CustomerAccountDTO fromAccount;
    private String status;
    private String transactionReferenceNumber;
    private Date transactionDate;
    private Amount amount;
    private CustomerAccountDTO customerAccountDTO;
    private BeneficiaryDTO beneficiary;
    private String chargesDebitMode;
    private Charges charges;
    private CustomerAccountDTO chargeDebitedAccount;

    private String billerId;
    private String billerName;

    public String getTransactionType() {
	return transactionType;
    }

    public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
    }

    public abstract String getType();

    public Integer getHistoryPeriod() {
	return historyPeriod;
    }

    public void setHistoryPeriod(Integer historyPeriod) {
	this.historyPeriod = historyPeriod;
    }

    public CustomerAccountDTO getFromAccount() {
	return fromAccount;
    }

    public void setFromAccount(CustomerAccountDTO fromAccount) {
	this.fromAccount = fromAccount;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getTransactionReferenceNumber() {
	return transactionReferenceNumber;
    }

    public void setTransactionReferenceNumber(String transactionReferenceNumber) {
	this.transactionReferenceNumber = transactionReferenceNumber;
    }

    public Date getTransactionDate() {
	return (Date) transactionDate.clone();
    }

    public void setTransactionDate(Date transactionDate) {
	this.transactionDate = (Date) transactionDate.clone();
    }

    public Amount getAmount() {
	return amount;
    }

    public void setAmount(Amount amount) {
	this.amount = amount;
    }

    public CustomerAccountDTO getCustomerAccountDTO() {
	return customerAccountDTO;
    }

    public void setCustomerAccountDTO(CustomerAccountDTO customerAccountDTO) {
	this.customerAccountDTO = customerAccountDTO;
    }

    public BeneficiaryDTO getBeneficiary() {
	return beneficiary;
    }

    public void setBeneficiary(BeneficiaryDTO beneficiary) {
	this.beneficiary = beneficiary;
    }

    public String getChargesDebitMode() {
	return chargesDebitMode;
    }

    public void setChargesDebitMode(String chargesDebitMode) {
	this.chargesDebitMode = chargesDebitMode;
    }

    public Charges getCharges() {
	return charges;
    }

    public void setCharges(Charges charges) {
	this.charges = charges;
    }

    public CustomerAccountDTO getChargeDebitedAccount() {
	return chargeDebitedAccount;
    }

    public void setChargeDebitedAccount(CustomerAccountDTO chargeDebitedAccount) {
	this.chargeDebitedAccount = chargeDebitedAccount;
    }

    public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    public String getBillerName() {
	return billerName;
    }

    public void setBillerName(String billerName) {
	this.billerName = billerName;
    }

}
