/* Copyright 2008 Barclays PLC */

/**************************** Revision History ****************************************************
 * Version        Author          Date                        Description
 * 0.1            Elicer Zheng        2009/02/08                  Initial version
 *
 *
 **************************************************************************************************/

package com.barclays.bmg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.barclays.bmg.type.Currency;

/**
 * @author Scott Li
 * 
 */
public class InsuranceAccountDTO extends CustomerAccountDTO {
    /** TODO Comment for <code>serialVersionUID</code>. */
    private static final long serialVersionUID = 894889208350501808L;

    private String coverageType;
    private String paymentFrequency;
    private String underlyingFundType = "Equity Growth";
    private String nomineeDetails = "Mr.Smith";
    private BigDecimal premiumPaid;
    private BigDecimal coverageAmount;
    private BigDecimal heldInUnitsNumber;
    private BigDecimal currentNAVOfEachUnit;
    private BigDecimal fundsInvestedOfNAV;
    private BigDecimal surrenderValue;
    private BigDecimal maturityValue;
    private BigDecimal sumAssured;
    private BigDecimal annualizedPremium;
    private BigDecimal currentMarketValue;
    private Currency currencyCode;
    private String policyNumber;
    private Date asOf;

    // left section - insurance
    private String insuranceType;
    private String customerName;
    private String insuranceCompany;
    private String relationType;
    private String beneficiary;
    private Tenure term;
    private Date policyInsuranceDate;
    private Date lastPremiumPaidDate;
    private Date nextPremiumDueDate;

    // right section - insurance
    private String policyStatus;
    private String passportNumber;
    private String dependantName;
    private String paymentMode;
    private Frequent frequency;
    private Date policyExpireDate;
    private BigDecimal lastPremiumPaidAmount;
    private BigDecimal nextPremiumDueAmount;

    // left section - assurance
    private String productName;
    private String assuranceFrequency;

    // right section - assurance
    private BigDecimal premiumAmount;
    private BigDecimal premiumDueDate;

    // frequent
    // private Frequent frequent;
    private boolean requestFlg = true;

    /**
     * Default Constructor.
     */
    public InsuranceAccountDTO() {
	super();
    }

    /**
     * @return the currencyCode
     */
    public Currency getCurrencyCode() {
	return currencyCode;
    }

    /**
     * @param currencyCode
     *            the currencyCode to set
     */
    public void setCurrencyCode(Currency currencyCode) {
	this.currencyCode = currencyCode;
    }

    /**
     * @return the annualizedPremium
     */
    public BigDecimal getAnnualizedPremium() {
	return annualizedPremium;
    }

    /**
     * @param annualizedPremium
     *            the annualizedPremium to set
     */
    public void setAnnualizedPremium(BigDecimal annualizedPremium) {
	this.annualizedPremium = annualizedPremium;
    }

    /**
     * @return the policyExpireDate
     */
    public Date getPolicyExpireDate() {
	if (policyExpireDate != null) {
	    return new Date(policyExpireDate.getTime());
	}
	return null;
    }

    /**
     * @param policyExpireDate
     *            the policyExpireDate to set
     */
    public void setPolicyExpireDate(Date policyExpireDate) {
	if (policyExpireDate != null) {
	    this.policyExpireDate = new Date(policyExpireDate.getTime());
	} else {
	    this.policyExpireDate = null;
	}
    }

    /**
     * @return the sumAssured
     */
    public BigDecimal getSumAssured() {
	return sumAssured;
    }

    /**
     * @param sumAssured
     *            the sumAssured to set
     */
    public void setSumAssured(BigDecimal sumAssured) {
	this.sumAssured = sumAssured;
    }

    /**
     * @return the insuranceType
     */
    public String getInsuranceType() {
	return insuranceType;
    }

    /**
     * @param insuranceType
     *            the insuranceType to set
     */
    public void setInsuranceType(String insuranceType) {
	this.insuranceType = insuranceType;
    }

    /**
     * @return the coverageType
     */
    public String getCoverageType() {
	return coverageType;
    }

    /**
     * @param coverageType
     *            the coverageType to set
     */
    public void setCoverageType(String coverageType) {
	this.coverageType = coverageType;
    }

    /**
     * @return the paymentFrequency
     */
    public String getPaymentFrequency() {
	return paymentFrequency;
    }

