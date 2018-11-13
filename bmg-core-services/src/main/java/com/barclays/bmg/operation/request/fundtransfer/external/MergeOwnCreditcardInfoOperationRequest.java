package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class MergeOwnCreditcardInfoOperationRequest extends RequestContext {

    private CustomerAccountDTO customerAccountDTO;

    public CustomerAccountDTO getCustomerAccountDTO() {
	return customerAccountDTO;
    }

    public void setCustomerAccountDTO(CustomerAccountDTO customerAccountDTO) {
	this.customerAccountDTO = customerAccountDTO;
    }

}
