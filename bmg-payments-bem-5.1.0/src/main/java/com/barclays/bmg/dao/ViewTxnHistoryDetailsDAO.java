package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.ViewTxnHistoryDetailsServiceRequest;
import com.barclays.bmg.service.response.ViewTxnHistoryDetailsServiceResponse;

public interface ViewTxnHistoryDetailsDAO {

	ViewTxnHistoryDetailsServiceResponse viewBillPaymentDetails(ViewTxnHistoryDetailsServiceRequest viewBillPaymentDetailsServiceRequest);
}