    /**
     * @param paymentFrequency
     *            the paymentFrequency to set
     */
    public void setPaymentFrequency(String paymentFrequency) {
	this.paymentFrequency = paymentFrequency;
    }

    /**
     * @return the insuranceCompany
     */
    public String getInsuranceCompany() {
	return insuranceCompany;
    }

    /**
     * @param insuranceCompany
     *            the insuranceCompany to set
     */
    public void setInsuranceCompany(String insuranceCompany) {
	this.insuranceCompany = insuranceCompany;
    }

    /**
     * @return the underlyingFundType
     */
    public String getUnderlyingFundType() {
	return underlyingFundType;
    }

    /**
     * @param underlyingFundType
     *            the underlyingFundType to set
     */
    public void setUnderlyingFundType(String underlyingFundType) {
	this.underlyingFundType = underlyingFundType;
    }

    /**
     * @return the nomineeDetails
     */
    public String getNomineeDetails() {
	return nomineeDetails;
    }

    /**
     * @param nomineeDetails
     *            the nomineeDetails to set
     */
    public void setNomineeDetails(String nomineeDetails) {
	this.nomineeDetails = nomineeDetails;
    }

    /**
     * @return the nextPremiumDueDate
     */
    public Date getNextPremiumDueDate() {
	if (nextPremiumDueDate != null) {
	    return new Date(nextPremiumDueDate.getTime());
	}
	return null;
    }

    /**
     * @param nextPremiumDueDate
     *            the nextPremiumDueDate to set
     */
    public void setNextPremiumDueDate(Date nextPremiumDueDate) {
	if (nextPremiumDueDate != null) {
	    this.nextPremiumDueDate = new Date(nextPremiumDueDate.getTime());
	} else {
	    this.nextPremiumDueDate = null;
	}

    }

    /**
     * @return the nextPremiumDueAmount
     */
    public BigDecimal getNextPremiumDueAmount() {
	return nextPremiumDueAmount;
    }

    /**
     * @param nextPremiumDueAmount
     *            the nextPremiumDueAmount to set
     */
    public void setNextPremiumDueAmount(BigDecimal nextPremiumDueAmount) {
	this.nextPremiumDueAmount = nextPremiumDueAmount;
    }

    /**
     * @return the premiumPaid
     */
    public BigDecimal getPremiumPaid() {
	return premiumPaid;
    }

    /**
     * @param premiumPaid
     *            the premiumPaid to set
     */
    public void setPremiumPaid(BigDecimal premiumPaid) {
	this.premiumPaid = premiumPaid;
    }

    /**
     * @return the coverageAmount
     */
    public BigDecimal getCoverageAmount() {
	return coverageAmount;
    }

    /**
     * @param coverageAmount
     *            the coverageAmount to set
     */
    public void setCoverageAmount(BigDecimal coverageAmount) {
	this.coverageAmount = coverageAmount;
    }

    /**
     * @return the heldInUnitsNumber
     */
    public BigDecimal getHeldInUnitsNumber() {
	return heldInUnitsNumber;
    }

    /**
     * @param heldInUnitsNumber
     *            the heldInUnitsNumber to set
     */
    public void setHeldInUnitsNumber(BigDecimal heldInUnitsNumber) {
	this.heldInUnitsNumber = heldInUnitsNumber;
    }

    /**
     * @return the currentNAVOfEachUnit
     */
    public BigDecimal getCurrentNAVOfEachUnit() {
	return currentNAVOfEachUnit;
    }

    /**
     * @param currentNAVOfEachUnit
     *            the currentNAVOfEachUnit to set
     */
    public void setCurrentNAVOfEachUnit(BigDecimal currentNAVOfEachUnit) {
	this.currentNAVOfEachUnit = currentNAVOfEachUnit;
    }

    /**
     * @return the fundsInvestedOfNAV
     */
    public BigDecimal getFundsInvestedOfNAV() {
	return fundsInvestedOfNAV;
    }

    /**
     * @param fundsInvestedOfNAV
     *            the fundsInvestedOfNAV to set
     */
    public void setFundsInvestedOfNAV(BigDecimal fundsInvestedOfNAV) {
	this.fundsInvestedOfNAV = fundsInvestedOfNAV;
    }

    /**
     * @return the surrenderValue
     */
    public BigDecimal getSurrenderValue() {
	return surrenderValue;
    }

    /**
     * @param surrenderValue
     *            the surrenderValue to set
     */
    public void setSurrenderValue(BigDecimal surrenderValue) {
	this.surrenderValue = surrenderValue;
    }

