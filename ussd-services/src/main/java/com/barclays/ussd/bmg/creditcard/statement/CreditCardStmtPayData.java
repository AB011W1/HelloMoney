package com.barclays.ussd.bmg.creditcard.statement;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardStmtPayData {
    @JsonProperty
    private List<CreditCardStatement> creditCardStmtBalanceInfoJSONModelList;

    public List<CreditCardStatement> getCreditCardStmtBalanceInfoJSONModelList() {
	return creditCardStmtBalanceInfoJSONModelList;
    }

    public void setCreditCardStmtBalanceInfoJSONModelList(List<CreditCardStatement> creditCardStmtBalanceInfoJSONModelList) {
	this.creditCardStmtBalanceInfoJSONModelList = creditCardStmtBalanceInfoJSONModelList;
    }

}
