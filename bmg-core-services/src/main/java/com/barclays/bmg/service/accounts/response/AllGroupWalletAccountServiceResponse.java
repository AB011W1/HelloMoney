package com.barclays.bmg.service.accounts.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.operation.accountdetails.response.AccountSer;

public class AllGroupWalletAccountServiceResponse extends ResponseContext {
	List<AccountSer>nonPersonalCustAcctList;

	public List<AccountSer> getNonPersonalCustAcctList() {
		return nonPersonalCustAcctList;
	}

	public void setNonPersonalCustAcctList(List<AccountSer> nonPersonalCustAcctList) {
		this.nonPersonalCustAcctList = nonPersonalCustAcctList;
	}

}
