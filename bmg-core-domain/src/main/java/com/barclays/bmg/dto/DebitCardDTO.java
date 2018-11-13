package com.barclays.bmg.dto;

import java.util.Date;

public class DebitCardDTO extends BaseDomainDTO {

	/**
	 *
	 */
	private static final long serialVersionUID = 6296923585217531074L;
	private String cardNumber;
	 private Date cardExpiryDate;
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
