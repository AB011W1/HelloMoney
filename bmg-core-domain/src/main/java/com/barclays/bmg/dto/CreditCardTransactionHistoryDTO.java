/**
 *
 */

package com.barclays.bmg.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            ELicer Zheng        16 Sep 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO Class/Interface description. Elicer Zheng
 */

public class CreditCardTransactionHistoryDTO implements Serializable {
    private String cardNumber;
    // private String maskCardNumber;

    private String cardHolder;
    private String cardType;

    // Add by li,can start: handling replacement card
    private List<String> replacementCardNumberList;
    // Add by li,can end: handling replacement card
    //
    // private CreditCardStmtBalanceInfoDTO balanceInfo;
    //
    // private CreditCardStmtPointsInfoDTO rewardInfo;
    //
    // private CreditCardStmtAccountInfoDTO accountInfo;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<CreditCardActivityDTO> creditCardActivityList;

    public List<String> getReplacementCardNumberList() {
	return replacementCardNumberList;
    }

    public void setReplacementCardNumberList(List<String> replacementCardNumberList) {
	this.replacementCardNumberList = replacementCardNumberList;
    }

    /**
     * @return the cardNumber
     */
    public String getCardNumber() {
	return cardNumber;
    }

    /**
     * @param cardNumber
     *            the cardNumber to set
     */
    public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
    }

    /**
     * @return the cardHolder
     */
    public String getCardHolder() {
	return cardHolder;
    }

    /**
     * @param cardHolder
     *            the cardHolder to set
     */
    public void setCardHolder(String cardHolder) {
	this.cardHolder = cardHolder;
    }

    /**
     * @return the creditCardActivityList
     */
    public List<CreditCardActivityDTO> getCreditCardActivityList() {
	return creditCardActivityList;
    }

    /**
     * @param creditCardActivityList
     *            the creditCardActivityList to set
     */
    public void setCreditCardActivityList(List<CreditCardActivityDTO> creditCardActivityList) {
	this.creditCardActivityList = creditCardActivityList;
    }

    public void addActivityDTO(CreditCardActivityDTO activityDTO) {
	if (this.creditCardActivityList == null) {
	    this.creditCardActivityList = new ArrayList<CreditCardActivityDTO>();
	}
	this.creditCardActivityList.add(activityDTO);
    }

    public String getCardType() {
	return cardType;
    }

    public void setCardType(String cardType) {
	this.cardType = cardType;
    }

    // public String getMaskCardNumber() {
    // return maskCardNumber;
    // }
    //
    // public void setMaskCardNumber(String maskCardNumber) {
    // this.maskCardNumber = maskCardNumber;
    // }

}
