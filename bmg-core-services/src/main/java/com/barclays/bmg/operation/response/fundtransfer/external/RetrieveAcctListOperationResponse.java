package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class RetrieveAcctListOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -4724069504566771633L;

    private List<? extends CustomerAccountDTO> acctList;

    public List<? extends CustomerAccountDTO> getAcctList() {
	return acctList;
    }

    public void setAcctList(List<? extends CustomerAccountDTO> acctList) {
	this.acctList = acctList;
    }

}
