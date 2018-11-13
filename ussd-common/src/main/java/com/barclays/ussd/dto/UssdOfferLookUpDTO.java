package com.barclays.ussd.dto;

public class UssdOfferLookUpDTO {

    private String businessId;
    private String bankName;
    private String branchName;
    private String cityName;
    private String bankCode;
    private String branchCode;
    private String clearingCode;
    private String restaurentName;
    private String offerTypeID;
    private String partnerName;

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
     * @return the bankName
     */
    public String getBankName() {
	return bankName;
    }

    /**
     * @param bankName
     *            the bankName to set
     */
    public void setBankName(String bankName) {
	this.bankName = bankName;
    }

    /**
     * @return the branchName
     */
    public String getBranchName() {
	return branchName;
    }

    /**
     * @param branchName
     *            the branchName to set
     */
    public void setBranchName(String branchName) {
	this.branchName = branchName;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
	return cityName;
    }

    /**
     * @param cityName
     *            the cityName to set
     */
    public void setCityName(String cityName) {
	this.cityName = cityName;
    }

    /**
     * @return the bankCode
     */
    public String getBankCode() {
	return bankCode;
    }

    /**
     * @param bankCode
     *            the bankCode to set
     */
    public void setBankCode(String bankCode) {
	this.bankCode = bankCode;
    }

    /**
     * @return the branchCode
     */
    public String getBranchCode() {
	return branchCode;
    }

    /**
     * @param branchCode
     *            the branchCode to set
     */
    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    /**
     * @return the clearingCode
     */
    public String getClearingCode() {
	return clearingCode;
    }

    /**
     * @param clearingCode
     *            the clearingCode to set
     */
    public void setClearingCode(String clearingCode) {
	this.clearingCode = clearingCode;
    }

    public String getRestaurentName() {
	return restaurentName;
    }

    public void setRestaurentName(String restaurentName) {
	this.restaurentName = restaurentName;
    }

    public String getOfferTypeID() {
	return offerTypeID;
    }

    public void setOfferTypeID(String offerTypeID) {
	this.offerTypeID = offerTypeID;
    }

    public String getPartnerName() {
	return partnerName;
    }

    public void setPartnerName(String partnerName) {
	this.partnerName = partnerName;
    }

}
