package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneTimeCashSendValidatePayData {
    @JsonProperty
    private String mkdActNo;

    @JsonProperty
    private String mobileNo;

    @JsonProperty
    private AccountDetails frmAct;

    public AccountDetails getFrmAct() {
        return frmAct;
    }

    public void setFrmAct(AccountDetails frmAct) {
        this.frmAct = frmAct;
    }

    public String getMkdActNo() {
	return mkdActNo;
    }

    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

    public TransactionAmt getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(TransactionAmt txnAmt) {
	this.txnAmt = txnAmt;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    @JsonProperty
    private TransactionAmt txnAmt;

    @JsonProperty
    private String txnRefNo;

    // CPB Change 18/04/2017
    @JsonProperty
    private TransactionAmt transactionFeeAmount;

	public TransactionAmt getTransactionFeeAmount() {
		return transactionFeeAmount;
	}

	public void setTransactionFeeAmount(TransactionAmt transactionFeeAmount) {
		this.transactionFeeAmount = transactionFeeAmount;
	}

	@JsonProperty
	private Double taxAmount;
	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}



}
