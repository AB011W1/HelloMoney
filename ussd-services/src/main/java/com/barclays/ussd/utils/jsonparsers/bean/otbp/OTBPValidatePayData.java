/**
 * FromAcntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.otbp;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerProfile;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.TransactionAmt;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTBPValidatePayData {

	@JsonProperty
	private OTBPValidateAccount fromAccount;

	@JsonProperty
	private CustomerMobileRegAcct creditcardJsonModel;

	public CustomerMobileRegAcct getCreditcardJsonModel() {
		return creditcardJsonModel;
	}

	public void setCreditcardJsonModel(CustomerMobileRegAcct creditcardJsonModel) {
		this.creditcardJsonModel = creditcardJsonModel;
	}

	@JsonProperty
	private Amount amount;

	@JsonProperty
	private String txnRefNo;

	@JsonProperty
	private String txnDate;

	@JsonProperty
	private List<OTBPValidateAccount> fromAcctList;

	@JsonProperty
	private BillerInfo billerInfo;


	// MakeBillPaymentRequest Fields - CBP 29/05/2017
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

	/**
	 * @return the fromAccount
	 */
	public OTBPValidateAccount getFromAccount() {
		return fromAccount;
	}

	/**
	 * @param fromAccount
	 *            the fromAccount to set
	 */
	public void setFromAccount(OTBPValidateAccount fromAccount) {
		this.fromAccount = fromAccount;
	}

	/**
	 * @return the amount
	 */
	public Amount getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	/**
	 * @return the txnRefNo
	 */
	public String getTxnRefNo() {
		return txnRefNo;
	}

	/**
	 * @param txnRefNo
	 *            the txnRefNo to set
	 */
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	/**
	 * @return the txnDate
	 */
	public String getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate
	 *            the txnDate to set
	 */
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public void setFromAcctList(List<OTBPValidateAccount> fromAcctList) {
		this.fromAcctList = fromAcctList;
	}

	public List<OTBPValidateAccount> getFromAcctList() {
		return fromAcctList;
	}

	/**
	 * @return the billerInfo
	 */
	public BillerInfo getBillerInfo() {
		return billerInfo;
	}

	/**
	 * @param billerInfo
	 *            the billerInfo to set
	 */
	public void setBillerInfo(BillerInfo billerInfo) {
		this.billerInfo = billerInfo;
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

}
