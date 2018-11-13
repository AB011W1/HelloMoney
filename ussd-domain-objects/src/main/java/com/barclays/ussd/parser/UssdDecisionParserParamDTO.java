package com.barclays.ussd.parser;

import java.io.Serializable;

import com.barclays.ussd.bean.Transaction;

public class UssdDecisionParserParamDTO implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    private String userInput;
    private String currentScreenId;
    private String businessId;
    private String countryCode;
    private String currentScreenNodeId;
    private String lang;

    /** for the authentication and verification */
    private boolean byPassRequest = false;
    private String serviceName;
    private String msisdn;

    // Flag for self registration
    private boolean selfRegRequest = false;

    // confirm option workaround flag
    private boolean errorneousPage = false;
    private Transaction userTransactionDetails;

    private boolean backOnErrorScreen = false;

    public boolean isByPassRequest() {
	return byPassRequest;
    }

    public void setByPassRequest(boolean byPassRequest) {
	this.byPassRequest = byPassRequest;
    }

    public String getCurrentScreenId() {
	return currentScreenId;
    }

    public void setCurrentScreenId(String currentScreenId) {
	this.currentScreenId = currentScreenId;
    }

    public String getCountryCode() {
	return countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    public String getCurrentScreenNodeId() {
	return currentScreenNodeId;
    }

    public void setCurrentScreenNodeId(String currentScreenNodeId) {
	this.currentScreenNodeId = currentScreenNodeId;
    }

    public Transaction getUserTransactionDetails() {
	return userTransactionDetails;
    }

    public void setUserTransactionDetails(Transaction userTransactionDetails) {
	this.userTransactionDetails = userTransactionDetails;
    }

    public String getUserInput() {
	return userInput;
    }

    public void setUserInput(String userInput) {
	this.userInput = userInput;
    }

    public String getLang() {
	return lang;
    }

    public void setLang(String lang) {
	this.lang = lang;
    }

    public boolean isSelfRegRequest() {
	return selfRegRequest;
    }

    public void setSelfRegRequest(boolean selfRegRequest) {
	this.selfRegRequest = selfRegRequest;
    }

    public String getServiceName() {
	return serviceName;
    }

    public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
    }

    public String getMsisdn() {
	return msisdn;
    }

    public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
    }

    public boolean isErrorneousPage() {
	return errorneousPage;
    }

    public void setErrorneousPage(boolean errorneousPage) {
	this.errorneousPage = errorneousPage;
    }

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    /**
     * @return the backOnErrorScreen
     */
    public boolean isBackOnErrorScreen() {
	return backOnErrorScreen;
    }

    /**
     * @param backOnErrorScreen
     *            the backOnErrorScreen to set
     */
    public void setBackOnErrorScreen(boolean backOnErrorScreen) {
	this.backOnErrorScreen = backOnErrorScreen;
    }

}
