package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;

public class FilterUrgentPayeeListOperationRequest extends RequestContext {

    List<CategorizedPayeeListDTO> categorizedPayeeList;
    private boolean isUrgent;

    public List<CategorizedPayeeListDTO> getCategorizedPayeeList() {
	return categorizedPayeeList;
    }

    public void setCategorizedPayeeList(List<CategorizedPayeeListDTO> categorizedPayeeList) {
	this.categorizedPayeeList = categorizedPayeeList;
    }

    public boolean isUrgent() {
	return isUrgent;
    }

    public void setUrgent(boolean isUrgent) {
	this.isUrgent = isUrgent;
    }

}
