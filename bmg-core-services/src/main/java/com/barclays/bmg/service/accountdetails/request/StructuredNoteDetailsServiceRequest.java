package com.barclays.bmg.service.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class StructuredNoteDetailsServiceRequest extends RequestContext {

    private String assetClass;

    public String getAssetClass() {
	return assetClass;
    }

    public void setAssetClass(String assetClass) {
	this.assetClass = assetClass;
    }

}
