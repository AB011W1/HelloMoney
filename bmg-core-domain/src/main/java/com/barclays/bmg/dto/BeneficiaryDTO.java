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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.type.Address;

/* *************************** Revision History *********************************
 * Version        Author          Date                            Description
 * 0.1            Jason Wang        Aug 5, 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO Class/Interface description.
 *
 * @author Jason Wang
 */
public class BeneficiaryDTO extends BaseDomainDTO {
    private static final long serialVersionUID = -7085533416881163257L;

    public static final String PRE_PAID_MOBILE = "001";

    // c_reg_payee_mst content start
    private String businessId;

    private String payeeId;

    private String customerId;

    private String payeeTypeCode;

    private String payeeNickname;

    private String beneficiaryName;
    private String beneficiaryNickName;
    private String beneficiaryOldNickName;
    private String beneficiaryId;
    private String billerId;

    private String externalBillerId;
    private String billRefNo;
    private String billRefNo1;

    private String billRefNo2;

    private BigDecimal billersMinPaymentAmount;

    private String paymentChannelId;

    private CustomerAccountDTO destinationAccount;

    private String destinationAccountNumber;

    //Kadikope
    private String actionCode;

    private String storeNumber;

    private String destinationBankCode;

    private String destinationBranchCode;

    private boolean destinationAccountIbanFlg;

    private String destinationIsoCountryCode;

    private String destinationResdentStatus;

    private String destinationAddressLine1;

    private String destinationAddressLine2;

    private String destinationAddressLine3;

    private String destinationBankIsoCountryCode;

    private String destinationBankSwiftCode;

    private String destinationBankClearingCode;

    private String destinationBankClearingNetCode;

    private String destinationBankChipsUid;

    private String destinationBankName;

    private String destinationBranchName;

    private String destinationBankAddressLine1;

    private String destinationBankAddressLine2;

    private String intermediaryBankAccountNumber;

    private String intermediaryBankSwiftCode;

    private String intermediaryBankClearingCode;

    private String intermediaryBankClearingNetCode;

    private String intermediaryBankChipsUid;

    private String intermediaryBankCode;

    private String intermediaryBankName;

    private String intermediaryBankAddressLine1;

    private String intermediaryBankAddressLine2;

    private String intermediaryBankIsoCountryCode;

    private String status;

    private String deleteFlag;

    private String modifiedBy;

    private Date modifiedDate;

    private String authorisedBy;

    private Date authorisedDate;

    private Date createdDate;

    private String cardNumber;

    // c_reg_payee_mst content end

    // c_biller_mst start
    private String billerName;

    private String billerCategoryId;

    private String billerCategoryName;

    private boolean presentmentFlag;

    private String referenceNoText1;

    private String referenceNoText2;

    private BigDecimal TransactionFee;

    private String billAggregatorId;

    private String currency;

    private String topupService;

    private String deliveryType;
    private String remitterName;
    private Address remitterAddress;
    private boolean requestFlg = true;
    private String transactionReferenceNumber;
    private Date transactionDateTime;
    private String contactNumber;
    private boolean ibanFlag;
    private boolean mobileProvider = false;

    // Changes Start for TZBRB Bill Payment By saurabh
    private BigDecimal billersMaxPaymentAmount;

    private String refNoKey1;

    private String billHolderRequired;

    private String serviceType;

    // Changes ends for TZBRB Bill Payment By saurabh

    //Kits changes starts
    private String narration;

    private String destinationBankSortCode;

    private String city;

    private String nib;

  //Probase
    private InvoiceDetails invoiceDetails;
    
	// Cards Migration V+ to FV
	private Date creditCardExpiryDate;

	
    public Date getCreditCardExpiryDate() {
		return creditCardExpiryDate;
	}

	public void setCreditCardExpiryDate(Date creditCardExpiryDate) {
		this.creditCardExpiryDate = creditCardExpiryDate;
	}

	public String getNib() {
		return nib;
	}

