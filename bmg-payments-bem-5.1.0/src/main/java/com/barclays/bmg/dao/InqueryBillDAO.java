package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.InqueryBillServiceRequest;
import com.barclays.bmg.service.response.InqueryBillServiceResponse;

public interface InqueryBillDAO {

	public InqueryBillServiceResponse inqueryBill(InqueryBillServiceRequest request);
}
