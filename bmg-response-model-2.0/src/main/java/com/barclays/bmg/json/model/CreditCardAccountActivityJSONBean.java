package com.barclays.bmg.json.model;

import java.io.Serializable;

import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CreditCardAccountActivityJSONBean implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 2105891967539193212L;

    private String dt = "";
    private String txnPostDt = "";
    private String refNo = "";
    private String txnPrt = "";

    private AmountJSONModel amt;
    private String drCrFlg = "";
    private String merchantName = "";

    public CreditCardAccountActivityJSONBean() {
	super();
    }

    public CreditCardAccountActivityJSONBean(CreditCardActivityDTO activityDTO, String actCurrency) {
	super();
	String currency = activityDTO.getCurrency();
	if (currency == null || currency.trim().length() == 0) {
	    currency = actCurrency;
	}

	this.dt = BMGFormatUtility.getShortDate(activityDTO.getTransactionDate());
	this.txnPostDt = BMGFormatUtility.getShortDate(activityDTO.getTransactionPostDate());

	this.refNo = activityDTO.getTransactoinReferenceNumber();
	this.txnPrt = activityDTO.getTransactionParticular();
	this.amt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(activityDTO.getTransactionAmount()));

	this.drCrFlg = activityDTO.getCreditDebitFlag();
	this.merchantName = activityDTO.getMerchantName();
    }

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

    public AmountJSONModel getAmt() {
	return amt;
    }

    public void setAmt(AmountJSONModel amt) {
	this.amt = amt;
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

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public String getMerchantName() {
	return merchantName;
    }

    public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
    }

}
