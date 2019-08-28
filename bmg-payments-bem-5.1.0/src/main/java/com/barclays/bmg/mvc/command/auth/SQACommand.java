package com.barclays.bmg.mvc.command.auth;

public class SQACommand {

    private String sqa;

    private String emsgs;

    public SQACommand() {
	super();
    }

    public String getSqa() {
	return sqa;
    }

    public void setSqa(String secondFactor) {
	this.sqa = secondFactor;
    }

    public String getEmsgs() {
	return emsgs;
    }

    public void setEmsgs(String emsgs) {
	this.emsgs = emsgs;
    }

}
