package com.barclays.ussd.utils.jsonparsers.bean.fdrates;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Amount {
    @JsonProperty
    private String amt;

    @JsonProperty
    private String curr;

    public Amount() {

    }

    public Amount(String amt, String curr) {
	this.amt = amt;
	this.curr = curr;
    }

    public String getAmt() {
	return amt;
    }

    public void setAmt(String amt) {
	this.amt = amt;
    }

    public String getCurr() {
	return curr;
    }

    public void setCurr(String curr) {
	this.curr = curr;
    }
}
