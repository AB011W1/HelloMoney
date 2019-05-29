package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.MakeBillPayment.MakeBillPaymentResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public class MakePayBillRespAdptOperation extends AbstractResAdptOperationAcct {

    public PayBillServiceResponse adaptResponse(WorkContext workContext, Object obj) {

		PayBillServiceResponse payBillServiceResponse = new PayBillServiceResponse();
		MakeBillPaymentResponse bemResponse = (MakeBillPaymentResponse) obj;
		payBillServiceResponse.setSuccess(checkResponseHeader(bemResponse.getResponseHeader()));

		if (payBillServiceResponse.isSuccess()) {
		    if (bemResponse.getBillPaymentResponseStatus().getSourceAccountAvailableBalance() != null) {
		    	payBillServiceResponse.setAvailBalance(BigDecimal.valueOf(bemResponse.getBillPaymentResponseStatus().getSourceAccountAvailableBalance()));
		    }
		    if(null != bemResponse.getBillPaymentResponseStatus()){
			    if(null != bemResponse.getBillPaymentResponseStatus().getTransactionResponseStatus()) {
			    	payBillServiceResponse.setTxnRefNo(bemResponse.getBillPaymentResponseStatus().getTransactionResponseStatus().getTransactionReferenceNumber());
			    	if(null != bemResponse.getBillPaymentResponseStatus().getTransactionResponseStatus().getTransactionDateTime()){
			    		payBillServiceResponse.setTxnTime(bemResponse.getBillPaymentResponseStatus().getTransactionResponseStatus().getTransactionDateTime().getTime());
			    	}
			    }
			    if(null != bemResponse.getBillPaymentResponseStatus().getBillPaymentDetails()){
				    payBillServiceResponse.setTokenNo(bemResponse.getBillPaymentResponseStatus().getBillPaymentDetails().getTokenNumber());
				    payBillServiceResponse.setRcptNo(bemResponse.getBillPaymentResponseStatus().getBillPaymentDetails().getReceiptNumber());
			    }
		    }
		}

		String resCdeStatus = bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode();

		if (StringUtils.isNotEmpty(resCdeStatus) && resCdeStatus.equals(ResponseCodeConstants.SUCCESS_RES_CODE)) {
		    payBillServiceResponse.setTxnMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
		} else if (StringUtils.isNotEmpty(resCdeStatus) && resCdeStatus.equals(ResponseCodeConstants.SUBMITTED_TXN_RES_CODE)) {
		    payBillServiceResponse.setTxnMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
		}

		return payBillServiceResponse;
    }

}
