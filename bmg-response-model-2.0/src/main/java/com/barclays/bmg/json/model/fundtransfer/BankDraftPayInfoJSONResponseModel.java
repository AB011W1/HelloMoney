package com.barclays.bmg.json.model.fundtransfer;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BankDraftBeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.ChargeDescJSONModel;
import com.barclays.bmg.json.model.billpayment.CountryCodeJSONModel;
import com.barclays.bmg.json.model.billpayment.DeliveryTypeJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class BankDraftPayInfoJSONResponseModel extends BMBPayloadData {

	private static final long serialVersionUID = -8905523464256182208L;
	private BankDraftBeneficiaryJSONModel payInfo;
	private AmountJSONModel txnLmt;
	private CasaAccountJSONModel frmAct;
	private List<DeliveryTypeJSONModel> delTypLst;
	private List<ChargeDescJSONModel> chrgesDesc;
	private String txnNot;
	private String delTypSel;
	private AmountJSONModel amount;
	private String drfTyp;
	private String drfTypSel;
	private String txnRefNo;
	private String txnDtTm;
	private List<String> currLst;
	private List<CountryCodeJSONModel> cntrLst;
	private String selBrnNam;
	private String selBrnCde;
	private String selPayAt;
	private String fxRate;
	private AmountJSONModel eqAmt;
	private String txnMsg;

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public String getTxnDtTm() {
		return txnDtTm;
	}

	public void setTxnDtTm(String txnDtTm) {
		this.txnDtTm = txnDtTm;
	}

	public BankDraftPayInfoJSONResponseModel() {
		delTypLst = new ArrayList<DeliveryTypeJSONModel>();
		chrgesDesc = new ArrayList<ChargeDescJSONModel>();
	}

	public BankDraftBeneficiaryJSONModel getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(BankDraftBeneficiaryJSONModel payInfo) {
		this.payInfo = payInfo;
	}

	public AmountJSONModel getTxnLmt() {
		return txnLmt;
	}

	public void setTxnLmt(AmountJSONModel txnLmt) {
		this.txnLmt = txnLmt;
	}

	public void addDeliveryType(DeliveryTypeJSONModel deliveryType) {
		delTypLst.add(deliveryType);
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

	public CasaAccountJSONModel getFrmAct() {
		return frmAct;
	}

	public void setFrmAct(CasaAccountJSONModel frmAct) {
		this.frmAct = frmAct;
	}

	public List<DeliveryTypeJSONModel> getDelTypLst() {
		return delTypLst;
	}

	public void setDelTypLst(List<DeliveryTypeJSONModel> delTypLst) {
		this.delTypLst = delTypLst;
	}

	public String getDelTypSel() {
		return delTypSel;
	}

	public void setDelTypSel(String delTypSel) {
		this.delTypSel = delTypSel;
	}

	public String getDrfTyp() {
		return drfTyp;
	}

	public void setDrfTyp(String drfTyp) {
		this.drfTyp = drfTyp;
	}

	public String getDrfTypSel() {
		return drfTypSel;
	}

	public void setDrfTypSel(String drfTypSel) {
		this.drfTypSel = drfTypSel;
	}

	public AmountJSONModel getAmount() {
		return amount;
	}

	public void setAmount(AmountJSONModel amount) {
		this.amount = amount;
	}

	public List<String> getCurrLst() {
		return currLst;
	}

	public void setCurrLst(List<String> currLst) {
		this.currLst = currLst;
	}

	public List<CountryCodeJSONModel> getCntrLst() {
		return cntrLst;
	}

	public void setCntrLst(List<CountryCodeJSONModel> cntrLst) {
		this.cntrLst = cntrLst;
	}

	public String getSelBrnNam() {
		return selBrnNam;
	}

	public void setSelBrnNam(String selBrnNam) {
		this.selBrnNam = selBrnNam;
	}

	public String getSelBrnCde() {
		return selBrnCde;
	}

	public void setSelBrnCde(String selBrnCde) {
		this.selBrnCde = selBrnCde;
	}

	public String getSelPayAt() {
		return selPayAt;
	}

	public void setSelPayAt(String selPayAt) {
		this.selPayAt = selPayAt;
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

	public String getTxnNot() {
		return txnNot;
	}

	public void setTxnNot(String txnNot) {
		this.txnNot = txnNot;
	}

	public String getTxnMsg() {
		return txnMsg;
	}

	public void setTxnMsg(String txnMsg) {
		this.txnMsg = txnMsg;
	}

}
