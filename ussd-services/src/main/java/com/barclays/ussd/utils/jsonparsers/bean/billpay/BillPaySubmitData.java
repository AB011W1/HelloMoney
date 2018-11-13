/**
 * BillPaySubmitData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.TransactionAmt;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaySubmitData {

    /**
     * txnRefNo
     */
    @JsonProperty
    private String txnRefNo;

    /**
     * pay details
     */
    @JsonProperty
    private BillPaySubmitPayDetails pay;

    /**
     * frmAct
     */
    @JsonProperty
    private AccountDetails frActNo;

    public AccountDetails getFrActNo() {
	return frActNo;
    }

    public void setFrActNo(AccountDetails frActNo) {
	this.frActNo = frActNo;
    }

    public BillPaySubmitPayDetails getPaymentDetails() {
	return pay;
    }

    public void setPay(BillPaySubmitPayDetails pay) {
	this.pay = pay;
    }

    /**
     * @param txnRefNo
     *            the txnRefNo to set
     */
    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    /**
     * @return the txnRefNo
     */
    public String getTxnRefNo() {
	return txnRefNo;
    }

    // MakeBillPaymentRequest Fields - CBP 26/05/2017
    @JsonProperty
    private TransactionAmt txnChargeAmt;
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


	public TransactionAmt getTxnChargeAmt() {
		return txnChargeAmt;
	}

	public void setTxnChargeAmt(TransactionAmt txnChargeAmt) {
		this.txnChargeAmt = txnChargeAmt;
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

	public BillPaySubmitPayDetails getPay() {
		return pay;
	}

}
