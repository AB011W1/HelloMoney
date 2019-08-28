package com.barclays.bmg.mvc.command.auth;

public class ChangePasswordCommand {

    private String oldPass;
    private String newPass;
    private String confNewPass;
    private String mobileNo;
    private String usrStatInMCE;

    public String getOldPass() {
	return oldPass;
    }

    public void setOldPass(String oldPass) {
	this.oldPass = oldPass;
    }

    public String getNewPass() {
	return newPass;
    }

    public void setNewPass(String newPass) {
	this.newPass = newPass;
    }

    public String getConfNewPass() {
	return confNewPass;
    }

    public void setConfNewPass(String confNewPass) {
	this.confNewPass = confNewPass;
    }

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

    /**
     * @return the usrStatInMCE
     */
    public String getUsrStatInMCE() {
	return usrStatInMCE;
    }

    /**
     * @param usrStatInMCE
     *            the usrStatInMCE to set
     */
    public void setUsrStatInMCE(String usrStatInMCE) {
	this.usrStatInMCE = usrStatInMCE;
    }
}
