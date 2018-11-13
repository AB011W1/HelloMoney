package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class RetreiveBeneficiaryDetailsServiceRequest extends RequestContext {
    private String payeeId;
    private String payeeType;

    private CustomerAccountDTO destAcct;

    // destAcct required for Individual BENE details
    public String getPayeeId() {
	return payeeId;
    }

    public void setPayeeId(String payeeId) {
	this.payeeId = payeeId;
    }

    public String getPayeeType() {
	return payeeType;
    }

    public void setPayeeType(String payeeType) {
	this.payeeType = payeeType;
    }

    public CustomerAccountDTO getDestAcct() {
	return destAcct;
    }

    public void setDestAcct(CustomerAccountDTO destAcct) {
	this.destAcct = destAcct;
    }
}
