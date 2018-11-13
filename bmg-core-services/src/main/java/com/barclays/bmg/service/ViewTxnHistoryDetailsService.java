package com.barclays.bmg.service;

import com.barclays.bmg.service.request.ViewTxnHistoryDetailsServiceRequest;
import com.barclays.bmg.service.response.ViewTxnHistoryDetailsServiceResponse;

public interface ViewTxnHistoryDetailsService {

    ViewTxnHistoryDetailsServiceResponse viewBillPaymentDetails(ViewTxnHistoryDetailsServiceRequest viewBillPaymentDetailsServiceRequest);

}
