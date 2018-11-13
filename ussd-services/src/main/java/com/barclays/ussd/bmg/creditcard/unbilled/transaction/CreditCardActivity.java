package com.barclays.ussd.bmg.creditcard.unbilled.transaction;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.bean.Amount;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardActivity {
    @JsonProperty
    private String dt;

    @JsonProperty
    private String refNo;

    @JsonProperty
    private String txnPrt;

    @JsonProperty
    private String merchantName;

    @JsonProperty
    private String drCrFlg;

    @JsonProperty
    private String txnPostDt;

    @JsonProperty
    private Amount amt;

    public String getDt() {
	return dt;
    }

    public void setDt(String dt) {
	this.dt = dt;
    }

    public String getRefNo() {
	return refNo;
    }

    public void setRefNo(String refNo) {
	this.refNo = refNo;
    }

    public String getTxnPrt() {
	return txnPrt;
    }

    public void setTxnPrt(String txnPrt) {
	this.txnPrt = txnPrt;
    }

    public String getMerchantName() {
	return merchantName;
    }

    public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
    }

    public String getDrCrFlg() {
	return drCrFlg;
    }

    public void setDrCrFlg(String drCrFlg) {
	this.drCrFlg = drCrFlg;
    }

    public String getTxnPostDt() {
	return txnPostDt;
    }

    public void setTxnPostDt(String txnPostDt) {
	this.txnPostDt = txnPostDt;
    }

    public Amount getAmt() {
	return amt;
    }

    public void setAmt(Amount amt) {
	this.amt = amt;
    }

}
