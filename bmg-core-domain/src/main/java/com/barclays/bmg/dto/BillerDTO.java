/*
 * Copyright (c) 2009 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */

package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class BillerDTO implements Serializable {

    private static final long serialVersionUID = 2291797624848476281L;
    public final static String BILLER_LANDLINE = "Landline";
    private String businessId;
    private String externalBillerId;
    private String billerId;
    //Kadikope
    private String actionCode;

    private String storeNumber;

    private String billerName;

    private String billerCategoryId;

    private String billerCategoryName;

    private boolean presentmentFlag;

    private String referenceNoText1;

    private String referenceNoText2;

    private BigDecimal minPaymentAmount;

    private BigDecimal TransactionFee;

    private String billAggregatorId;

    private String currency;

    private String supportCreditCardFlag;

    private String onlineBillerFlag;

    private String payeeReferenceFields;

    private String mobileDenominaiton;

    private BigDecimal[] mobileDenominaitonList;

    private boolean mobileProvider = false;

    private String serviceType;

    private String branchCode;

    private String billerAccountNumber;

    // Changes Start for TZBRB Bill Payment By saurabh

    private String referenceNoKey1;
    private BigDecimal maxPaymentAmount;

    private String pilotMode;

    // Changes End for TZBRB Bill Payment By saurabh

    public String getPilotMode() {
		return pilotMode;
	}

	public void setPilotMode(String pilotMode) {
		this.pilotMode = pilotMode;
	}

	public boolean isMobileProvider() {
	return mobileProvider;
    }

    public void setMobileProvider(boolean mobileProvider) {
	this.mobileProvider = mobileProvider;
    }

    /**
     * @return the businessId
     */
    public String getBusinessId() {
	return businessId;
    }

    /**
     * @param businessId
     *            the businessId to set
     */
    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    /**
     * @return the externalBillerId
     */
    public String getExternalBillerId() {
	return externalBillerId;
    }

    /**
     * @param externalBillerId
     *            the externalBillerId to set
     */
    public void setExternalBillerId(String externalBillerId) {
	this.externalBillerId = externalBillerId;
    }

    /**
     * @return the billerId
     */
    public String getBillerId() {
	return billerId;
    }

    /**
     * @param billerId
     *            the billerId to set
     */
    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    /**
     * @return the billerName
     */
    public String getBillerName() {
	return billerName;
    }

    /**
     * @param billerName
     *            the billerName to set
     */
    public void setBillerName(String billerName) {
	this.billerName = billerName;
    }

    /**
     * @return the billerCategoryId
     */
    public String getBillerCategoryId() {
	return billerCategoryId;
    }

    /**
     * @param billerCategoryId
     *            the billerCategoryId to set
     */
    public void setBillerCategoryId(String billerCategoryId) {
	this.billerCategoryId = billerCategoryId;
    }

    /**
     * @return the billerCategoryName
     */
    public String getBillerCategoryName() {
	return billerCategoryName;
    }

    /**
     * @param billerCategoryName
     *            the billerCategoryName to set
     */
    public void setBillerCategoryName(String billerCategoryName) {
	this.billerCategoryName = billerCategoryName;
    }

    /**
     * @return the transactionFee
     */
    public BigDecimal getTransactionFee() {
	return TransactionFee;
    }

    /**
     * @param transactionFee
     *            the transactionFee to set
     */
    public void setTransactionFee(BigDecimal transactionFee) {
	TransactionFee = transactionFee;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
	return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(String currency) {
	this.currency = currency;
    }

    /**
     * @return the referenceNoText1
     */
    public String getReferenceNoText1() {
	return referenceNoText1;
    }

    /**
     * @param referenceNoText1
     *            the referenceNoText1 to set
     */
    public void setReferenceNoText1(String referenceNoText1) {
	this.referenceNoText1 = referenceNoText1;
    }

    /**
     * @return the referenceNoText2
     */
    public String getReferenceNoText2() {
	return referenceNoText2;
    }

    /**
     * @param referenceNoText2
     *            the referenceNoText2 to set
     */
    public void setReferenceNoText2(String referenceNoText2) {
	this.referenceNoText2 = referenceNoText2;
    }

    /**
     * @return the payeeReferenceFields
     */
    public String getPayeeReferenceFields() {
	return payeeReferenceFields;
    }

    /**
     * @param payeeReferenceFields
     *            the payeeReferenceFields to set
     */
    public void setPayeeReferenceFields(String payeeReferenceFields) {
	this.payeeReferenceFields = payeeReferenceFields;
    }

    /**
     * @return the mobileDenominaiton
     */
    public String getMobileDenominaiton() {
	return mobileDenominaiton;
    }

    /**
     * @param mobileDenominaiton
     *            the mobileDenominaiton to set
     */
    public void setMobileDenominaiton(String mobileDenominaiton) {
	this.mobileDenominaiton = mobileDenominaiton;
    }

    /**
     * @return the mobileDenominaitonList
     */
    public BigDecimal[] getMobileDenominaitonList() {
	if (null != this.mobileDenominaitonList && this.mobileDenominaitonList.length > 0) {
	    return Arrays.copyOf(this.mobileDenominaitonList, this.mobileDenominaitonList.length);
	} else {
	    String[] denominationList = new String[20];
	    if (null != this.mobileDenominaiton && !"".equals(this.mobileDenominaiton)) {
		denominationList = this.mobileDenominaiton.split(",");
	    }
	    BigDecimal[] amountList = new BigDecimal[denominationList.length];
	    for (int i = 0; i < denominationList.length; i++) {
		amountList[i] = new BigDecimal(denominationList[i]);
	    }
	    return amountList;
	}
    }

    /**
     * @param mobileDenominaitonList
     *            the mobileDenominaitonList to set
     */
    public void setMobileDenominaitonList(BigDecimal[] mobileDenominaitonList) {
	if (mobileDenominaitonList != null) {
	    this.mobileDenominaitonList = Arrays.copyOf(mobileDenominaitonList, mobileDenominaitonList.length);
	} else {
	    this.mobileDenominaitonList = null;
	}
    }

    /**
     * @return the presentmentFlag
     */
    public boolean getPresentmentFlag() {
	return presentmentFlag;
    }

    /**
     * @param presentmentFlag
     *            the presentmentFlag to set
     */
    public void setPresentmentFlag(boolean presentmentFlag) {
	this.presentmentFlag = presentmentFlag;
    }

    /**
     * @return the supportCreditCardFlag
     */
    public String getSupportCreditCardFlag() {
	return supportCreditCardFlag;
    }

    /**
     * @param supportCreditCardFlag
     *            the supportCreditCardFlag to set
     */
    public void setSupportCreditCardFlag(String supportCreditCardFlag) {
	this.supportCreditCardFlag = supportCreditCardFlag;
    }

    /**
     * @return the onlineBillerFlag
     */
    public String getOnlineBillerFlag() {
	return onlineBillerFlag;
    }

    /**
     * @param onlineBillerFlag
     *            the onlineBillerFlag to set
     */
    public void setOnlineBillerFlag(String onlineBillerFlag) {
	this.onlineBillerFlag = onlineBillerFlag;
    }

    /**
     * @return the billAggregatorId
     */
    public String getBillAggregatorId() {
	return billAggregatorId;
    }

    /**
     * @param billAggregatorId
     *            the billAggregatorId to set
     */
    public void setBillAggregatorId(String billAggregatorId) {
	this.billAggregatorId = billAggregatorId;
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((billerId == null) ? 0 : billerId.hashCode());
	result = prime * result + ((businessId == null) ? 0 : businessId.hashCode());
	return result;
    }

    /**
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	BillerDTO other = (BillerDTO) obj;
	if (billerId == null) {
	    if (other.billerId != null) {
		return false;
	    }
	} else if (!billerId.equals(other.billerId)) {
	    return false;
	}
	if (businessId == null) {
	    if (other.businessId != null) {
		return false;
	    }
	} else if (!businessId.equals(other.businessId)) {
	    return false;
	}
	return true;
    }

    public String getServiceType() {
	return serviceType;
    }

    public void setServiceType(String serviceType) {
	this.serviceType = serviceType;
    }

    /**
     * @param minPaymentAmount
     *            the minPaymentAmount to set
     */
    public void setMinPaymentAmount(BigDecimal minPaymentAmount) {
	this.minPaymentAmount = minPaymentAmount;
    }

    /**
     * @return the minPaymentAmount
     */
    public BigDecimal getMinPaymentAmount() {
	return minPaymentAmount;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getBillerAccountNumber() {
	return billerAccountNumber;
    }

    public void setBillerAccountNumber(String billerAccountNumber) {
	this.billerAccountNumber = billerAccountNumber;
    }

    public String getReferenceNoKey1() {
	return referenceNoKey1;
    }

    public void setReferenceNoKey1(String referenceNoKey1) {
	this.referenceNoKey1 = referenceNoKey1;
    }

    public BigDecimal getMaxPaymentAmount() {
	return maxPaymentAmount;
    }

    public void setMaxPaymentAmount(BigDecimal maxPaymentAmount) {
	this.maxPaymentAmount = maxPaymentAmount;
    }

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

}
