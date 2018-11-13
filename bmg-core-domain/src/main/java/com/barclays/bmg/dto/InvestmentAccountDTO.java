package com.barclays.bmg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.barclays.bmg.type.Currency;

public class InvestmentAccountDTO extends CustomerAccountDTO {

    private static final long serialVersionUID = 1L;

    // first section
    private String investmentName;
    private String assetTypeCode;
    private String assetTypeName;

    // second and left section
    private BigDecimal levarageAmount;
    private BigDecimal noOfUnit;
    private BigDecimal totalAmountInvested;
    private NAV previousNAV;
    private BigDecimal currentMarketValue;
    private BigDecimal dailygrowthDecline;
    private Date dateOfInvestment;

    // second and right section
    private BigDecimal interestOutstanding;
    private BigDecimal weightedAverage;
    private NAV currentNAV;
    private BigDecimal sinceIncepGrowth;
    private BigDecimal monthToDateGrowth;
    private BigDecimal yearToDateGrowthDecline;

    private String nominee;
    private String fundName;
    private String fundType;
    private BigDecimal unrealizedGainOrLose;
    private BigDecimal averageCostPerUnit;
    private AccountNumber maskAccountNumber;
    private Currency currencyCode;
    private Date asOf;

    private BigDecimal dividendInterestReceived;
    private BigDecimal lienUnits;
    private BigDecimal redeemedUnits;

    private boolean requestFlg = true;

    /**
     * @return the previousNAV
     */
    public NAV getPreviousNAV() {
	return previousNAV;
    }

    /**
     * @param previousNAV
     *            the previousNAV to set
     */
    public void setPreviousNAV(NAV previousNAV) {
	this.previousNAV = previousNAV;
    }

    /**
     * @return the weightedAverage
     */
    public BigDecimal getWeightedAverage() {
	return weightedAverage;
    }

    /**
     * @param weightedAverage
     *            the weightedAverage to set
     */
    public void setWeightedAverage(BigDecimal weightedAverage) {
	this.weightedAverage = weightedAverage;
    }

    /**
     * @return the fundName
     */
    public String getFundName() {
	return fundName;
    }

    /**
     * @param fundName
     *            the fundName to set
     */
    public void setFundName(String fundName) {
	this.fundName = fundName;
    }

    /**
     * @return the fundType
     */
    public String getFundType() {
	return fundType;
    }

    /**
     * @param fundType
     *            the fundType to set
     */
    public void setFundType(String fundType) {
	this.fundType = fundType;
    }

    /**
     * @return the investmentName
     */
    public String getInvestmentName() {
	return investmentName;
    }

    /**
     * @param investmentName
     *            the investmentName to set
     */
    public void setInvestmentName(String investmentName) {
	this.investmentName = investmentName;
    }

    /**
     * @return the currentNAV
     */
    public NAV getCurrentNAV() {
	return currentNAV;
    }

    /**
     * @param currentNAV
     *            the currentNAV to set
     */
    public void setCurrentNAV(NAV currentNAV) {
	this.currentNAV = currentNAV;
    }

    /**
     * @return the unrealizedGainOrLose
     */
    public BigDecimal getUnrealizedGainOrLose() {
	return unrealizedGainOrLose;
    }

    /**
     * @param unrealizedGainOrLose
     *            the unrealizedGainOrLose to set
     */
    public void setUnrealizedGainOrLose(BigDecimal unrealizedGainOrLose) {
	this.unrealizedGainOrLose = unrealizedGainOrLose;
    }

    /**
     * @return the averageCostPerUnit
     */
    public BigDecimal getAverageCostPerUnit() {
	return averageCostPerUnit;
    }

    /**
     * @param averageCostPerUnit
     *            the averageCostPerUnit to set
     */
    public void setAverageCostPerUnit(BigDecimal averageCostPerUnit) {
	this.averageCostPerUnit = averageCostPerUnit;
    }

    /**
     * @return the nominee
     */
    public String getNominee() {
	return nominee;
    }

    /**
     * @param nominee
     *            the nominee to set
     */
    public void setNominee(String nominee) {
	this.nominee = nominee;
    }

    /**
     * @return the noOfUnit
     */
    public BigDecimal getNoOfUnit() {
	return noOfUnit;
    }

    /**
     * @param noOfUnit
     *            the noOfUnit to set
     */
    public void setNoOfUnit(BigDecimal noOfUnit) {
	this.noOfUnit = noOfUnit;
    }

    /**
     * @return the totalAmountInvested
     */
    public BigDecimal getTotalAmountInvested() {
	return totalAmountInvested;
    }

