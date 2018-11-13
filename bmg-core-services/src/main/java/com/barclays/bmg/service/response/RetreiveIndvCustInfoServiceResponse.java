package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerDTO;

public class RetreiveIndvCustInfoServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 5953301368217770847L;
    private CustomerDTO custDTO;

    public CustomerDTO getCustDTO() {
	return custDTO;
    }

    public void setCustDTO(CustomerDTO custDTO) {
	this.custDTO = custDTO;
    }
}
