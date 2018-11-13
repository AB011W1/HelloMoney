package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class FormValidateOperationRequest extends RequestContext {

    private Amount txnAmt;
    private CustomerAccountDTO frmAct;
    private String txnType;
    private String toActNo;
    private String creditCardFlag;

    // BillerID change for RetrieveChargeDetails - CBP 29/09/2017
    private String billerId;

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

    /**
     * @return the toActNo
     */
    public String getToActNo() {
	return toActNo;
    }

    /**
     * @param toActNo
     *            the toActNo to set
     */
    public void setToActNo(String toActNo) {
	this.toActNo = toActNo;
    }

	public String getCreditCardFlag() {
		return creditCardFlag;
	}

	public void setCreditCardFlag(String creditCardFlag) {
		this.creditCardFlag = creditCardFlag;
	}

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}


}
