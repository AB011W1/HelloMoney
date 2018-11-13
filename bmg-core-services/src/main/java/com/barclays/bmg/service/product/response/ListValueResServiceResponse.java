package com.barclays.bmg.service.product.response;

import com.barclays.bmg.context.ResponseContext;

public class ListValueResServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 5547505559061918231L;
    private String listValueLabel;

    public String getListValueLabel() {
	return listValueLabel;
    }

    public void setListValueLabel(String listValueLabel) {
	this.listValueLabel = listValueLabel;
    }

}
