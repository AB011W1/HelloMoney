package com.barclays.ussd.bmg.creditcard.unbilled.transaction;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardDetails {

    @JsonProperty
    private String crdNo;

    @JsonProperty
    private String crdHlds;

    @JsonProperty
    private List<CreditCardActivity> actActvLst;

    @JsonProperty
    private String mkdCrdNo;

    @JsonProperty
    private String crdTyp;

    public String getCrdNo() {
	return crdNo;
    }

    public void setCrdNo(String crdNo) {
	this.crdNo = crdNo;
    }

    public String getCrdHlds() {
	return crdHlds;
    }

    public void setCrdHlds(String crdHlds) {
	this.crdHlds = crdHlds;
    }

    public List<CreditCardActivity> getActActvLst() {
	return actActvLst;
    }

    public void setActActvLst(List<CreditCardActivity> actActvLst) {
	this.actActvLst = actActvLst;
    }

    public String getMkdCrdNo() {
	return mkdCrdNo;
    }

    public void setMkdCrdNo(String mkdCrdNo) {
	this.mkdCrdNo = mkdCrdNo;
    }

    public String getCrdTyp() {
	return crdTyp;
    }

    public void setCrdTyp(String crdTyp) {
	this.crdTyp = crdTyp;
    }

}
