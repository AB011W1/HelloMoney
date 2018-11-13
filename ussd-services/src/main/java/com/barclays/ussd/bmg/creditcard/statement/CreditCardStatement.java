package com.barclays.ussd.bmg.creditcard.statement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.ussd.bean.Amount;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardStatement {
    @JsonProperty
    private Amount prvBal;

    @JsonProperty
    private Amount pmtRecv;

    @JsonProperty
    private Amount totCshWdr;

    @JsonProperty
    private Amount actBal;

    @JsonProperty
    private Amount totPur;

    @JsonProperty
    private Amount feeAndChrg;

    @JsonProperty
    private String statementDate;

    @JsonProperty
    private Amount minDueAmt; //CR75

    @JsonProperty
    private String dueDate;

    public Amount getPrvBal() {
	return prvBal;
    }

    public void setPrvBal(Amount prvBal) {
	this.prvBal = prvBal;
    }

    public Amount getPmtRecv() {
	return pmtRecv;
    }

    public void setPmtRecv(Amount pmtRecv) {
	this.pmtRecv = pmtRecv;
    }

    public Amount getTotCshWdr() {
	return totCshWdr;
    }

    public void setTotCshWdr(Amount totCshWdr) {
	this.totCshWdr = totCshWdr;
    }

    public Amount getActBal() {
	return actBal;
    }

    public void setActBal(Amount actBal) {
	this.actBal = actBal;
    }

    public Amount getTotPur() {
	return totPur;
    }

    public void setTotPur(Amount totPur) {
	this.totPur = totPur;
    }

    public Amount getFeeAndChrg() {
	return feeAndChrg;
    }

    public void setFeeAndChrg(Amount feeAndChrg) {
	this.feeAndChrg = feeAndChrg;
    }

    public String getStatementDate() {
	return statementDate;
    }

    public void setStatementDate(String statementDate) {
	this.statementDate = statementDate;
    }

	public Amount getMinDueAmt() {
		return minDueAmt;
	}

	public void setMinDueAmt(Amount minDueAmt) {
		this.minDueAmt = minDueAmt;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


}
