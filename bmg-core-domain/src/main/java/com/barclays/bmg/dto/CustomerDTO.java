package com.barclays.bmg.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.dto.BaseDomainDTO;

public class CustomerDTO extends BaseDomainDTO {
    /**
	 *
	 */
    private static final long serialVersionUID = 8275286881812542291L;
    public final static String CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
    private String givenName;
    private String surname1;
    private String surname2;
    private String fullName;
    private String middleName;
    private String customerID;
    private String customerAuthMode;
    private String customerSegment;
    private String salutation;
    private Date DOB;
    private Date DOE;
    private String orgCode;
    private String residenceStatus;
    private String mobilePhone;
    private String userId;
    private String userName;
    private String email;
    private String oldScvId;
    private String userStatusInMCE;
    
   //Botswana welcome banner INC INC0063990  
    private String welcomeBanner;
    private String bocBannerFlag;
    private String isBannerFlag;
    
    public String getIsBannerFlag() {
		return isBannerFlag;
	}

	public void setIsBannerFlag(String isBannerFlag) {
		this.isBannerFlag = isBannerFlag;
	}

	public String getBocBannerFlag() {
		return bocBannerFlag;
	}

	public void setBocBannerFlag(String bocBannerFlag) {
		this.bocBannerFlag = bocBannerFlag;
	}

	public String getWelcomeBanner() {
		return welcomeBanner;
	}

