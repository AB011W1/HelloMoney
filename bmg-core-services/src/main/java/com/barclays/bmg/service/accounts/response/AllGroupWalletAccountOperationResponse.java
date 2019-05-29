package com.barclays.bmg.service.accounts.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.operation.accountdetails.response.AccountOps;

public class AllGroupWalletAccountOperationResponse extends ResponseContext {
	List<AccountOps>NonPersonalCustAcctList;

	public List<AccountOps> getNonPersonalCustAcctList() {
		return NonPersonalCustAcctList;
	}

	public void setNonPersonalCustAcctList(List<AccountOps> nonPersonalCustAcctList) {
		NonPersonalCustAcctList = nonPersonalCustAcctList;
	}
}
