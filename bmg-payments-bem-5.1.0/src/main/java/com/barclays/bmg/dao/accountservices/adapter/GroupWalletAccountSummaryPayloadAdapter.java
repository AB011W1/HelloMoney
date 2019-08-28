package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveAcctTransactionHistory.SearchCriteria;
import com.barclays.bem.RetrieveNonPersonalCustAccountList.AccountRetrievalCriteria;
import com.barclays.bem.RetrieveNonPersonalCustAccountList.NonPersonalCustomerInfoType;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accounts.request.AllGroupWalletAccountServiceRequest;

public class GroupWalletAccountSummaryPayloadAdapter {
	public AccountRetrievalCriteria adaptPayLoad(WorkContext workContext) {
		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		AccountRetrievalCriteria accountRetrievalCriteria = new AccountRetrievalCriteria();

		AllGroupWalletAccountServiceRequest request = (AllGroupWalletAccountServiceRequest) args[0];

		NonPersonalCustomerInfoType nonPersonalCustomerInfoType=new NonPersonalCustomerInfoType();
		nonPersonalCustomerInfoType.setCustomerNumber(request.getCustomerID());
		nonPersonalCustomerInfoType.setAccountType(request.getCustomerType());

		SearchCriteria searchCriteria=new SearchCriteria();
		searchCriteria.setKey(request.getActionCode());
		searchCriteria.setValue(request.getActionCodeValue());

		SearchCriteria[] searchCriterias=new SearchCriteria[]{searchCriteria};

		accountRetrievalCriteria.setNonPersonalCustomerInfo(nonPersonalCustomerInfoType);
		accountRetrievalCriteria.setSearchCriteria(searchCriterias);

		return accountRetrievalCriteria;
	}
}
