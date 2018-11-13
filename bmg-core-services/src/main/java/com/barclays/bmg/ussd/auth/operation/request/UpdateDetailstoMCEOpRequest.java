package com.barclays.bmg.ussd.auth.operation.request;

import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class UpdateDetailstoMCEOpRequest extends RequestContext {
    private String mobileNo;

    private String scvid;

    private String prefLang;

    private String registrationStatus;

    private String actionType;

    private List<? extends CustomerAccountDTO> accountList;

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

    public String getScvid() {
	return scvid;
    }

    public void setScvid(String scvid) {
	this.scvid = scvid;
    }

    public String getPrefLang() {
	return prefLang;
    }

    public void setPrefLang(String prefLang) {
	this.prefLang = prefLang;
    }

    public String getRegistrationStatus() {
	return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
	this.registrationStatus = registrationStatus;
    }

    public List<? extends CustomerAccountDTO> getAccountList() {
	return accountList;
    }

    public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
	this.accountList = accountList;
    }

    public String getActionType() {
	return actionType;
    }

    public void setActionType(String actionType) {
	this.actionType = actionType;
    }
}
