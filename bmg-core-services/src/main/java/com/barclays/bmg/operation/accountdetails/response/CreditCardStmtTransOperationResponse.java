package com.barclays.bmg.operation.accountdetails.response;

import java.util.Calendar;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;

public class CreditCardStmtTransOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -3850995936136860971L;

    private CreditCardAccountDTO creditCardAccountDTO;

    private List<Calendar> statementDates;

    private List<CreditCardStmtBalanceInfoDTO> creditCardStmtBalanceInfoDTO;

    public CreditCardAccountDTO getCreditCardAccountDTO() {
	return creditCardAccountDTO;
    }

    public void setCreditCardAccountDTO(CreditCardAccountDTO creditCardAccountDTO) {
	this.creditCardAccountDTO = creditCardAccountDTO;
    }

    public List<Calendar> getStatementDates() {
	return statementDates;
    }

    public void setStatementDates(List<Calendar> statementDates) {
	this.statementDates = statementDates;
    }

    public List<CreditCardStmtBalanceInfoDTO> getCreditCardStmtBalanceInfoDTO() {
	return creditCardStmtBalanceInfoDTO;
    }

    public void setCreditCardStmtBalanceInfoDTO(List<CreditCardStmtBalanceInfoDTO> creditCardStmtBalanceInfoDTO) {
	this.creditCardStmtBalanceInfoDTO = creditCardStmtBalanceInfoDTO;
    }

}
