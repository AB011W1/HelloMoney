package com.barclays.bmg.mvc.command.accountdetails;

public class AccountDetailsCommand {

    private String actNo;

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public void setAccountNumber(String accountNumber) {
	this.actNo = accountNumber;
    }

}
