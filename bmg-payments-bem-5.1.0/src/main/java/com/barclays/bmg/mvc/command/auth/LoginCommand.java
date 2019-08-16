package com.barclays.bmg.mvc.command.auth;

/**
 * @author E20042299
 * 
 */
public class LoginCommand {

    private String usrNam;
    private String pass;
    private String usrStatInMCE;
    private String pinStatInCrypto;
    private String scvId;

    public LoginCommand() {
	super();
    }

    public String getUsrNam() {
	return usrNam;
    }

    public void setUsrNam(String usrNam) {
	this.usrNam = usrNam;
    }

    public String getPass() {
	return pass;
    }

    public void setPass(String pass) {
	this.pass = pass;
    }

    public void setUsername(String usrNam) {
	this.usrNam = usrNam;
    }

    public void setPassword(String pass) {
	this.pass = pass;
    }

    public String getUsrStatInMCE() {
	return usrStatInMCE;
    }

    public void setUsrStatInMCE(String usrStatInMCE) {
	this.usrStatInMCE = usrStatInMCE;
    }

    public String getPinStatInCrypto() {
	return pinStatInCrypto;
    }

    public void setPinStatInCrypto(String pinStatInCrypto) {
	this.pinStatInCrypto = pinStatInCrypto;
    }

    public String getScvId() {
	return scvId;
    }

    public void setScvId(String scvId) {
	this.scvId = scvId;
    }

}
