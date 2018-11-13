package com.barclays.bmg.operation.response.fundtransfer;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class FundTransferExecuteOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -309523308874395609L;
    private Date trnDate;
    private String trnRef;
    private String errorKey;
    private String txnType;

    private boolean authRequired;

    public Date getTrnDate() {
	return new Date(trnDate.getTime());
    }

    public void setTrnDate(Date trnDate) {
	if (trnDate == null) {
	    this.trnDate = null;
	} else {
	    this.trnDate = new Date(trnDate.getTime());
	}
    }

    public String getTrnRef() {
	return trnRef;
    }

    public void setTrnRef(String trnRef) {
	this.trnRef = trnRef;
    }

    public String getErrorKey() {
	return errorKey;
    }

    public void setErrorKey(String errorKey) {
	this.errorKey = errorKey;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

    public boolean isAuthRequired() {
	return authRequired;
    }

    public void setAuthRequired(boolean authRequired) {
	this.authRequired = authRequired;
    }

}
