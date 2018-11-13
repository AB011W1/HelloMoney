package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.ViewBillPaymentDetails.ViewBillPaymentInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.ViewTxnHistoryDetailsServiceRequest;

public class ViewBillPaymentDetailsPayloadAdapter {

	public ViewBillPaymentInfo adaptPayload(WorkContext workContext){

		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		ViewTxnHistoryDetailsServiceRequest viewBillPaymentDetailsServiceRequest=
									(ViewTxnHistoryDetailsServiceRequest)args[0];


        ViewBillPaymentInfo viewBillPaymentInfo = new ViewBillPaymentInfo();
        viewBillPaymentInfo.setTransactionReferenceNumber(viewBillPaymentDetailsServiceRequest.getTransactionRefNo());
		return viewBillPaymentInfo;
	}

}
