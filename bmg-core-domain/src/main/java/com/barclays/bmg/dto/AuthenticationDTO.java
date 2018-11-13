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

import java.util.Map;

import com.barclays.bmg.type.AuthType;

public class AuthenticationDTO<T> implements java.io.Serializable {
    private static final long serialVersionUID = 2177136267994568134L;
    private AuthType authType;
    private Object challengeId;
    private String otpPrefix;

    public String getOtpPrefix() {
	return otpPrefix;
    }

    public void setOtpPrefix(String otpPrefix) {
	this.otpPrefix = otpPrefix;
    }

    private int retryBeforeLock;
    private Object token;
    private Object oldToken;
    private T authenticationObject;

    public T getAuthenticationObject() {
	return authenticationObject;
    }

    public void setAuthenticationObject(T authenticationObject) {
	this.authenticationObject = authenticationObject;
    }

    private boolean authenticated;
    private int maxRetryTimes;
    private int failures;
    private String resultCode;
    private boolean needChangePWD;
    private boolean needChangeTxnPWD;

    private boolean pwdMigrated;

    public boolean isPwdMigrated() {
	return pwdMigrated;
    }

    public void setPwdMigrated(boolean pwdMigrated) {
	this.pwdMigrated = pwdMigrated;
    }

    private Map<String, String> PPMap;

    public boolean isNeedChangeTxnPWD() {
	return needChangeTxnPWD;
    }

    public void setNeedChangeTxnPWD(boolean needChangeTxnPWD) {
	this.needChangeTxnPWD = needChangeTxnPWD;
    }

    private boolean needResetToken;

    private boolean needWarningChangePWD;
    private boolean needWarningChangeTXNPWD;

    public boolean isNeedWarningChangeTXNPWD() {
	return needWarningChangeTXNPWD;
    }

    public void setNeedWarningChangeTXNPWD(boolean needWarningChangeTXNPWD) {
	this.needWarningChangeTXNPWD = needWarningChangeTXNPWD;
    }

    public int getTxnPasswordLeftWarningDays() {
	return txnPasswordLeftWarningDays;
    }

    public void setTxnPasswordLeftWarningDays(int txnPasswordLeftWarningDays) {
	this.txnPasswordLeftWarningDays = txnPasswordLeftWarningDays;
    }

    private int txnPasswordLeftWarningDays;
    private int passwordLeftWarningDays;
    private String loginName;

    private CustomerDTO customerDTO;
    private String userId;

    private String usage;
    private String preferredLanguage;

    private String[] smsParams;
    private String smsTemplate;

    public String[] getSmsParams() {
	String str[] = smsParams;
	return str;
    }

    public void setSmsParams(String[] smsParams) {
	String[] str = smsParams;
	this.smsParams = str;
    }

    public String getSmsTemplate() {
	return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
	this.smsTemplate = smsTemplate;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public int getPasswordLeftWarningDays() {
	return passwordLeftWarningDays;
    }

    public void setPasswordLeftWarningDays(int passwordLeftWarningDays) {
	this.passwordLeftWarningDays = passwordLeftWarningDays;
    }

    /**
     * @return the authType
     */
    public AuthType getAuthType() {
	return authType;
    }

    /**
     * @param authType
     *            the authType to set
     */
    public void setAuthType(AuthType authType) {
	this.authType = authType;
    }

    public boolean isNeedWarningChangePWD() {
	return needWarningChangePWD;
    }

    public void setNeedWarningChangePWD(boolean needWarningChangePWD) {
	this.needWarningChangePWD = needWarningChangePWD;
    }

    /**
     * @return the challengeId
     */
    public Object getChallengeId() {
	return challengeId;
    }

    /**
     * @param challengeId
     *            the challengeId to set
     */
    public void setChallengeId(Object challengeId) {
	this.challengeId = challengeId;
    }

    /**
     * @return the token
     */
    public Object getToken() {
	return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(Object token) {
	this.token = token;
    }

    /**
     * @return the oldToken
     */
    public Object getOldToken() {
	return oldToken;
    }

    /**
     * @param oldToken
     *            the oldToken to set
     */
    public void setOldToken(Object oldToken) {
	this.oldToken = oldToken;
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
	return authenticated;
    }

    /**
     * @param authenticated
     *            the authenticated to set
     */
    public void setAuthenticated(boolean authenticated) {
	this.authenticated = authenticated;
    }

    /**
     * @return the retryBeforeLock
     */
    public int getRetryBeforeLock() {
	return retryBeforeLock;
    }

    /**
     * @param retryBeforeLock
     *            the retryBeforeLock to set
     */
    public void setRetryBeforeLock(int retryBeforeLock) {
	this.retryBeforeLock = retryBeforeLock;
    }

    /**
     * @return the max retry times
     */
    public int getMaxRetryTimes() {
	return maxRetryTimes;
    }

    /**
     * @param maxRetryTimes
     */
    public void setMaxRetryTimes(int maxRetryTimes) {
	this.maxRetryTimes = maxRetryTimes;
    }

    /**
     * @return the failure times
     */
    public int getFailures() {
	return failures;
    }

    /**
     * @param failures
     *            the failure times
     */
    public void setFailures(int failures) {
	this.failures = failures;
    }

    /**
     * @return authentication verify result, can be "success", "retry" or "lock"
     */
    public String getResultCode() {
	return resultCode;
    }

    /**
     * @param resultCode
     *            authentication verify result
     */
    public void setResultCode(String resultCode) {
	this.resultCode = resultCode;
    }

    /**
     * @return need to change password
     */
    public boolean isNeedChangePWD() {
	return needChangePWD;
    }

    /**
     * @param needChangePWD
     *            need to change password
     */
    public void setNeedChangePWD(boolean needChangePWD) {
	this.needChangePWD = needChangePWD;
    }

    public String getLoginName() {
	return loginName;
    }

    public void setLoginName(String loginName) {
	this.loginName = loginName;
    }

    public CustomerDTO getCustomerDTO() {
	return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
	this.customerDTO = customerDTO;
    }

    public boolean isNeedResetToken() {
	return needResetToken;
    }

    public void setNeedResetToken(boolean needResetToken) {
	this.needResetToken = needResetToken;
    }

    public String getUsage() {
	return usage;
    }

    public void setUsage(String usage) {
	this.usage = usage;
    }

    /**
     * @return the pPMap
     */
    public Map<String, String> getPPMap() {
	return PPMap;
    }

    /**
     * @param map
     *            the pPMap to set
     */
    public void setPPMap(Map<String, String> map) {
	PPMap = map;
    }

    public String getPreferredLanguage() {
	return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
	this.preferredLanguage = preferredLanguage;
    }
}
