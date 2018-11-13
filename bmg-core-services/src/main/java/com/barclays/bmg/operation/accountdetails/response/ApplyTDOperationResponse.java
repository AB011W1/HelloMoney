package com.barclays.bmg.operation.accountdetails.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class ApplyTDOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1695401155047913117L;
    private List<? extends CustomerAccountDTO> sourceAccList;

    public List<? extends CustomerAccountDTO> getSourceAccList() {
	return sourceAccList;
    }

    public void setSourceAccList(List<? extends CustomerAccountDTO> sourceAccList) {
	this.sourceAccList = sourceAccList;
    }

}
