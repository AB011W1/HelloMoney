package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.TransactionAmt;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CpbPesaLinkValidatePayData {
    @JsonProperty
    private String mkdActNo;

    @JsonProperty
    private String mobileNo;

    @JsonProperty
    private AccountDetails frmAct;

    // MakeBillPaymentRequest Fields
    @JsonProperty
    private Double chargeAmount;
    @JsonProperty
	private String feeGLAccount;
    @JsonProperty
	private String chargeNarration;
    @JsonProperty
	private Double taxAmount;
    @JsonProperty
	private String taxGLAccount;
    @JsonProperty
	private String ExciseDutyNarration;
    @JsonProperty
	private String typeCode;
    @JsonProperty
	private String value;

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

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getFeeGLAccount() {
		return feeGLAccount;
	}

	public void setFeeGLAccount(String feeGLAccount) {
		this.feeGLAccount = feeGLAccount;
	}

	public String getChargeNarration() {
		return chargeNarration;
	}

	public void setChargeNarration(String chargeNarration) {
		this.chargeNarration = chargeNarration;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxGLAccount() {
		return taxGLAccount;
	}

	public void setTaxGLAccount(String taxGLAccount) {
		this.taxGLAccount = taxGLAccount;
	}

	public String getExciseDutyNarration() {
		return ExciseDutyNarration;
	}

	public void setExciseDutyNarration(String exciseDutyNarration) {
		ExciseDutyNarration = exciseDutyNarration;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



}
