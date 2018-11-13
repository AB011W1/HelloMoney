package com.barclays.bmg.operation.response.billpayment;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;

public class RetreivePayeeListOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -8530256437753445579L;

    List<CategorizedPayeeListDTO> categorizedPayeeList;
    private String payGrp;

    public List<CategorizedPayeeListDTO> getCategorizedPayeeList() {
	return categorizedPayeeList;
    }

    public void setCategorizedPayeeList(List<CategorizedPayeeListDTO> categorizedPayeeList) {
	this.categorizedPayeeList = categorizedPayeeList;
    }

    public String getPayGrp() {
	return payGrp;
    }

    public void setPayGrp(String payGrp) {
	this.payGrp = payGrp;
    }

}
