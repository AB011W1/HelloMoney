/* Copyright 2008 Barclays PLC */

/**************************** Revision History ****************************************************
 * Version        Author          Date                        Description
 * 0.1            Elicer Zheng        2009/02/08                  Initial version
 *
 *
 **************************************************************************************************/

package com.barclays.bmg.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.type.AddressDetail;

/**
 * @author Scott Li
 */
public class CreditCardDTO extends BaseDomainDTO {
    private static final long serialVersionUID = 8838490950340895604L;

    /**
     * Card Activation Flag N = no need activate Y = need activate
     */
    public static final String CARD_ACTIVATION_NO_NEED = "N";

    public static final String CARD_ACTIVATION_NEED = "Y";

    /**
     * Card Status 0,1,F,S = active 0 = permanent 1 = temporary F = blocked due to lost/stolen S = Chip exceeded T = transfer P = purged
     */
    public static final String CARD_STATUS_PERMANENT = "0";

    public static final String CARD_STATUS_TEMPORARY = "1";

    public static final String CARD_STATUS_BLOCKED = "F";

    public static final String CARD_STATUS_CHIP_EXCEEDED = "S";

    public static final String CARD_STATUS_TRANSFER = "T";

    public static final String CARD_STATUS_PURGED = "P";

    public static final List<String> ACTIVE_CARD_STATUS_LIST = new ArrayList<String>();
    static {
	ACTIVE_CARD_STATUS_LIST.add(CARD_STATUS_PERMANENT);
	ACTIVE_CARD_STATUS_LIST.add(CARD_STATUS_TEMPORARY);
	ACTIVE_CARD_STATUS_LIST.add(CARD_STATUS_BLOCKED);
	ACTIVE_CARD_STATUS_LIST.add(CARD_STATUS_CHIP_EXCEEDED);
    }

    /**
     * Card Holder Type 1 = Primary 0 = Supplementary
     */
    public static final String PRIMARY = "P";

    public static final String SUPPLEMENTARY = "S";

    /**
     * Issue Action Code Type 0 = issue credit card pin 1 = deactive credit card pin
     */
    public static final String ISSUE_CREDIT_CARD_PIN = "0";

    public static final String DEACTIVE_CREDIT_CARD_PIN = "1";

    private String cardNumber;

    private String nameInCard;

    private String cardHolder;

    private String cardStatus;

    private String primaryOrSupplementary;

    private Date cardExpireDate;

    private Date cardActivationDate;

    private String cardActivationFlag;

    private String cardIssueActionCode;

    private String creditCardOwnershipTypeCode;

    private AddressDetail deliverAddress;

    private CreditCardAccountDTO creditCardAccount;

    public CreditCardDTO() {
	deliverAddress = new AddressDetail();
    }

    /**
     * @return the cardNumber
     */
    public String getCardNumber() {
	return cardNumber;
    }

    public String getCreditCardOwnershipTypeCode() {
	return creditCardOwnershipTypeCode;
    }

    public void setCreditCardOwnershipTypeCode(String creditCardOwnershipTypeCode) {
	this.creditCardOwnershipTypeCode = creditCardOwnershipTypeCode;
    }

