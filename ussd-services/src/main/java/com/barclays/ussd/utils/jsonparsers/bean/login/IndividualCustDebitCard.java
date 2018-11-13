package com.barclays.ussd.utils.jsonparsers.bean.login;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualCustDebitCard {

	@JsonProperty
	private String cardNumber;

	@JsonProperty
	private Date cardExpiryDate;

	@JsonProperty
	private String cardLifecycleStatusCode;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(Date cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getCardLifecycleStatusCode() {
		return cardLifecycleStatusCode;
	}

	public void setCardLifecycleStatusCode(String cardLifecycleStatusCode) {
		this.cardLifecycleStatusCode = cardLifecycleStatusCode;
	}

}
