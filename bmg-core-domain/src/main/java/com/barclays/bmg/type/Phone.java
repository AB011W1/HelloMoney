package com.barclays.bmg.type;

import java.io.Serializable;
import java.util.Date;

/**************************** Revision History ******************************
 * Version        Author          Date                        Description
 * 0.1            Eric Zhang        2009/02/26                Initial version
 *
 *
 ****************************************************************************/

/**
 * @author Eric Zhang
 */

public class Phone implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5299337494537656292L;

    /**
     *
     */
    public Phone() {
	super();

    }

    private String customerNo;
    private String telephoneType;
    private String countryCode;
    private String areaCode;
    private String number;
    private String extension;
    private String okToCall;
    private String okToSms;
    private String bestTimeToCall;
    private String lastMaintainedBy;
    private Date lastMaintaineDdate;
    private String lastMaintainedTime;

    /**
     * @return the customerNo
     */
    public final String getCustomerNo() {
	return customerNo;
    }

    /**
     * @param param
     *            the customerNo to set
     */
    public final void setCustomerNo(String param) {
	this.customerNo = param;
    }

    /**
     * @return the telephoneType
     */
    public final String getTelephoneType() {
	return telephoneType;
    }

    /**
     * @param param
     *            the telephoneType to set
     */
    public final void setTelephoneType(String param) {
	this.telephoneType = param;
    }

    /**
     * @return the countryCode
     */
    public final String getCountryCode() {
	return countryCode;
    }

    /**
     * @param param
     *            the countryCode to set
     */
    public final void setCountryCode(String param) {
	this.countryCode = param;
    }

    /**
     * @return the areaCode
     */
    public final String getAreaCode() {
	return areaCode;
    }

    /**
     * @param param
     *            the areaCode to set
     */
    public final void setAreaCode(String param) {
	this.areaCode = param;
    }

    /**
     * @return the number
     */
    public final String getNumber() {
	return number;
    }

    /**
     * @param param
     *            the number to set
     */
    public final void setNumber(String param) {
	this.number = param;
    }

    /**
     * @return the extension
     */
    public final String getExtension() {
	return extension;
    }

    /**
     * @param param
     *            the extension to set
     */
    public final void setExtension(String param) {
	this.extension = param;
    }

    /**
     * @return the okToCall
     */
    public final String getOkToCall() {
	return okToCall;
    }

    /**
     * @param param
     *            the okToCall to set
     */
    public final void setOkToCall(String param) {
	this.okToCall = param;
    }

    /**
     * @return the okToSms
     */
    public final String getOkToSms() {
	return okToSms;
    }

    /**
     * @param param
     *            the okToSms to set
     */
    public final void setOkToSms(String param) {
	this.okToSms = param;
    }

    /**
     * @return the bestTimeToCall
     */
    public final String getBestTimeToCall() {
	return bestTimeToCall;
    }

    /**
     * @param param
     *            the bestTimeToCall to set
     */
    public final void setBestTimeToCall(String param) {
	this.bestTimeToCall = param;
    }

    /**
     * @return the lastMaintainedBy
     */
    public final String getLastMaintainedBy() {
	return lastMaintainedBy;
    }

    /**
     * @param param
     *            the lastMaintainedBy to set
     */
    public final void setLastMaintainedBy(String param) {
	this.lastMaintainedBy = param;
    }

    /**
     * @return the lastMaintaineDdate
     */
    public final Date getLastMaintaineDdate() {
	if (lastMaintaineDdate != null) {
	    return new Date(lastMaintaineDdate.getTime());
	}
	return null;
    }

    /**
     * @param param
     *            the lastMaintaineDdate to set
     */
    public final void setLastMaintaineDdate(Date param) {
	if (param != null) {
	    this.lastMaintaineDdate = new Date(param.getTime());
	} else {
	    this.lastMaintaineDdate = null;
	}
    }

    /**
     * @return the lastMaintainedTime
     */
    public final String getLastMaintainedTime() {
	return lastMaintainedTime;
    }

    /**
     * @param param
     *            the lastMaintainedTime to set
     */
    public final void setLastMaintainedTime(String param) {
	this.lastMaintainedTime = param;
    }
}
