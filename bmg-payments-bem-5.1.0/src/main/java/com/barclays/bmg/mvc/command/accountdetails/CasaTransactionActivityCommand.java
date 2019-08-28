package com.barclays.bmg.mvc.command.accountdetails;

public class CasaTransactionActivityCommand {

    private String actNo;
    private String brnCde;
    private String days;

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public void setAccountNumber(String accountNumber) {
	this.actNo = accountNumber;
    }

    public String getDays() {
	return days;
    }

    public void setDays(String days) {
	this.days = days;
    }

    public String getBrnCde() {
	return brnCde;
    }

    public void setBrnCde(String brnCde) {
	this.brnCde = brnCde;
    }

}
