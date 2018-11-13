package com.barclays.bmg.channel.resolver.config;

import java.util.Map;

public class FunctionUrlResolverConfig {
    private String id;
    private String opCde;
    private Map<String, String> attrMap;

    public String getOpCde() {
	return opCde;
    }

    public void setOpCde(String opCde) {
	this.opCde = opCde;
    }

    public Map<String, String> getAttrMap() {
	return attrMap;
    }

    public void setAttrMap(Map<String, String> attrMap) {
	this.attrMap = attrMap;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

}
