package com.barclays.bmg.operation.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TdAccountDTO;

public class TDAccountDetailsOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1695401155047913117L;

    private TdAccountDTO tdAccountDTO;
    private String priMatInstruction;
    private String intMatInstruction;

    public TdAccountDTO getTdAccountDTO() {
	return tdAccountDTO;
    }

    public void setTdAccountDTO(TdAccountDTO tdAccountDTO) {
	this.tdAccountDTO = tdAccountDTO;
    }

    public String getPriMatInstruction() {
	return priMatInstruction;
    }

    public void setPriMatInstruction(String priMatInstruction) {
	this.priMatInstruction = priMatInstruction;
    }

    public String getIntMatInstruction() {
	return intMatInstruction;
    }

    public void setIntMatInstruction(String intMatInstruction) {
	this.intMatInstruction = intMatInstruction;
    }

}
