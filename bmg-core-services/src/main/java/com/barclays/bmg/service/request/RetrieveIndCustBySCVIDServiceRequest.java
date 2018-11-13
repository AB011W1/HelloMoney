package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerDTO;

public class RetrieveIndCustBySCVIDServiceRequest extends RequestContext {

    private CustomerDTO customer;

    public CustomerDTO getCustomer() {
	return customer;
    }

    public void setCustomer(CustomerDTO customer) {
	this.customer = customer;
    }

}
