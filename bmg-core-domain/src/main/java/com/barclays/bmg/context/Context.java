package com.barclays.bmg.context;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.dto.CustomerAccountDTO;

/**
 * Context
 *
 * @author e20026338
 *
 */
public class Context {

    private String businessId;
    private String activityId;
    private String systemId;
    private String languageId;
    private String countryCode;
    private String userId;
    private String customerId;
    private String activityRefNo;
    private String localCurrency;
    private String sessionId;
    private String mobilePhone;
    private Map<String, String> ppMap;
    private String fullName;
    private String lastLoginTime;
    private String loginId;
    private String applicationVersion;
    private String deviceId;
    private String deviceOsName;
    private String deviceOsVersion;
    private String deviceModelName;
    private String serviceVersion;

    private String orgRefNo;
    private String timezoneOffset;

    private Map<String, BigDecimal> currencyConversionMap = new HashMap<String, BigDecimal>();
    private String opCde;
    private String pinStatus;
    private String usrSta;
    private String mobileUserId;// Added by Umesh
    
    //ZMBRB,BWBRB,TZBRB one-off
    private String bankLetter;

  public String getBankLetter() {
		return bankLetter;
	}
	public void setBankLetter(String bankLetter) {
		this.bankLetter = bankLetter;
	}

	//GHIPS2
    private String isFreeDialUssdFlow;
    
    //Kits Fullname. Added for KITS debtor name INC INC1009890417 
    private String kitsFullName;

    public String getKitsFullName() {
		return kitsFullName;
	}
	public void setKitsFullName(String kitsFullName) {
		this.kitsFullName = kitsFullName;
	}
	public String getIsFreeDialUssdFlow() {
		return isFreeDialUssdFlow;
	}
	public void setIsFreeDialUssdFlow(String isFreeDialUssdFlow) {
		this.isFreeDialUssdFlow = isFreeDialUssdFlow;
	}

  //Changes for caching of account list & reduce one call to enhance performance
    private List<? extends CustomerAccountDTO> accountList;
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Context[activityRefNo=").append(activityRefNo);
	sb.append(",orgRefNo=").append(orgRefNo);
	sb.append(",fullName=").append(fullName);
	sb.append(",mobilePhone=").append(mobilePhone);
	sb.append(",ppMap=").append(ppMap);
	sb.append(",timezoneOffset=").append(timezoneOffset);
	sb.append(",userId=").append(userId);
	sb.append(",customerId=").append(customerId);
	sb.append(",loginId=").append(loginId);
	sb.append("]");
	return sb.toString();
    }
    //Changes for caching of account list & reduce one call to enhance performance
    public List<? extends CustomerAccountDTO> getAccountList() {
		return accountList;
	}
    //Changes for caching of account list & reduce one call to enhance performance
	public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
		this.accountList = accountList;
	}

	/**
     * @return the usrSta
     */
    public String getUsrSta() {
	return usrSta;
    }

    /**
	 *
	 */
    public void setUsrSta(String usrSta) {
	this.usrSta = usrSta;
    }

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

    public String getLocalCurrency() {
	return localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
	this.localCurrency = localCurrency;
    }

    private Map<String, Object> contextMap = new HashMap<String, Object>();

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public String getSystemId() {
	return systemId;
    }

    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    public String getLanguageId() {
	return languageId;
    }

    public void setLanguageId(String languageId) {
	this.languageId = languageId;
    }

    public String getCountryCode() {
	return countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getCustomerId() {
	return customerId;
    }

    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    public Map<String, Object> getContextMap() {
	return contextMap;
    }

    public void setContextMap(Map<String, Object> contextMap) {
	this.contextMap = contextMap;
    }

    public Object getValue(String key) {
	return contextMap.get(key);
    }

    public void setValue(String key, Object value) {
	contextMap.put(key, value);
    }

    public void addEntry(String key, Object value) {
	contextMap.put(key, value);
    }

    public void removeEntry(String key) {
	contextMap.remove(key);
    }

    public String getActivityRefNo() {
	return activityRefNo;
    }

    public void setActivityRefNo(String activityRefNo) {
	this.activityRefNo = activityRefNo;
    }

    public String getApplicationVersion() {
	return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
	this.applicationVersion = applicationVersion;
    }

    public String getDeviceId() {
	return deviceId;
    }

    public void setDeviceId(String deviceId) {
	this.deviceId = deviceId;
    }

    public String getDeviceOsName() {
	return deviceOsName;
    }

    public void setDeviceOsName(String deviceOsName) {
	this.deviceOsName = deviceOsName;
    }

    public String getDeviceOsVersion() {
	return deviceOsVersion;
    }

    public void setDeviceOsVersion(String deviceOsVersion) {
	this.deviceOsVersion = deviceOsVersion;
    }

    public String getDeviceModelName() {
	return deviceModelName;
    }

    public void setDeviceModelName(String deviceModelName) {
	this.deviceModelName = deviceModelName;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    public Map<String, String> getPpMap() {
	return ppMap;
    }

    public void setPpMap(Map<String, String> ppMap) {
	this.ppMap = ppMap;
    }

    public String getFullName() {
	return fullName;
    }

    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    public String getLastLoginTime() {
	return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
	this.lastLoginTime = lastLoginTime;
    }

    public String getLoginId() {
	return loginId;
    }

    public void setLoginId(String loginId) {
	this.loginId = loginId;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String getServiceVersion() {
	return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
	this.serviceVersion = serviceVersion;
    }

    public String getOrgRefNo() {
	return orgRefNo;
    }

    public void setOrgRefNo(String orgRefNo) {
	this.orgRefNo = orgRefNo;
    }

    public String getTimezoneOffset() {
	return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
	this.timezoneOffset = timezoneOffset;
    }

    @Override
    public int hashCode() {
	int hashCode = 0;
	if (!StringUtils.isEmpty(businessId)) {
	    hashCode = hashCode + businessId.hashCode();
	}
	if (!StringUtils.isEmpty(systemId)) {
	    hashCode = hashCode + systemId.hashCode();
	}
	if (!StringUtils.isEmpty(languageId)) {
	    hashCode = hashCode + languageId.hashCode();
	}
	if (!StringUtils.isEmpty(countryCode)) {
	    hashCode = hashCode + countryCode.hashCode();
	}
	if (!StringUtils.isEmpty(userId)) {
	    hashCode = hashCode + userId.hashCode();
	}
	if (!StringUtils.isEmpty(customerId)) {
	    hashCode = hashCode + customerId.hashCode();
	}
	if (!StringUtils.isEmpty(sessionId)) {
	    hashCode = hashCode + sessionId.hashCode();
	}
	return hashCode;
    }

    public Map<String, BigDecimal> getCurrencyConversionMap() {
	return currencyConversionMap;
    }

    public void setCurrencyConversionMap(Map<String, BigDecimal> currencyConversionMap) {
	this.currencyConversionMap = currencyConversionMap;
    }

    public String getOpCde() {
	return opCde;
    }

    public void setOpCde(String opCde) {
	this.opCde = opCde;
    }

    public String getMobileUserId() {
	return mobileUserId;
    }

    public void setMobileUserId(String mobileUserId) {
	this.mobileUserId = mobileUserId;
    }

}
