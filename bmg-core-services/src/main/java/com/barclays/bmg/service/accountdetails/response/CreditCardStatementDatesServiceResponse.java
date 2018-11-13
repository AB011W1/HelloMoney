package com.barclays.bmg.service.accountdetails.response;

import java.util.Calendar;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;

public class CreditCardStatementDatesServiceResponse extends ResponseContext {

    private static final long serialVersionUID = 2562162262096123412L;

    private List<CreditCardStmtBalanceInfoDTO> statementObj;
    private List<Calendar> statementDateList;

    public List<CreditCardStmtBalanceInfoDTO> getStatementObj() {
	return statementObj;
    }

    public void setStatementObj(List<CreditCardStmtBalanceInfoDTO> statementObj) {
	this.statementObj = statementObj;
    }

    public List<Calendar> getStatementDateList() {
	return statementDateList;
    }

    public void setStatementDateList(List<Calendar> statementDateList) {
	this.statementDateList = statementDateList;
    }

}