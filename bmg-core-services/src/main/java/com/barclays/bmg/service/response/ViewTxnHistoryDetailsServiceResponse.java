package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillPaymentHistory;

public class ViewTxnHistoryDetailsServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -8879597447975211942L;
    private BillPaymentHistory billPaymentHistory;

    public BillPaymentHistory getBillPaymentHistory() {
	return billPaymentHistory;
    }

    public void setBillPaymentHistory(BillPaymentHistory billPaymentHistory) {
	this.billPaymentHistory = billPaymentHistory;
    }

}
