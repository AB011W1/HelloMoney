package com.barclays.bmg.json.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class AccountActivityJSONBean implements Serializable {

	private static final long serialVersionUID = 2105891967539193212L;

	private String dt = "";

	private String refNo = "";
	private String txnPrt = "";
	private AmountJSONModel drAmt;
	private AmountJSONModel crAmt;
	private String bal = "";

	public AccountActivityJSONBean() {
		super();
	}

	public AccountActivityJSONBean(AccountActivityDTO activityDTO) {

		String currency = activityDTO.getCurrency();

		this.dt = BMGFormatUtility.getShortDate(activityDTO
				.getTransactionDate());
		this.refNo = activityDTO.getTransactoinReferenceNumber();
		this.txnPrt = BMGFormatUtility.removeSpaceInBetween(activityDTO
				.getTransactionParticular());
		this.drAmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility
				.getFormattedAmount(activityDTO.getDebitAmount()));
		this.crAmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility
				.getFormattedAmount(activityDTO.getCreditAmount()));
		this.bal = BMGFormatUtility
				.getFormattedAmount(activityDTO.getBalance());
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getRefNo() {

		String refNum = "";
		if (!StringUtils.isEmpty(refNo)) {
			refNum = refNo.trim();
		} else {
			refNum = refNo;
		}
		return refNum;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getTxnPrt() {
		return txnPrt;
	}

	public void setTxnPrt(String txnPrt) {
		this.txnPrt = txnPrt;
	}

	public AmountJSONModel getDrAmt() {
		return drAmt;
	}

	public void setDrAmt(AmountJSONModel drAmt) {
		this.drAmt = drAmt;
	}

	public AmountJSONModel getCrAmt() {
		return crAmt;
	}

	public void setCrAmt(AmountJSONModel crAmt) {
		this.crAmt = crAmt;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

}
