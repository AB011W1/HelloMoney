package com.barclays.bmg.operation.request.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;

public class GetOwnCreditCardAccountsOperationRequest extends RequestContext {

    List<CategorizedPayeeListDTO> categorizedPayeeList;

    public List<CategorizedPayeeListDTO> getCategorizedPayeeList() {
	return categorizedPayeeList;
    }

    public void setCategorizedPayeeList(List<CategorizedPayeeListDTO> categorizedPayeeList) {
	this.categorizedPayeeList = categorizedPayeeList;
    }

}
