package com.barclays.bmg.channel.resolver.config;

import java.util.Map;

public class ControllerResolverConfig {

    private Map<String, ServiceVersionControllerConfig> snvMapping;
    private String parentResolver;
    private boolean hasParentResolver;

    public Map<String, ServiceVersionControllerConfig> getSnvMapping() {
	return snvMapping;
    }

    public void setSnvMapping(Map<String, ServiceVersionControllerConfig> snvMapping) {
	this.snvMapping = snvMapping;
    }

    public String getParentResolver() {
	return parentResolver;
    }

    public void setParentResolver(String parentResolver) {
	this.parentResolver = parentResolver;
    }

    public boolean hasParentResolver() {
	return hasParentResolver;
    }

    public void setHasParentResolver(boolean hasParentResolver) {
	this.hasParentResolver = hasParentResolver;
    }

}
