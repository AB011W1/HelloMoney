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

/**
 * Product Eligibility DTO.
 * 
 * @version $Revision: 1.1 $
 * 
 * 
 * @author Max Zhang
 */

public class ProductEligibilityDTO implements Serializable {

    private static final long serialVersionUID = -5838804465360548368L;

    private long serialNumber;

    private String activityId;

    private String businessId;

    private String roleCategoryCode;

    private String crdrIndicator;

    private String productCode;

    private String productCategory;

    private String incOrExc;

    private String accountStatus;

    private String accountBlockCode1;

    private String accountBlockCode2;

    private String cardType;

    private String cardStatus;

    private String cardBlockCode;

    private String systemId;

    /**
     * 
     * Constructor.
     * 
     */
    public ProductEligibilityDTO() {

    }

    /**
     * @return the serialNumber
     */
    public final long getSerialNumber() {
	return serialNumber;
    }

    /**
     * @param serialNumber
     *            the serialNumber to set
     */
    public final void setSerialNumber(long serialNumber) {
	this.serialNumber = serialNumber;
    }

    /**
     * @return the activityId
     */
    public final String getActivityId() {
	return activityId;
    }

    /**
     * @param activityId
     *            the activityId to set
     */
    public final void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    /**
     * @return the businessId
     */
    public final String getBusinessId() {
	return businessId;
    }

    /**
     * @param businessId
     *            the businessId to set
     */
    public final void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    /**
     * @return the roleCategoryCode
     */
    public final String getRoleCategoryCode() {
	return roleCategoryCode;
    }

    /**
     * @param roleCategoryCode
     *            the roleCategoryCode to set
     */
    public final void setRoleCategoryCode(String roleCategoryCode) {
	this.roleCategoryCode = roleCategoryCode;
    }

    /**
     * @return the crdrIndicator
     */
    public final String getCrdrIndicator() {
	return crdrIndicator;
    }

    /**
     * @param crdrIndicator
     *            the crdrIndicator to set
     */
    public final void setCrdrIndicator(String crdrIndicator) {
	this.crdrIndicator = crdrIndicator;
    }

    /**
     * @return the productCode
     */
    public final String getProductCode() {
	return productCode;
    }

    /**
     * @param productCode
     *            the productCode to set
     */
    public final void setProductCode(String productCode) {
	this.productCode = productCode;
    }

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

    /**
     * @return the incOrExc
     */
    public final String getIncOrExc() {
	return incOrExc;
    }

    /**
     * @param incOrExc
     *            the incOrExc to set
     */
    public final void setIncOrExc(String incOrExc) {
	this.incOrExc = incOrExc;
    }

    /**
     * @return the accountStatus
     */
    public final String getAccountStatus() {
	return accountStatus;
    }

    /**
     * @param accountStatus
     *            the accountStatus to set
     */
    public final void setAccountStatus(String accountStatus) {
	this.accountStatus = accountStatus;
    }

    /**
     * @return the accountBlockCode1
     */
    public final String getAccountBlockCode1() {
	return accountBlockCode1;
    }

    /**
     * @param accountBlockCode1
     *            the accountBlockCode1 to set
     */
    public final void setAccountBlockCode1(String accountBlockCode1) {
	this.accountBlockCode1 = accountBlockCode1;
    }

    /**
     * @return the accountBlockCode2
     */
    public final String getAccountBlockCode2() {
	return accountBlockCode2;
    }

    /**
     * @param accountBlockCode2
     *            the accountBlockCode2 to set
     */
    public final void setAccountBlockCode2(String accountBlockCode2) {
	this.accountBlockCode2 = accountBlockCode2;
    }

    /**
     * @return the cardType
     */
    public final String getCardType() {
	return cardType;
    }

    /**
     * @param cardType
     *            the cardType to set
     */
    public final void setCardType(String cardType) {
	this.cardType = cardType;
    }

    /**
     * @return the cardStatus
     */
    public final String getCardStatus() {
	return cardStatus;
    }

    /**
     * @param cardStatus
     *            the cardStatus to set
     */
    public final void setCardStatus(String cardStatus) {
	this.cardStatus = cardStatus;
    }

    /**
     * @return the cardBlockCode
     */
    public final String getCardBlockCode() {
	return cardBlockCode;
    }

    /**
     * @param cardBlockCode
     *            the cardBlockCode to set
     */
    public final void setCardBlockCode(String cardBlockCode) {
	this.cardBlockCode = cardBlockCode;
    }

    /**
     * @return the systemId
     */
    public final String getSystemId() {
	return systemId;
    }

    /**
     * @param systemId
     *            the systemId to set
     */
    public final void setSystemId(String systemId) {
	this.systemId = systemId;
    }

}
