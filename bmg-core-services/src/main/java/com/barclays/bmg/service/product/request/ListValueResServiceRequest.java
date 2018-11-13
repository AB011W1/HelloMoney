package com.barclays.bmg.service.product.request;

import com.barclays.bmg.context.RequestContext;

public class ListValueResServiceRequest extends RequestContext {

    private String group;
    private String listValueKey;
    private String filterKey1;
    private String systemId = "UB";
    private String businessId;
    private String langId;

    public ListValueResServiceRequest() {
    }

    public ListValueResServiceRequest(String businessId, String groupId, String langId, String listValueKey) {
	this.businessId = businessId + "BRB";
	this.listValueKey = listValueKey;
	this.group = groupId;
	this.langId = langId;
    }

    public String getGroup() {
	return group;
    }

    public void setGroup(String group) {
	this.group = group;
    }

    public String getListValueKey() {
	return listValueKey;
    }

    public void setListValueKey(String listValueKey) {
	this.listValueKey = listValueKey;
    }

    public String getFilterKey1() {
	return filterKey1;
    }

    public void setFilterKey1(String filterKey1) {
	this.filterKey1 = filterKey1;
    }

    public String getSystemId() {
	return systemId;
    }

    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getLangId() {
	return langId;
    }

    public void setLangId(String langId) {
	this.langId = langId;
    }

}
