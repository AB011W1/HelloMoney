package com.barclays.bmg.channel.resolver.config;

import java.util.HashMap;
import java.util.Map;

public class ServiceVersionControllerConfig {

    String opCode;
    private Map<String, String> serviceControllerMapping = new HashMap<String, String>();
    private String defaultControllerName;

    public String getDefaultControllerName() {
	return defaultControllerName;
    }

    public void setDefaultControllerName(String defaultControllerName) {
	this.defaultControllerName = defaultControllerName;
    }

    public String getOpCode() {
	return opCode;
    }

    public void setOpCode(String opCode) {
	this.opCode = opCode;
    }

    public String getControllerName(String serviceVersion) {
	String controllerName = serviceControllerMapping.get(serviceVersion);

	if (controllerName == null) {
	    controllerName = defaultControllerName;
	}

	return controllerName;
    }

    public void addServiceControllerMapping(String serviceVersion, String controllerName) {
	serviceControllerMapping.put(serviceVersion, controllerName);
    }
}
