package com.barclays.bmg.dto;

import com.barclays.bmg.dto.BaseDomainDTO;
import com.barclays.bmg.dto.CASAAccountDTO;

public class StatementRequestDTO extends BaseDomainDTO {

    private static final long serialVersionUID = 8337138977584082852L;

    private CASAAccountDTO account;

    private String fromDate;
    private String toDate;
    // private String branchCode;
    // private String branchName;
    private Long transactionDateTime;

    public String getFromDate() {
	return fromDate;
    }

    public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
    }

    public String getToDate() {
	return toDate;
    }

    public void setToDate(String toDate) {
	this.toDate = toDate;
    }

    public void setTransactionDateTime(Long transactionDateTime) {
	this.transactionDateTime = transactionDateTime;
    }

    public CASAAccountDTO getAccount() {
	return account;
    }

    public void setAccount(CASAAccountDTO account) {
	this.account = account;
    }

    /**
     * @return the transactionDateTime
     */
    public long getTransactionDateTime() {
	return transactionDateTime;
    }

    /**
     * @param transactionDateTime
     *            the transactionDateTime to set
     */
    public void setTransactionDateTime(long transactionDateTime) {
	this.transactionDateTime = transactionDateTime;
    }

    public void reset() {
	account = null;
	this.fromDate = null;
	this.toDate = null;
    }

}
