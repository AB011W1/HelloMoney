package com.barclays.bmg.mvc.interceptor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;

public class AllowedAttributesSet implements InitializingBean {

    private String applicationVersionExpr;
    private String deviceIdExpr;
    private String deviceOsNameExpr;
    private String deviceOSVersionExpr;
    private String deviceModelNameExpr;

    public String getApplicationVersionExpr() {
	return applicationVersionExpr;
    }

    public void setApplicationVersionExpr(String applicationVersionExpr) {
	this.applicationVersionExpr = applicationVersionExpr;
    }

    public String getDeviceIdExpr() {
	return deviceIdExpr;
    }

    public void setDeviceIdExpr(String deviceIdExpr) {
	this.deviceIdExpr = deviceIdExpr;
    }

    public String getDeviceOsNameExpr() {
	return deviceOsNameExpr;
    }

    public void setDeviceOsNameExpr(String deviceOsNameExpr) {
	this.deviceOsNameExpr = deviceOsNameExpr;
    }

    public String getDeviceOSVersionExpr() {
	return deviceOSVersionExpr;
    }

    public void setDeviceOSVersionExpr(String deviceOSVersionExpr) {
	this.deviceOSVersionExpr = deviceOSVersionExpr;
    }

    public String getDeviceModelNameExpr() {
	return deviceModelNameExpr;
    }

    public void setDeviceModelNameExpr(String deviceModelNameExpr) {
	this.deviceModelNameExpr = deviceModelNameExpr;
    }

    public void afterPropertiesSet() throws Exception {
	if (applicationVersionExpr == null || deviceIdExpr == null || deviceOsNameExpr == null || deviceOSVersionExpr == null
		|| deviceModelNameExpr == null) {
	    throw new BeanDefinitionValidationException("The allowed pattern does not have all neccessary components ");
	}

    }

}
