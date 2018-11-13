package com.barclays.bmg.json.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barclays.bmg.json.model.accounts.CustomerProfileJSONModel;
import com.barclays.bmg.json.model.accounts.EmailMessageJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class CreditCardSummaryJSONModel extends BMBPayloadData {

    private static final long serialVersionUID = -2972111608539629883L;

    protected String dispNoActsFnd = "No";

    private List<? extends AccountJSONModel> custActs = Collections.emptyList();

    private CustomerProfileJSONModel custProf = new CustomerProfileJSONModel();

    private List<EmailMessageJSONModel> emsgs = new ArrayList<EmailMessageJSONModel>();

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

}