    /**
     * @param totalAmountInvested
     *            the totalAmountInvested to set
     */
    public void setTotalAmountInvested(BigDecimal totalAmountInvested) {
	this.totalAmountInvested = totalAmountInvested;
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
     * @return the dailygrowthDecline
     */
    public BigDecimal getDailygrowthDecline() {
	return dailygrowthDecline;
    }

    /**
     * @param dailygrowthDecline
     *            the dailygrowthDecline to set
     */
    public void setDailygrowthDecline(BigDecimal dailygrowthDecline) {
	this.dailygrowthDecline = dailygrowthDecline;
    }

    /**
     * @return the yearToDateGrowthDecline
     */
    public BigDecimal getYearToDateGrowthDecline() {
	return yearToDateGrowthDecline;
    }

    /**
     * @param yearToDateGrowthDecline
     *            the yearToDateGrowthDecline to set
     */
    public void setYearToDateGrowthDecline(BigDecimal yearToDateGrowthDecline) {
	this.yearToDateGrowthDecline = yearToDateGrowthDecline;
    }

    /**
     * @return the sinceIncepGrowth
     */
    public BigDecimal getSinceIncepGrowth() {
	return sinceIncepGrowth;
    }

    /**
     * @param sinceIncepGrowth
     *            the sinceIncepGrowth to set
     */
    public void setSinceIncepGrowth(BigDecimal sinceIncepGrowth) {
	this.sinceIncepGrowth = sinceIncepGrowth;
    }

    /**
     * @return the monthToDateGrowth
     */
    public BigDecimal getMonthToDateGrowth() {
	return monthToDateGrowth;
    }

    /**
     * @param monthToDateGrowth
     *            the monthToDateGrowth to set
     */
    public void setMonthToDateGrowth(BigDecimal monthToDateGrowth) {
	this.monthToDateGrowth = monthToDateGrowth;
    }

    /**
     * @return the maskAccountNumber
     */
    public AccountNumber getMaskAccountNumber() {
	return maskAccountNumber;
    }

    /**
     * @param maskAccountNumber
     *            the maskAccountNumber to set
     */
    public void setMaskAccountNumber(AccountNumber maskAccountNumber) {
	this.maskAccountNumber = maskAccountNumber;
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

    public BigDecimal getLevarageAmount() {
	return levarageAmount;
    }

    public void setLevarageAmount(BigDecimal levarageAmount) {
	this.levarageAmount = levarageAmount;
    }

    public Date getDateOfInvestment() {
	if (dateOfInvestment != null) {
	    return new Date(dateOfInvestment.getTime());
	}
	return null;
    }

    public void setDateOfInvestment(Date dateOfInvestment) {
	if (dateOfInvestment != null) {
	    this.dateOfInvestment = new Date(dateOfInvestment.getTime());
	} else {
	    this.dateOfInvestment = null;
	}
    }

    public BigDecimal getInterestOutstanding() {
	return interestOutstanding;
    }

    public void setInterestOutstanding(BigDecimal interestOutstanding) {
	this.interestOutstanding = interestOutstanding;
    }

    public boolean isRequestFlg() {
	return requestFlg;
    }

    public void setRequestFlg(boolean requestFlg) {
	this.requestFlg = requestFlg;
    }

    // public int hashCode() {
    // return super.hashCode();
    // }
    //
    // public boolean equals(Object obj) {
    //
    // return super.equals(obj);
    // }
    public String getAssetTypeCode() {
	return assetTypeCode;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((assetTypeCode == null) ? 0 : assetTypeCode.hashCode());
	result = prime * result + ((investmentName == null) ? 0 : investmentName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!super.equals(obj)) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	InvestmentAccountDTO other = (InvestmentAccountDTO) obj;
	if (getAccountNumber() == null) {
	    if (other.getAccountNumber() != null) {
		return false;
	    }
	} else if (!getAccountNumber().equals(other.getAccountNumber())) {
	    return false;
	}
	if (getProductCode() == null) {
	    if (other.getProductCode() != null) {
		return false;
	    }
	} else if (!getProductCode().equals(other.getProductCode())) {
	    return false;
	}
	if (assetTypeCode == null) {
	    if (other.assetTypeCode != null) {
		return false;
	    }
	} else if (!assetTypeCode.equals(other.assetTypeCode)) {
	    return false;
	}
	if (investmentName == null) {
	    if (other.investmentName != null) {
		return false;
	    }
	} else if (!investmentName.equals(other.investmentName)) {
	    return false;
	}
	return true;
    }

    public void setAssetTypeCode(String assetTypeCode) {
	this.assetTypeCode = assetTypeCode;
    }

    public String getAssetTypeName() {
	return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
	this.assetTypeName = assetTypeName;
    }

    public BigDecimal getDividendInterestReceived() {
	return dividendInterestReceived;
    }

    public void setDividendInterestReceived(BigDecimal dividendInterestReceived) {
	this.dividendInterestReceived = dividendInterestReceived;
    }

    public BigDecimal getLienUnits() {
	return lienUnits;
    }

    public void setLienUnits(BigDecimal lienUnits) {
	this.lienUnits = lienUnits;
    }

    public BigDecimal getRedeemedUnits() {
	return redeemedUnits;
    }

    public void setRedeemedUnits(BigDecimal redeemedUnits) {
	this.redeemedUnits = redeemedUnits;
    }

}
