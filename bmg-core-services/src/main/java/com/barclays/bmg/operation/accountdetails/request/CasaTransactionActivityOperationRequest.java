package com.barclays.bmg.operation.accountdetails.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CASAAccountDTO;

public class CasaTransactionActivityOperationRequest extends RequestContext {
    private String accountNo;
    private String brnCde;
    private String days;
    private CASAAccountDTO casaAccountDTO;

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

    public String getDays() {
	return days;
    }

    public void setDays(String days) {
	this.days = days;
    }

    public CASAAccountDTO getCasaAccountDTO() {
	return casaAccountDTO;
    }

    public void setCasaAccountDTO(CASAAccountDTO casaAccountDTO) {
	this.casaAccountDTO = casaAccountDTO;
    }

    public String getBrnCde() {
	return brnCde;
    }

    public void setBrnCde(String brnCde) {
	this.brnCde = brnCde;
    }

}
