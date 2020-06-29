package com.barclays.bmg.json.model;


import java.io.Serializable;

import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CreditCardActivityInfoJSONModel extends BMBPayloadData implements Serializable {
	
	private AmountJSONModel pmtRecv;
    private AmountJSONModel totPur;
    private AmountJSONModel totCshWdr;
    private AmountJSONModel feeAndChrg;
    private String currency;
    
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public AmountJSONModel getPmtRecv() {
		return pmtRecv;
	}
	public void setPmtRecv(AmountJSONModel pmtRecv) {
		this.pmtRecv = pmtRecv;
	}
	public AmountJSONModel getTotPur() {
		return totPur;
	}
	public void setTotPur(AmountJSONModel totPur) {
		this.totPur = totPur;
	}
	public AmountJSONModel getTotCshWdr() {
		return totCshWdr;
	}
	public void setTotCshWdr(AmountJSONModel totCshWdr) {
		this.totCshWdr = totCshWdr;
	}
	public AmountJSONModel getFeeAndChrg() {
		return feeAndChrg;
	}
	public void setFeeAndChrg(AmountJSONModel feeAndChrg) {
		this.feeAndChrg = feeAndChrg;
	}
    
	public CreditCardActivityInfoJSONModel(CreditCardStmtBalanceInfoDTO ccStmtBalInfo, String currency) {
		super();

		this.pmtRecv = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getPaymentReceived()));
		this.totPur = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getTotalPurchase()));
		this.totCshWdr = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getTotalCashWithdrawn()));
		this.feeAndChrg = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(ccStmtBalInfo.getFeeAndCharge()));
		this.currency = currency;
}

}