package com.barclays.bmg.json.model.fundtransfer;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.ChargeDescJSONModel;
import com.barclays.bmg.json.model.billpayment.CurrencyJSONModel;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.PaymentReasonDetailsJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class ExternalFTPayInfoJSONResponseModel extends BMBPayloadData{

	private static final long serialVersionUID = 6093929599748997558L;
	private ExternalBeneficiaryJSONModel payInfo;
	private AmountJSONModel txnLmt;
	private CasaAccountJSONModel frmAct;
	private List<CurrencyJSONModel> currLst;
	private List<ChargeDescJSONModel> chrgesDesc;
	private List<PaymentReasonDetailsJSONModel> payRsonDtls;

	// Payment Reason and Details .....

	public ExternalFTPayInfoJSONResponseModel(){
		currLst = new ArrayList<CurrencyJSONModel>();
		chrgesDesc = new ArrayList<ChargeDescJSONModel>();
		payRsonDtls = new ArrayList<PaymentReasonDetailsJSONModel>();
	}

	public ExternalBeneficiaryJSONModel getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(ExternalBeneficiaryJSONModel payInfo) {
		this.payInfo = payInfo;
	}

	public AmountJSONModel getTxnLmt() {
		return txnLmt;
	}

	public void setTxnLmt(AmountJSONModel txnLmt) {
		this.txnLmt = txnLmt;
	}



	public List<CurrencyJSONModel> getCurrLst() {
		return currLst;
	}

	public void add(CurrencyJSONModel currency) {
		currLst.add(currency);
	}

	public List<ChargeDescJSONModel> getChrgesDesc() {
		return chrgesDesc;
	}

	public void setChrgesDesc(List<ChargeDescJSONModel> chrgesDesc) {
		this.chrgesDesc = chrgesDesc;
	}

	public void add(ChargeDescJSONModel chargeDesc) {
		chrgesDesc.add(chargeDesc);
	}

	public void add(PaymentReasonDetailsJSONModel payRsonDtl) {
		payRsonDtls.add(payRsonDtl);
	}

	public List<PaymentReasonDetailsJSONModel> getPayRsonDtls() {
		return payRsonDtls;
	}

	public void setPayRsonDtls(List<PaymentReasonDetailsJSONModel> payRsonDtls) {
		this.payRsonDtls = payRsonDtls;
	}

	public void setCurrLst(List<CurrencyJSONModel> currLst) {
		this.currLst = currLst;
	}

	public CasaAccountJSONModel getFrmAct() {
		return frmAct;
	}

	public void setFrmAct(CasaAccountJSONModel frmAct) {
		this.frmAct = frmAct;
	}

}