    /**
     * @param cardNumber
     *            the cardNumber to set
     */
    public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
    }

    /**
     * @return the nameInCard
     */
    public String getNameInCard() {
	return nameInCard;
    }

    /**
     * @param nameInCard
     *            the nameInCard to set
     */
    public void setNameInCard(String nameInCard) {
	this.nameInCard = nameInCard;
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
     * @return the cardStatus
     */
    public String getCardStatus() {
	return cardStatus;
    }

    /**
     * @param cardStatus
     *            the cardStatus to set
     */
    public void setCardStatus(String cardStatus) {
	this.cardStatus = cardStatus;
    }

    /**
     * @return the primaryOrSupplementary
     */
    public String getPrimaryOrSupplementary() {
	return primaryOrSupplementary;
    }

    /**
     * @param primaryOrSupplementary
     *            the primaryOrSupplementary to set
     */
    public void setPrimaryOrSupplementary(String primaryOrSupplementary) {
	this.primaryOrSupplementary = primaryOrSupplementary;
    }

    /**
     * @return the cardExpireDate
     */
    public Date getCardExpireDate() {
	if (cardExpireDate != null) {
	    return new Date(cardExpireDate.getTime());
	}
	return null;
    }

    /**
     * @param cardExpireDate
     *            the cardExpireDate to set
     */
    public void setCardExpireDate(Date cardExpireDate) {
	if (cardExpireDate != null) {
	    this.cardExpireDate = new Date(cardExpireDate.getTime());
	} else {
	    this.cardExpireDate = null;
	}
    }

    public Date getCardActivationDate() {
	if (cardActivationDate != null) {
	    return new Date(cardActivationDate.getTime());
	}
	return null;
    }

    public void setCardActivationDate(Date cardActivationDate) {
	if (cardActivationDate != null) {
	    this.cardActivationDate = new Date(cardActivationDate.getTime());
	} else {
	    this.cardActivationDate = null;
	}
    }

    public String getCardActivationFlag() {
	return cardActivationFlag;
    }

    public void setCardActivationFlag(String cardActivationFlag) {
	this.cardActivationFlag = cardActivationFlag;
    }

    public AddressDetail getDeliverAddress() {
	return deliverAddress;
    }

    public void setDeliverAddress(AddressDetail deliverAddress) {
	this.deliverAddress = deliverAddress;
    }

    /**
     * @return the creditCardAccount
     */
    public final CreditCardAccountDTO getCreditCardAccount() {
	return creditCardAccount;
    }

    /**
     * @param creditCardAccount
     *            the creditCardAccount to set
     */
    public final void setCreditCardAccount(CreditCardAccountDTO creditCardAccount) {
	this.creditCardAccount = creditCardAccount;
    }

    /**
     * @param cardIssueActionCode
     *            the cardIssueActionCode to set
     */
    public void setCardIssueActionCode(String cardIssueActionCode) {
	this.cardIssueActionCode = cardIssueActionCode;
    }

    /**
     * @return the cardIssueActionCode
     */
    public String getCardIssueActionCode() {
	return cardIssueActionCode;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}

	if (this == obj) {
	    return true;
	}

	if (!(obj instanceof CreditCardDTO)) {
	    return false;
	}

	CreditCardDTO creditCard = (CreditCardDTO) obj;
	// compare credit card account
	if (creditCardAccount == null) {
	    if (creditCard.getCreditCardAccount() != null) {
		return false;
	    }
	} else if (!creditCardAccount.equals(creditCard.getCreditCardAccount())) {
	    return false;
	}

	// compare credit card number
	if (cardNumber == null) {
	    if (creditCard.getCardNumber() != null) {
		return false;
	    }
	} else if (!cardNumber.equals(creditCard.getCardNumber())) {
	    return false;
	}

	return true;
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    // ORCHARD CHANGES START
    private String creditCardOrgCode;
    private Integer creditCardSequenceNumber;
    private String creditCardBlockCode;

    public String getCreditCardBlockCode() {
	return creditCardBlockCode;
    }

    public void setCreditCardBlockCode(String creditCardBlockCode) {
	this.creditCardBlockCode = creditCardBlockCode;
    }

    public String getCreditCardOrgCode() {
	return creditCardOrgCode;
    }

    public void setCreditCardOrgCode(String creditCardOrgCode) {
	this.creditCardOrgCode = creditCardOrgCode;
    }

    public Integer getCreditCardSequenceNumber() {
	return creditCardSequenceNumber;
    }

    public void setCreditCardSequenceNumber(Integer creditCardSequenceNumber) {
	this.creditCardSequenceNumber = creditCardSequenceNumber;
    }
    // ORCHARD CHANGES END
}
