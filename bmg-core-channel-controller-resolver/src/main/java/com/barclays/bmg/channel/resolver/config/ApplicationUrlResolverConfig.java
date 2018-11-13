package com.barclays.bmg.channel.resolver.config;

import java.util.Map;

public class ApplicationUrlResolverConfig {

    Map<String, FunctionUrlResolverConfig> functionMap;
    private String svcVer;

    public Map<String, FunctionUrlResolverConfig> getFunctionMap() {
	return functionMap;
    }

    public void setFunctionMap(Map<String, FunctionUrlResolverConfig> functionMap) {
	this.functionMap = functionMap;
    }

    public String getSvcVer() {
	return svcVer;
    }

    public void setSvcVer(String svcVer) {
	this.svcVer = svcVer;
    }

}
