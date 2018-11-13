package com.barclays.bmg.json.model.billpayment;



public class CCPayeeJSONModel extends PayeeInformationJSONModel {


	private TransactionInformationRestBean trRestBean;

	/**
	 *
	 */
	private static final long serialVersionUID = -2309242992334926598L;
	private CreditCardAccountJSONBean creditCardAccountJSONBean;


	public TransactionInformationRestBean getTrRestBean() {
		return trRestBean;
	}

	public void setTrRestBean(TransactionInformationRestBean trRestBean) {
		this.trRestBean = trRestBean;
	}

	public CreditCardAccountJSONBean getCreditCardAccountJSONBean() {
		return creditCardAccountJSONBean;
	}

	public void setCreditCardAccountJSONBean(
			CreditCardAccountJSONBean creditCardAccountJSONBean) {
		this.creditCardAccountJSONBean = creditCardAccountJSONBean;
	}



}
