package com.barclays.bmg.operation.accountdetails.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;

public class CreditCardUnbilledTransOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1119005114026387184L;

    private CreditCardAccountDTO creditCardAccountDTO;
    private CreditCardTransactionHistoryDTO creditCardHistory;
    List<CreditCardActivityDTO> activityList;

    public CreditCardAccountDTO getCreditCardAccountDTO() {
	return creditCardAccountDTO;
    }

    public void setCreditCardAccountDTO(CreditCardAccountDTO creditCardAccountDTO) {
	this.creditCardAccountDTO = creditCardAccountDTO;
    }

    public CreditCardTransactionHistoryDTO getCreditCardHistory() {
	return creditCardHistory;
    }

    public void setCreditCardHistory(CreditCardTransactionHistoryDTO creditCardHistory) {
	this.creditCardHistory = creditCardHistory;
    }

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public List<CreditCardActivityDTO> getActivityList() {
	return activityList;
    }

    public void setActivityList(List<CreditCardActivityDTO> activityList) {
	this.activityList = activityList;
    }

}
