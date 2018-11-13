package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public interface PayBillDAO {

	public PayBillServiceResponse payBill(PayBillServiceRequest request);
}
