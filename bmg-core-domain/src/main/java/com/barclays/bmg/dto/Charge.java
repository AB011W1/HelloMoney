/**
 *
 */
package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            Jason        19 Oct 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO Class/Interface description.
 *
 * @author Jason
 */
public class Charge implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String chargeTypeCode;
    private Amount chargeAmount;
    private Amount chargeAmountInLCY;
    private boolean updated;
    private String debitAccountNumber;
    private String debitAccountCurrency;

    // New fields for CPB MakeBillPayment 10/05/2017
    private Double cpbChargeAmount;
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;
	private String cpbMakeBillPaymentFlag;


    /**
     * @return the debitAccountNumber
     */
    public String getDebitAccountNumber() {
	return debitAccountNumber;
    }

    /**
     * @param debitAccountNumber
     *            the debitAccountNumber to set
     */
    public void setDebitAccountNumber(String debitAccountNumber) {
	this.debitAccountNumber = debitAccountNumber;
    }

    /**
     * @return the debitAccountCurrency
     */
    public String getDebitAccountCurrency() {
	return debitAccountCurrency;
    }

    /**
     * @param debitAccountCurrency
     *            the debitAccountCurrency to set
     */
    public void setDebitAccountCurrency(String debitAccountCurrency) {
	this.debitAccountCurrency = debitAccountCurrency;
    }

    /**
     * @return the updated
     */
    public boolean isUpdated() {
	return updated;
    }

    /**
     * @param updated
     *            the updated to set
     */
    public void setUpdated(boolean updated) {
	this.updated = updated;
    }

    /**
     * @return the chargeAmountInLCY
     */
    public Amount getChargeAmountInLCY() {
	return chargeAmountInLCY;
    }

    /**
     * @param chargeAmountInLCY
     *            the chargeAmountInLCY to set
     */
    public void setChargeAmountInLCY(Amount chargeAmountInLCY) {
	this.chargeAmountInLCY = chargeAmountInLCY;
    }

    private boolean waiverFlag;
    private BigDecimal sourceToLCYFXRate;
    private BigDecimal lCYToTargetFXRate;
    private BigDecimal effectiveFXRate;

    public Charge() {

    }

    /**
     * @return the waiverFlag
     */
    public boolean isWaiverFlag() {
	return waiverFlag;
    }

    /**
     * @param waiverFlag
     *            the waiverFlag to set
     */
    public void setWaiverFlag(boolean waiverFlag) {
	this.waiverFlag = waiverFlag;
    }

    /**
     * @return the sourceToLCYFXRate
     */
    public BigDecimal getSourceToLCYFXRate() {
	return sourceToLCYFXRate;
    }

    /**
     * @param sourceToLCYFXRate
     *            the sourceToLCYFXRate to set
     */
    public void setSourceToLCYFXRate(BigDecimal sourceToLCYFXRate) {
	this.sourceToLCYFXRate = sourceToLCYFXRate;
    }

    /**
     * @return the lCYToTargetFXRat
     */
    public BigDecimal getLCYToTargetFXRate() {
	return lCYToTargetFXRate;
    }

    /**
     * @param toTargetFXRat
     *            the lCYToTargetFXRat to set
     */
    public void setLCYToTargetFXRate(BigDecimal toTargetFXRate) {
	lCYToTargetFXRate = toTargetFXRate;
    }

    /**
     * @return the effectiveFXRate
     */
    public BigDecimal getEffectiveFXRate() {
	return effectiveFXRate;
    }

    /**
     * @param effectiveFXRate
     *            the effectiveFXRate to set
     */
    public void setEffectiveFXRate(BigDecimal effectiveFXRate) {
	this.effectiveFXRate = effectiveFXRate;
    }

    public Charge(String chargeType, Amount amount) {
	this.chargeTypeCode = chargeType;
	this.chargeAmount = amount;

    }

    /**
     * @param chargeTypeCode
     *            the chargeTypeCode to set
     */
    public void setChargeTypeCode(String chargeTypeCode) {
	this.chargeTypeCode = chargeTypeCode;
    }

    /**
     * @return the chargeTypeCode
     */
    public String getChargeTypeCode() {
	return chargeTypeCode;
    }

    /**
     * @param chargeAmount
     *            the chargeAmount to set
     */
    public void setChargeAmount(Amount chargeAmount) {
	this.chargeAmount = chargeAmount;
    }

    /**
     * @return the chargeAmount
     */
    public Amount getChargeAmount() {
	return chargeAmount;
    }

	public Double getCpbChargeAmount() {
		return cpbChargeAmount;
	}

	public void setCpbChargeAmount(Double cpbChargeAmount) {
		this.cpbChargeAmount = cpbChargeAmount;
	}

	public String getFeeGLAccount() {
		return feeGLAccount;
	}

	public void setFeeGLAccount(String feeGLAccount) {
		this.feeGLAccount = feeGLAccount;
	}

	public String getChargeNarration() {
		return chargeNarration;
	}

	public void setChargeNarration(String chargeNarration) {
		this.chargeNarration = chargeNarration;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxGLAccount() {
		return taxGLAccount;
	}

	public void setTaxGLAccount(String taxGLAccount) {
		this.taxGLAccount = taxGLAccount;
	}

	public String getExciseDutyNarration() {
		return ExciseDutyNarration;
	}

	public void setExciseDutyNarration(String exciseDutyNarration) {
		ExciseDutyNarration = exciseDutyNarration;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCpbMakeBillPaymentFlag() {
		return cpbMakeBillPaymentFlag;
	}

	public void setCpbMakeBillPaymentFlag(String cpbMakeBillPaymentFlag) {
		this.cpbMakeBillPaymentFlag = cpbMakeBillPaymentFlag;
	}



}
