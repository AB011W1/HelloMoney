package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public interface CreditCardPaymentDAO {

	public PayBillServiceResponse makeCardPayment(PayBillServiceRequest request);
}
