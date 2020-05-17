package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.List;

import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CreditCardStmtBalanceInfoJSONModel extends BMBPayloadData implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = -7707436185857552034L;

    private AmountJSONModel prvBal;
    private AmountJSONModel pmtRecv;
    private AmountJSONModel totPur;
    private AmountJSONModel totCshWdr;
    private AmountJSONModel feeAndChrg;
    private AmountJSONModel actBal;
    private String statementDate;
    private AmountJSONModel minDueAmt; //CR75
    private String dueDate;
    private String actNo;
	private String currency;
	private String sequenceNumber;


	public CreditCardStmtBalanceInfoJSONModel(CreditCardStmtBalanceInfoDTO ccStmtBalInfo, String currency, String actNo) {
	super();

	this.prvBal = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getPrevBalance()));
	this.pmtRecv = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getPaymentReceived()));
	this.totPur = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getTotalPurchase()));
	this.totCshWdr = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getTotalCashWithdrawn()));
	this.feeAndChrg = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getFeeAndCharge()));
	this.actBal = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getTotalOutsAmt()));
	this.statementDate = BMGFormatUtility.getShortDate(ccStmtBalInfo.getStatementDate().getTime());
	this.minDueAmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getMinDue())); ////CR75
	this.dueDate = BMGFormatUtility.getShortDate(ccStmtBalInfo.getDueDate().getTime());
	this.sequenceNumber = ccStmtBalInfo.getSequenceNumber();
	this.actNo = actNo;
	this.currency = currency;
    }

    public CreditCardStmtBalanceInfoJSONModel() {
	super();
    }

    public CreditCardStmtBalanceInfoJSONModel(CreditCardStmtBalanceInfoDTO ccStmtBalInfo, String currency) {
    		super();

    		this.prvBal = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getPrevBalance()));
    		this.pmtRecv = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getPaymentReceived()));
    		this.totPur = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getTotalPurchase()));
    		this.totCshWdr = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getTotalCashWithdrawn()));
    		this.feeAndChrg = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getFeeAndCharge()));
    		this.actBal = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getTotalOutsAmt()));
    		this.statementDate = BMGFormatUtility.getShortDate(ccStmtBalInfo.getStatementDate().getTime());
    		this.minDueAmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getMinDue())); ////CR75
    		this.dueDate = BMGFormatUtility.getShortDate(ccStmtBalInfo.getDueDate().getTime());
	}
    
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public AmountJSONModel getPrvBal() {
	return prvBal;
    }

    public void setPrvBal(AmountJSONModel prvBal) {
	this.prvBal = prvBal;
    }

    public AmountJSONModel getPmtRecv() {
	return pmtRecv;
    }

    public void setPmtRecv(AmountJSONModel pmtRecv) {
	this.pmtRecv = pmtRecv;
    }

    public AmountJSONModel getTotCshWdr() {
	return totCshWdr;
    }

    public void setTotCshWdr(AmountJSONModel totCshWdr) {
	this.totCshWdr = totCshWdr;
    }

    public AmountJSONModel getActBal() {
	return actBal;
    }

    public void setActBal(AmountJSONModel actBal) {
	this.actBal = actBal;
    }

    public AmountJSONModel getTotPur() {
	return totPur;
    }

    public void setTotPur(AmountJSONModel totPur) {
	this.totPur = totPur;
    }

    public AmountJSONModel getFeeAndChrg() {
	return feeAndChrg;
    }

    public void setFeeAndChrg(AmountJSONModel feeAndChrg) {
	this.feeAndChrg = feeAndChrg;
    }

    public String getStatementDate() {
	return statementDate;
    }

    public void setStatementDate(String statementDate) {
	this.statementDate = statementDate;
    }

	public AmountJSONModel getMinDueAmt() {
		return minDueAmt;
	}

	public void setMinDueAmt(AmountJSONModel minDueAmt) {
		this.minDueAmt = minDueAmt;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}