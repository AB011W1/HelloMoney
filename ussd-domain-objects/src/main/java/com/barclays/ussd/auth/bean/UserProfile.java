/** copy right @ Barclays*/
package com.barclays.ussd.auth.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class UserProfile.
 */
public class UserProfile implements Serializable {

    /** The Constant IS_PRIMIARY_INDICATOR. */
    private static final String PRIMARY_INDICATOR = "Y";

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(UserProfile.class);

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Mobile Number is 12 digit Number First two digits are country code and rest is mobile number Through country code we will get the Locale.
     */
    private String msisdn;

    /** The msg. */
    private String msg;

    /** The user name. */
    private String userName;

    //CR-77
    private String customerFirstName;

    /*   *//** Added for the BMG session Handler. */
    /*
     * private transient HttpContext localContext;
     */

    /** User is block or not. */
    private String active;

    /** Valid MSISDN number from the request and make flag true and false. */
    private boolean validateMSISDNNumber;
    /** Valid Password from the request and make flag true and false. */
    private boolean validPassword;

    /** field for error message. */
    private String errorMsg = null;

    /** The language. */
    private String language;

    // User access permissions
    private String usrSta;

    // user pin status
    private String pinSta;

    /** The casa account list. */
    private List<CasaAccount> casaAccountList;

    private String businessId;
    private String countryCode;

    /**
     * get the primary account from the list.
     *
     * @return the main account
     */
    public CasaAccount getMainAccount() {
	CasaAccount primaryAcct = null;
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info("Retrieving the main account for the customer");
	}
	for (CasaAccount acct : this.getCasaAccountList()) {
	    if (PRIMARY_INDICATOR.equalsIgnoreCase(acct.getPrimaryIndicator())) {
		primaryAcct = acct;
		break;
	    }
	}
	return primaryAcct;
    }

    /**
     * Gets the msisdn.
     *
     * @return the msisdn
     */
    public String getMsisdn() {
	return msisdn;
    }

    /**
     * Sets the msisdn.
     *
     * @param msisdn
     *            the msisdn to set
     */
    public void setMsisdn(final String msisdn) {
	this.msisdn = msisdn;
    }

    /**
     * Gets the msg.
     *
     * @return the msg
     */
    public String getMsg() {
	return msg;
    }

    /**
     * Sets the msg.
     *
     * @param msg
     *            the msg to set
     */
    public void setMsg(final String msg) {
	this.msg = msg;
    }

    /**
     * Gets the user name.
     *
     * @return the userName
     */
    public String getUserName() {
	return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName
     *            the userName to set
     */
    public void setUserName(final String userName) {
	this.userName = userName;
    }

    /* *//**
     * Gets the local context.
     *
     * @return the localContext
     */
    /*
     * public HttpContext getLocalContext() { return localContext; }
     *//**
     * Sets the local context.
     *
     * @param localContext
     *            the localContext to set
     */
    /*
     * public void setLocalContext(final HttpContext localContext) { this.localContext = localContext; }
     */

    /**
     * Gets the active.
     *
     * @return the active
     */
    public String getActive() {
	return active;
    }

    /**
     * Sets the active.
     *
     * @param active
     *            the active to set
     */
    public void setActive(final String active) {
	this.active = active;
    }

    /**
     * Checks if is validate msisdn number.
     *
     * @return the validateMSISDNNumber
     */
    public boolean isValidateMSISDNNumber() {
	return validateMSISDNNumber;
    }

    /**
     * Sets the validate msisdn number.
     *
     * @param validateMSISDNNumber
     *            the validateMSISDNNumber to set
     */
    public void setValidateMSISDNNumber(final boolean validateMSISDNNumber) {
	this.validateMSISDNNumber = validateMSISDNNumber;
    }

    /**
     * Checks if is validate password.
     *
     * @return the validatePassword
     */
    public boolean isValidPassword() {
	return validPassword;
    }

    /**
     * Sets the validate password.
     *
     * @param validPassword
     *            the validatePassword to set
     */
    public void setValidPassword(final boolean validPassword) {
	this.validPassword = validPassword;
    }

    /**
     * Gets the error msg.
     *
     * @return the errorMsg
     */
    public String getErrorMsg() {
	return errorMsg;
    }

    /**
     * Sets the error msg.
     *
     * @param errorMsg
     *            the errorMsg to set
     */
    public void setErrorMsg(final String errorMsg) {
	this.errorMsg = errorMsg;
    }

    /**
     * Gets the language.
     *
     * @return the language
     */
    public String getLanguage() {
	return language;
    }

    /**
     * Sets the language.
     *
     * @param language
     *            the language to set
     */
    public void setLanguage(final String language) {
	this.language = language;
    }

    /**
     * Gets the casa account list.
     *
     * @return the casaAccountList
     */
    public List<CasaAccount> getCasaAccountList() {
	return casaAccountList;
    }

    /**
     * Sets the casa account list.
     *
     * @param casaAccountList
     *            the casaAccountList to set
     */
    public void setCasaAccountList(final List<CasaAccount> casaAccountList) {
	this.casaAccountList = casaAccountList;
    }

    public String getUsrSta() {
	return usrSta;
    }

    public void setUsrSta(final String usrSta) {
	this.usrSta = usrSta;
    }

    public String getPinSta() {
	return pinSta;
    }

    public void setPinSta(final String pinSta) {
	this.pinSta = pinSta;
    }

    public static Logger getLOGGER() {
	return LOGGER;
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
    public void setBusinessId(final String businessId) {
	this.businessId = businessId;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
	return countryCode;
    }

    /**
     * @param countryCode
     *            the countryCode to set
     */
    public void setCountryCode(final String countryCode) {
	this.countryCode = countryCode;
    }

    /**
     * @return the priInd
     */
    public static String getPriInd() {
	return PRIMARY_INDICATOR;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("msisdn:").append(msisdn);
	sb.append(",msg:").append(msg);
	sb.append(",userName:").append(userName);
	sb.append(",active:").append(active);
	sb.append(",errorMsg:").append(errorMsg);
	sb.append(",language:").append(language);
	sb.append(",usrSta:").append(usrSta);
	sb.append(",pinSta:").append(pinSta);
	sb.append(",businessId:").append(businessId);
	sb.append(",countryCode:").append(countryCode);

	return sb.toString();
    }

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

}
