package com.barclays.bmg.service;

import com.barclays.bmg.service.request.InqueryBillServiceRequest;
import com.barclays.bmg.service.response.InqueryBillServiceResponse;

public interface InqueryBillService {

    public InqueryBillServiceResponse inqueryBill(InqueryBillServiceRequest request);
}
