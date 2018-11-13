package com.barclays.ussd.bmg.creditcard.unbilled.transaction;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardUnbilledTranPayData {
    @JsonProperty
    private CreditCardDetails crdDetls;

    public CreditCardDetails getCrdDetls() {
	return crdDetls;
    }

    public void setCrdDetls(CreditCardDetails crdDetls) {
	this.crdDetls = crdDetls;
    }

}
