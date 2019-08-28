package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.AccountTransactionSearchInfo.AccountTransactionSearchInfo;
import com.barclays.bem.BEMBaseDataTypes.TransactionSearchTypeCode;
import com.barclays.bem.Branch.Branch;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accounts.request.RetrevCASAAcctTranActivityServiceRequest;

public class RetrevCASAAcctTranActivityPayloadAdapter {
	public AccountTransactionSearchInfo adaptPayLoad(WorkContext workContext) {
		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		AccountTransactionSearchInfo accountTransactionSearchInfo = new AccountTransactionSearchInfo();

		RetrevCASAAcctTranActivityServiceRequest request = (RetrevCASAAcctTranActivityServiceRequest) args[0];
		accountTransactionSearchInfo.setFilterCriteria("Y");
		accountTransactionSearchInfo.setActionCode(request.getActionCode());
		accountTransactionSearchInfo.setTokenNumber(request.getUserID());
		accountTransactionSearchInfo.setAccountNumber(request.getAccountNumber().trim());
		Branch branch=new Branch();
		branch.setBranchCode(request.getBranchCode());
		accountTransactionSearchInfo.setBranch(branch);
		TransactionSearchTypeCode transactionSearchTypeCode=null;
		if(request.getTransactionStatus()!=null)

		transactionSearchTypeCode = new TransactionSearchTypeCode(request.getTransactionStatus()){};

		accountTransactionSearchInfo.setTransactionSearchTypeCode(transactionSearchTypeCode);
		accountTransactionSearchInfo.setTransactionRefNumber(request.getTrxReferenceNumber());

		return accountTransactionSearchInfo;
	}
}
