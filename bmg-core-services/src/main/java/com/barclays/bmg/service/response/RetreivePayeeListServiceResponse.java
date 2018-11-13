package com.barclays.bmg.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class RetreivePayeeListServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -1699114389201116017L;
    List<BeneficiaryDTO> payeeList;

    public List<BeneficiaryDTO> getPayeeList() {
	return payeeList;
    }

    public void setPayeeList(List<BeneficiaryDTO> payeeList) {
	this.payeeList = payeeList;
    }
}
