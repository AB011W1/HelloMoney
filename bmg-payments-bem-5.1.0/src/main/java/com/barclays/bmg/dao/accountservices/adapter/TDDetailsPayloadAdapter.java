package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.Branch.Branch;
import com.barclays.bem.RetrieveTimeDepositDetails.TimeDepositInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.service.accountdetails.request.TDDetailsServiceRequest;

public class TDDetailsPayloadAdapter {

    public TimeDepositInfo adaptPayLoad(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	TDDetailsServiceRequest tdDetailsServiceRequest = (TDDetailsServiceRequest) args[0];
	TimeDepositInfo requestBody = new TimeDepositInfo();
	TdAccountDTO tdAccountDTO = tdDetailsServiceRequest.getCoreTDAcctDTO();
	requestBody.setTimeDepositAccountNumber(tdAccountDTO.getAccountNumber());
	requestBody.setDepositNumber(tdAccountDTO.getDepositDTO().getDepositNumber());
	requestBody.setOriginalDepositNumber(tdAccountDTO.getDepositDTO().getOriginalDepositNumber());
	Branch branch = new Branch();
	branch.setBranchCode(tdAccountDTO.getBranchCode());
	requestBody.setBranch(branch);
	return requestBody;
    }
}