package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.MakeCreditCardPayment.MakeCreditCardPaymentResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.operation.request.RetailTxnCheckHeaderReq;
import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public class CreditCardPayRespAdptOperation extends BMBRetailTxnRespAdptOperation {

    public PayBillServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	PayBillServiceResponse payBillServiceResponse = new PayBillServiceResponse();
	MakeCreditCardPaymentResponse bemResponse = (MakeCreditCardPaymentResponse) obj;
	// Report Problem
	RetailTxnCheckHeaderReq retailTxnCheckHeaderReq = new RetailTxnCheckHeaderReq();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	PayBillServiceRequest payBillRequest = (PayBillServiceRequest) args[0];

	retailTxnCheckHeaderReq.setFrmAcctNo(payBillRequest.getFromAccount().getAccountNumber());
	retailTxnCheckHeaderReq.setFrmAcctPrdCode(payBillRequest.getFromAccount().getProductCode());

	if (checkResponseHeader(workContext, bemResponse.getResponseHeader(), retailTxnCheckHeaderReq)) {
	    payBillServiceResponse.setSuccess(true);
	    if (bemResponse.getCreditCardPaymentResponseStatus().getSourceAccountAvailableBalance() != null) {
		payBillServiceResponse.setAvailBalance(BigDecimal.valueOf(bemResponse.getCreditCardPaymentResponseStatus()
			.getSourceAccountAvailableBalance()));
	    }
	    payBillServiceResponse.setTxnRefNo(bemResponse.getCreditCardPaymentResponseStatus().getTransactionResponseStatus()
		    .getTransactionReferenceNumber());
	    if (bemResponse.getCreditCardPaymentResponseStatus().getTransactionResponseStatus().getTransactionDateTime() != null) {
		payBillServiceResponse.setTxnTime(bemResponse.getCreditCardPaymentResponseStatus().getTransactionResponseStatus()
			.getTransactionDateTime().getTime());
	    }
	} else {
	    payBillServiceResponse.setSuccess(false);
	}

	String resCdeStatus = bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode().toString();

	if (StringUtils.isNotEmpty(resCdeStatus) && resCdeStatus.equals(ResponseCodeConstants.SUCCESS_RES_CODE)) {
	    payBillServiceResponse.setTxnMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
	} else if (StringUtils.isNotEmpty(resCdeStatus) && resCdeStatus.equals(ResponseCodeConstants.SUBMITTED_TXN_RES_CODE)) {
	    payBillServiceResponse.setTxnMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
	}

	return payBillServiceResponse;
    }

}
