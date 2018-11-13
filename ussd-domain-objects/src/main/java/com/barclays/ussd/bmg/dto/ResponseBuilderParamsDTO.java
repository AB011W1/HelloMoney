package com.barclays.ussd.bmg.dto;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.utils.UssdResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class ResponseBuilderParamsDTO.
 */
public class ResponseBuilderParamsDTO {

    /** The json string. */
    private String jsonString;

    /** The bmg op code. */
    private String bmgOpCode;

    /** The tran data id. */
    private String tranDataId;

    private String tranDataOpCode;

    /** The header id. */
    private String headerId;

    /** The ussd session mgmt. */
    private USSDSessionManagement ussdSessionMgmt;

    /** The ussd resource bundle. */
    private UssdResourceBundle ussdResourceBundle;

    /** The msisdn. */
    private String msisdn;

    // confirm option workaround flag
    private boolean errorneousPage = false;

    private NavigationOptionsDTO backHomeOptions;

    public NavigationOptionsDTO getBackHomeOptions() {
	return backHomeOptions;
    }

    public void setBackHomeOptions(NavigationOptionsDTO backHomeOptions) {
	this.backHomeOptions = backHomeOptions;
    }

    /**
     * Gets the json string.
     * 
     * @return the json string
     */
    public String getJsonString() {
	return jsonString;
    }

    /**
     * Sets the json string.
     * 
     * @param jsonString
     *            the new json string
     */
    public void setJsonString(String jsonString) {
	this.jsonString = jsonString;
    }

    /**
     * Gets the bmg op code.
     * 
     * @return the bmg op code
     */
    public String getBmgOpCode() {
	return bmgOpCode;
    }

    /**
     * Sets the bmg op code.
     * 
     * @param bmgOpCode
     *            the new bmg op code
     */
    public void setBmgOpCode(String bmgOpCode) {
	this.bmgOpCode = bmgOpCode;
    }

    /**
     * Gets the tran data id.
     * 
     * @return the tran data id
     */
    public String getTranDataId() {
	return tranDataId;
    }

    /**
     * Sets the tran data id.
     * 
     * @param tranDataId
     *            the new tran data id
     */
    public void setTranDataId(String tranDataId) {
	this.tranDataId = tranDataId;
    }

    /**
     * @return the tranDataOpCode
     */
    public String getTranDataOpCode() {
	return tranDataOpCode;
    }

    /**
     * @param tranDataOpCode
     *            the tranDataOpCode to set
     */
    public void setTranDataOpCode(String tranDataOpCode) {
	this.tranDataOpCode = tranDataOpCode;
    }

    /**
     * Gets the header id.
     * 
     * @return the header id
     */
    public String getHeaderId() {
	return headerId;
    }

    /**
     * Sets the header id.
     * 
     * @param headerId
     *            the new header id
     */
    public void setHeaderId(String headerId) {
	this.headerId = headerId;
    }

    /**
     * Gets the ussd session mgmt.
     * 
     * @return the ussd session mgmt
     */
    public USSDSessionManagement getUssdSessionMgmt() {
	return ussdSessionMgmt;
    }

    /**
     * Sets the ussd session mgmt.
     * 
     * @param ussdSessionMgmt
     *            the new ussd session mgmt
     */
    public void setUssdSessionMgmt(USSDSessionManagement ussdSessionMgmt) {
	this.ussdSessionMgmt = ussdSessionMgmt;
    }

    /**
     * Sets the ussd resource bundle.
     * 
     * @param ussdResourceBundle
     *            the ussdResourceBundle to set
     */
    public void setUssdResourceBundle(UssdResourceBundle ussdResourceBundle) {
	this.ussdResourceBundle = ussdResourceBundle;
    }

    /**
     * Gets the ussd resource bundle.
     * 
     * @return the ussdResourceBundle
     */
    public UssdResourceBundle getUssdResourceBundle() {
	return ussdResourceBundle;
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
    public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
    }

    public boolean isErrorneousPage() {
	return errorneousPage;
    }

    public void setErrorneousPage(boolean errorneousPage) {
	this.errorneousPage = errorneousPage;
    }
}
