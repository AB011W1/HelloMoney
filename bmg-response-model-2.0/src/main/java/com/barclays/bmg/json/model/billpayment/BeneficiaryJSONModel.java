package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;
import java.util.List;

import com.barclays.bmg.json.model.AmountJSONModel;

public class BeneficiaryJSONModel extends PayeeInformationJSONModel implements
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2157871205997469299L;

	private String payId;

	private String payNckNam;

	private String bilrTyp;

	private String bilrNam;

	private String bilrId;

	private String billHldrNam;

	private AmountJSONModel txnLmt;

	private String txnCurr;

	private String billHldAdd;

	private ReferenceNumber refNo;

	private AmountJSONModel amt;

	private AmountJSONModel fee;

	private AmountJSONModel bilrMinAmt;

	private AmountJSONModel bilrMaxAmt;

	private AmountJSONModel outBalAmt;

	// WUC-2 Botswana Biller change - 03/07/2017
	private ReferenceNumber wucContractRefNo;

	private String crdNo;
	private String beneNam;
	private String bilrSer;
	private List<AmountJSONModel> bilrAmtLst;

	private String billerAreaCode;

	public String getBillerAreaCode() {
		return billerAreaCode;
	}

	public void setBillerAreaCode(String billerAreaCode) {
		this.billerAreaCode = billerAreaCode;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getPayNckNam() {
		return payNckNam;
	}

	public void setPayNckNam(String payNckNam) {
		this.payNckNam = payNckNam;
	}

	public String getBilrTyp() {
		return bilrTyp;
	}

	public void setBilrTyp(String bilrTyp) {
		this.bilrTyp = bilrTyp;
	}

	public String getBilrNam() {
		return bilrNam;
	}

	public void setBilrNam(String bilrNam) {
		this.bilrNam = bilrNam;
	}

	public String getBilrId() {
		return bilrId;
	}

	public void setBilrId(String bilrId) {
		this.bilrId = bilrId;
	}

	public AmountJSONModel getTxnLmt() {
		return txnLmt;
	}

	public void setTxnLmt(AmountJSONModel txnLmt) {
		this.txnLmt = txnLmt;
	}

	public String getTxnCurr() {
		return txnCurr;
	}

	public void setTxnCurr(String txnCurr) {
		this.txnCurr = txnCurr;
	}

	public String getBillHldAdd() {
		return billHldAdd;
	}

	public void setBillHldAdd(String billHldAdd) {
		this.billHldAdd = billHldAdd;
	}

	public ReferenceNumber getRefNo() {
		return refNo;
	}

	public void setRefNo(ReferenceNumber refNo) {
		this.refNo = refNo;
	}

	public AmountJSONModel getAmt() {
		return amt;
	}

	public void setAmt(AmountJSONModel amt) {
		this.amt = amt;
	}

	public AmountJSONModel getFee() {
		return fee;
	}

	public void setFee(AmountJSONModel fee) {
		this.fee = fee;
	}


	public AmountJSONModel getBilrMinAmt() {
		return bilrMinAmt;
	}

	public void setBilrMinAmt(AmountJSONModel bilrMinAmt) {
		this.bilrMinAmt = bilrMinAmt;
	}

	public AmountJSONModel getBilrMaxAmt() {
		return bilrMaxAmt;
	}

	public void setBilrMaxAmt(AmountJSONModel bilrMaxAmt) {
		this.bilrMaxAmt = bilrMaxAmt;
	}

	public AmountJSONModel getOutBalAmt() {
		return outBalAmt;
	}

	public void setOutBalAmt(AmountJSONModel outBalAmt) {
		this.outBalAmt = outBalAmt;
	}

	public String getBillHldrNam() {
		return billHldrNam;
	}

	public void setBillHldrNam(String billHldrNam) {
		this.billHldrNam = billHldrNam;
	}

	public String getCrdNo() {
		return crdNo;
	}

	public void setCrdNo(String crdNo) {
		this.crdNo = crdNo;
	}

	public String getBeneNam() {
		return beneNam;
	}

	public void setBeneNam(String beneNam) {
		this.beneNam = beneNam;
	}

	public List<AmountJSONModel> getBilrAmtLst() {
		return bilrAmtLst;
	}

	public void setBilrAmtLst(List<AmountJSONModel> bilrAmtLst) {
		this.bilrAmtLst = bilrAmtLst;
	}

	public String getBilrSer() {
		return bilrSer;
	}

	public void setBilrSer(String bilrSer) {
		this.bilrSer = bilrSer;
	}

	public ReferenceNumber getWucContractRefNo() {
		return wucContractRefNo;
	}

	public void setWucContractRefNo(ReferenceNumber wucContractRefNo) {
		this.wucContractRefNo = wucContractRefNo;
	}



}
