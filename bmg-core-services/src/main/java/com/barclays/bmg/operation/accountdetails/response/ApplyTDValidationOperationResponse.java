package com.barclays.bmg.operation.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class ApplyTDValidationOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1695401155047913117L;
    private CustomerAccountDTO sourceCustomerAccountDTO;
    private String acctNo;
    private String transactionRefNum;
    private String depositAmount;
    private String tenureMonths;
    private String tenureDays;
    private String productId;

    public String getAcctNo() {
	return acctNo;
    }

    public void setAcctNo(String acctNo) {
	this.acctNo = acctNo;
    }

    public String getDepositAmount() {
	return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
	this.depositAmount = depositAmount;
    }

    public String getTenureMonths() {
	return tenureMonths;
    }

    public void setTenureMonths(String tenureMonths) {
	this.tenureMonths = tenureMonths;
    }

    public String getTenureDays() {
	return tenureDays;
    }

    public void setTenureDays(String tenureDays) {
	this.tenureDays = tenureDays;
    }

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public void setSourceCustomerAccountDTO(CustomerAccountDTO sourceCustomerAccountDTO) {
	this.sourceCustomerAccountDTO = sourceCustomerAccountDTO;
    }

    public CustomerAccountDTO getSourceCustomerAccountDTO() {
	return sourceCustomerAccountDTO;
    }

    public void setTransactionRefNum(String transactionRefNum) {
	this.transactionRefNum = transactionRefNum;
    }

    public String getTransactionRefNum() {
	return transactionRefNum;
    }

    public void setProductId(String productId) {
	this.productId = productId;
    }

    public String getProductId() {
	return productId;
    }
}