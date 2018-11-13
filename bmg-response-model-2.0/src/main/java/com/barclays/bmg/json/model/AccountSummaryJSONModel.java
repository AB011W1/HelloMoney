package com.barclays.bmg.json.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barclays.bmg.json.model.accounts.CustomerProfileJSONModel;
import com.barclays.bmg.json.model.accounts.EmailMessageJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class AccountSummaryJSONModel extends BMBPayloadData {

    private static final long serialVersionUID = 6959409375219643563L;

    protected String dispNoActsFnd = "No";

    private List<? extends AccountJSONModel> custActs = Collections.emptyList();

    private CustomerProfileJSONModel custProf = new CustomerProfileJSONModel();

    private List<EmailMessageJSONModel> emsgs = new ArrayList<EmailMessageJSONModel>();

    private AmountJSONModel totAsst;
    private AmountJSONModel totDebt;
    private AmountJSONModel totNetWorth;

    public String getDispNoActsFnd() {

	if (custActs == null || custActs.size() == 0) {
	    dispNoActsFnd = "Yes";
	}
	return dispNoActsFnd;
    }

    public void setDisplayNoAccountFound(String displayNoAccountFound) {
	// this.displayNoAccountFound = displayNoAccountFound;
    }

    public CustomerProfileJSONModel getCustProf() {
	return custProf;
    }

    public void setCustProf(CustomerProfileJSONModel custProf) {
	this.custProf = custProf;
    }

    public List<EmailMessageJSONModel> getEmsgs() {
	return emsgs;
    }

    public void setEmsgs(List<EmailMessageJSONModel> emsgs) {
	this.emsgs = emsgs;
    }

    public List<? extends AccountJSONModel> getCustActs() {
	return custActs;
    }

    public void setCustActs(List<? extends AccountJSONModel> custActs) {
	this.custActs = custActs;
    }

    public AmountJSONModel getTotAsst() {
	return totAsst;
    }

    public void setTotAsst(AmountJSONModel totAsst) {
	this.totAsst = totAsst;
    }

    public AmountJSONModel getTotDebt() {
	return totDebt;
    }

    public void setTotDebt(AmountJSONModel totDebt) {
	this.totDebt = totDebt;
    }

    public AmountJSONModel getTotNetWorth() {
	return totNetWorth;
    }

    public void setTotNetWorth(AmountJSONModel totNetWorth) {
	this.totNetWorth = totNetWorth;
    }

}
