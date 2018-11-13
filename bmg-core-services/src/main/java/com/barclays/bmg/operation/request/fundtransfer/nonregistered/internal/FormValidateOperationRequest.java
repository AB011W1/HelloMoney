package com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class FormValidateOperationRequest extends RequestContext {

    private Amount txnAmt;
    private CustomerAccountDTO frmAct;
    private String txnType;
    private String creditCardFlag;


    public Amount getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(Amount txnAmt) {
	this.txnAmt = txnAmt;
    }

    public CustomerAccountDTO getFrmAct() {
	return frmAct;
    }

    public void setFrmAct(CustomerAccountDTO frmAct) {
	this.frmAct = frmAct;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

	public String getCreditCardFlag() {
		return creditCardFlag;
	}

	public void setCreditCardFlag(String creditCardFlag) {
		this.creditCardFlag = creditCardFlag;
	}
}
