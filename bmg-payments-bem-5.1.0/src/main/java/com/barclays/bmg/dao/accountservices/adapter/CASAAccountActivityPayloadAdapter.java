package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.AccountTransactionSearchInfo.AccountTransactionSearchInfo;
import com.barclays.bem.Branch.Branch;
import com.barclays.bem.PageInformationRequest.PageInformationRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.utils.ConvertUtils;

/*
 * Reference
 * com.barclays.mcfe.ssc.retail.core.service.md.ssa.RetailCASAAccountServiceMDImpl
 */
public class CASAAccountActivityPayloadAdapter {

    public AccountTransactionSearchInfo adaptPayLoad(WorkContext workContext) {

	AccountTransactionSearchInfo requestBody = new AccountTransactionSearchInfo();

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();

	CASAAccountActivityServiceRequest casaAccountActivityServiceRequest = (CASAAccountActivityServiceRequest) args[0];

	if (casaAccountActivityServiceRequest.isStatementTrxFlag()) {

	} else {
	    // TransactoinActivitySearch;
	    if (casaAccountActivityServiceRequest.getStartDate() != null) {
		requestBody.setStartDate(ConvertUtils.getCalendarByDate(casaAccountActivityServiceRequest.getStartDate()));
	    }
	    if (casaAccountActivityServiceRequest.getEndDate() != null) {
		requestBody.setEndDate(ConvertUtils.getCalendarByDate(casaAccountActivityServiceRequest.getEndDate()));
	    }

	}

	requestBody.setAccountNumber(casaAccountActivityServiceRequest.getAccountNo());

	Branch branch = new Branch();
	branch.setBranchCode(casaAccountActivityServiceRequest.getBranchCode());
	requestBody.setBranch(branch);

	requestBody.setTransactionTypeCode("all");

	PageInformationRequest pageInfo = new PageInformationRequest();
	pageInfo.setPageNumber(casaAccountActivityServiceRequest.getPageNo());
	requestBody.setPageInfo(pageInfo);

	return requestBody;

    }
}
