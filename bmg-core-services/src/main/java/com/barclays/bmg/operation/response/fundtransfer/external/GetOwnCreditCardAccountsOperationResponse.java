package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;

public class GetOwnCreditCardAccountsOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -6992726615487696834L;

    List<CategorizedPayeeListDTO> categorizedPayeeList;

    public List<CategorizedPayeeListDTO> getCategorizedPayeeList() {
	return categorizedPayeeList;
    }

    public void setCategorizedPayeeList(List<CategorizedPayeeListDTO> categorizedPayeeList) {
	this.categorizedPayeeList = categorizedPayeeList;
    }

}
