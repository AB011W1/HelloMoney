package com.barclays.bmg.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            Martin Sit      15 Apr 2010                  Initial version
 *
 *
 ********************************************************************************/

/**
 * @author Martin Sit
 */
public class CreditCardTransactionHistoryListDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<CreditCardActivityDTO> creditCardActivityList;

    private CreditCardStmtAccountInfoDTO accountInfo;

    private CreditCardStmtBalanceInfoDTO balanceInfo;

    private CreditCardStmtPointsInfoDTO rewardInfo;

    private List<CreditCardTransactionHistoryDTO> creditCardDTOList;

    /**
     * @return the creditCardDTOList
     */
    public List<CreditCardTransactionHistoryDTO> getCreditCardDTOList() {
	if (creditCardDTOList == null) {
	    return new ArrayList<CreditCardTransactionHistoryDTO>();
	}

	return creditCardDTOList;
    }

    /**
     * @param creditCardDTOList
     *            the creditCardDTOList to set
     */
    public void setCreditCardDTOList(List<CreditCardTransactionHistoryDTO> creditCardDTOList) {
	this.creditCardDTOList = creditCardDTOList;
    }

    /**
     * @return the creditCardActivityList
     */
    public List<CreditCardActivityDTO> getCreditCardActivityList() {
	if (creditCardActivityList == null) {
	    return new ArrayList<CreditCardActivityDTO>();
	}
	return creditCardActivityList;
    }

    /**
     * @param creditCardActivityList
     *            the creditCardActivityList to set
     */
    public void setCreditCardActivityList(List<CreditCardActivityDTO> creditCardActivityList) {
	this.creditCardActivityList = creditCardActivityList;
    }

    public CreditCardStmtAccountInfoDTO getAccountInfo() {
	return accountInfo;
    }

    public void setAccountInfo(CreditCardStmtAccountInfoDTO accountInfo) {
	this.accountInfo = accountInfo;
    }

    public CreditCardStmtBalanceInfoDTO getBalanceInfo() {
	return balanceInfo;
    }

    public void setBalanceInfo(CreditCardStmtBalanceInfoDTO balanceInfo) {
	this.balanceInfo = balanceInfo;
    }

    public CreditCardStmtPointsInfoDTO getRewardInfo() {
	return rewardInfo;
    }

    public void setRewardInfo(CreditCardStmtPointsInfoDTO rewardInfo) {
	this.rewardInfo = rewardInfo;
    }

}
