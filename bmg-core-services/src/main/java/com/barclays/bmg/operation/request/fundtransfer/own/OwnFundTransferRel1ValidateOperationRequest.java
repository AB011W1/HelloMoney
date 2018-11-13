package com.barclays.bmg.operation.request.fundtransfer.own;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class OwnFundTransferRel1ValidateOperationRequest extends RequestContext {

    private CustomerAccountDTO srcAcct;
    private CustomerAccountDTO destAcct;

    public CustomerAccountDTO getSrcAcct() {
	return srcAcct;
    }

    public void setSrcAcct(CustomerAccountDTO srcAcct) {
	this.srcAcct = srcAcct;
    }

    public CustomerAccountDTO getDestAcct() {
	return destAcct;
    }

    public void setDestAcct(CustomerAccountDTO destAcct) {
	this.destAcct = destAcct;
    }

}
