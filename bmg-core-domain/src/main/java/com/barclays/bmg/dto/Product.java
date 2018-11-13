package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private static final long serialVersionUID = -3353481996057597229L;

    private String productCode;

    private String productName;

    private String currency;

    private BigDecimal minBalance;

    private String productCategory;

    private Tenure tenureTerm;

    private String interestPayoutFrequency;

    private String interestCompoundingFrequency;

    /**
     * @return the productCategory
     */
    public final String getProductCategory() {
	return productCategory;
    }

    /**
     * @param productCategory
     *            the productCategory to set
     */
    public final void setProductCategory(String productCategory) {
	this.productCategory = productCategory;
    }

    public BigDecimal getMinBalance() {
	return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
	this.minBalance = minBalance;
    }

    public String getProductCode() {
	return productCode;
    }

    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    public String getProductName() {
	return productName;
    }

    public void setProductName(String productName) {
	this.productName = productName;
    }

    public String getCurrency() {
	return currency;
    }

    public void setCurrency(String currency) {
	this.currency = currency;
    }

    public Tenure getTenureTerm() {
	return tenureTerm;
    }

    public void setTenureTerm(Tenure tenureTerm) {
	this.tenureTerm = tenureTerm;
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

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Product other = (Product) obj;
	if (currency == null) {
	    if (other.currency != null)
		return false;
	} else if (!currency.equals(other.currency))
	    return false;
	if (productCode == null) {
	    if (other.productCode != null)
		return false;
	} else if (!productCode.equals(other.productCode))
	    return false;
	if (productName == null) {
	    if (other.productName != null)
		return false;
	} else if (!productName.equals(other.productName))
	    return false;
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((currency == null) ? 0 : currency.hashCode());
	result += prime * result + ((productCode == null) ? 0 : productCode.hashCode());
	result += prime * result + ((productName == null) ? 0 : productName.hashCode());
	return result;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(64);
	sb.append("Product[productCode=").append(productCode).append(",productName=").append(productName).append(",currency=").append(currency)
		.append("]");

	return sb.toString();
	// return "Product[productCode=" + productCode + ",productName="
	// + productName + ",currency=" + currency + "]";
    }
}
