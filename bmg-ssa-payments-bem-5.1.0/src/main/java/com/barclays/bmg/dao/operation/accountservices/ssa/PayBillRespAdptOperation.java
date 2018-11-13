package com.barclays.bmg.dao.operation.accountservices.ssa;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.SSAMakeBillPayment.SSAMakeBillPaymentResponse;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public class PayBillRespAdptOperation extends AbstractResAdptOperation {

    public PayBillServiceResponse adaptResponse(WorkContext workContext, Object obj) {
	PayBillServiceResponse payBillServiceResponse = new PayBillServiceResponse();
	SSAMakeBillPaymentResponse bemResponse = (SSAMakeBillPaymentResponse) obj;
	checkRespHeader(bemResponse.getResponseHeader(), payBillServiceResponse);

	if (payBillServiceResponse.isSuccess()) {
	    if (bemResponse.getBillPaymentResponseStatus().getSourceAccountAvailableBalance() != null) {
		payBillServiceResponse.setAvailBalance(BigDecimal.valueOf(bemResponse.getBillPaymentResponseStatus()
			.getSourceAccountAvailableBalance()));
	    }
	    payBillServiceResponse.setTxnRefNo(bemResponse.getBillPaymentResponseStatus().getTransactionResponseStatus()
		    .getTransactionReferenceNumber());

	    if (bemResponse.getBillPaymentResponseStatus().getTransactionResponseStatus().getTransactionDateTime() != null) {
		payBillServiceResponse.setTxnTime(bemResponse.getBillPaymentResponseStatus().getTransactionResponseStatus().getTransactionDateTime()
			.getTime());
	    }

	    if (bemResponse.getBillPaymentResponseStatus() != null && bemResponse.getBillPaymentResponseStatus().getBillPaymentDetails() != null) {
		payBillServiceResponse.setPymntRefNo(bemResponse.getBillPaymentResponseStatus().getBillPaymentDetails().getPaymentReferenceNumber());
		payBillServiceResponse.setVoucherNo(bemResponse.getBillPaymentResponseStatus().getBillPaymentDetails().getVoucherNumber());
		payBillServiceResponse.setRcptNo(bemResponse.getBillPaymentResponseStatus().getBillPaymentDetails().getReceiptNumber());
		payBillServiceResponse.setTokenNo(bemResponse.getBillPaymentResponseStatus().getBillPaymentDetails().getTokenNumber());
	    }

	    String resCdeStatus = bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode();

	    if (StringUtils.isNotEmpty(resCdeStatus) && resCdeStatus.equals(ResponseCodeConstants.SUCCESS_RES_CODE)) {
		payBillServiceResponse.setTxnMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
	    } else if (StringUtils.isNotEmpty(resCdeStatus) && resCdeStatus.equals(ResponseCodeConstants.SUBMITTED_TXN_RES_CODE)) {
		payBillServiceResponse.setTxnMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
	    }

	}
	return payBillServiceResponse;
    }

    private void checkRespHeader(BEMResHeader resHeader, ResponseContext response) {
	String resCode = resHeader.getServiceResStatus().getServiceResCode();
	String serviceId = resHeader.getServiceContext().getServiceID();
	if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {
	    if (resHeader.getErrorList() != null && resHeader.getErrorList().length > 0) {
		for (com.barclays.bem.BEMServiceHeader.Error error : resHeader.getErrorList()) {
		    // To handle InProgress transaction
		    if (ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT.equals(serviceId)) {
			BMBDataAccessException dae = new BMBDataAccessException(error.getPPErrorCode(), error.getPPErrorDesc(), resHeader
				.getServiceContext().getConsumerUniqueRefNo());
			// LOGGER.error("Error for SERVICE_MAKE_BILL_PAYMENT_FOR_ALL", dae);
			throw dae;

		    } else if (ErrorCodeConstant.VAS_INVALID_ACCT_NO.equals(error.getErrorCode())) {
			response.setSuccess(false);
			response.setResCde(BillPaymentResponseCodeConstants.VAS_INVALID_ACCT_NO);
		    }
		}
	    }
	}
	if (response.isSuccess()) {
	    response.setSuccess(super.checkResponseHeader(resHeader));
	}
    }
}
