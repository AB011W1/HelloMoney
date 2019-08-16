package com.barclays.bmg.mvc.command.accountdetails;

public class CreditCardUnbilledTransCommand {

    private String actNo;
    private String crdNo;

    public String getCrdNo() {
	return crdNo;
    }

    public void setCrdNo(String crdNo) {
	this.crdNo = crdNo;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

}
