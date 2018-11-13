package com.barclays.ussd.utils.jsonparsers.bean.login;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfRegistrationExecuteData {
    @JsonProperty
    private String status;

    @JsonProperty
    private String bemRefNo;
    @JsonProperty
    private String txnResRefNo;

    @JsonProperty
    private String txnRefNo;

    @JsonProperty
    private String resDtTm;

    @JsonProperty
    private String txnDtTm;

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getBemRefNo() {
	return bemRefNo;
    }

    public void setBemRefNo(String bemRefNo) {
	this.bemRefNo = bemRefNo;
    }

    public String getTxnResRefNo() {
	return txnResRefNo;
    }

    public void setTxnResRefNo(String txnResRefNo) {
	this.txnResRefNo = txnResRefNo;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public String getResDtTm() {
	return resDtTm;
    }

    public void setResDtTm(String resDtTm) {
	this.resDtTm = resDtTm;
    }

    public String getTxnDtTm() {
	return txnDtTm;
    }

    public void setTxnDtTm(String txnDtTm) {
	this.txnDtTm = txnDtTm;
    }

}
