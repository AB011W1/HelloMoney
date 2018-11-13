package com.barclays.bmg.operation.request.fundtransfer.own;

import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class ValidateAccountListOperationRequest extends RequestContext {

    private List<? extends CustomerAccountDTO> srcAcctLst;
    private List<? extends CustomerAccountDTO> destAcctLst;

    public List<? extends CustomerAccountDTO> getSrcAcctLst() {
	return srcAcctLst;
    }

    public void setSrcAcctLst(List<? extends CustomerAccountDTO> srcAcctLst) {
	this.srcAcctLst = srcAcctLst;
    }

    public List<? extends CustomerAccountDTO> getDestAcctLst() {
	return destAcctLst;
    }

    public void setDestAcctLst(List<? extends CustomerAccountDTO> destAcctLst) {
	this.destAcctLst = destAcctLst;
    }

}
