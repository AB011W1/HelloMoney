package com.barclays.bmg.mvc.command.accountdetails;

public class CreditCardStmtTransCommand {

    private String actNo;
    private String crdNo;
    private String bilDt;
    private String orgCode;

    public String getOrgCode() {
	return orgCode;
    }

    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getCrdNo() {
	return crdNo;
    }

    public void setCrdNo(String crdNo) {
	this.crdNo = crdNo;
    }

    public String getBilDt() {
	return bilDt;
    }

    public void setBilDt(String bilDt) {
	this.bilDt = bilDt;
    }

}
