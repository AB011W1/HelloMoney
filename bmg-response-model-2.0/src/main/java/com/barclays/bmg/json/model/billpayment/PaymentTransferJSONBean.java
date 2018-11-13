package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;





public class PaymentTransferJSONBean extends BMBPayloadData
		implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1388478403351472162L;

	private List<AccountJSONBean> frActLst = new ArrayList<AccountJSONBean>();

	private AccountJSONBean frActNo;

	private PayeeInformationJSONModel pay;

	private String curr;

	private List<CurrencyJSONModel> currLst;

	private String txnNot;

	private String paySer;

	private String fxRate;
	private AmountJSONModel eqAmt;
	private AmountJSONModel intAmt;

	// Fields for PayBills MakeBillPayment request CPB - 26/05/2017
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;
	private AmountJSONModel txnChargeAmt;

	/**
	 * Modified for adding txn Ref no in bill Payment.
	 */
	private String txnRefNo;


	public void add(AccountJSONBean accountJSONBean) {
		frActLst.add(accountJSONBean);
	}

	public List<AccountJSONBean> getFrActLst() {
		return frActLst;
	}

	public void setFrActLst(List<AccountJSONBean> frActLst) {
		this.frActLst = frActLst;
	}

	public AccountJSONBean getFrActNo() {
		return frActNo;
	}

	public void setFrActNo(AccountJSONBean frActNo) {
		this.frActNo = frActNo;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}


	public PayeeInformationJSONModel getPay() {
		return pay;
	}

	public void setPay(PayeeInformationJSONModel pay) {
		this.pay = pay;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public String getTxnNot() {
		return txnNot;
	}

	public void setTxnNot(String txnNot) {
		this.txnNot = txnNot;
	}

	public String getPaySer() {
		return paySer;
	}

	public void setPaySer(String paySer) {
		this.paySer = paySer;
	}

	public AmountJSONModel getIntAmt() {
		return intAmt;
	}

	public void setIntAmt(AmountJSONModel intAmt) {
		this.intAmt = intAmt;
	}

	public List<CurrencyJSONModel> getCurrLst() {
		return currLst;
	}

	public void setCurrLst(List<CurrencyJSONModel> currLst) {
		this.currLst = currLst;
	}

	public void add(CurrencyJSONModel currency) {
		currLst.add(currency);
	}

	public String getFxRate() {
		return fxRate;
	}

	public void setFxRate(String fxRate) {
		this.fxRate = fxRate;
	}

	public AmountJSONModel getEqAmt() {
		return eqAmt;
	}

	public void setEqAmt(AmountJSONModel eqAmt) {
		this.eqAmt = eqAmt;
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
