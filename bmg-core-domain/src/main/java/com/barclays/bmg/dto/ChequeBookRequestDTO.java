package com.barclays.bmg.dto;

import java.util.Date;

public class ChequeBookRequestDTO extends BaseDomainDTO {

    private static final long serialVersionUID = -315637646368048555L;
    private CASAAccountDTO account;
    private String bookType;
    private String bookSize;
    // Add by dale for new field delivery type
    private String branchCode;
    private String branchName;
    private Date transactionDateTime;

    public String getBranchName() {
	return branchName;
    }

    public void setBranchName(String branchName) {
	this.branchName = branchName;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getBookType() {
	return bookType;
    }

    public void setBookType(String bookType) {
	this.bookType = bookType;
    }

    public String getBookSize() {
	return bookSize;
    }

    public void setBookSize(String bookSize) {
	this.bookSize = bookSize;
    }

    public CASAAccountDTO getAccount() {
	return account;
    }

    public void setAccount(CASAAccountDTO account) {
	this.account = account;
    }

    public void reset() {
	account = null;
	this.bookSize = null;
	this.bookType = null;
	this.branchCode = null;
	this.branchName = null;
    }

    public Date getTransactionDateTime() {
	return (Date) transactionDateTime.clone();
    }

    public void setTransactionDateTime(Date transactionDateTime) {
	this.transactionDateTime = (Date) transactionDateTime.clone();
    }

}
