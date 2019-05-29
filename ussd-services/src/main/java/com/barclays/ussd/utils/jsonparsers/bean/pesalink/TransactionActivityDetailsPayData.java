package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionActivityDetailsPayData {
	@JsonProperty
	List <CASAccountTransactionList>casaAccountTransactionList;

	public List<CASAccountTransactionList> getCasaAccountTransactionList() {
		return casaAccountTransactionList;
	}

	public void setCasaAccountTransactionList(
			List<CASAccountTransactionList> casaAccountTransactionList) {
		this.casaAccountTransactionList = casaAccountTransactionList;
	}
}
