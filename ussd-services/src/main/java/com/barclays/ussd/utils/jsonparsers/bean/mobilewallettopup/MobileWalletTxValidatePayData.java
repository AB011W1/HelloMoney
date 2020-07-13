package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileWalletTxValidatePayData {

	@JsonProperty
	private CustomerMobileRegAcct creditcardJsonMod;

	@JsonProperty
	private MobileWalletAcntDetails srcAcct;

	@JsonProperty
	private MobileWalletProvider prvder;

	@JsonProperty
	private String txnRefNo;

	@JsonProperty
	private TransactionAmt txnAmt;

	@JsonProperty
	private String mblNo;


	// Mobile Number Validation
		@JsonProperty
		private  String payeeName;
	
	// CPB - MakeBillPaymentRequest Fields
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

    
    
	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getTxnRefNo() {
		return this.txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	/**
	 * @return the srcAcct
	 */
	public MobileWalletAcntDetails getSrcAcct() {
		return srcAcct;
	}

	/**
	 * @param srcAcct
	 *            the srcAcct to set
	 */
	public void setSrcAcct(MobileWalletAcntDetails srcAcct) {
		this.srcAcct = srcAcct;
	}

	/**
	 * @return the prvder
	 */
	public MobileWalletProvider getPrvder() {
		return prvder;
	}

	/**
	 * @param prvder
	 *            the prvder to set
	 */
	public void setPrvder(MobileWalletProvider prvder) {
		this.prvder = prvder;
	}

	/**
	 * @return the txnAmt
	 */
	public TransactionAmt getTxnAmt() {
		return txnAmt;
	}

	/**
	 * @param txnAmt the txnAmt to set
	 */
	public void setTxnAmt(TransactionAmt txnAmt) {
		this.txnAmt = txnAmt;
	}

	/**
	 * @return the mblNo
	 */
	public String getMblNo() {
		return mblNo;
	}

	/**
	 * @param mblNo the mblNo to set
	 */
	public void setMblNo(String mblNo) {
		this.mblNo = mblNo;
	}
	
	public CustomerMobileRegAcct getCreditcardJsonMod() {
		return creditcardJsonMod;
	}

	public void setCreditcardJsonMod(CustomerMobileRegAcct creditcardJsonMod) {
		this.creditcardJsonMod = creditcardJsonMod;
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