	public void setNib(String nib) {
		this.nib = nib;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getDestinationBankSortCode() {
		return destinationBankSortCode;
	}

	public void setDestinationBankSortCode(String destinationBankSortCode) {
		this.destinationBankSortCode = destinationBankSortCode;
	}

    //Kits changes ends


	/**
     * @return the billRefNo
     */
    public String getBillRefNo() {
	return billRefNo;
    }

    /**
     * @param billRefNo
     *            the billRefNo to set
     */
    public void setBillRefNo(String billRefNo) {
	this.billRefNo = billRefNo;
    }

    public boolean isMobileProvider() {
	return mobileProvider;
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

    public void setMobileProvider(boolean mobileProvider) {
	this.mobileProvider = mobileProvider;
    }

    /**
     * @return the intermediaryBankCode
     */
    public String getIntermediaryBankCode() {
	return intermediaryBankCode;
    }

    /**
     * @param intermediaryBankCode
     *            the intermediaryBankCode to set
     */
    public void setIntermediaryBankCode(String intermediaryBankCode) {
	this.intermediaryBankCode = intermediaryBankCode;
    }

    /**
     * @return the destinationBranchName
     */
    public String getDestinationBranchName() {
	return destinationBranchName;
    }

    /**
     * @param destinationBranchName
     *            the destinationBranchName to set
     */
    public void setDestinationBranchName(String destinationBranchName) {
	this.destinationBranchName = destinationBranchName;
    }

    private boolean supportCreditCardFlag;

    private String onlineBillerFlag;

    private String payeeReferenceFields;

    private BigDecimal[] mobileDenominaitonList;

    private String mobileDenominaiton;

    private boolean oneTime;

    private boolean addPayee;

    private String bankRountingName;

    /**
     * @return the destinationAccount
     */
    public CustomerAccountDTO getDestinationAccount() {
	return destinationAccount;
    }

    /**
     * @param destinationAccount
     *            the destinationAccount to set
     */
    public void setDestinationAccount(CustomerAccountDTO destinationAccount) {
	this.destinationAccount = destinationAccount;
    }

    /**
     * @return the addPayee
     */
    public boolean isAddPayee() {
	return addPayee;
    }

    /**
     * @param addPayee
     *            the addPayee to set
     */
    public void setAddPayee(boolean addPayee) {
	this.addPayee = addPayee;
    }

    /**
     * @return the oneTime
     */
    public boolean isOneTime() {
	return oneTime;
    }

    /**
     * @param oneTime
     *            the oneTime to set
     */
    public void setOneTime(boolean oneTime) {
	this.oneTime = oneTime;
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
     * @return the payeeId
     */
    public String getPayeeId() {
	return payeeId;
    }

    /**
     * @param payeeId
     *            the payeeId to set
     */
    public void setPayeeId(String payeeId) {
	this.payeeId = payeeId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
	return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    /**
     * @return the payeeNickname
     */
    public String getPayeeNickname() {
	return payeeNickname;
    }

    /**
     * @return the bankRountingName
     */
    public String getBankRountingName() {
	if (null != bankRountingName) {
	    return bankRountingName;
	} else {
	    String bankName = StringUtils.isNotEmpty(this.getDestinationBankName()) ? this.getDestinationBankName() : "";
	    String branchName = StringUtils.isNotEmpty(this.getDestinationBranchName()) ? " " + this.getDestinationBranchName() : "";
	    return bankName + branchName;
	}
    }

    /**
     * @param bankRountingName
     *            the bankRountingName to set
     */
    public void setBankRountingName(String bankRountingName) {
	this.bankRountingName = bankRountingName;
    }

    /**
     * @param payeeNickname
     *            the payeeNickname to set
     */
    public void setPayeeNickname(String payeeNickname) {
	this.payeeNickname = payeeNickname;
    }

    /**
     * @return the payeeName
     */
    public String getBeneficiaryName() {
	String str = "";
	if (!StringUtils.isEmpty(beneficiaryName)) {
	    str = beneficiaryName.replaceAll("\\s+", " ").trim();
	} else {
	    str = beneficiaryName;
	}
	return str;
    }

    /**
     * @param payeeName
     *            the payeeName to set
     */
    public void setBeneficiaryName(String payeeName) {
	this.beneficiaryName = payeeName;
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
     * @return the paymentChannelId
     */
    public String getPaymentChannelId() {
	return paymentChannelId;
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
     * @param paymentChannelId
     *            the paymentChannelId to set
     */
    public void setPaymentChannelId(String paymentChannelId) {
	this.paymentChannelId = paymentChannelId;
    }

    /**
     * @return the destinationAccountNumber
     */
    public String getDestinationAccountNumber() {
	return destinationAccountNumber;
    }

    /**
     * @param destinationAccountNumber
     *            the destinationAccountNumber to set
     */
    public void setDestinationAccountNumber(String destinationAccountNumber) {
	this.destinationAccountNumber = destinationAccountNumber;
    }

    /**
     * @return the mobileDenominaitonList
     */
    public BigDecimal[] getMobileDenominaitonList() {
	if (null != this.mobileDenominaitonList && this.mobileDenominaitonList.length > 0) {
	    return Arrays.copyOf(this.mobileDenominaitonList, this.mobileDenominaitonList.length);
	} else {
	    String[] denominationList = null;
	    // if (null != this.billerCategoryId && PRE_PAID_MOBILE.equals(this.billerCategoryId)
	    if (null != this.billerCategoryId && null != this.mobileDenominaiton) {
		denominationList = this.mobileDenominaiton.split(",");
	    }
	    if (null != denominationList) {
		BigDecimal[] amountList = new BigDecimal[denominationList.length];
		for (int i = 0; i < denominationList.length; i++) {
		    amountList[i] = new BigDecimal(denominationList[i]);
		}
		this.mobileDenominaitonList = amountList;

		return amountList;
	    } else {
		return null;
	    }
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
     * @return the destinationBankCode
     */
    public String getDestinationBankCode() {
	return destinationBankCode;
    }

    /**
     * @param destinationBankCode
     *            the destinationBankCode to set
     */
    public void setDestinationBankCode(String destinationBankCode) {
	this.destinationBankCode = destinationBankCode;
    }

    /**
     * @return the destinationBranchCode
     */
    public String getDestinationBranchCode() {
	return destinationBranchCode;
    }

    /**
     * @param destinationBranchCode
     *            the destinationBranchCode to set
     */
    public void setDestinationBranchCode(String destinationBranchCode) {
	this.destinationBranchCode = destinationBranchCode;
    }

    /**
     * @return the destinationAccountIbanFlg
     */
    public boolean getDestinationAccountIbanFlg() {
	return destinationAccountIbanFlg;
    }

    /**
     * @param destinationAccountIbanFlg
     *            the destinationAccountIbanFlg to set
     */
    public void setDestinationAccountIbanFlg(boolean destinationAccountIbanFlg) {
	this.destinationAccountIbanFlg = destinationAccountIbanFlg;
    }

    /**
     * @return the destinationIsoCountryCode
     */
    public String getDestinationIsoCountryCode() {
	return destinationIsoCountryCode;
    }

    /**
     * @param destinationIsoCountryCode
     *            the destinationIsoCountryCode to set
     */
    public void setDestinationIsoCountryCode(String destinationIsoCountryCode) {
	this.destinationIsoCountryCode = destinationIsoCountryCode;
    }

    /**
     * @return the destinationResdentStatus
     */
    public String getDestinationResdentStatus() {
	return destinationResdentStatus;
    }

    /**
     * @param destinationResdentStatus
     *            the destinationResdentStatus to set
     */
    public void setDestinationResdentStatus(String destinationResdentStatus) {
	this.destinationResdentStatus = destinationResdentStatus;
    }

    /**
     * @return the destinationAddressLine1
     */
    public String getDestinationAddressLine1() {
	return destinationAddressLine1;
    }

    /**
     * @param destinationAddressLine1
     *            the destinationAddressLine1 to set
     */
    public void setDestinationAddressLine1(String destinationAddressLine1) {
	this.destinationAddressLine1 = destinationAddressLine1;
    }

    /**
     * @return the destinationAddressLine2
     */
    public String getDestinationAddressLine2() {
	return destinationAddressLine2;
    }

    /**
     * @param destinationAddressLine2
     *            the destinationAddressLine2 to set
     */
    public void setDestinationAddressLine2(String destinationAddressLine2) {
	this.destinationAddressLine2 = destinationAddressLine2;
    }

    public String getDestinationAddressLine3() {
	return destinationAddressLine3;
    }

    public void setDestinationAddressLine3(String destinationAddressLine3) {
	this.destinationAddressLine3 = destinationAddressLine3;
    }

    /**
     * @return the destinationBankIsoCountryCode
     */
    public String getDestinationBankIsoCountryCode() {
	return destinationBankIsoCountryCode;
    }

    /**
     * @param destinationBankIsoCountryCode
     *            the destinationBankIsoCountryCode to set
     */
    public void setDestinationBankIsoCountryCode(String destinationBankIsoCountryCode) {
	this.destinationBankIsoCountryCode = destinationBankIsoCountryCode;
    }

    /**
     * @return the destinationBankSwiftCode
     */
    public String getDestinationBankSwiftCode() {
	return destinationBankSwiftCode;
    }

    /**
     * @param destinationBankSwiftCode
     *            the destinationBankSwiftCode to set
     */
    public void setDestinationBankSwiftCode(String destinationBankSwiftCode) {
	this.destinationBankSwiftCode = destinationBankSwiftCode;
    }

    /**
     * @return the destinationBankClearingCode
     */
    public String getDestinationBankClearingCode() {
	return destinationBankClearingCode;
    }

    /**
     * @param destinationBankClearingCode
     *            the destinationBankClearingCode to set
     */
    public void setDestinationBankClearingCode(String destinationBankClearingCode) {
	this.destinationBankClearingCode = destinationBankClearingCode;
    }

    /**
     * @return the destinationBankChipsUid
     */
    public String getDestinationBankChipsUid() {
	return destinationBankChipsUid;
    }

    /**
     * @param destinationBankChipsUid
     *            the destinationBankChipsUid to set
     */
    public void setDestinationBankChipsUid(String destinationBankChipsUid) {
	this.destinationBankChipsUid = destinationBankChipsUid;
    }

    /**
     * @return the destinationBankName
     */
    public String getDestinationBankName() {
	return destinationBankName;
    }

    /**
     * @param destinationBankName
     *            the destinationBankName to set
     */
    public void setDestinationBankName(String destinationBankName) {
	this.destinationBankName = destinationBankName;
    }

    /**
     * @return the destinationBankAddressLine1
     */
    public String getDestinationBankAddressLine1() {
	return destinationBankAddressLine1;
    }

    /**
     * @param destinationBankAddressLine1
     *            the destinationBankAddressLine1 to set
     */
    public void setDestinationBankAddressLine1(String destinationBankAddressLine1) {
	this.destinationBankAddressLine1 = destinationBankAddressLine1;
    }

    /**
     * @return the destinationBankAddressLine2
     */
    public String getDestinationBankAddressLine2() {
	return destinationBankAddressLine2;
    }

    /**
     * @param destinationBankAddressLine2
     *            the destinationBankAddressLine2 to set
     */
    public void setDestinationBankAddressLine2(String destinationBankAddressLine2) {
	this.destinationBankAddressLine2 = destinationBankAddressLine2;
    }

    /**
     * @return the intermediaryBankAccountNumber
     */
    public String getIntermediaryBankAccountNumber() {
	return intermediaryBankAccountNumber;
    }

    /**
     * @param intermediaryBankAccountNumber
     *            the intermediaryBankAccountNumber to set
     */
    public void setIntermediaryBankAccountNumber(String intermediaryBankAccountNumber) {
	this.intermediaryBankAccountNumber = intermediaryBankAccountNumber;
    }

    /**
     * @return the intermediaryBankSwiftCode
     */
    public String getIntermediaryBankSwiftCode() {
	return intermediaryBankSwiftCode;
    }

    /**
     * @param intermediaryBankSwiftCode
     *            the intermediaryBankSwiftCode to set
     */
    public void setIntermediaryBankSwiftCode(String intermediaryBankSwiftCode) {
	this.intermediaryBankSwiftCode = intermediaryBankSwiftCode;
    }

    /**
     * @return the intermediaryBankClearingCode
     */
    public String getIntermediaryBankClearingCode() {
	return intermediaryBankClearingCode;
    }

    /**
     * @param intermediaryBankClearingCode
     *            the intermediaryBankClearingCode to set
     */
    public void setIntermediaryBankClearingCode(String intermediaryBankClearingCode) {
	this.intermediaryBankClearingCode = intermediaryBankClearingCode;
    }

    /**
     * @return the intermediaryBankChipsUid
     */
    public String getIntermediaryBankChipsUid() {
	return intermediaryBankChipsUid;
    }

    /**
     * @param intermediaryBankChipsUid
     *            the intermediaryBankChipsUid to set
     */
    public void setIntermediaryBankChipsUid(String intermediaryBankChipsUid) {
	this.intermediaryBankChipsUid = intermediaryBankChipsUid;
    }

    /**
     * @return the intermediaryBankName
     */
    public String getIntermediaryBankName() {
	return intermediaryBankName;
    }

    /**
     * @param intermediaryBankName
     *            the intermediaryBankName to set
     */
    public void setIntermediaryBankName(String intermediaryBankName) {
	this.intermediaryBankName = intermediaryBankName;
    }

    /**
     * @return the intermediaryBankAddressLine1
     */
    public String getIntermediaryBankAddressLine1() {
	return intermediaryBankAddressLine1;
    }

    /**
     * @param intermediaryBankAddressLine1
     *            the intermediaryBankAddressLine1 to set
     */
    public void setIntermediaryBankAddressLine1(String intermediaryBankAddressLine1) {
	this.intermediaryBankAddressLine1 = intermediaryBankAddressLine1;
    }

    /**
     * @return the intermediaryBankAddressLine2
     */
    public String getIntermediaryBankAddressLine2() {
	return intermediaryBankAddressLine2;
    }

    /**
     * @param intermediaryBankAddressLine2
     *            the intermediaryBankAddressLine2 to set
     */
    public void setIntermediaryBankAddressLine2(String intermediaryBankAddressLine2) {
	this.intermediaryBankAddressLine2 = intermediaryBankAddressLine2;
    }

    /**
     * @return the status
     */
    public String getStatus() {
	return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
	this.status = status;
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

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
	return modifiedBy;
    }

    /**
     * @param modifiedBy
     *            the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedDate
     */
    public Date getModifiedDate() {
	if (modifiedDate != null) {
	    return new Date(modifiedDate.getTime());
	}
	return null;
    }

    /**
     * @param modifiedDate
     *            the modifiedDate to set
     */
    public void setModifiedDate(Date modifiedDate) {
	if (modifiedDate != null) {
	    this.modifiedDate = new Date(modifiedDate.getTime());
	} else {
	    this.modifiedDate = null;
	}
    }

    /**
     * @return the authorisedBy
     */
    public String getAuthorisedBy() {
	return authorisedBy;
    }

    /**
     * @param authorisedBy
     *            the authorisedBy to set
     */
    public void setAuthorisedBy(String authorisedBy) {
	this.authorisedBy = authorisedBy;
    }

    /**
     * @return the authorisedDate
     */
    public Date getAuthorisedDate() {
	if (authorisedDate != null) {
	    return new Date(authorisedDate.getTime());
	}
	return null;
    }

    /**
     * @param authorisedDate
     *            the authorisedDate to set
     */
    public void setAuthorisedDate(Date authorisedDate) {
	if (authorisedDate != null) {
	    this.authorisedDate = new Date(authorisedDate.getTime());
	} else {
	    this.authorisedDate = null;
	}
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
     * @return the payeeTypeCode
     */
    public String getPayeeTypeCode() {
	return payeeTypeCode;
    }

    /**
     * @param payeeTypeCode
     *            the payeeTypeCode to set
     */
    public void setPayeeTypeCode(String payeeTypeCode) {
	this.payeeTypeCode = payeeTypeCode;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
	if (createdDate != null) {
	    return new Date(createdDate.getTime());
	}
	return null;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
	if (createdDate != null) {
	    this.createdDate = new Date(createdDate.getTime());
	} else {
	    this.createdDate = null;
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
     * @return the supportCreditCardFlag
     */
    public boolean getSupportCreditCardFlag() {
	return supportCreditCardFlag;
    }

    /**
     * @param supportCreditCardFlag
     *            the supportCreditCardFlag to set
     */
    public void setSupportCreditCardFlag(boolean supportCreditCardFlag) {
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
     * @return the billRefNo1
     */
    public String getBillRefNo1() {
	return billRefNo1;
    }

    /**
     * @param billRefNo1
     *            the billRefNo1 to set
     */
    public void setBillRefNo1(String billRefNo1) {
	this.billRefNo1 = billRefNo1;
    }

    /**
     * @return the destinationBankClearingNetCode
     */
    public String getDestinationBankClearingNetCode() {
	return destinationBankClearingNetCode;
    }

    /**
     * @param destinationBankClearingNetCode
     *            the destinationBankClearingNetCode to set
     */
    public void setDestinationBankClearingNetCode(String destinationBankClearingNetCode) {
	this.destinationBankClearingNetCode = destinationBankClearingNetCode;
    }

    /**
     * @return the intermediaryBankClearingNetCode
     */
    public String getIntermediaryBankClearingNetCode() {
	return intermediaryBankClearingNetCode;
    }

    /**
     * @param intermediaryBankClearingNetCode
     *            the intermediaryBankClearingNetCode to set
     */
    public void setIntermediaryBankClearingNetCode(String intermediaryBankClearingNetCode) {
	this.intermediaryBankClearingNetCode = intermediaryBankClearingNetCode;
    }

    /**
     * @return the billRefNo2
     */
    public String getBillRefNo2() {
	return billRefNo2;
    }

    /**
     * @param billRefNo2
     *            the billRefNo2 to set
     */
    public void setBillRefNo2(String billRefNo2) {
	this.billRefNo2 = billRefNo2;
    }

    /**
     * @return the autoAuthorize
     */
    public String getAutoAuthorize() {
	if (StringUtils.isEmpty(this.getStatus())) {
	    return "false";
	} else {
	    return this.getStatus().equals("ACTIVE") ? "true" : "false";
	}
    }

    /**
     * @return the customerAuthMechanismTypeCode
     */
    public String getCustomerAuthMechanismTypeCode() {
	return "OTP";
    }

    @Override
    public final String toString() {
	final StringBuilder sb = new StringBuilder();
	sb.append("Payee nick name::");
	sb.append(this.getPayeeNickname());
	if (this.getBillRefNo1() != null) {
	    sb.append("Bill refer nick name:");
	    sb.append(":");
	    sb.append(this.getPayeeNickname());

	}

	if (this.getDestinationAccountNumber() != null) {
	    sb.append("Destination Account number:");
	    sb.append(":");
	    sb.append(maskAccountNumber(this.getDestinationAccountNumber()));
	}

	sb.append("Payee Id:");
	sb.append(":");
	sb.append(this.getPayeeId());
	return sb.toString();
    }

    /**
     * mask account number as hiding the sensetive information
     *
     * @param accountNumber
     * @return
     */
    private String maskAccountNumber(String accountNumber) {
	int remainAccountLength = 4;
	String maskedAcctNo = "";
	if (accountNumber != null && accountNumber.length() > remainAccountLength) {
	    maskedAcctNo = accountNumber.substring(accountNumber.length() - remainAccountLength, accountNumber.length());
	}
	return "*" + maskedAcctNo;
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((payeeId == null) ? 0 : payeeId.hashCode());
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
	// if (this.payeeId == null && obj == null) {
	// return true;
	// }
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	BeneficiaryDTO other = (BeneficiaryDTO) obj;
	// if (payeeId == null) {
	// if (other.payeeId != null) {
	// return false;
	// }
	// } else if (!payeeId.equals(other.payeeId)) {
	// return false;
	// }
	if (null != this.payeeTypeCode) {
	    if (null == other.getPayeeTypeCode()) {
		return false;
	    } else {
		if (this.payeeTypeCode.equals(other.getPayeeTypeCode())) {
		    if (this.payeeTypeCode.equals("BP")) {
			if ((null != this.payeeNickname && null != other.getPayeeNickname() && this.payeeNickname.equals(other.getPayeeNickname()))
				&& (null != this.getBillerId() && null != other.getBillerId() && this.getBillerId().equals(other.getBillerId()))
				&& (null != this.billRefNo1 && this.billRefNo1.equals(other.getBillRefNo1()))) {
			    return true;
			}
		    }
		    if (this.payeeTypeCode.equals("IAC")) {
			if ((null != this.payeeNickname && this.payeeNickname.equals(other.getPayeeNickname()))
				&& (null != this.destinationAccountNumber && this.destinationAccountNumber
					.equals(other.getDestinationAccountNumber()))) {
			    return true;
			}
		    }
		    if (this.payeeTypeCode.equals("BCP")) {
			if ((null != this.payeeNickname && this.payeeNickname.equals(other.getPayeeNickname()))
				&& (null != this.cardNumber && this.cardNumber.equals(other.getCardNumber()))) {
			    return true;
			}
		    }
		    if (this.payeeTypeCode.equals("DAC")) {
			if ((null != this.payeeNickname && this.payeeNickname.equals(other.getPayeeNickname()))
				&& (null != this.destinationAccountNumber && this.destinationAccountNumber
					.equals(other.getDestinationAccountNumber()))
				&& (null != this.destinationBankCode && this.destinationBankCode.equals(other.getDestinationBankCode()))
				&& (this.destinationBranchCode.equals(other.getDestinationBranchCode()))) {
			    return true;
			}
		    }
		    if (this.payeeTypeCode.equals("INTL")) {
			if ((null != this.payeeNickname && this.payeeNickname.equals(other.getPayeeNickname()))
				&& (null != this.destinationAccountNumber && this.destinationAccountNumber
					.equals(other.getDestinationAccountNumber()))
				&& (null != this.beneficiaryName && this.beneficiaryName.equals(other.getBeneficiaryName()))
				&& (null != this.destinationBankSwiftCode && this.destinationBankSwiftCode
					.equals(other.getDestinationBankSwiftCode()))) {
			    return true;
			}
		    }
		    if (this.payeeTypeCode.equals("BKD")) {
			if ((null != this.payeeNickname && this.payeeNickname.equals(other.getPayeeNickname()))) {
			    return true;
			}
		    }
		    if (this.payeeTypeCode.equals("MTP")) {
			if ((null != this.payeeNickname && this.payeeNickname.equals(other.getPayeeNickname()))) {
			    return true;
			}
		    }

		}
	    }
	}
	return false;
    }

    public String getIntermediaryBankIsoCountryCode() {
	return intermediaryBankIsoCountryCode;
    }

    public void setIntermediaryBankIsoCountryCode(String intermediaryBankIsoCountryCode) {
	this.intermediaryBankIsoCountryCode = intermediaryBankIsoCountryCode;
    }

    public String getTopupService() {
	return topupService;
    }

    public void setTopupService(String topupService) {
	this.topupService = topupService;
    }

    public String getDeliveryType() {
	return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
	this.deliveryType = deliveryType;
    }

    public String getRemitterName() {
	return remitterName;
    }

    public void setRemitterName(String remitterName) {
	this.remitterName = remitterName;
    }

    public Address getRemitterAddress() {
	return remitterAddress;
    }

    public void setRemitterAddress(Address remitterAddress) {
	this.remitterAddress = remitterAddress;
    }

    /**
     * @return the requestFlg
     */
    public boolean isRequestFlg() {
	return requestFlg;
    }

    /**
     * @param requestFlg
     *            the requestFlg to set
     */
    public void setRequestFlg(boolean requestFlg) {
	this.requestFlg = requestFlg;
    }

    /**
     * @return the transactionReferenceNumber
     */
    public String getTransactionReferenceNumber() {
	return transactionReferenceNumber;
    }

    /**
     * @param transactionReferenceNumber
     *            the transactionReferenceNumber to set
     */
    public void setTransactionReferenceNumber(String transactionReferenceNumber) {
	this.transactionReferenceNumber = transactionReferenceNumber;
    }

    /**
     * @return the transactionDateTime
     */
    public Date getTransactionDateTime() {
	if (transactionDateTime != null) {
	    return new Date(transactionDateTime.getTime());
	}
	return null;
    }

    /**
     * @param transactionDateTime
     *            the transactionDateTime to set
     */
    public void setTransactionDateTime(Date transactionDateTime) {
	if (transactionDateTime != null) {
	    this.transactionDateTime = new Date(transactionDateTime.getTime());
	} else {
	    this.transactionDateTime = null;
	}
    }

    /**
     * @param contactNumber
     *            the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
	return contactNumber;
    }

    /**
     * @param billersMinPaymentAmount
     *            the billersMinPaymentAmount to set
     */
    public void setBillersMinPaymentAmount(BigDecimal billersMinPaymentAmount) {
	this.billersMinPaymentAmount = billersMinPaymentAmount;
    }

    /**
     * @return the billersMinPaymentAmount
     */
    public BigDecimal getBillersMinPaymentAmount() {
	return billersMinPaymentAmount;
    }

    public boolean isIbanFlag() {
	return ibanFlag;
    }

    public void setIbanFlag(boolean ibanFlag) {
	this.ibanFlag = ibanFlag;
    }

    public BigDecimal getBillersMaxPaymentAmount() {
	return billersMaxPaymentAmount;
    }

    public void setBillersMaxPaymentAmount(BigDecimal billersMaxPaymentAmount) {
	this.billersMaxPaymentAmount = billersMaxPaymentAmount;
    }

    public String getRefNoKey1() {
	return refNoKey1;
    }

    public void setRefNoKey1(String refNoKey1) {
	this.refNoKey1 = refNoKey1;
    }

    public String getBillHolderRequired() {
	return billHolderRequired;
    }

    public void setBillHolderRequired(String billHolderRequired) {
	this.billHolderRequired = billHolderRequired;
    }

    public String getServiceType() {
	return serviceType;
    }

    public void setServiceType(String serviceType) {
	this.serviceType = serviceType;
    }

    public String getBeneficiaryId() {
	return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
	this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryNickName() {
	return beneficiaryNickName;
    }

    public void setBeneficiaryNickName(String beneficiaryNickName) {
	this.beneficiaryNickName = beneficiaryNickName;
    }

	public String getBeneficiaryOldNickName() {
		return beneficiaryOldNickName;
	}

	public void setBeneficiaryOldNickName(String beneficiaryOldNickName) {
		this.beneficiaryOldNickName = beneficiaryOldNickName;
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
	//Probase
	public void setInvoiceDetails(InvoiceDetails invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public InvoiceDetails getInvoiceDetails() {
		return invoiceDetails;
	}



}