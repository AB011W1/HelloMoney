package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.TransactionAmt;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnFundTxValidatePayData
{
	@JsonProperty
	private String frActNo;

	@JsonProperty
	private String toActNo;

	@JsonProperty
	private String frMskActNo;

	@JsonProperty
	private String toMskActNo;

	@JsonProperty
	private String mkdCrdNo;

	@JsonProperty
	private TransactionAmt txnAmt;

	@JsonProperty
	private String txnRefNo;


	@JsonProperty
	private String  payId;
	@JsonProperty
	private String payName;
	@JsonProperty
	private String  beneName;

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

    @JsonProperty
	private String xelerateOfflineError;
    @JsonProperty
	private String xelerateOfflineDesc;

	public String getFrActNo() {
		return frActNo;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public void setFrActNo(String frActNo) {
		this.frActNo = frActNo;
	}


	public String getFrMskActNo() {
		return frMskActNo;
	}

	public void setFrMskActNo(String frMskActNo) {
		this.frMskActNo = frMskActNo;
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

	public String getToActNo() {
		return toActNo;
	}

	public void setToActNo(String toActNo) {
		this.toActNo = toActNo;
	}

	public String getToMskActNo() {
		return toMskActNo;
	}

	public void setToMskActNo(String toMskActNo) {
		this.toMskActNo = toMskActNo;
	}

	public String getMkdCrdNo() {
		return mkdCrdNo;
	}

	public void setMkdCrdNo(String mkdCrdNo) {
		this.mkdCrdNo = mkdCrdNo;
	}

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

	public String getXelerateOfflineError() {
		return xelerateOfflineError;
	}

	public void setXelerateOfflineError(String xelerateOfflineError) {
		this.xelerateOfflineError = xelerateOfflineError;
	}

	public String getXelerateOfflineDesc() {
		return xelerateOfflineDesc;
	}

	public void setXelerateOfflineDesc(String xelerateOfflineDesc) {
		this.xelerateOfflineDesc = xelerateOfflineDesc;
	}




}
