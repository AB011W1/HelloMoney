package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.AccountNumber.AccountNumber;
import com.barclays.bem.Branch.Branch;
import com.barclays.bem.RetrieveCASAAcctDetails.RetrieveCASAAccountDetailsRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;

public class CASADetailsPayloadAdapter {

    public void adaptPayLoad(WorkContext workContext, RetrieveCASAAccountDetailsRequest casaDetailsRequest) {
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	CASADetailsServiceRequest casaDetailsServiceRequest = (CASADetailsServiceRequest) args[0];

	Branch branch = new Branch();
	branch.setBranchCode(casaDetailsServiceRequest.getBranchCode());
	casaDetailsRequest.setBranch(branch);

	AccountNumber acNo = new AccountNumber();
	casaDetailsRequest.setCASAAccountInfo(acNo);
	acNo.setAccountNumber(casaDetailsServiceRequest.getAccountNo());

    }
}
