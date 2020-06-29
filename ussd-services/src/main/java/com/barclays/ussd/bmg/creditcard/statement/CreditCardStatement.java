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
    
    @JsonProperty
    private String actNo;// ccAcountNo
    
    @JsonProperty
    private String currency;
    
    @JsonProperty
    private String sequenceNumber;

    public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
