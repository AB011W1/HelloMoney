package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class GetSelectedAccountOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -4421019984525246485L;

    private CustomerAccountDTO selectedAcct;

    public CustomerAccountDTO getSelectedAcct() {
	return selectedAcct;
    }

    public void setSelectedAcct(CustomerAccountDTO selectedAcct) {
	this.selectedAcct = selectedAcct;
    }

}
