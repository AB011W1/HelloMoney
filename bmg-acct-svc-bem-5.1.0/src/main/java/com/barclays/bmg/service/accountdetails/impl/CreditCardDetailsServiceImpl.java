package com.barclays.bmg.service.accountdetails.impl;

import com.barclays.bmg.dao.accountdetails.CreditCardDetailsDAO;
import com.barclays.bmg.service.accountdetails.CreditCardDetailsService;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.request.CreditCardStatementDatesServiceRequest;
import com.barclays.bmg.service.accountdetails.request.CreditCardUnbilledTransServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardAccountDetailsServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CreditCardStatementDatesServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CreditCardTransActivityServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CreditCardUnbilledTransServiceResponse;

public class CreditCardDetailsServiceImpl implements CreditCardDetailsService {

    CreditCardDetailsDAO creditCardDetailsDAO;

    public CreditCardAccountDetailsServiceResponse retrieveCreditCardAccountDetails(CreditCardAccountDetailsServiceRequest request) {

	return creditCardDetailsDAO.retrieveCreditCardAccountDetails(request);

    }

    public CreditCardTransActivityServiceResponse retrieveCreditCardAccountActivity(CreditCardAccountActivityServiceRequest request) {

	return creditCardDetailsDAO.retrieveCreditCardTransactionActivity(request);
    }

    public CreditCardUnbilledTransServiceResponse retrieveCreditCardUnbilledTrans(CreditCardUnbilledTransServiceRequest request) {

	return creditCardDetailsDAO.retrieveCreditCardUnbilledTrans(request);
    }

    public CreditCardStatementDatesServiceResponse retrieveCreditCardStatementDates(CreditCardStatementDatesServiceRequest request) {
	return creditCardDetailsDAO.retrieveCreditCardStatementDates(request);
    }

    public void setCreditCardDetailsDAO(CreditCardDetailsDAO creditCardDetailsDAO) {
	this.creditCardDetailsDAO = creditCardDetailsDAO;
    }

}
