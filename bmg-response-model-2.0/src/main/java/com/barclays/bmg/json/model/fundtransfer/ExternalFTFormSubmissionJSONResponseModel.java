package com.barclays.bmg.json.model.fundtransfer;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.ChargeDescJSONModel;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.RemittanceInfoJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class ExternalFTFormSubmissionJSONResponseModel extends BMBPayloadData {

	/**
	 *
	 */
	private static final long serialVersionUID = 4769014684179250004L;
	private ExternalBeneficiaryJSONModel payInfo;
	private CasaAccountJSONModel frmAct;
	private ChargeDescJSONModel chrgesDesc;
	private AmountJSONModel txnAmt;
	private String payRson;
	private String payDtls;
	private String fxRate;
	private AmountJSONModel eqAmt;
	private String txnRefNo;
	private RemittanceInfoJSONModel remInfo;
	private String txnNot;

	public ExternalBeneficiaryJSONModel getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(ExternalBeneficiaryJSONModel payInfo) {
		this.payInfo = payInfo;
	}

	public CasaAccountJSONModel getFrmAct() {
		return frmAct;
	}

	public void setFrmAct(CasaAccountJSONModel frmAct) {
		this.frmAct = frmAct;
	}

	public ChargeDescJSONModel getChrgesDesc() {
		return chrgesDesc;
	}

	public void setChrgesDesc(ChargeDescJSONModel chrgesDesc) {
		this.chrgesDesc = chrgesDesc;
	}

	public AmountJSONModel getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(AmountJSONModel txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getPayRson() {
		return payRson;
	}

	public void setPayRson(String payRson) {
		this.payRson = payRson;
	}

	public String getPayDtls() {
		return payDtls;
	}

	public void setPayDtls(String payDtls) {
		this.payDtls = payDtls;
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

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public RemittanceInfoJSONModel getRemInfo() {
		return remInfo;
	}

	public void setRemInfo(RemittanceInfoJSONModel remInfo) {
		this.remInfo = remInfo;
	}

	public String getTxnNot() {
		return txnNot;
	}

	public void setTxnNot(String txnNot) {
		this.txnNot = txnNot;
	}

}
