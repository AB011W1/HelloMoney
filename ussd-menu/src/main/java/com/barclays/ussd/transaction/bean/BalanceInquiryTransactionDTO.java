package com.barclays.ussd.transaction.bean;

import java.util.Map;

public class BalanceInquiryTransactionDTO
{
	private Map<String, String> accountListMap;

	public Map<String, String> getAccountListMap() {
		return accountListMap;
	}

	public void setAccountListMap(Map<String, String> accountListMap) {
		this.accountListMap = accountListMap;
	}

}
