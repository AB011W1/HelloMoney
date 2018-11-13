package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * @author BTCI
 *
 */
public class OneTimeBillPayFormSubmitJSONModel extends BMBPayloadData implements
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<CasaAccountJSONModel> fromAcctList = new ArrayList<CasaAccountJSONModel>();

	private CasaAccountJSONModel fromAccount;
	private AmountJSONModel amount;
	private BillerJSONModel billerInfo;
	private String txnRefNo;
	private String txnDate;

	// Fields for PayBills MakeBillPayment request CPB - 29/05/2017
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;
	private AmountJSONModel txnChargeAmt;

	/**
	 * @return CasaAccountJSONModel List
	 */
	private CreditCardAccountJSONModel creditcardJsonModel ;
	public CreditCardAccountJSONModel getCreditcardJsonModel() {
		return creditcardJsonModel;
	}

	public void setCreditcardJsonModel(
			CreditCardAccountJSONModel creditcardJsonModel) {
		this.creditcardJsonModel = creditcardJsonModel;
	}
	public List<CasaAccountJSONModel> getFromAcctList() {
		return fromAcctList;
	}

	/**
	 * @param fromAcctList
	 */
	public void setFromAcctList(List<CasaAccountJSONModel> fromAcctList) {
		this.fromAcctList = fromAcctList;
	}

	/**
	 * @return CasaAccountJSONModel
	 */
	public CasaAccountJSONModel getFromAccount() {
		return fromAccount;
	}

	/**
	 * @param fromAccount
	 */
	public void setFromAccount(CasaAccountJSONModel fromAccount) {
		this.fromAccount = fromAccount;
	}

	/**
	 * @return AmountJSONModel
	 */
	public AmountJSONModel getAmount() {
		return amount;
	}

	public void setAmount(AmountJSONModel amount) {
		this.amount = amount;
	}

	/**
	 * @return BillerJSONModel
	 */
	public BillerJSONModel getBillerInfo() {
		return billerInfo;
	}

	/**
	 * @param billerInfo
	 */
	public void setBillerInfo(BillerJSONModel billerInfo) {
		this.billerInfo = billerInfo;
	}

	/**
	 * @return String
	 */
	public String getTxnRefNo() {
		return txnRefNo;
	}

	/**
	 * @param txnRefNo
	 */
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	/**
	 * @return String
	 */
	public String getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate
	 */
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	/**
	 * @param account
	 */
	public void add(CasaAccountJSONModel account) {
		fromAcctList.add(account);
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

	public AmountJSONModel getTxnChargeAmt() {
		return txnChargeAmt;
	}

	public void setTxnChargeAmt(AmountJSONModel txnChargeAmt) {
		this.txnChargeAmt = txnChargeAmt;
	}


}
