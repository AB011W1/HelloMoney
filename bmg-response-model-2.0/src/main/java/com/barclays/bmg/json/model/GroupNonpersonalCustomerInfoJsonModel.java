package com.barclays.bmg.json.model;

import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.operation.accountdetails.response.AccountOps;

@SuppressWarnings("serial")
public class GroupNonpersonalCustomerInfoJsonModel extends BMBPayloadData {
	List<AccountOps>NonPersonalCustAcctList;

	public List<AccountOps> getNonPersonalCustAcctList() {
		return NonPersonalCustAcctList;
	}

	public void setNonPersonalCustAcctList(List<AccountOps> nonPersonalCustAcctList) {
		NonPersonalCustAcctList = nonPersonalCustAcctList;
	}
}
