package com.barclays.bmg.service;

import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public interface PayBillService {

    public PayBillServiceResponse payBill(PayBillServiceRequest request);
}
