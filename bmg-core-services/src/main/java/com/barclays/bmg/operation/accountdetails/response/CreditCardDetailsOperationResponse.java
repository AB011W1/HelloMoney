package com.barclays.bmg.operation.accountdetails.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardStmtPointsInfoDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;

public class CreditCardDetailsOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 9166514658996620010L;

    private CreditCardAccountDTO creditCardAccountDTO;
    private CreditCardTransactionHistoryDTO primaryCardHistory;
    private List<CreditCardTransactionHistoryDTO> replacementCardGroupedHistoryList;
    private List<CreditCardTransactionHistoryDTO> supplimentryCardGroupedHistoryList;
    private CreditCardStmtPointsInfoDTO creditCardStmtPointsInfo;

    public CreditCardAccountDTO getCreditCardAccountDTO() {
	return creditCardAccountDTO;
    }

    public void setCreditCardAccountDTO(CreditCardAccountDTO creditCardAccountDTO) {
	this.creditCardAccountDTO = creditCardAccountDTO;
    }

    public CreditCardTransactionHistoryDTO getPrimaryCardHistory() {
	return primaryCardHistory;
    }

    public void setPrimaryCardHistory(CreditCardTransactionHistoryDTO primaryCardHistory) {
	this.primaryCardHistory = primaryCardHistory;
    }

    public List<CreditCardTransactionHistoryDTO> getReplacementCardGroupedHistoryList() {
	return replacementCardGroupedHistoryList;
    }

    public void setReplacementCardGroupedHistoryList(List<CreditCardTransactionHistoryDTO> replacementCardGroupedHistoryList) {
	this.replacementCardGroupedHistoryList = replacementCardGroupedHistoryList;
    }

    public List<CreditCardTransactionHistoryDTO> getSupplimentryCardGroupedHistoryList() {
	return supplimentryCardGroupedHistoryList;
    }

    public void setSupplimentryCardGroupedHistoryList(List<CreditCardTransactionHistoryDTO> supplimentryCardGroupedHistoryList) {
	this.supplimentryCardGroupedHistoryList = supplimentryCardGroupedHistoryList;
    }

    public CreditCardStmtPointsInfoDTO getCreditCardStmtPointsInfo() {
	return creditCardStmtPointsInfo;
    }

    public void setCreditCardStmtPointsInfo(CreditCardStmtPointsInfoDTO creditCardStmtPointsInfo) {
	this.creditCardStmtPointsInfo = creditCardStmtPointsInfo;
    }

}
