package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;

public interface BillerListDAO {

	public BillerServiceResponse getAllBillerList(BillerServiceRequest request);
	public BillerServiceResponse getBillPaymentsBillerList(BillerServiceRequest request);
	public BillerServiceResponse getBillerForBillerId(BillerServiceRequest request);
	public BillerServiceResponse getActionCodeForBillerId(BillerServiceRequest request);

}
