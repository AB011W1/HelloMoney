package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.dto.AccountTrnxDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class AccountTrnxHistoryJSONBean implements Serializable {



	private static final long serialVersionUID = 2105891967539193212L;

	private final static String CREDIT = "C";

	private final static String DEBIT = "D";

	private String dt = "";
	private String refNo = "";
	private String txnPrt = "";
	private AmountJSONModel drAmt;
	private AmountJSONModel crAmt;
//	private AmountJSONModel amount;
	private String txnType = "";

	public AccountTrnxHistoryJSONBean() {
		super();
	}

	public AccountTrnxHistoryJSONBean(AccountTrnxDTO acctTrnxDTO) {

		String currency = acctTrnxDTO.getTransactionCurrencyCode();
		Calendar calendar  = acctTrnxDTO.getTransactionDateTime();
		Date date = calendar.getTime();
		this.dt = BMGFormatUtility.getShortDate(date);
		this.refNo = BMGFormatUtility.removeSpaceInBetween(acctTrnxDTO.getTrnxReferenceNumber());
		this.txnPrt = BMGFormatUtility.removeSpaceInBetween(acctTrnxDTO.getTransactionDescriptionCode());
		String trnxType = StringUtils.equalsIgnoreCase(acctTrnxDTO.getCreditDebitTypeCode(),CREDIT) ? "CR" : "DR";
		this.txnType = BMGFormatUtility.removeSpaceInBetween(trnxType);
		if(StringUtils.equalsIgnoreCase(CREDIT,acctTrnxDTO.getCreditDebitTypeCode())){
			this.crAmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(acctTrnxDTO.getTransactionCurrencyAmount()));
		}else if(StringUtils.equalsIgnoreCase(DEBIT,acctTrnxDTO.getCreditDebitTypeCode())){
			this.drAmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(acctTrnxDTO.getTransactionCurrencyAmount()));
		}

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

	/**
	 * @return the dt
	 */
	public String getDt() {
		return dt;
	}

	/**
	 * @param dt the dt to set
	 */
	public void setDt(String dt) {
		this.dt = dt;
	}

	/**
	 * @return the txnPrtcular
	 */
	public String getTxnPrtcular() {
		return txnPrt;
	}

	/**
	 * @param txnPrtcular the txnPrtcular to set
	 */
	public void setTxnPrtcular(String txnPrtcular) {
		this.txnPrt = txnPrtcular;
	}



	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return the txnPrt
	 */
	public String getTxnPrt() {
		return txnPrt;
	}

	/**
	 * @param txnPrt the txnPrt to set
	 */
	public void setTxnPrt(String txnPrt) {
		this.txnPrt = txnPrt;
	}

	/**
	 * @return the drAmt
	 */
	public AmountJSONModel getDrAmt() {
		return drAmt;
	}

	/**
	 * @param drAmt the drAmt to set
	 */
	public void setDrAmt(AmountJSONModel drAmt) {
		this.drAmt = drAmt;
	}

	/**
	 * @return the crAmt
	 */
	public AmountJSONModel getCrAmt() {
		return crAmt;
	}

	/**
	 * @param crAmt the crAmt to set
	 */
	public void setCrAmt(AmountJSONModel crAmt) {
		this.crAmt = crAmt;
	}


}
