package com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountChequBookDetails {

    @JsonProperty
    private String actNo;
    @JsonProperty
    private String brnCde;
    @JsonProperty
    private String typ;
    @JsonProperty
    private String desc;
    @JsonProperty
    private String curr;
    @JsonProperty
    private String mkdActNo;
    @JsonProperty
    private String brnNam;
    @JsonProperty
    private String priInd;

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getBrnCde() {
	return brnCde;
    }

    public void setBrnCde(String brnCde) {
	this.brnCde = brnCde;
    }

    public String getTyp() {
	return typ;
    }

    public void setTyp(String typ) {
	this.typ = typ;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public String getCurr() {
	return curr;
    }

    public void setCurr(String curr) {
	this.curr = curr;
    }

    public String getMkdActNo() {
	return mkdActNo;
    }

    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    public String getBrnNam() {
	return brnNam;
    }

    public void setBrnNam(String brnNam) {
	this.brnNam = brnNam;
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
