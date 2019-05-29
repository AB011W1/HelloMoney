package com.barclays.bmg.service.request;

import java.math.BigDecimal;
import java.util.Map;

import com.barclays.bmg.context.RequestContext;

public class RetreiveChargeDetailsServiceRequest extends RequestContext {

    private String chargeDetailTaskCode;
    private String currency;
    private BigDecimal txnAmt;
    private String frmAcct;
    private String custSegmentCode;
    private boolean isStaff;


    Map<String, Object> contextMap ;

    public Map<String, Object> getContextMap() {
		return contextMap;
	}

	public void setContextMap(Map<String, Object> contextMap) {
		this.contextMap = contextMap;
	}

	//CPB change 08/05/2017
    private String branchCode;
    //Biller ID change - CBP 29/09/2017
    private String billerID;

    public String getChargeDetailTaskCode() {
	return chargeDetailTaskCode;
    }

    public void setChargeDetailTaskCode(String chargeDetailTaskCode) {
	this.chargeDetailTaskCode = chargeDetailTaskCode;
    }

    public String getCurrency() {
	return currency;
    }

    public void setCurrency(String currency) {
	this.currency = currency;
    }

    public BigDecimal getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
	this.txnAmt = txnAmt;
    }

    public String getFrmAcct() {
	return frmAcct;
    }

    public void setFrmAcct(String frmAcct) {
	this.frmAcct = frmAcct;
    }

    public String getCustSegmentCode() {
	return custSegmentCode;
    }

    public void setCustSegmentCode(String custSegmentCode) {
	this.custSegmentCode = custSegmentCode;
    }

    public boolean isStaff() {
	return isStaff;
    }

    public void setStaff(boolean isStaff) {
	this.isStaff = isStaff;
    }

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBillerID() {
		return billerID;
	}

	public void setBillerID(String billerID) {
		this.billerID = billerID;
	}

}