	public void setWelcomeBanner(String welcomeBanner) {
		this.welcomeBanner = welcomeBanner;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String firstName;
    private String lastName;
    /**
     * Added for Limited Access flag in MCE
     */
    private String customerAccessStatusCode;

    /**
     * @return the userStatusInMCE
     */
    public String getUserStatusInMCE() {
	return userStatusInMCE;
    }

    /**
     * @param userStatusInMCE
     *            the userStatusInMCE to set
     */
    public void setUserStatusInMCE(String userStatusInMCE) {
	this.userStatusInMCE = userStatusInMCE;
    }

    // if more than one emails, then they will be divided with ,
    private String emailAddresses;

    // for CRA0022 service retrieveIndividualCustByCCNumber
    private CreditCardAccountDTO creditCardAccountDTO;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    private boolean scvIdChangedFlag;
    private String scvStatusTypeCode;
    private Boolean highSeverityMemoIndicator;
    private Map<String, String> customerIdentification;
    private Map<String, String> productProcessorDetails;
    private List<PostalAddressDTO> postalAddresses;

    // Hello money specific fields added.
    private String language;
    private String pinStatus;

    /**
     * @return the pinStatus
     */
    public String getPinStatus() {
	return pinStatus;
    }

    /**
	 *
	 */
    public void setPinStatus(String pinStatus) {
	this.pinStatus = pinStatus;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
	return language;
    }

    /**
	 *
	 */
    public void setLanguage(String language) {
	this.language = language;
    }

    // define for audit log function
    private String auditLogType;

    public String getMiddleName() {
	return middleName;
    }

    public void setMiddleName(String middleName) {
	this.middleName = middleName;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    /**
     * @return the mobilePhone
     */
    public String getMobilePhone() {
	return mobilePhone;
    }

    public Boolean getHighSeverityMemoIndicator() {
	return highSeverityMemoIndicator;
    }

    public void setHighSeverityMemoIndicator(Boolean highSeverityMemoIndicator) {
	this.highSeverityMemoIndicator = highSeverityMemoIndicator;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    /**
     * @param mobilePhone
     *            the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    /**
     * @return the orgCode
     */
    public String getOrgCode() {
	return orgCode;
    }

    /**
     * @param orgCode
     *            the orgCode to set
     */
    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

    public String getGivenName() {
	return givenName;
    }

    public void setGivenName(String givenName) {
	this.givenName = givenName;
    }

    public String getSurname1() {
	return surname1;
    }

    public void setSurname1(String surname1) {
	this.surname1 = surname1;
    }

    public String getSurname2() {
	return surname2;
    }

    public void setSurname2(String surname2) {
	this.surname2 = surname2;
    }

    public String getFullName() {
	return fullName;
    }

    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    public CustomerDTO() {
	super();
    }

    public String getCustomerID() {
	return customerID;
    }

    public void setCustomerID(String customerID) {
	this.customerID = customerID;
    }

    public String getCustomerAuthMode() {
	return customerAuthMode;
    }

    public void setCustomerAuthMode(String customerAuthMode) {
	this.customerAuthMode = customerAuthMode;
    }

    public String getCustomerSegment() {
	return customerSegment;
    }

    public void setCustomerSegment(String customerSegment) {
	this.customerSegment = customerSegment;
    }

    public String getSalutation() {
	return salutation;
    }

    public void setSalutation(String salutation) {
	this.salutation = salutation;
    }

    public Date getDOB() {
	if (DOB != null) {
	    return new Date(DOB.getTime());
	}
	return null;
    }

    public void setDOB(Date dob) {
	if (dob != null) {
	    this.DOB = new Date(dob.getTime());
	} else {
	    this.DOB = null;
	}
    }

    public Date getDOE() {
	if (DOE != null) {
	    return new Date(DOE.getTime());
	}
	return null;
    }

    public void setDOE(Date doe) {
	if (doe != null) {
	    this.DOE = new Date(doe.getTime());
	} else {
	    this.DOE = null;
	}
    }

    public String getResidenceStatus() {
	return residenceStatus;
    }

    public void setResidenceStatus(String residenceStatus) {
	this.residenceStatus = residenceStatus;
    }

    public String getOldScvId() {
	return oldScvId;
    }

    public void setOldScvId(String oldScvId) {
	this.oldScvId = oldScvId;
    }

    public boolean isScvIdChangedFlag() {
	return scvIdChangedFlag;
    }

    public void setScvIdChangedFlag(boolean scvIdChangedFlag) {
	this.scvIdChangedFlag = scvIdChangedFlag;
    }

    public String getScvStatusTypeCode() {
	return scvStatusTypeCode;
    }

    public void setScvStatusTypeCode(String scvStatusTypeCode) {
	this.scvStatusTypeCode = scvStatusTypeCode;
    }

    public Map<String, String> getCustomerIdentification() {
	return customerIdentification;
    }

    public void setCustomerIdentification(Map<String, String> customerIdentification) {
	this.customerIdentification = customerIdentification;
    }

    public Map<String, String> getProductProcessorDetails() {
	return productProcessorDetails;
    }

    public void setProductProcessorDetails(Map<String, String> productProcessorDetails) {
	this.productProcessorDetails = productProcessorDetails;
    }

    public List<PostalAddressDTO> getPostalAddresses() {
	return postalAddresses;
    }

    public void setPostalAddresses(List<PostalAddressDTO> postalAddresses) {
	this.postalAddresses = postalAddresses;
    }

    public String getAuditLogType() {
	return auditLogType;
    }

    public void setAuditLogType(String auditLogType) {
	this.auditLogType = auditLogType;
    }

    public void setEmailAddresses(String emailAddresses) {
	this.emailAddresses = emailAddresses;
    }

    public String getEmailAddresses() {
	return emailAddresses;
    }

    public CreditCardAccountDTO getCreditCardAccountDTO() {
	return creditCardAccountDTO;
    }

    public void setCreditCardAccountDTO(CreditCardAccountDTO creditCardAccountDTO) {
	this.creditCardAccountDTO = creditCardAccountDTO;
    }

    /***
     * Set customerAccessStatusCode
     * @return
     */
	public String getCustomerAccessStatusCode() {
		return customerAccessStatusCode;
	}

	/**
	 * Get customerAccessStatusCode
	 * @param customerAccessStatusCode
	 */
	public void setCustomerAccessStatusCode(String customerAccessStatusCode) {
		this.customerAccessStatusCode = customerAccessStatusCode;
	}
}