    /**
     * @return the maturityValue
     */
    public BigDecimal getMaturityValue() {
	return maturityValue;
    }

    /**
     * @param maturityValue
     *            the maturityValue to set
     */
    public void setMaturityValue(BigDecimal maturityValue) {
	this.maturityValue = maturityValue;
    }

    /**
     * @return the beneficiary
     */
    public String getBeneficiary() {
	return beneficiary;
    }

    /**
     * @param beneficiary
     *            the beneficiary to set
     */
    public void setBeneficiary(String beneficiary) {
	this.beneficiary = beneficiary;
    }

    /**
     * @return the currentMarketValue
     */
    public BigDecimal getCurrentMarketValue() {
	return currentMarketValue;
    }

    /**
     * @param currentMarketValue
     *            the currentMarketValue to set
     */
    public void setCurrentMarketValue(BigDecimal currentMarketValue) {
	this.currentMarketValue = currentMarketValue;
    }

    /**
     * @return the term
     */
    public Tenure getTerm() {
	return term;
    }

    /**
     * @param term
     *            the term to set
     */
    public void setTerm(Tenure term) {
	this.term = term;
    }

    public String getPolicyNumber() {
	return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
    }

    public BigDecimal getPremiumAmount() {
	return premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
	this.premiumAmount = premiumAmount;
    }

    public Date getAsOf() {
	if (asOf != null) {
	    return new Date(asOf.getTime());
	}
	return null;
    }

    public void setAsOf(Date asOf) {
	if (asOf != null) {
	    this.asOf = new Date(asOf.getTime());
	} else {
	    this.asOf = null;
	}
    }

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public String getRelationType() {
	return relationType;
    }

    public void setRelationType(String relationType) {
	this.relationType = relationType;
    }

    public Date getPolicyInsuranceDate() {
	if (policyInsuranceDate != null) {
	    return new Date(policyInsuranceDate.getTime());
	}
	return null;
    }

    public void setPolicyInsuranceDate(Date policyInsuranceDate) {
	if (policyInsuranceDate != null) {
	    this.policyInsuranceDate = new Date(policyInsuranceDate.getTime());
	} else {
	    this.policyInsuranceDate = null;
	}
    }

    public String getPolicyStatus() {
	return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
	this.policyStatus = policyStatus;
    }

    public String getPassportNumber() {
	return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
	this.passportNumber = passportNumber;
    }

    public String getDependantName() {
	return dependantName;
    }

    public void setDependantName(String dependantName) {
	this.dependantName = dependantName;
    }

    public String getPaymentMode() {
	return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
	this.paymentMode = paymentMode;
    }

    public Frequent getFrequency() {
	return frequency;
    }

    public void setFrequency(Frequent frequency) {
	this.frequency = frequency;
    }

    public Date getLastPremiumPaidDate() {
	if (lastPremiumPaidDate != null) {
	    return new Date(lastPremiumPaidDate.getTime());
	}
	return null;
    }

    public void setLastPremiumPaidDate(Date lastPremiumPaidDate) {
	if (lastPremiumPaidDate != null) {
	    this.lastPremiumPaidDate = new Date(lastPremiumPaidDate.getTime());
	} else {
	    this.lastPremiumPaidDate = null;
	}
    }

    public BigDecimal getLastPremiumPaidAmount() {
	return lastPremiumPaidAmount;
    }

    public void setLastPremiumPaidAmount(BigDecimal lastPremiumPaidAmount) {
	this.lastPremiumPaidAmount = lastPremiumPaidAmount;
    }

    @Override
    public String getProductName() {
	return productName;
    }

    @Override
    public void setProductName(String productName) {
	this.productName = productName;
    }

    public BigDecimal getPremiumDueDate() {
	return premiumDueDate;
    }

    public void setPremiumDueDate(BigDecimal premiumDueDate) {
	this.premiumDueDate = premiumDueDate;
    }

    public String getAssuranceFrequency() {
	return assuranceFrequency;
    }

    public void setAssuranceFrequency(String assuranceFrequency) {
	this.assuranceFrequency = assuranceFrequency;
    }

    public boolean isRequestFlg() {
	return requestFlg;
    }

    public void setRequestFlg(boolean requestFlg) {
	this.requestFlg = requestFlg;
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return super.equals(obj);
    }

}
