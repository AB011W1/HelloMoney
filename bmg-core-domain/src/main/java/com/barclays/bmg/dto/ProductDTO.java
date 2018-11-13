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

/**
 * TODO This class is used to store the product info in the cache.
 * 
 * @version $Revision: 1.1 $
 * 
 * @since TODO Add text or delete.
 * @author Elicer Zheng
 */

public class ProductDTO implements Serializable, Comparable<ProductDTO> {
    /** TODO Comment for <code>serialVersionUID</code>. */

    private static final long serialVersionUID = 1L;
    private String productCode;

    private String systemID;

    private String businessID;
    private String productNameKey;
    private int displayOrder;
    private String categoryCode;
    private String deleteFlag;

    private String productDesc;

    private String productGroup;

    private String currencyCode;

    private String supportedTenureType;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private String interestPayoutFrequency;

    private String interestCompoundingFrequency;

    private Boolean supportCheque = Boolean.FALSE;

    private String internetEnabled;

    /**
     * @return the internetEnabled
     */
    public String getInternetEnabled() {
	return internetEnabled;
    }

    /**
     * @param internetEnabled
     *            the internetEnabled to set
     */
    public void setInternetEnabled(String internetEnabled) {
	this.internetEnabled = internetEnabled;
    }

    /**
     * @return the supportedTenureType
     */
    public String getSupportedTenureType() {
	return supportedTenureType;
    }

    /**
     * @param supportedTenureType
     *            the supportedTenureType to set
     */
    public void setSupportedTenureType(String supportedTenureType) {
	this.supportedTenureType = supportedTenureType;
    }

    /**
     * @return the minAmount
     */
    public BigDecimal getMinAmount() {
	return minAmount;
    }

    /**
     * @param minAmount
     *            the minAmount to set
     */
    public void setMinAmount(BigDecimal minAmount) {
	this.minAmount = minAmount;
    }

    /**
     * @return the maxAmount
     */
    public BigDecimal getMaxAmount() {
	return maxAmount;
    }

    /**
     * @param maxAmount
     *            the maxAmount to set
     */
    public void setMaxAmount(BigDecimal maxAmount) {
	this.maxAmount = maxAmount;
    }

    /**
     * @return the currencyCode
     */
    public final String getCurrencyCode() {
	return currencyCode;
    }

    /**
     * @param currencyCode
     *            the currencyCode to set
     */
    public final void setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
    }

    /**
     * @return the productDesc
     */
    public String getProductDesc() {
	return productDesc;
    }

    /**
     * @param productDesc
     *            the productDesc to set
     */
    public void setProductDesc(String productDesc) {
	this.productDesc = productDesc;
    }

    /**
     * @return the productGroup
     */
    public String getProductGroup() {
	return productGroup;
    }

    /**
     * @param productGroup
     *            the productGroup to set
     */
    public void setProductGroup(String productGroup) {
	this.productGroup = productGroup;
    }

    /**
     * @return the productCode
     */
    public String getProductCode() {
	return productCode;
    }

    /**
     * @param productCode
     *            the productCode to set
     */
    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    /**
     * @return the systemID
     */
    public String getSystemID() {
	return systemID;
    }

    /**
     * @param systemID
     *            the systemID to set
     */
    public void setSystemID(String systemID) {
	this.systemID = systemID;
    }

    /**
     * @return the businessID
     */
    public String getBusinessID() {
	return businessID;
    }

    /**
     * @param businessID
     *            the businessID to set
     */
    public void setBusinessID(String businessID) {
	this.businessID = businessID;
    }

    /**
     * @return the productNameKey
     */
    public String getProductNameKey() {
	return productNameKey;
    }

    /**
     * @param productNameKey
     *            the productNameKey to set
     */
    public void setProductNameKey(String productNameKey) {
	this.productNameKey = productNameKey;
    }

    /**
     * @return the displayOrder
     */
    public int getDisplayOrder() {
	return displayOrder;
    }

    /**
     * @param displayOrder
     *            the displayOrder to set
     */
    public void setDisplayOrder(int displayOrder) {
	this.displayOrder = displayOrder;
    }

    /**
     * @return the categoryCode
     */
    public String getCategoryCode() {
	return categoryCode;
    }

    /**
     * @param categoryCode
     *            the categoryCode to set
     */
    public void setCategoryCode(String categoryCode) {
	this.categoryCode = categoryCode;
    }

    /**
     * @return the deleteFlag
     */
    public String getDeleteFlag() {
	return deleteFlag;
    }

    /**
     * @param deleteFlag
     *            the deleteFlag to set
     */
    public void setDeleteFlag(String deleteFlag) {
	this.deleteFlag = deleteFlag;
    }

    public String getInterestPayoutFrequency() {
	return interestPayoutFrequency;
    }

    public void setInterestPayoutFrequency(String interestPayoutFrequency) {
	this.interestPayoutFrequency = interestPayoutFrequency;
    }

    public String getInterestCompoundingFrequency() {
	return interestCompoundingFrequency;
    }

    public void setInterestCompoundingFrequency(String interestCompoundingFrequency) {
	this.interestCompoundingFrequency = interestCompoundingFrequency;
    }

    public Boolean getSupportCheque() {
	return supportCheque;
    }

    public void setSupportCheque(Boolean supportCheque) {
	this.supportCheque = supportCheque;
    }

    /**
     * TODO Constructor description, including pre/post conditions and invariants.
     * 
     */
    public ProductDTO() {
	super();
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((businessID == null) ? 0 : businessID.hashCode());
	result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
	result = prime * result + ((systemID == null) ? 0 : systemID.hashCode());
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
	ProductDTO other = (ProductDTO) obj;
	if (businessID == null) {
	    if (other.businessID != null) {
		return false;
	    }
	} else if (!businessID.equals(other.businessID)) {
	    return false;
	}
	if (productCode == null) {
	    if (other.productCode != null) {
		return false;
	    }
	} else if (!productCode.equals(other.productCode)) {
	    return false;
	}
	if (systemID == null) {
	    if (other.systemID != null) {
		return false;
	    }
	} else if (!systemID.equals(other.systemID)) {
	    return false;
	}
	return true;
    }

    /**
     * @param o
     * @return
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(ProductDTO destiDTO) {
	if (this.getDisplayOrder() > destiDTO.getDisplayOrder()) {
	    return 1;
	} else if (this.getDisplayOrder() < destiDTO.getDisplayOrder()) {
	    return -1;
	} else {
	    return 0;
	}
    }

}
