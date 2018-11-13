package com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OBAFTSrcAcct {
    @JsonProperty
    private String actNo;

    @JsonProperty
    private String mkdActNo;

    @JsonProperty
    private String curr;

    @JsonProperty
    private String priInd;

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getMkdActNo() {
	return mkdActNo;
    }

    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    public String getCurr() {
	return curr;
    }

    public void setCurr(String curr) {
	this.curr = curr;
    }

    /**
     * @return the priInd
     */
    public String getPriInd() {
	return priInd;
    }

    /**
     * @param priInd
     *            the priInd to set
     */
    public void setPriInd(String priInd) {
	this.priInd = priInd;
    }

}
