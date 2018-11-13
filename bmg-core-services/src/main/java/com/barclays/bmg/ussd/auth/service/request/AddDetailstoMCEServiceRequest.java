package com.barclays.bmg.ussd.auth.service.request;

import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class AddDetailstoMCEServiceRequest extends RequestContext {

    private String mobileNo;

    private String scvid;

    private String prefLang;

    private String customerAccessStatusCode;

    public String getPrefLang() {
	return prefLang;
    }

    public void setPrefLang(String prefLang) {
	this.prefLang = prefLang;
    }

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

    public List<? extends CustomerAccountDTO> getAccountList() {
	return accountList;
    }

    public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
	this.accountList = accountList;
    }

	public String getCustomerAccessStatusCode() {
		return customerAccessStatusCode;
	}

	public void setCustomerAccessStatusCode(String customerAccessStatusCode) {
		this.customerAccessStatusCode = customerAccessStatusCode;
	}

}
