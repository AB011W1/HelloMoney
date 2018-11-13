package com.barclays.bmg.service.impl;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.dao.CreditCardPaymentDAO;
import com.barclays.bmg.dao.PayBillDAO;
import com.barclays.bmg.service.PayBillService;
import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.service.response.PayBillServiceResponse;
import com.barclays.bmg.service.sessionactivity.annotation.SessionActivitySupport;

public class PayBillServiceImpl implements PayBillService {

    private PayBillDAO payBillDAO;
    private CreditCardPaymentDAO creditCardPaymentDAO;

    @Override
    @SessionActivitySupport(activityType = "billPayment")
    public PayBillServiceResponse payBill(PayBillServiceRequest request) {

	PayBillServiceResponse response = null;
	String billPayTxnTyp = request.getBillPayTxnTyp();
	if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(billPayTxnTyp) || BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(billPayTxnTyp)
		|| BillPaymentConstants.PAYEE_TYPE_MOBILE_WALLET.equals(billPayTxnTyp)
		|| BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT_ONE_TIME.equals(billPayTxnTyp)
		|| BillPaymentConstants.KITS_PAY_TO_ACCOUNT.equals(billPayTxnTyp)
		|| BillPaymentConstants.KITS_PAY_TO_PHONE.equals(billPayTxnTyp)) {
	    response = payBillDAO.payBill(request);
	} else if (BillPaymentConstants.PAYEE_TYPE_BARCLAY_CARD.equals(billPayTxnTyp)
		|| BillPaymentConstants.PAYEE_TYPE_CREDIT_CARD_PAYMENT.equals(billPayTxnTyp)) {
	    response = makeCreditCardPayment(request);
	}

	return response;
    }

    public PayBillDAO getPayBillDAO() {
	return payBillDAO;
    }

    public void setPayBillDAO(PayBillDAO payBillDAO) {
	this.payBillDAO = payBillDAO;
    }

    public CreditCardPaymentDAO getCreditCardPaymentDAO() {
	return creditCardPaymentDAO;
    }

    public void setCreditCardPaymentDAO(CreditCardPaymentDAO creditCardPaymentDAO) {
	this.creditCardPaymentDAO = creditCardPaymentDAO;
    }

    private PayBillServiceResponse makeCreditCardPayment(PayBillServiceRequest request) {
	return creditCardPaymentDAO.makeCardPayment(request);
    }

}
