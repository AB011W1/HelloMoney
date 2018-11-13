package com.barclays.ussd.utils.jsonparsers.bean.login;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveindividualCustCardListPayData {


    @JsonProperty
    List<IndividualCustDebitCard> debitCardList;



	public List<IndividualCustDebitCard> getDebitCardList() {
		return debitCardList;
	}

	public void setDebitCardList(List<IndividualCustDebitCard> debitCardList) {
		this.debitCardList = debitCardList;
	}



}
