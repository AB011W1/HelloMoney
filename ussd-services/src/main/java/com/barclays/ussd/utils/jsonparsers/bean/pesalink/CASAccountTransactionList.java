package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CASAccountTransactionList {
	@JsonProperty
	TransactionActivity transactionActivity;

	public TransactionActivity getTransactionActivity() {
		return transactionActivity;
	}

	public void setTransactionActivity(TransactionActivity transactionActivity) {
		this.transactionActivity = transactionActivity;
	}
}
