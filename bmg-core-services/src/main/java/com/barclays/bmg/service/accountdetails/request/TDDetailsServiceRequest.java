package com.barclays.bmg.service.accountdetails.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.TdAccountDTO;

public class TDDetailsServiceRequest extends RequestContext {

    private TdAccountDTO coreTDAcctDTO;

    public TdAccountDTO getCoreTDAcctDTO() {
	return coreTDAcctDTO;
    }

    public void setCoreTDAcctDTO(TdAccountDTO coreTDAcctDTO) {
	this.coreTDAcctDTO = coreTDAcctDTO;
    }

}
