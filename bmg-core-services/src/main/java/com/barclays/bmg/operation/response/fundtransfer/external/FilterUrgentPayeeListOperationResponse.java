package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;

public class FilterUrgentPayeeListOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1891573454413481867L;

    List<CategorizedPayeeListDTO> categorizedPayeeList;
    List<CategorizedPayeeListDTO> urgentPayeeList;

    public List<CategorizedPayeeListDTO> getCategorizedPayeeList() {
	return categorizedPayeeList;
    }

    public void setCategorizedPayeeList(List<CategorizedPayeeListDTO> categorizedPayeeList) {
	this.categorizedPayeeList = categorizedPayeeList;
    }

    public List<CategorizedPayeeListDTO> getUrgentPayeeList() {
	return urgentPayeeList;
    }

    public void setUrgentPayeeList(List<CategorizedPayeeListDTO> urgentPayeeList) {
	this.urgentPayeeList = urgentPayeeList;
    }

}
